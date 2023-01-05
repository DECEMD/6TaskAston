package com.example.sixthtaskaston.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.sixthtaskaston.R
import com.example.sixthtaskaston.adapters.ContactsListFragAdapter
import com.example.sixthtaskaston.databinding.FragmentContactsListBinding
import com.example.sixthtaskaston.model.DataBaseRequests
import com.example.sixthtaskaston.model.DataBaseRequests.DATA_FROM_SECOND_FRAGMENT
import com.example.sixthtaskaston.model.DataBaseRequests.PERSON_DATA_BY_ID
import com.example.sixthtaskaston.model.Person
import kotlin.random.Random

class ContactsListFragment : Fragment(), SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentContactsListBinding
    private val adapter = ContactsListFragAdapter()
    private lateinit var listOfNames: List<String>
    private lateinit var listOfSurnames: List<String>
    private lateinit var person: Person
    private lateinit var menuItemSearch: MenuItem
    private val menuProvider = object : MenuProvider {
        override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
            menuInflater.inflate(R.menu.search_view_toolbar, menu)
            menuItemSearch = menu.findItem(R.id.search_action)
            val searchView = menuItemSearch.actionView as SearchView
            searchView.setOnQueryTextListener(this@ContactsListFragment)
        }

        override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
            return false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter.setData(getData())
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentContactsListBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        catchResultFromDetailedFragment()
        requireActivity().addMenuProvider(menuProvider, viewLifecycleOwner)
    }

    private fun initRecyclerView() {
        binding.apply {
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.adapter = adapter

            val dividerItemDecoration = DividerItemDecoration(activity, RecyclerView.VERTICAL)
            ResourcesCompat.getDrawable(resources, R.drawable.divider, null)?.let { drawable ->
                dividerItemDecoration.setDrawable(drawable)
            }
            recyclerView.addItemDecoration(dividerItemDecoration)
        }
    }

    private fun catchResultFromDetailedFragment() {
        parentFragmentManager.setFragmentResultListener(DATA_FROM_SECOND_FRAGMENT,
            this) { _, result ->
            run {
                person = result.getParcelable(PERSON_DATA_BY_ID)!!
                adapter.update(person)
            }
        }
    }

    override fun onQueryTextSubmit(newText: String?): Boolean {
        return false
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        adapter.filter.filter(newText)
        return false
    }

    private fun getData(): MutableList<Person> {
        listOfNames = DataBaseRequests.getNameListData(activity as Activity)
        listOfSurnames = DataBaseRequests.getSurnameListData(activity as Activity)

        val returnListOfPersons = mutableListOf<Person>()
        val random = Random(10)

        for (i in MIN_CONTACTS_ID..MAX_CONTACTS_ID) {
            returnListOfPersons.add(Person(
                i,
                listOfNames[random.nextInt(MAX_INDEX_OF_LISTS)],
                listOfSurnames[random.nextInt(MAX_INDEX_OF_LISTS)],
                "+7999${random.nextInt(9999999)}"
            ))
            when (i) {
                FIRST_DAMAGED_INDEX_FROM_PICSUM -> returnListOfPersons[i].contactId = 101
                SECOND_DAMAGED_INDEX_FROM_PICSUM -> returnListOfPersons[i].contactId = 102
                else -> {}
            }
        }
        return returnListOfPersons
    }

    companion object {
        fun newInstance() = ContactsListFragment()

        private const val MIN_CONTACTS_ID = 0
        private const val MAX_CONTACTS_ID = 100
        private const val MAX_INDEX_OF_LISTS = 11
        private const val FIRST_DAMAGED_INDEX_FROM_PICSUM = 86
        private const val SECOND_DAMAGED_INDEX_FROM_PICSUM = 97
    }
}
