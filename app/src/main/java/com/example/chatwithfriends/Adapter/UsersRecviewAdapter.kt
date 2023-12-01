package com.example.chatwithfriends.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatwithfriends.Activity.ChatActivity
import com.example.chatwithfriends.R
import com.example.chatwithfriends.Modal.Users
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class UsersRecviewAdapter(var userlist:ArrayList<Users>, var context:Context) :RecyclerView.Adapter<UsersRecviewAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view=LayoutInflater.from(parent.context).inflate(R.layout.usershowlayout,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return userlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var users=userlist.get(position)
        Picasso.get().load(users.pic).placeholder(R.drawable.avtar).into(holder.pic)
        holder.username.text=users.firstname


        holder.relative.setOnClickListener{
            var intent=Intent(context, ChatActivity::class.java)
            intent.putExtra("name",users.firstname)
            intent.putExtra("uid",users.uid)
            intent.putExtra("pic",users.pic)

            context.startActivity(intent)

        }

    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var pic=itemView.findViewById<CircleImageView>(R.id.profile_img)
        var username=itemView.findViewById<TextView>(R.id.memberusername)
        var relative=itemView.findViewById<RelativeLayout>(R.id.relative)

    }
}