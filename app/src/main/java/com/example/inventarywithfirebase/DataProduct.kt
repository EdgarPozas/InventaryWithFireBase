package com.example.inventarywithfirebase

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.auth.User

data class DataProduct(
    var Id:String="",
    var UserId:String="",
    var Name:String="",
    var Description:String="") {

    fun save(db:FirebaseFirestore): Task<DocumentReference> {
        val product=hashMapOf(
            "Name" to Name,
            "Description" to Description,
            "UserId" to UserId
        )

        return db.collection("products").add(product)
    }

    fun update(db:FirebaseFirestore): Task<Void> {
        return db.collection("products").document(Id).update(
            "Name",Name,
            "Description",Description
        )
    }

    fun delete(db:FirebaseFirestore): Task<Void> {
        return db.collection("products").document(Id).delete()
    }

    companion object{
        fun findAll(db:FirebaseFirestore,id: String): Task<QuerySnapshot> {
            return db.collection("products").whereEqualTo("UserId", id).get()
        }
    }
}