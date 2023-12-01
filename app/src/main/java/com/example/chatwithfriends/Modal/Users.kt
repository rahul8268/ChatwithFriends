package com.example.chatwithfriends.Modal

data class Users(
    var firstname:String?=null,
    var email:String?=null,
    var password:String?=null,
    var pic:String?=null,
    var uid:String?=null,
    var sendroom:String?=null,
    var lastname: String?,
    var recroom:String?=null

)
{
    constructor() : this(null,null,null,null,null,null,null,null)

    constructor(firstname: String,email: String?,password: String?,uid: String?) :
            this(firstname, email, password, null, uid, null, null,null)


}