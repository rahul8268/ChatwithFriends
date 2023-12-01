package com.example.chatwithfriends.Adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ListAdapter
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.chatwithfriends.Modal.Member
import com.example.chatwithfriends.Modal.Users
import com.example.chatwithfriends.R
import com.example.chatwithfriends.databinding.MembershowlayoutBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.squareup.picasso.Picasso

class MebershowAdapter(var context: Context, var memberlist:ArrayList<Member>) :
    RecyclerView.Adapter<MebershowAdapter.ViewHolder>() {




    class ViewHolder(val binding: MembershowlayoutBinding) :RecyclerView.ViewHolder(binding.root){





    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=MembershowlayoutBinding.inflate(LayoutInflater.from(context),parent,false)

        return ViewHolder(binding)
    }


    override fun getItemCount(): Int {
        return memberlist.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var member=memberlist[position]
        with(holder){
            binding.memberusername.text=member.firstname
            Picasso.get().load(member.pic).placeholder(R.drawable.avtar).into(binding.mprofile)


            var recverid=member.uid.toString()
            var senderid=FirebaseAuth.getInstance().uid

            var senderrroom=senderid+recverid
            var recevroom=recverid+senderid



            var flag=0

            binding.followbtn.setOnClickListener {




                if(flag==1) {

                    FirebaseDatabase.getInstance().reference.child("Members")
                        .child(recevroom).child("sendresponcecode").setValue(1)



                    binding.followbtn.text = "Following"

                    flag=2



                }else{

                    FirebaseDatabase.getInstance().reference.child("Members")
                        .child(senderrroom).child("sendresponcecode").setValue(2)

                    binding.followbtn.text = "Follow"

                    flag=1
                }




            }

            FirebaseDatabase.getInstance().reference.child("Members")
                .child(recevroom).addListenerForSingleValueEvent(object :ValueEventListener{
                    override fun onDataChange(snapshot: DataSnapshot) {

                        var member:Member?=snapshot.getValue<Member>()
                       var responcecode= member?.sendresponcecode


                        if(responcecode==1)

                            binding.followbtn.text = "Following"
                        else
                            binding.followbtn.text = "Follow"
                    }

                    override fun onCancelled(error: DatabaseError) {
                        TODO("Not yet implemented")
                    }

                })


        }
    }



}