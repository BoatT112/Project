package com.example.project

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class Login : AppCompatActivity() {
    var auth: FirebaseAuth? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        auth = FirebaseAuth.getInstance()

        if (auth!!.currentUser != null) {
            val it = Intent(this, MainActivity::class.java)
            startActivity(it)
            finish()
        }

        bLogin.setOnClickListener {
            val email = edtMailLog.text.toString().trim()
            val pass = edtPassLog.text.toString().trim()

            if (email.isEmpty()) {
                Toast.makeText(this, "กรุณากรอก Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pass.isEmpty()) {
                Toast.makeText(this, "กรุณากรอก Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            auth!!.signInWithEmailAndPassword(email, pass).addOnCompleteListener { task ->
                if (!task.isSuccessful) {
                    if (pass.length < 8) {
                        edtPassLog.error = "กรอกรหัสผ่านให้มากกว่า 8 ตัวอักษร"
                    }else{
                        Toast.makeText(this, "เข้าสู่ระบบ ล้มเหลว เนื่องจาก : "+ task.exception!!.message, Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(this, "เข้าสู่ระบบแล้ว", Toast.LENGTH_SHORT).show()
                    val it = Intent(this,MainActivity::class.java)
                    startActivity(it)
                    finish()
                }
            }
        }
        bRegis.setOnClickListener {
            val i = Intent(this,Register::class.java)
            startActivity(i)
        }

        bclear.setOnClickListener {
            edtMailLog.text.clear()
            edtPassLog.text.clear()
        }
    }
}