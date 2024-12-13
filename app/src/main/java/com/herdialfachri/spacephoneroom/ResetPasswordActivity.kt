package com.herdialfachri.spacephoneroom

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.herdialfachri.spacephoneroom.repository.UserRepository

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        userRepository = UserRepository(applicationContext)

        val emailInput: EditText = findViewById(R.id.email_input)
        val newPasswordInput: EditText = findViewById(R.id.new_password_input)
        val resetButton: Button = findViewById(R.id.reset_button)

        resetButton.setOnClickListener {
            val email = emailInput.text.toString()
            val newPassword = newPasswordInput.text.toString()

            if (userRepository.resetPassword(email, newPassword)) {
                Toast.makeText(this, "Reset password berhasil", Toast.LENGTH_SHORT).show()
                finish() // Kembali ke LoginActivity setelah reset password berhasil
            } else {
                Toast.makeText(this, "Reset password gagal", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
