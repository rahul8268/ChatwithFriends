package com.example.chatwithfriends.Modal

data class Member(var firstname:String?=null,var pic:String?=null,var uid:String?=null,var sendresponcecode:Int?=null,var senderroom:String?=null)
{
    constructor(firstname: String?,pic: String?,uid: String?):this(firstname,pic,uid,null,null)

}

