package com.example.inventarywithfirebase

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.firebase.firestore.FirebaseFirestore

class ProductAll : Fragment() {

    private var productList:ListView?=null
    private var main:Main?=null
    private var actualView:View?=null
    private var products:ArrayList<DataProduct>?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        actualView=inflater.inflate(R.layout.fragment_product_all, container, false)

        main=activity as Main

        val db=FirebaseFirestore.getInstance()

        productList=actualView?.findViewById<ListView>(R.id.productList)
        val btnRefresh=actualView?.findViewById<Button>(R.id.btnRefresh)
        btnRefresh?.setOnClickListener {
            updateList()
        }

        val btnClose=actualView?.findViewById<Button>(R.id.btnClose)
        btnClose?.setOnClickListener {
            val intent=Intent(Main@main,Login::class.java )
            startActivity(intent)
            main?.finish()
        }

        productList?.setOnItemLongClickListener { parent, view, position, id ->
            products?.get(position)?.delete(db)
            updateList()
            return@setOnItemLongClickListener true
        }

        updateList()
        return actualView
    }

    fun updateList()
    {
        val db = FirebaseFirestore.getInstance()

        DataProduct.findAll(db,main?.user?.Id?:"").addOnSuccessListener { snapshot ->
            var items=ArrayList<String>()
            var documents=snapshot.toObjects(DataProduct::class.java)
            products=ArrayList<DataProduct>()
            var counter=0
            for(document in documents){
                document.Id=snapshot.documents[counter].id
                products?.add(document)
                items.add("${document.Name} ${document.Description}")
                counter++
            }
            val arrayAdapter=ArrayAdapter<String>(main!!.baseContext,android.R.layout.simple_list_item_1,items)
            productList?.adapter=arrayAdapter
        }
    }
}