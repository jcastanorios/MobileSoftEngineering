package com.vinylsmobile.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.vinylsmobile.R
import com.vinylsmobile.ui.fragments.AlbumFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, AlbumFragment())
                .commit()
        }
    }
}