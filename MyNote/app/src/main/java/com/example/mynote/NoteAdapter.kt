package com.example.mynote

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.adapter_note_layout.view.*

class NoteAdapter(con1: Context, nList: ArrayList<Note_Class>) :
    ArrayAdapter<Note_Class>(con1, 0, nList) {


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {


        var lInf = LayoutInflater.from(context).inflate(R.layout.adapter_note_layout, parent, false)

        var n: Note_Class? = getItem(position)

        lInf.adTitle.text = n!!.title
        lInf.adTime.text = n.timestamp.toString()



        return lInf

    }


}