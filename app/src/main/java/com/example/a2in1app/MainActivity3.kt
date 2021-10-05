package com.example.a2in1app

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar

class MainActivity3 : AppCompatActivity() {
    lateinit var edGph:EditText
    lateinit var button2: Button
    lateinit var rvGph:RecyclerView
    lateinit var tvGph:TextView
    lateinit var tvGl:TextView
    lateinit var intent1: Intent
    lateinit var intent2: Intent
    lateinit var clphrGame:ConstraintLayout
    lateinit var information2:ArrayList<String>
    val phrase="done is better than perfect"
    var guessphrase=""
    var answer=""
    private val myAnswerDictionary = mutableMapOf<Int, Char>()
    var count=10
    var count1=10


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        this.setTitle("Guess phrase Game")

        setContentView(R.layout.activity_main3)
        edGph=findViewById(R.id.edGph)
        button2=findViewById(R.id.button2)
        rvGph=findViewById(R.id.rvGph)
        information2= ArrayList()
        intent1=Intent(this,MainActivity2::class.java)
        intent2= Intent(this,MainActivity::class.java)


        for(i in phrase.indices){
            if (phrase[i] == ' '){
                guessphrase +=" "
                myAnswerDictionary[i]=' '
            }else{
                guessphrase +="*"
                myAnswerDictionary[i]='*'
            }
        }

       // tvGph.text="guess the phrase:$guessphrase"

        rvGph.adapter=myAdapter(information2)
        rvGph.layoutManager=LinearLayoutManager(this)

        button2.setOnClickListener {
            checkphrase()
        }


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item: MenuItem =menu!!.getItem(0)
        item.title="New Game"
        val item1: MenuItem =menu!!.getItem(1)
        item1.title="Number Game"
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item1 -> {
                this.recreate()
                return true
            }
            R.id.item2 -> {
                startActivity(intent1)
                return true
            }
            R.id.item3 -> {
                startActivity(intent2)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun checkphrase(){
        var input = edGph.text.toString()
        edGph.hint="guess phrase"
        if (input.isNotEmpty()){
            if ( count>0 ){
                if (input==phrase){
                    disable()
                    alertdialog("you got it \n would you play again?")
                }else if (input!=phrase){
                    count--
                    information2.add("wrong guess :$input")
                    information2.add("$count guessing remaining")
                }

            }else {
                edGph.hint="guess letter"
                if(count1>0){
                    count1--
                    checkletter()
                }
                else if (count1==0){
                    disable()
                    alertdialog("you lose \n play again?")
                }
            }
            edGph.text.clear()
            edGph.clearFocus()
            rvGph.adapter?.notifyDataSetChanged()
        }else{
            Snackbar.make(clphrGame,"guess the phrase", Snackbar.LENGTH_LONG).show()
        }
    }

    fun checkletter(){
        var found=0
        var input=edGph.text.toString()
        if(input!=phrase){
            if (input.length == 1){
                for (i in phrase.indices){
                    if (input[0]==phrase[i]){
                        myAnswerDictionary[i]=input[0]
                        found++
                    }
                }
                answer="${myAnswerDictionary.values}"
                //tvGph.text ="guess the phrase $answer"
               // tvGl.text=" guessed letter : $input"
                information2.add("found $found $input ")
                information2.add("guessing remaining $count1")
            }else{
                Snackbar.make(clphrGame,"plase Enter one letter", Snackbar.LENGTH_LONG).show()
            }
        }else{
            alertdialog("you got it \n play again?")
        }

    }


    fun disable(){
        button2.isEnabled=false
        button2.isClickable=false
        edGph.isEnabled=false
        edGph.isClickable=false
    }

    fun alertdialog(title:String){
        val builder = AlertDialog.Builder(this)
        builder.setMessage(title)
        builder.setCancelable(false)
        builder.setPositiveButton("yes", DialogInterface.OnClickListener {
                dialog,id-> this.recreate()
        })
        builder.setNegativeButton("no", DialogInterface.OnClickListener{
                dialog,id-> dialog.cancel()
        })
        val alert =builder.create()
        alert.setTitle("game over")
        alert.show()

    }
}