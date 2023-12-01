package com.example.chatwithfriends.Adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.chatwithfriends.Modal.Messege
import com.example.chatwithfriends.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.squareup.picasso.Picasso

class ChatAdapter(var context:Context,var msglist:ArrayList<Messege>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

val ITEM_SENT=1
val ITEM_REC=2





  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    if(viewType==ITEM_SENT) {
      var view = LayoutInflater.from(parent.context).inflate(com.example.chatwithfriends.R.layout.sendmsg, parent, false)
   return SendViewholder(view)
    }else{
      var view=LayoutInflater.from(parent.context).inflate(com.example.chatwithfriends.R.layout.receiver,parent,false)
   return RecViewHolder(view)
    }
  }

  override fun getItemViewType(position: Int): Int {

      if(msglist.get(position).senderid.equals(FirebaseAuth.getInstance().uid)){
          return ITEM_SENT

      }else{
          return ITEM_REC
      }

  }


  override fun getItemCount(): Int {
    return msglist.size
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    var msg: Messege =msglist[position]
    if(holder::class== SendViewholder::class){




        (holder as SendViewholder).sendmsg.text = msg.msg



    }else{
        ( holder as RecViewHolder).recmsg.text=msg.msg

       // ( holder as RecViewHolder).timestamp.text=msg.timestamp
    }
  }
  class SendViewholder(itemview:View):RecyclerView.ViewHolder(itemview)
  {
     var sendmsg:TextView=itemview.findViewById(com.example.chatwithfriends.R.id.sendtxt)
     // var timestamp:TextView=itemview.findViewById(com.example.chatwithfriends.R.id.sendtimestamp)



  }

  class  RecViewHolder(itemview:View):RecyclerView.ViewHolder(itemview){

    var recmsg: TextView =itemview.findViewById(com.example.chatwithfriends.R.id.rectext)

     // var timestamp:TextView=itemview.findViewById(com.example.chatwithfriends.R.id.timestamp)
  }

}




