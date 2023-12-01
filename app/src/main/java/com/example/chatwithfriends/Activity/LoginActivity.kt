package com.example.chatwithfriends.Activity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.chatwithfriends.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {

    lateinit var binding:ActivityLoginBinding
    lateinit var auth: FirebaseAuth
    lateinit var database: FirebaseDatabase
    lateinit var dialog:ProgressDialog



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        database= FirebaseDatabase.getInstance()
        dialog=ProgressDialog(this)

        dialog.setTitle("Please wait")
        dialog.setMessage("Login your account")
















        binding.noaccount.setOnClickListener {


            startActivity(Intent(this, SingupActivty::class.java))
            finish()

        }

        binding.btnlogin.setOnClickListener {


            var email=binding.email.text.toString()
            var password=binding.password.text.toString()


            if(email!=""&&password!=""){


                auth.signInWithEmailAndPassword(email, password).addOnCompleteListener {

                    dialog.show()



                    if(it.isSuccessful) {
                        dialog.dismiss()
                    var intent= Intent(this, MainActivity::class.java)
                        intent.putExtra("uid",auth.uid)
                        startActivity(intent)
                    }else{
                        dialog.dismiss()

                        Toast.makeText(this,"email and password are wrong", Toast.LENGTH_LONG).show()
                    }


                    }



            }else{
                Toast.makeText(this,"please fill the  blanks", Toast.LENGTH_LONG).show()
                dialog.dismiss()
            }





        }

    }





    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.uid
        if(currentUser!=null){
            startActivity(Intent(this, MainActivity::class.java))
        }
    }


}