package com.example.inventarywithfirebase

import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import java.io.Serializable

data class DataUser(
        var Id:String="",
        var Name:String="",
        var LastName:String="",
        var Email:String="",
        var Password:String="") : Serializable {

    fun save(db:FirebaseFirestore): Task<DocumentReference> {
        val user=hashMapOf(
                "Name" to Name,
                "LastName" to LastName,
                "Email" to Email,
                "Password" to Password
        )

        return db.collection("users").add(user)
    }

    fun update(db:FirebaseFirestore): Task<Void> {
        return db.collection("users").document(Id).update(
            "Name",Name,
            "LastName",LastName,
            "Email",Email,
            "Password",Password
        )
    }

    fun delete(db:FirebaseFirestore): Task<Void> {
        return db.collection("users").document(Id).delete()
    }

    companion object{
        fun login(db:FirebaseFirestore,email: String,password: String): Task<QuerySnapshot> {
            return db.collection("users")
                    .whereEqualTo("Email", email)
                    .whereEqualTo("Password", password)
                    .get()
        }
    }
}