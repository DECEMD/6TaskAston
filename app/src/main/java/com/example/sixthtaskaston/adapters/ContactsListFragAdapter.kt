package com.example.sixthtaskaston.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sixthtaskaston.MainActivity
import com.example.sixthtaskaston.databinding.ContactsListItemBinding
import com.example.sixthtaskaston.fragments.ContactsFragmentDetailed
import com.example.sixthtaskaston.model.DataBaseRequests.setImage
import com.example.sixthtaskaston.model.Person
import java.util.Locale

class ContactsListFragAdapter: ListAdapter<Person, ContactsListFragAdapter.ContactsHolder>(DiffCallback()), Filterable {
    var contactsList = mutableListOf<Person>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsHolder {
        val binding =
            ContactsListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ContactsHolder(binding, parent.context as MainActivity, this)
    }

    override fun onBindViewHolder(holder: ContactsHolder, position: Int) {
        holder.bind(contactsList[position])
    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    class ContactsHolder(
        private val binding: ContactsListItemBinding,
        private val context: MainActivity,
        private val adapter: ContactsListFragAdapter,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(person: Person) = with(binding) {
            nameTextView.text = person.name
            surnameTextView.text = person.surname
            phoneTextView.text = person.phoneNumber
            setImage(person, avatarImage, progressBar, context)
            binding.root.setOnClickListener {
                runDetailedFragment(person)
            }
            binding.root.setOnLongClickListener {
                setPopUpListener(person)
                true
            }
        }

        private fun setPopUpListener(person: Person) {
            binding.root.alpha = 0.7f
            PopupMenu(context, binding.root).apply {
                menuInflater.inflate(com.example.sixthtaskaston.R.menu.pop_up_menu, this.menu)
                gravity = Gravity.END
                setOnMenuItemClickListener {
                    when (it.itemId) {
                        com.example.sixthtaskaston.R.id.details -> {
                            runDetailedFragment(person)
                        }
                        com.example.sixthtaskaston.R.id.delete -> {
                            removeItem()
                        }
                    }
                    true
                }
                show()
                setOnDismissListener {
                    binding.root.alpha = 1f
                    dismiss()
                }
            }
        }

        private fun removeItem() {
            adapter.contactsList.removeAt(adapterPosition)
            adapter.notifyItemRemoved(adapterPosition)
        }

        private fun runDetailedFragment(person: Person) {
            context.supportFragmentManager.beginTransaction().addToBackStack(null)
                .replace(com.example.sixthtaskaston.R.id.fragment_container,
                    ContactsFragmentDetailed.newInstance(person)).commit()
        }
    }

    override fun getFilter(): Filter {
        return exampleFilter
    }

    private val exampleFilter: Filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults {
            val filteredList = mutableListOf<Person>()
            if (constraint.isEmpty()) {
                filteredList.addAll(contactsList)
            } else {
                for (item in contactsList) {
                    if (item.name.lowercase(Locale.ROOT).startsWith(constraint.toString().lowercase(
                            Locale.ROOT)) ||
                        item.surname.lowercase(Locale.ROOT).startsWith(constraint.toString().lowercase(
                            Locale.ROOT))) {
                        filteredList.add(item)
                    } else continue
                }
            }
            val results = FilterResults()
            results.values = filteredList
            return results
        }

        override fun publishResults(constraint: CharSequence, results: FilterResults) {
            submitList(results.values as MutableList<Person>)
        }
    }

    fun setData(contactsList: MutableList<Person>?) {
        this.contactsList = contactsList!!
        submitList(contactsList)
    }

    fun update(person: Person) {
        val diffResult = DiffCallback()
        diffResult.areContentsTheSame(person, person)
        diffResult.areItemsTheSame(person, person)
    }
}