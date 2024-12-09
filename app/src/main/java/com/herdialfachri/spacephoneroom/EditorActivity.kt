package com.herdialfachri.spacephoneroom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.herdialfachri.spacephoneroom.dao.AppDatabase
import com.herdialfachri.spacephoneroom.dao.User

class EditorActivity : AppCompatActivity() {
    private lateinit var fullName: EditText
    private lateinit var email: EditText
    private lateinit var phone: EditText
    private lateinit var address: EditText
    private lateinit var btnSimpan: Button
    private lateinit var btnPilihGambar: ImageView
    private lateinit var database: AppDatabase
    private var gambarPath: String? = null

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

        // Back button
        val toolbar: Toolbar = findViewById(R.id.back_button_editor)
        toolbar.setNavigationOnClickListener {

            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        val intent = intent.extras
        if(intent != null) {
            val user = database.userDao().get(intent.getInt("id"))
            fullName.setText(user.fullName)
            email.setText(user.email)
            phone.setText(user.phone)
            address.setText(user.address)
            gambarPath = user.gambar
            if (gambarPath != null) {
                Glide.with(this).load(gambarPath).into(btnPilihGambar)
            }
        }

        btnPilihGambar.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            startActivityForResult(intent, 1)
        }

        btnSimpan.setOnClickListener {
            if (fullName.text.isNotEmpty() && email.text.isNotEmpty() && phone.text.isNotEmpty()) {
                if(intent != null) {
                    // Kode edit data
                    database.userDao().update(
                        User(
                            intent.getInt("id", 0),
                            fullName.text.toString(),
                            email.text.toString(),
                            phone.text.toString(),
                            address.text.toString(),
                            gambarPath
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
                            gambarPath
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
