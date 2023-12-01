package com.example.chatwithfriends.Activity

import android.R
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.SearchView
import android.widget.Toast

import com.example.chatwithfriends.Adapter.MebershowAdapter

import com.example.chatwithfriends.Modal.Member
import com.example.chatwithfriends.Modal.Searchmodal
import com.example.chatwithfriends.Modal.Users

import com.example.chatwithfriends.databinding.ActivityMemberslistBinding


import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.util.Locale

class MemberslistActivity : AppCompatActivity() {
    lateinit var binding: ActivityMemberslistBinding
    lateinit var memberlist:ArrayList<Member>
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var templist:ArrayList<Member>




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMemberslistBinding.inflate(layoutInflater)
        setContentView(binding.root)

        memberlist=ArrayList<Member>()
        templist=ArrayList<Member>()


        auth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
       var  adapter=MebershowAdapter(this@MemberslistActivity,memberlist)
        binding.recmember.adapter=adapter
        var uid=auth.uid.toString()



        templist.addAll(memberlist)


//
//          var username=intent.getStringExtra("username")
//          var pic=intent.getStringExtra("pic")
//
//




        database.reference.child("Users").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                memberlist.clear()
                for(datasnaoshot:DataSnapshot in snapshot.children){
                    var user: Users? = datasnaoshot.getValue<Users>()
                    if (user != null) {

                        var firstname=user.firstname.toString()
                        var pic =user.pic.toString()
                        var uid=user.uid
                        var sendroom=user.recroom



                        var member=Member(firstname,pic,uid)


                        var revroom=auth.uid+uid

                        Log.d("revroom",revroom)
                        Log.d("sendroom",sendroom.toString())

                        if(datasnaoshot.key !=auth.uid){

                            if(revroom!=sendroom && auth.uid+uid!=user.sendroom) {



                                memberlist.add(member)
                            }
                        }





                    }


                    adapter.notifyDataSetChanged()

                }






            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MemberslistActivity,"error",Toast.LENGTH_LONG).show()

            }


        })








    }
}