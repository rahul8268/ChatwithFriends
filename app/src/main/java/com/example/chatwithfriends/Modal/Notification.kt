package com.example.chatwithfriends.Modal

data class Notification(var pic:String?=null,var firstname:String?,var recresponcecode:Int?
                        ,var uid:String?=null,var sendresponcecode:Int?)
{
    constructor(pic: String?,firstnamee: String?,uid: String?,sendresponcecode: Int?):this(pic,firstnamee,null,uid,sendresponcecode)
    constructor(pic: String?,firstname: String?,uid: String?):this(pic,firstname,null,uid,null)
    constructor():this(null,null,null,null,null)

    constructor(sendresponcecode: Int?):this(null,null,null,null,sendresponcecode)
}