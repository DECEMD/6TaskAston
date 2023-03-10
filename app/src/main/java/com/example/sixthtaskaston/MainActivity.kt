package com.example.sixthtaskaston

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sixthtaskaston.fragments.ContactsListFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ContactsListFragment.newInstance())
                .commit()
        }
    }
}