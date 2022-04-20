package com.example.mynote

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.long_click_layout.*
import kotlinx.android.synthetic.main.long_click_layout.view.*
import kotlinx.android.synthetic.main.new_note.*
import kotlinx.android.synthetic.main.new_note.view.*
import kotlinx.android.synthetic.main.new_note.view.btnDelete
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    var myRef: DatabaseReference? = null
    var mNoteList: ArrayList<Note_Class>? = null

    var mTitle: String? = null
    var mNote: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val database = FirebaseDatabase.getInstance()
        myRef = database.getReference("Notes")

        mNoteList = ArrayList()


        val vInflater = layoutInflater.inflate(R.layout.new_note, null)
        //  AlertDialog.Builder(this).setView(vInflater).create().show()

        val alDialog = AlertDialog.Builder(this).setView(vInflater).create()

        //       mTitle = vInflater.etTitle.text.toString()
        //       mNote = vInflater.etNote.text.toString()


        FAB1.setOnClickListener {
            alDialog.show()

            vInflater.btnDelete.setOnClickListener {
                btn_add()
                alDialog.dismiss()  // to let the dialog hide when i finish writing
            }

        }


        listV1.onItemClickListener =
            AdapterView.OnItemClickListener { parent, view, position, id ->

                var myN = mNoteList!![position]

                var nIntent = Intent(this, N_Activity::class.java)

                nIntent.putExtra("Title_Key", myN.title)
                nIntent.putExtra("Note_Key", myN.my_note)

                startActivity(nIntent)


            }


        // all of this giving errors
        listV1.setOnItemLongClickListener { parent, view, position, id ->

            try {

                var vInflaterLong = layoutInflater.inflate(R.layout.long_click_layout, null)
                AlertDialog.Builder(this).setView(vInflaterLong).create().show()

                var mnList = mNoteList!![position]


                etLongTitle.setText(mnList.title)
                etLongNote.setText(mnList.my_note)

                listV1.btnDelete.setOnClickListener {

                    myRef?.child(mnList.id1.toString())?.removeValue()
                    alDialog.dismiss()

                }


            } catch (e: Exception) {
                Toast.makeText(this, "oh nooo", Toast.LENGTH_SHORT).show()
            }







            false
        }


    }

    fun getCurrentDate(): String {
        var calender = Calendar.getInstance()
        var SDF = SimpleDateFormat("EEEE hh:mm a")
        var strDate = SDF.format(calender.time)

        return strDate

    }


    override fun onStart() {
        super.onStart()

        myRef?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                mNoteList?.clear()  // not to show the adpter layout again while retrieving data from the firebase

                for (nt in snapshot.children) {
                    mNoteList!!.add(
                        0,
                        nt.getValue(Note_Class::class.java)!!
                    )  // 0 is to put the last note in the first line and you can delete it or change it
                }


                var myNoteAdapter = NoteAdapter(applicationContext, mNoteList!!)
                listV1.adapter = myNoteAdapter


            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

    }


    fun btn_add() {

        try {


            // using edit text always gives me a null exception
            var mTitle = "title10" //etTitle.text.toString()//
            var mNote = "note10" //etNote.text.toString()//

            if (mTitle!!.isNotEmpty() && mNote!!.isNotEmpty()) {

                val id = myRef!!.push().key!!
                var myNote = Note_Class(id, mTitle!!, mNote!!, getCurrentDate())
                myRef!!.child(id).setValue(myNote)
                Toast.makeText(this, "Done!", Toast.LENGTH_SHORT).show()


            } else {
                Toast.makeText(this, "Add Values!", Toast.LENGTH_SHORT).show()
            }

        } catch (ex: Exception) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_LONG).show()
        }

    }


}






