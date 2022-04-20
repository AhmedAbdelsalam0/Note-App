package com.example.mynote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_n_.*

class N_Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_n_)


        var t1 = intent.extras!!.getString("Title_Key")
        var n1 = intent.extras!!.getString("Note_Key")


        txt_Title.text = t1
        txt_Note.text = n1


    }
}