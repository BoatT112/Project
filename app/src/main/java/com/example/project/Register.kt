package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_register.*
import java.lang.reflect.Member

class Employee(val id:String, val First:String, val Last:String){}
class Register : AppCompatActivity() {
    var auth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        auth = FirebaseAuth.getInstance()
        if (auth!!.currentUser != null) {
            val it = Intent(this, Member::class.java)
            startActivity(it)
            finish()
        }

        bRegisOK.setOnClickListener {
            val First = edtFname.text.toString()
            val Lsat = edtLname.text.toString()
            val email = edtMailReg.text.toString().trim()
            val pass = edtPassReg.text.toString().trim()

            val fb = FirebaseDatabase.getInstance()
            val ref = fb.getReference("Employee")
            val id: String? = ref.push().key

            val employee = Employee(id.toString(), First, Lsat)

            if (First.isEmpty()) {
                Toast.makeText(this, "กรุณากรอก ขื่อ", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (Lsat.isEmpty()) {
                Toast.makeText(this, "กรุณากรอก นามสกุล", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (email.isEmpty()) {
                Toast.makeText(this, "กรุณากรอก Email", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
            if (pass.isEmpty()) {
                Toast.makeText(this, "กรุณากรอก Password", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }


            auth!!.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    if (pass.length < 8) {
                        edtPassReg.error = "กรอกรหัสผ่านให้มากกว่า 8 ตัวอักษร"
                    } else {
                        edtMailReg.setText("")
                        edtPassReg.setText("")
                        Toast.makeText(
                            this,
                            "Login ล้มเหลว เนื่องจาก : " + task.exception!!.message,
                            Toast.LENGTH_LONG

                        ).show()
                    }
                } else {
                    ref.child(id.toString()).setValue(employee).addOnCompleteListener {
                        edtFname.setText("")
                        edtLname.setText("")
                        Toast.makeText(this, "Login Success!", Toast.LENGTH_LONG).show()
                        val it = Intent(this, MainActivity::class.java)
                        startActivity(it)
                        finish()
                    }

                }

            }
            bBack.setOnClickListener {
                val i = Intent(this, Login::class.java)
                startActivity(i)
            }
        }
    }
}