package com.example.inventarywithfirebase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore


class ProductNewEdit : Fragment() {

    var main:Main?=null
    var txtName: EditText?=null
    var txtDescription: EditText?=null
    var btnInsert: Button?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val actualView=inflater.inflate(R.layout.fragment_product_new_edit, container, false)
        main=activity as Main

        val db= FirebaseFirestore.getInstance()

        txtName=actualView.findViewById<EditText>(R.id.txtName)
        txtDescription=actualView.findViewById<EditText>(R.id.txtDescription)
        btnInsert=actualView.findViewById<Button>(R.id.btnInsert)

        btnInsert?.setOnClickListener {
            val contents= arrayOf(txtName?.text.toString(), txtDescription?.text.toString())

            if (contents.any { x->x=="" })
            {
                Toast.makeText(actualView.context, "Check de fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val product=DataProduct("", main?.user?.Id ?: "", contents[0], contents[1])
            product.save(db)
            .addOnSuccessListener { snapshot ->
                Toast.makeText(main, "Successfully register", Toast.LENGTH_SHORT).show()

                txtName?.setText("")
                txtDescription?.setText("")

                main?.setTab(1)
            }
            .addOnFailureListener { e ->
                Toast.makeText(main, "Error $e", Toast.LENGTH_SHORT).show()
            }
        }

        return actualView
    }
}