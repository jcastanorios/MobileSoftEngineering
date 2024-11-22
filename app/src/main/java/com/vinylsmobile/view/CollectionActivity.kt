package com.vinylsmobile.view

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import com.vinylsmobile.Application
import com.vinylsmobile.R
import com.vinylsmobile.UserRole

class CollectionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_collection)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, CollectionFragment())
                .commit()
        }

        val fab: View = findViewById(R.id.fab_collection_activity)

        val app = application as Application
        val userRole = app.getUserRole()
        if (userRole == UserRole.VISITOR) {
            fab.visibility = View.GONE
        } else {
            fab.visibility = View.VISIBLE
            fab.setOnClickListener { view ->
                showPopupMenu(view)
            }
        }
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.inflate(R.menu.menu_fab_collection_activity_options)
        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.action_create_album -> {
                    // Handle settings
                    val intent = Intent(this, CreateAlbumActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }
}