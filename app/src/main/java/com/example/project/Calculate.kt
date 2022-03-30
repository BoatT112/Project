package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_calculate.*
import kotlinx.android.synthetic.main.activity_login.*

class Calculate : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculate)

        val i = intent
        var txt1 = i.getStringExtra("txt1")
        var txt3 = i.getStringExtra("txt2")
        var txt4 = i.getStringExtra("txt3")

        val txt2 = "%,.2f".format(txt1.toString().toDouble())
        textBMI.text = "%,.2f".format(txt1.toString().toDouble())

        textBMR.text = txt3.toString()
        textTDEE.text = txt4.toString()

        if (txt2.toString().toDouble() < 18.59)
            tv1.text = "(ต่ำกว่าเกณฑ์)"
        else if (txt2.toString().toDouble() > 18.59 && txt2.toString().toDouble() < 24.99) {
            tv1.text = "(ปกติ)"
        } else if (txt2.toString().toDouble() > 25 && txt2.toString().toDouble() < 29.99) {
            tv1.text = "(อ้วน)"
        } else {
            tv1.text = "(เกินพิกัด)"
        }


        bBack2.setOnClickListener {
            val i = Intent(this,MainActivity::class.java)
            startActivity(i)
        }
    }
}





