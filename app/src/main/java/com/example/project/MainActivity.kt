package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isEmpty
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.bBack

class MainActivity : AppCompatActivity() {

    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        auth = FirebaseAuth.getInstance()

        val userData = auth!!.currentUser
       


        bLogout.setOnClickListener {
            auth!!.signOut()
            Toast.makeText(this, "ออกจากระบบแล้ว", Toast.LENGTH_LONG).show()

            val it = Intent(this, Login::class.java)
            startActivity(it)
            finish()
        }


        bCalculate.setOnClickListener {


            if (rd1.isChecked == false && rd2.isChecked == false && rd3.isChecked == false && rd4.isChecked == false && rd5.isChecked == false) {
                Toast.makeText(this, "กรุณาเลือกกิจกรรม", Toast.LENGTH_SHORT).show()
            } else if (radioWomen.isChecked == false && radioMen.isChecked == false) {
                Toast.makeText(this, "กรุณาเลือกเพศ", Toast.LENGTH_SHORT).show()
            } else if (edtHeight.text.toString() == "" || edtAge.text.toString() == "" || edtWeight.text.toString() == "") {
                Toast.makeText(this, "กรุณาใส่ข้อมูลให้ถูกต้อง", Toast.LENGTH_SHORT).show()
            } else {
                var txt1 = edtWeight.text.toString().toDouble()
                var txt2 = (edtHeight.text.toString().toDouble() * 0.01)
                var txt3 = (txt1.toDouble() / Math.pow(txt2.toDouble(), 2.0))
                var txt4 = edtAge.text.toString().toDouble()

                var m: Double = 0.0
                var n = 0.0
                if (radioMen.isChecked) {
                    m = (66 + (13.7 * edtWeight.text.toString()
                        .toDouble()) + (5 * edtHeight.text.toString().toDouble()) - (6.8 * txt4))
                } else if (radioWomen.isChecked) {
                    m = (665 + (9.6 * edtWeight.text.toString()
                        .toDouble()) + (1.8 * edtHeight.text.toString().toDouble()) - (4.7 * txt4))
                }

                if (rd1.isChecked) {
                    n = (m.toDouble() * 1.2)
                } else if (rd2.isChecked) {
                    n = (m.toDouble() * 1.375)
                } else if (rd3.isChecked) {
                    n = (m.toDouble() * 1.55)
                } else if (rd4.isChecked) {
                    n = (m.toDouble() * 1.725)
                } else if (rd5.isChecked) {
                    n = (m.toDouble() * 1.9)
                }

                val i = Intent(this, Calculate::class.java)
                i.putExtra("txt1", txt3.toString())
                i.putExtra("txt2", m.toInt().toString())
                i.putExtra("txt3", n.toInt().toString())
                startActivity(i)


            }

        }


        bClear2.setOnClickListener {
            RDG.clearCheck()
            radioGroup.clearCheck()
            edtWeight.text.clear()
            edtHeight.text.clear()
            edtAge.text.clear()
        }

    }


}




