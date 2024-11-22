package com.vinylsmobile.view

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.vinylsmobile.Application
import com.vinylsmobile.R
import com.vinylsmobile.UserRole

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val visitorButton: Button = findViewById(R.id.visitButton)
        val collectorButton: Button = findViewById(R.id.collectButton)

        fun setVisitorRole(userRole: UserRole) {
            val app = application as Application
            app.setUserRole(userRole)
        }

        fun goToCollectionActivity() {
            val intent = Intent(this, CollectionActivity::class.java)
            startActivity(intent)
        }

        visitorButton.setOnClickListener {
            setVisitorRole(UserRole.VISITOR)
            goToCollectionActivity()
        }
        collectorButton.setOnClickListener {
            setVisitorRole(UserRole.COLLECTOR)
            goToCollectionActivity()
        }
    }
}