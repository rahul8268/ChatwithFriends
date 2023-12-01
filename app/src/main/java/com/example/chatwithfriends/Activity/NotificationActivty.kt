package com.example.chatwithfriends.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.chatwithfriends.Adapter.NotificationAdapter
import com.example.chatwithfriends.Modal.Member
import com.example.chatwithfriends.Modal.Notification
import com.example.chatwithfriends.Modal.Users
import com.example.chatwithfriends.R
import com.example.chatwithfriends.databinding.ActivityNotificationActivtyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase

import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import kotlinx.coroutines.delay


class NotificationActivty : AppCompatActivity() {
    lateinit var binding: ActivityNotificationActivtyBinding
    var nlist = ArrayList<Notification>()
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)



        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        setSupportActionBar(binding.ntoolbar)

        var adapter = NotificationAdapter(this, nlist)

        binding.nrec.adapter = adapter


        database.reference.child("Members")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    nlist.clear()
                    for (snapshot: DataSnapshot in snapshot.children) {

                        var member: Member? = snapshot.getValue<Member>()

                        var firstname = member?.firstname
                        var pic = member?.pic
                        var senderresponce = member?.sendresponcecode
                        var uid = member?.uid

                        var notification = Notification(pic, firstname, uid, senderresponce)



                        if (senderresponce == 1) {



                                nlist.add(notification)





                        }


                    }
                    adapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(this@NotificationActivty,"error", Toast.LENGTH_LONG).show()

                }


            })









        binding.backbtn.setOnClickListener {

            startActivity(Intent(this, MainActivity::class.java))
        }


    }
}
