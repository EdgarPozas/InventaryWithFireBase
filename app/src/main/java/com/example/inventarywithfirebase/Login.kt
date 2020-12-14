package com.example.inventarywithfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore
import java.io.Serializable

class Login : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        val db = FirebaseFirestore.getInstance()

        val txtEmail=findViewById<EditText>(R.id.txtEmail)
        val txtPassword=findViewById<EditText>(R.id.txtPassword)

        val btnRegister=findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {
            val intent = Intent(this, Register::class.java)
            startActivity(intent)
        }

        val btnLogin=findViewById<Button>(R.id.btnLogin)
        btnLogin.setOnClickListener {

            val contents=arrayOf(txtEmail.text.toString(),txtPassword.text.toString())

            if(contents.any{x->x==""}){
                Toast.makeText(this,"Please check the fields",Toast.LENGTH_SHORT)
                return@setOnClickListener
            }

            DataUser.login(db,contents[0],contents[1])
            .addOnSuccessListener { snapshot ->
                if (snapshot.isEmpty)
                {
                    Toast.makeText(this,"Please check the fields",Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

                val user=snapshot.toObjects(DataUser::class.java)[0]
                user.Id=snapshot.documents[0].id

                Toast.makeText(this,"Login successfully",Toast.LENGTH_LONG).show()

                val intent=Intent(this@Login,Main::class.java)
                intent.putExtra("user",user)

                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this,"Please check the fields",Toast.LENGTH_SHORT).show()
            }
        }
    }
}