package com.example.inventarywithfirebase

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class Register : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        val db = FirebaseFirestore.getInstance()

        val txtName=findViewById<EditText>(R.id.txtName)
        val txtLastName=findViewById<EditText>(R.id.txtLastName)
        val txtEmail=findViewById<EditText>(R.id.txtEmail)
        val txtPassword=findViewById<EditText>(R.id.txtPassword)
        val txtRePassword=findViewById<EditText>(R.id.txtRePassword)

        val btnRegister=findViewById<Button>(R.id.btnRegister)
        btnRegister.setOnClickListener {

            val contents= arrayOf(txtName.text.toString(),txtLastName.text.toString(),txtEmail.text.toString(),txtPassword.text.toString(),txtRePassword.text.toString())

            if (contents.any { x->x=="" })
            {
                Toast.makeText(this, "Check de fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(txtPassword.text.toString()!=txtRePassword.text.toString())
            {
                Toast.makeText(this, "The passwords doesn't match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user=DataUser("",contents[0],contents[1],contents[2],contents[3])

            user.save(db)
            .addOnSuccessListener { snapshot ->
                Toast.makeText(this, "Successfully register", Toast.LENGTH_SHORT).show()
                val intent: Intent = Intent(this,Login::class.java)
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(this, "Error $e", Toast.LENGTH_SHORT).show()
            }
        }
    }
}