package com.herdialfachri.spacephoneroom

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.herdialfachri.spacephoneroom.adapter.UserAdapter
import com.herdialfachri.spacephoneroom.dao.AppDatabase
import com.herdialfachri.spacephoneroom.dao.User

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var adapter: UserAdapter
    private var list = mutableListOf<User>()
    private lateinit var database: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.rv_user)
        fab = findViewById(R.id.floatingbtn)

        database = AppDatabase.getInstance(applicationContext)
        adapter = UserAdapter(list)

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(DividerItemDecoration(applicationContext, RecyclerView.VERTICAL))

        fab.setOnClickListener {
            startActivity(Intent(this, EditorActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        getData()
    }

    private fun getData() {
        list.clear()
        list.addAll(database.userDao().getAll())
        adapter.notifyDataSetChanged()
    }
}
