package com.herdialfachri.spacephoneroom.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.herdialfachri.spacephoneroom.R
import com.herdialfachri.spacephoneroom.entitiy.AppDatabase

class DetailActivity : AppCompatActivity() {
    private lateinit var detailGambar: ImageView
    private lateinit var detailFullName: TextView
    private lateinit var detailEmail: TextView
    private lateinit var detailPhone: TextView
    private lateinit var detailAddress: TextView
    private lateinit var btnEdit: Button
    private lateinit var btnDelete: Button
    private lateinit var database: AppDatabase
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        detailGambar = findViewById(R.id.detail_gambar)
        detailFullName = findViewById(R.id.detail_full_name)
        detailEmail = findViewById(R.id.detail_email)
        detailPhone = findViewById(R.id.detail_phone)
        detailAddress = findViewById(R.id.detail_address)
        btnEdit = findViewById(R.id.btn_edit)
        btnDelete = findViewById(R.id.btn_delete)

        database = AppDatabase.getInstance(applicationContext)

        // Back button
        val toolbar: Toolbar = findViewById(R.id.back_button_detail)
        toolbar.setNavigationOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val intent = intent.extras
        if (intent != null) {
            userId = intent.getInt("id")
            val user = database.userDao().get(userId)

            detailFullName.text = user.fullName
            detailEmail.text = user.email
            detailPhone.text = user.phone
            detailAddress.text = user.address
            if (user.gambar != null) {
                Glide.with(this).load(user.gambar).into(detailGambar)
            } else {
                detailGambar.setImageResource(R.drawable.default_image) // Gambar default jika null
            }

            // Ambil email pengguna yang login dari SharedPreferences
            val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
            val currentEmail = sharedPreferences.getString("email", null)

            // Periksa izin edit dan hapus
            if (user.creatorEmail == currentEmail) {
                btnEdit.setOnClickListener {
                    val editIntent = Intent(this, EditorActivity::class.java)
                    editIntent.putExtra("id", userId)
                    startActivity(editIntent)
                }

                btnDelete.setOnClickListener {
                    val builder = AlertDialog.Builder(this@DetailActivity)
                    builder.setTitle("Konfirmasi Hapus")
                    builder.setMessage("Apakah kamu yakin ingin menghapus data ini?")
                    builder.setPositiveButton("Ya") { dialog, _ ->
                        database.userDao().delete(user)
                        Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
                        val mainIntent = Intent(this, MainActivity::class.java)
                        mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(mainIntent)
                        finish()
                    }
                    builder.setNegativeButton("Tidak") { dialog, _ ->
                        dialog.dismiss()
                    }
                    val dialog = builder.create()
                    dialog.show()
                }
            } else {
                btnEdit.isEnabled = false
                btnDelete.isEnabled = false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        val user = database.userDao().get(userId)
        detailFullName.text = user.fullName
        detailEmail.text = user.email
        detailPhone.text = user.phone
        detailAddress.text = user.address
        if (user.gambar != null) {
            Glide.with(this).load(user.gambar).into(detailGambar)
        } else {
            detailGambar.setImageResource(R.drawable.default_image) // Gambar default jika null
        }
    }
}
