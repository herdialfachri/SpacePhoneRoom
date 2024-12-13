package com.herdialfachri.spacephoneroom

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
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
        val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.back_button_detail)
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
        }

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
                database.userDao().delete(database.userDao().get(userId))
                dialog.dismiss()
                finish() // Kembali ke activity sebelumnya
            }
            builder.setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }
            val dialog = builder.create()
            dialog.show()
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