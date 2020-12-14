package com.example.inventarywithfirebase

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class EditUser : Fragment() {

    var main:Main?=null
    var txtName:EditText?=null
    var txtLastName:EditText?=null
    var txtEmail:EditText?=null
    var txtPassword:EditText?=null
    var txtRePassword:EditText?=null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val actualView= inflater.inflate(R.layout.fragment_edit_user, container, false)
        main=activity as Main

        val db=FirebaseFirestore.getInstance()

        txtName=actualView.findViewById<EditText>(R.id.txtName)
        txtLastName=actualView.findViewById<EditText>(R.id.txtLastName)
        txtEmail=actualView.findViewById<EditText>(R.id.txtEmail)
        txtPassword=actualView.findViewById<EditText>(R.id.txtPassword)
        txtRePassword=actualView.findViewById<EditText>(R.id.txtRePassword)

        updateFields()

        val btnUpdate=actualView.findViewById<Button>(R.id.btnUpdate)
        btnUpdate.setOnClickListener {
            val contents= arrayOf(txtName?.text.toString(),txtLastName?.text.toString(),txtEmail?.text.toString(),txtPassword?.text.toString(),txtRePassword?.text.toString())

            if (contents.any { x->x=="" })
            {
                Toast.makeText(actualView.context, "Check de fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if(txtPassword?.text.toString()!=txtRePassword?.text.toString())
            {
                Toast.makeText(actualView.context, "The passwords doesn't match", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val alertDialog: AlertDialog? = activity?.let {
                val builder=AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton("Accept",DialogInterface.OnClickListener { dialog, id ->
                        main?.user?.Name=txtName?.text.toString()
                        main?.user?.LastName=txtLastName?.text.toString()
                        main?.user?.Email=txtEmail?.text.toString()
                        main?.user?.Password=txtPassword?.text.toString()
                        main?.user?.update(db)
                        updateFields()
                    })
                    setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
                }
                builder.setMessage("Do you want update the fields?")?.setTitle("Confirm")
                builder.create()
            }
            alertDialog?.show()
        }

        val btnDelete=actualView.findViewById<Button>(R.id.btnDelete)
        btnDelete.setOnClickListener {
            val alertDialog: AlertDialog? = activity?.let {
                val builder=AlertDialog.Builder(it)
                builder.apply {
                    setPositiveButton("Accept",DialogInterface.OnClickListener { dialog, id ->
                        main?.user?.delete(db)
                        val intent: Intent = Intent(main,Login::class.java)
                        startActivity(intent)
                        main?.finish()
                    })
                    setNegativeButton("Cancel",DialogInterface.OnClickListener { dialog, id ->
                        dialog.cancel()
                    })
                }
                builder.setMessage("Do you want delete your account?")?.setTitle("Confirm")
                builder.create()
            }
            alertDialog?.show()
        }

        val btnRefresh=actualView.findViewById<Button>(R.id.btnRefresh)
        btnRefresh.setOnClickListener {
           updateFields()
        }

        return actualView
    }

    fun updateFields()
    {
        txtName?.setText(main?.user?.Name)
        txtLastName?.setText(main?.user?.LastName)
        txtEmail?.setText(main?.user?.Email)
        txtPassword?.setText(main?.user?.Password)
        txtRePassword?.setText(main?.user?.Password)

        main?.title="${main?.user?.Name} ${main?.user?.LastName}"
    }
}