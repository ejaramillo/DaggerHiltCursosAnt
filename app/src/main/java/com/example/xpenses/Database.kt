package com.example.xpenses

import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class Database @Inject constructor(private val database: FirebaseFirestore) {

    fun getDaysRef() = database.collection(Constants.COLL_DAYS)

    fun getDayRef(day: String) = getDaysRef().document(day)
}
