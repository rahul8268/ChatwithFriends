package com.example.chatwithfriends.Activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatwithfriends.Modal.Users
import com.example.chatwithfriends.R
import com.example.chatwithfriends.databinding.ActivitySingupActivtyBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class SingupActivty : AppCompatActivity() {
    lateinit var binding: ActivitySingupActivtyBinding
    lateinit var auth:FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var prrograsdialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySingupActivtyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        prrograsdialog=ProgressDialog(this)



        prrograsdialog.setTitle("Please wait")
        prrograsdialog.setMessage("Account creating")

        binding.loginhere.setOnClickListener {

            startActivity(Intent(this, LoginActivity::class.java))
            finish()

        }


        binding.btnsingup.setOnClickListener {

            prrograsdialog.show()

            var firstname=binding.firstname.text.toString()
            var lastname=binding.lastname.text.toString()
            var email=binding.email.text.toString()
            var password=binding.password.text.toString()
            var cpassword=binding.cpassword.text.toString()

            if(firstname!=""&&email!=""&&password!=""&&cpassword!=""){

                if(password==cpassword){



                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                        prrograsdialog.dismiss()
                        Toast.makeText(this,"Account created seccesfully ",Toast.LENGTH_LONG).show()

                        var  uid=auth.uid



                        var users= Users(firstname, email, password, uid,)

                        database.reference.child("Users").child(auth.uid.toString()).setValue(users).addOnCompleteListener {

                            startActivity(Intent(this, LoginActivity::class.java))

                        }

                    }






                }else{


                    Toast.makeText(this,"password not match",Toast.LENGTH_LONG).show()
                    prrograsdialog.dismiss()

                }


            }else{
                Toast.makeText(this,"please fill the  blanks",Toast.LENGTH_LONG).show()
                prrograsdialog.dismiss()
            }





        }


    }
}