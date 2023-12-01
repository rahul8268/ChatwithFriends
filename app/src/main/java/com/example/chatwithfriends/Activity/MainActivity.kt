package com.example.chatwithfriends.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.view.isGone
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.chatwithfriends.R
import com.example.chatwithfriends.Modal.Users
import com.example.chatwithfriends.Adapter.UsersRecviewAdapter
import com.example.chatwithfriends.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
     lateinit var userslist:ArrayList<Users>
     companion object{

         var username=""
         var pic=""
     }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        userslist=ArrayList<Users>()


        auth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()

        setSupportActionBar(binding.toolbar)



        binding.recview.layoutManager=LinearLayoutManager(this)

        var adapter= UsersRecviewAdapter(userslist,this)
        binding.recview.adapter=adapter


        database.reference.child("Users").addValueEventListener(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                userslist.clear()
                for(datasnaoshot:DataSnapshot in snapshot.children){
                    var user: Users? = datasnaoshot.getValue<Users>()
                    if (user != null) {

                         username=user.firstname.toString()
                         pic=user.pic.toString()
                        var sendroom=user.sendroom
                        var recroom=user.recroom
                        var rroom=user.uid+auth.uid

                        Log.d("rroom",auth.uid.toString())
                        Log.d("senderrrom",user.uid.toString())

                        if(user.uid!=null) {
                            database.reference.child("Members").child(auth.uid + user.uid)
                                .setValue(user)
                        }

                        if(datasnaoshot.key !=auth.uid){


                            if(sendroom==auth.uid+user.uid || recroom==auth.uid+user.uid) {

                                binding.addddd.isGone=true
                                binding.andstart.isGone=true
                                binding.topid.isGone=true

                              userslist.add(user)
                            }
                        }
                    }
                    adapter.notifyDataSetChanged()

                }

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity,"error",Toast.LENGTH_LONG).show()
            }


        })

        binding.notification.setOnClickListener {

            startActivity(Intent(this@MainActivity,NotificationActivty::class.java))
        }



        binding.addmember.setOnClickListener {

                    startActivity(Intent(this@MainActivity,MemberslistActivity::class.java))

                           }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        MenuInflater(this).inflate(R.menu.toolbarmenu,menu)


        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var id=item.itemId
        if(id== R.id.logout){

            auth.signOut()
            startActivity(Intent(this, LoginActivity::class.java))
            finishAffinity()
        }else{

            startActivity(Intent(this, ProfileActivity::class.java))
        }

        return super.onOptionsItemSelected(item)

    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}