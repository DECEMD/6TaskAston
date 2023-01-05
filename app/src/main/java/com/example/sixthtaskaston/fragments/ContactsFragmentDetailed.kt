package com.example.sixthtaskaston.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.sixthtaskaston.databinding.FragmentContactsDetailedBinding
import com.example.sixthtaskaston.model.DataBaseRequests.DATA_FROM_SECOND_FRAGMENT
import com.example.sixthtaskaston.model.DataBaseRequests.PERSON_DATA_BY_ID
import com.example.sixthtaskaston.model.Person

class ContactsFragmentDetailed : Fragment() {
    private var person: Person? = null
    private lateinit var binding: FragmentContactsDetailedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let{
            person = it.getParcelable(CERTAIN_ITEM_DATA)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        binding = FragmentContactsDetailedBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            editTextName.setText(person?.name)
            editTextSurname.setText(person?.surname)
            editTextPhone.setText(person?.phoneNumber)

            saveDateButton.setOnClickListener {
                softCloseKeyBoard(activity as Activity)
                prepareDataToSend()
                sendDataBack()
                parentFragmentManager.popBackStack()
            }
        }
    }

    private fun sendDataBack(){
        val result = Bundle().also {
            it.putParcelable(PERSON_DATA_BY_ID, person)
        }
        parentFragmentManager.setFragmentResult(DATA_FROM_SECOND_FRAGMENT, result)
    }

    private fun prepareDataToSend(){
        person?.name = binding.editTextName.text.toString()
        person?.surname = binding.editTextSurname.text.toString()
        person?.phoneNumber = binding.editTextPhone.text.toString()
    }

    private fun softCloseKeyBoard(context: Activity) {
        val imm: InputMethodManager =
            context.getSystemService(AppCompatActivity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    companion object {
        fun newInstance(person: Person) = ContactsFragmentDetailed().apply {
                arguments = Bundle().apply {
                    putParcelable(CERTAIN_ITEM_DATA, person)
                }
            }

        const val CERTAIN_ITEM_DATA = "CERTAIN_ITEM_DATA"
    }
}