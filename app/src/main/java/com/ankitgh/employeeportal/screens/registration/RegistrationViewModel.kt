package com.ankitgh.employeeportal.screens.registration

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.ankitgh.employeeportal.data.firestoremodel.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class RegistrationViewModel @ViewModelInject constructor(firebaseDB: FirebaseFirestore, firebaseAuth: FirebaseAuth) : ViewModel() {

    fun registerUser(user: User) {
        //firebaseAuth.signInWithEmailAndPassword(user.email,user.password)
    }
}