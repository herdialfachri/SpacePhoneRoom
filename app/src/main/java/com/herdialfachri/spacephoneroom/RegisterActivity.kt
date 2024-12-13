package com.herdialfachri.spacephoneroom

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.herdialfachri.spacephoneroom.repository.UserRepository

class RegisterActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        userRepository = UserRepository(applicationContext)

        val emailInput: EditText = findViewById(R.id.email_input)
        val passwordInput: EditText = findViewById(R.id.password_input)
        val registerButton: Button = findViewById(R.id.register_button)

        registerButton.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (userRepository.register(email, password) != null) {
                Toast.makeText(this, "Registrasi berhasil", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke LoginActivity setelah registrasi berhasil
            } else {
                Toast.makeText(this, "Registrasi gagal", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
