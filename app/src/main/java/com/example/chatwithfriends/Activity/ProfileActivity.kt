package com.example.chatwithfriends.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.chatwithfriends.Modal.Users
import com.example.chatwithfriends.R
import com.example.chatwithfriends.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso

class ProfileActivity : AppCompatActivity() {
    lateinit var binding: ActivityProfileBinding
    lateinit var storage: FirebaseStorage
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var dialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        dialog=ProgressDialog(this)
        storage= FirebaseStorage.getInstance()
        database= FirebaseDatabase.getInstance()
        auth= FirebaseAuth.getInstance()


        dialog.setTitle("please wait")
        dialog.setMessage("profile updating")

        database.reference.child("Users").child(auth.uid.toString()).addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                var users:Users?=snapshot.getValue<Users>()



                    Picasso.get().load(users?.pic).placeholder(R.drawable.avtar).into(binding.profileImg)


                binding.name.text = users?.firstname.toString()
                binding.emailid.text=users?.email



            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ProfileActivity,"error",Toast.LENGTH_LONG).show()

            }


        })


        binding.addpicbtn.setOnClickListener {

            var intent=Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent,100)


        }

        binding.updatebtn.setOnClickListener {




        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data?.data!=null){

            var uriimg=data.data

            binding.profileImg.setImageURI(uriimg)

            var referenc:StorageReference=storage.reference.child("profile").child(auth.uid.toString())

            if (uriimg != null) {
                binding.updatebtn.setOnClickListener {
                    dialog.show()
                referenc.putFile(uriimg).addOnSuccessListener {
                    Toast.makeText(this,"profile pic updated",Toast.LENGTH_LONG).show()
                    referenc.downloadUrl.addOnCompleteListener {


                        var imgurl=it.result.toString()
                        Log.d("imgurl",imgurl)

                      database.reference.child("Users").child(auth.uid.toString()).child("pic").setValue(imgurl)


                        dialog.dismiss()







                    }
                }
            }
            }
        }

    }
}