package com.herdialfachri.spacephoneroom.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.herdialfachri.spacephoneroom.R

class ProfileActivity : AppCompatActivity() {

    private lateinit var username: EditText
    private lateinit var password: EditText
    private lateinit var logoutButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        username = findViewById(R.id.username)
        password = findViewById(R.id.password)
        logoutButton = findViewById(R.id.logout_button)

        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val email = sharedPreferences.getString("email", "")
        val userPassword = "********" // Menyembunyikan kata sandi

        username.setText(email)
        password.setText(userPassword)

        logoutButton.setOnClickListener {
            sharedPreferences.edit().clear().apply()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}
