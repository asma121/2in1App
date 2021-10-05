package com.example.a2in1app

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    lateinit var buNg:Button
    lateinit var buGph:Button
    private lateinit var intent2:Intent
    private lateinit var intent1: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.setTitle("Main Activity")

        buNg=findViewById(R.id.buNg)
        buGph=findViewById(R.id.buGph)

        buNg.setOnClickListener {
             intent1 = Intent(this,MainActivity2::class.java)
            startActivity(intent1)
        }

        buGph.setOnClickListener {
             intent2=Intent(this,MainActivity3::class.java)
            startActivity(intent2)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.item1 -> {
                 startActivity(intent1)
                return true
            }
            R.id.item2 -> {

                startActivity(intent2)
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}