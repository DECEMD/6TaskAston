package com.example.sixthtaskaston.adapters

import androidx.recyclerview.widget.DiffUtil
import com.example.sixthtaskaston.model.Person

class DiffCallback : DiffUtil.ItemCallback<Person>() {

    override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem.contactId == newItem.contactId
    }

    override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
        return oldItem == newItem
    }
}