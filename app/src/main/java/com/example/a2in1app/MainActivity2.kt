package com.example.a2in1app

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlin.random.Random

class MainActivity2 : AppCompatActivity() {
    lateinit var edNg:EditText
    lateinit var button: Button
    lateinit var rvNg:RecyclerView
    lateinit var conlay:ConstraintLayout
    lateinit var intent1:Intent
    lateinit var intent2:Intent
    lateinit var information1:ArrayList<String>
    var count=3
    var ranumber= Random.nextInt(0,10)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        this.setTitle("Numbers Game")

        edNg=findViewById(R.id.edNg)
        button=findViewById(R.id.button)
        rvNg=findViewById(R.id.rvNg)
        information1= ArrayList()
        intent1=Intent(this,MainActivity3::class.java)
        intent2= Intent(this,MainActivity::class.java)

        rvNg.adapter=myAdapter(information1)
        rvNg.layoutManager=LinearLayoutManager(this)

        button.setOnClickListener {
            addMessage()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val item:MenuItem=menu!!.getItem(0)
        item.title="New Game"
        val item1:MenuItem=menu!!.getItem(1)
        item1.title="Guess the phrase"
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


    private fun disableEntry(){
        button.isEnabled = false
        button.isClickable = false
        edNg.isEnabled = false
        edNg.isClickable = false
    }

    private fun showAlertDialog(title: String) {
        val Builder = AlertDialog.Builder(this)

        Builder.setMessage(title)
            .setCancelable(false)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> this.recreate()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        val alert = Builder.create()
        alert.setTitle("Game Over")
        alert.show()
    }

    private fun addMessage(){
        val msg = edNg.text.toString()
        if(msg.isNotEmpty()){
            if(count>0){
                if(msg.toInt() == ranumber){
                    disableEntry()
                    showAlertDialog("You win!\n\nPlay again?")
                }else{
                    count--
                    information1.add("You guessed $msg")
                    information1.add("You have $count guesses left")
                }
                if(count==0){
                    disableEntry()
                    information1.add("You lose - The correct answer was $ranumber")
                    information1.add("Game Over")
                    showAlertDialog("You lose...\nThe correct answer was $ranumber.\n\nPlay again?")
                }
            }
            edNg.text.clear()
            edNg.clearFocus()
            rvNg.adapter?.notifyDataSetChanged()
        }else{
            Snackbar.make(conlay, "Please enter a number", Snackbar.LENGTH_LONG).show()
        }
    }

}