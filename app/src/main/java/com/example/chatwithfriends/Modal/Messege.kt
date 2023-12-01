package com.example.chatwithfriends.Modal

data class Messege(var msgid: String?, var msg: String?, var senderid: String?,var timestamp:String?=null,var pic:String?=null){

    constructor(msg:String,senderid: String,timestamp: String?,pic: String?):this(null,msg, senderid,timestamp)

    constructor():this(null,null,null)

    constructor(pic: String?):this(null,null,null,null,pic)


}
