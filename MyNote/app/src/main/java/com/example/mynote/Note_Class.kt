package com.example.mynote

class Note_Class/*var id1: String,var title: String,var my_note: String,var timestamp: MutableMap<String,String>*/ {

    var id1: String? = null
    var title: String? = null
    var my_note: String? = null
    var timestamp: String? = null


    constructor() {

    }


    constructor(id1: String, title: String, my_note: String, timestamp: String) {
        this.id1 = id1
        this.title = title
        this.my_note = my_note
        this.timestamp = timestamp
    }


}