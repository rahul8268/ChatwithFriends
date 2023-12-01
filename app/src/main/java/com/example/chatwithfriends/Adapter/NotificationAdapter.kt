package com.example.chatwithfriends.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.chatwithfriends.Modal.Notification
import com.example.chatwithfriends.R
import com.example.chatwithfriends.databinding.NotificationshowlayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class NotificationAdapter(var context:Context,var nlist:ArrayList<Notification>) :RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {
    class ViewHolder( var  binding:NotificationshowlayoutBinding) :RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var binding=NotificationshowlayoutBinding.inflate(LayoutInflater.from(context),parent,false)

        return ViewHolder(binding)

    }

    override fun getItemCount(): Int {
        return nlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var notification=nlist[position]
        holder.binding.nusername.text=notification.firstname
        Picasso.get().load(notification.pic).placeholder(R.drawable.avtar).into(holder.binding.nprofilepic)

        holder.binding.accept.setOnClickListener {


            FirebaseDatabase.getInstance().reference.child("Users")
                .child(FirebaseAuth.getInstance().uid.toString()) .child("recroom")
                .setValue(notification.uid+FirebaseAuth.getInstance().uid)


            FirebaseDatabase.getInstance().reference.child("Users")
                .child(notification.uid.toString()) .child("sendroom")
                .setValue(FirebaseAuth.getInstance().uid+notification.uid)


            FirebaseDatabase.getInstance().reference.child("Members")
                .child(notification.uid+FirebaseAuth.getInstance().uid).child("sendresponcecode").setValue(2)






            holder.binding.accept.text="Accepetd"

            Toast.makeText(context," Start chating "+notification.firstname,Toast.LENGTH_LONG ).show()







        }



    }
}