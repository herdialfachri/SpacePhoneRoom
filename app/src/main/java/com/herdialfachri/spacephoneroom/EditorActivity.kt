package com.herdialfachri.spacephoneroom

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.herdialfachri.spacephoneroom.entitiy.AppDatabase
import com.herdialfachri.spacephoneroom.entitiy.User

class EditorActivity : AppCompatActivity() {
    private lateinit var fullName: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var address: EditText
    private lateinit var btnSimpan: Button
    private lateinit var btnPilihGambar: ImageView
    private lateinit var database: AppDatabase
    private var gambarPath: String? = null
    private var loginId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editor)
        fullName = findViewById(R.id.full_name)
        email = findViewById(R.id.email)
        phone = findViewById(R.id.phone)
        address = findViewById(R.id.address)
        btnSimpan = findViewById(R.id.btnSimpan)
        btnPilihGambar = findViewById(R.id.btnPilihGambar)

        database = AppDatabase.getInstance(applicationContext)

        // Ambil email pengguna yang login dari SharedPreferences
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val currentEmail = sharedPreferences.getString("email", null)

        // Back button
        val toolbar: Toolbar = findViewById(R.id.back_button_editor)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val intent = intent.extras
        if (intent != null) {
            val user = database.userDao().get(intent.getInt("id"))
            if (user.creatorEmail != currentEmail) {
                Toast.makeText(this, "Anda tidak memiliki izin untuk mengedit data ini.", Toast.LENGTH_SHORT).show()
                finish()
                return
            }
            fullName.setText(user.fullName)
            email.setText(user.email)
            phone.setText(user.phone)
            address.setText(user.address)
            gambarPath = user.gambar
            loginId = user.loginId // Dapatkan loginId dari user
            if (gambarPath != null) {
                Glide.with(this).load(gambarPath).into(btnPilihGambar)
            }
        } else {
            // Set email pengguna yang sedang login
            email.setText(currentEmail)
        }

        btnPilihGambar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        btnSimpan.setOnClickListener {
            if (fullName.text.isNotEmpty() && email.text.isNotEmpty() && phone.text.isNotEmpty()) {
                if (loginId == null) {
                    loginId = database.userLoginDao().getLoginIdByEmail(currentEmail!!)
                }
                if (intent != null) {
                    // Kode edit data
                    database.userDao().update(
                        User(
                            intent.getInt("id", 0),
                            fullName.text.toString(),
                            email.text.toString(),
                            phone.text.toString(),
                            address.text.toString(),
                            gambarPath,
                            loginId, // Sertakan loginId
                            currentEmail
                        )
                    )
                } else {
                    // Kode tambah data
                    database.userDao().insertAll(
                        User(
                            null,
                            fullName.text.toString(),
                            email.text.toString(),
                            phone.text.toString(),
                            address.text.toString(),
                            gambarPath,
                            loginId, // Sertakan loginId
                            currentEmail // Sertakan email pengguna yang sedang login sebagai creatorEmail
                        )
                    )
                }
                val mainIntent = Intent(this, MainActivity::class.java)
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(mainIntent)
                finish()
            } else {
                Toast.makeText(applicationContext, "Silahkan isi data dengan benar", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) {
            val selectedImageUri = data?.data
            gambarPath = selectedImageUri.toString()
            Glide.with(this).load(gambarPath).into(btnPilihGambar)
        }
    }
}
