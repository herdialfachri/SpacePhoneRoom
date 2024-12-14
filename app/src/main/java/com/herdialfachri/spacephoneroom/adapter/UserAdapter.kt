package com.herdialfachri.spacephoneroom.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.herdialfachri.spacephoneroom.activity.DetailActivity
import com.herdialfachri.spacephoneroom.R
import com.herdialfachri.spacephoneroom.entitiy.User

class UserAdapter(var list: List<User>): RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var gambar: ImageView = view.findViewById(R.id.gambar)
        var fullName: TextView = view.findViewById(R.id.full_name)
        var phone: TextView = view.findViewById(R.id.phone)
        var address: TextView = view.findViewById(R.id.address)

        init {
            view.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val context = it.context
                    val detailIntent = Intent(context, DetailActivity::class.java)
                    detailIntent.putExtra("id", list[position].uid)
                    context.startActivity(detailIntent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.fullName.text = list[position].fullName
        holder.phone.text = list[position].phone
        holder.address.text = list[position].address

        if (list[position].gambar != null) {
            Glide.with(holder.itemView.context)
                .load(list[position].gambar)
                .into(holder.gambar)
        } else {
            holder.gambar.setImageResource(R.drawable.default_image)
        }
    }
}
