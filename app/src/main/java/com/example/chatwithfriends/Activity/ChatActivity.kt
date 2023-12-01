package com.example.chatwithfriends.Activity

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Adapter
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatwithfriends.Adapter.ChatAdapter
import com.example.chatwithfriends.Modal.Messege
import com.example.chatwithfriends.R
import com.example.chatwithfriends.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.io.File
import java.io.FileOutputStream
import java.lang.ref.Reference
import java.lang.ref.WeakReference
import java.util.Date

class ChatActivity : AppCompatActivity() {
    lateinit var binding: ActivityChatBinding
    lateinit var msglist: ArrayList<Messege>
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var storage: FirebaseStorage
    lateinit var senderoom: String
    lateinit var receverroom: String
    lateinit var adapter: ChatAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)


        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        storage = FirebaseStorage.getInstance()
        msglist = ArrayList<Messege>()

        adapter = ChatAdapter(this, msglist)


        var username = intent.getStringExtra("name")
        var receveruid = intent.getStringExtra("uid")
        var senderuid = auth.uid
        var lastmsg = intent.getStringExtra("lastmsg")
        var pic = intent.getStringExtra("pic")

        senderoom = senderuid + receveruid
        receverroom = receveruid + senderuid




        setSupportActionBar(binding.toolbar)


        //cemra open


        //back botton
        binding.backarrow.setOnClickListener {

            startActivity(Intent(this, MainActivity::class.java))
        }

        //chat toolbar profile
        Picasso.get().load(pic).placeholder(R.drawable.avtar).into(binding.chatprofileImage)

        // chat toolbar username

        binding.chatusername.text = username



        binding.recview.layoutManager = LinearLayoutManager(this)


        binding.recview.adapter = adapter


        database.reference.child("Chats")
            .child(senderoom)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {

                    msglist.clear()

                    for (msgsnapshot: DataSnapshot in snapshot.children) {

                        var msg: Messege? = msgsnapshot.getValue<Messege>()









                        if (msg != null) {

                            if (senderuid + receveruid == senderoom || receveruid + senderuid == receverroom) {
                                msglist.add(msg)

                            }
                        }

                        adapter.notifyDataSetChanged()
                    }

                }


                override fun onCancelled(error: DatabaseError) {

                    Toast.makeText(this@ChatActivity,"error",Toast.LENGTH_LONG).show()

                }

            })




        binding.send.setOnClickListener {

            var msgtxt = binding.textbox.text.toString()

            var msg = Messege(msgtxt, senderuid.toString(), Date().time.toString(), null)

            if (msgtxt != "") {

                binding.textbox.setText("")


                if(msg!=null){

                database.reference.child("Chats")
                    .child(senderoom)
                    .push().setValue(msg).addOnCompleteListener {

                        database.reference.child("Chats")
                            .child(receverroom)
                            .push().setValue(msg).addOnCompleteListener {


                            }


                    }


            }
            }
        }




    }


    override fun onBackPressed() {
        super.onBackPressed()

        finish()
    }




}



