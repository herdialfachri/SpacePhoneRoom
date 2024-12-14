package com.herdialfachri.spacephoneroom

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.herdialfachri.spacephoneroom.adapter.UserAdapter
import com.herdialfachri.spacephoneroom.entitiy.AppDatabase
import com.herdialfachri.spacephoneroom.entitiy.User

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var adapter: UserAdapter
    private var list = mutableListOf<User>()
    private lateinit var database: AppDatabase
    private lateinit var loginButton: Button
    private lateinit var userCenter: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv_user)
        fab = findViewById(R.id.floatingbtn)
        loginButton = findViewById(R.id.login_button)
        userCenter = findViewById(R.id.user_center)

        database = AppDatabase.getInstance(applicationContext)
        adapter = UserAdapter(list)

        recyclerView.adapter = adapter

        // Konfigurasi GridLayoutManager dengan 2 kolom
        val gridLayoutManager = GridLayoutManager(applicationContext, 2)
        recyclerView.layoutManager = gridLayoutManager

        fab.setOnClickListener {
            startActivity(Intent(this, EditorActivity::class.java))
        }

        val tvMore: TextView = findViewById(R.id.tvMore)
        tvMore.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://herdialfachri.my.id/"))
            startActivity(browserIntent)
        }

        loginButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        userCenter.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        checkLoginStatus()
    }

    override fun onResume() {
        super.onResume()
        getData()
        checkLoginStatus()
    }

    private fun getData() {
        list.clear()
        list.addAll(database.userDao().getAll())
        adapter.notifyDataSetChanged()
    }

    private fun checkLoginStatus() {
        val sharedPreferences = getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
        val token = sharedPreferences.getString("token", null)

        if (token != null) {
            // Pengguna sudah login
            loginButton.visibility = View.GONE
            userCenter.visibility = View.VISIBLE
        } else {
            // Pengguna belum login
            loginButton.visibility = View.VISIBLE
            userCenter.visibility = View.GONE
        }
    }
}
