package com.vinylsmobile.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vinylsmobile.R

class CollectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AlbumFragment())
                .commit()
        }
    }
}