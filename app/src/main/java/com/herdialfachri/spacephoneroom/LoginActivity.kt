package com.herdialfachri.spacephoneroom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.herdialfachri.spacephoneroom.repository.UserRepository

class LoginActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        userRepository = UserRepository(applicationContext)

        val emailInput: EditText = findViewById(R.id.email_input)
        val passwordInput: EditText = findViewById(R.id.password_input)
        val loginButton: Button = findViewById(R.id.login_button)
        val registerButton: TextView = findViewById(R.id.register_button)
        val forgotPasswordButton: TextView = findViewById(R.id.forgot_password_button)
        val backHome: Button = findViewById(R.id.back_home)

        loginButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            val token = userRepository.login(email, password)
            if (token != null) {
                val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                sharedPreferences.edit().putString("token", token).apply()
                sharedPreferences.edit().putString("email", email).apply() // Simpan email pengguna yang login

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                emailInput.error = "Email atau kata sandi salah"
            }
        }

        registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        forgotPasswordButton.setOnClickListener {
            val intent = Intent(this, ResetPasswordActivity::class.java)
            startActivity(intent)
        }

        backHome.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}
