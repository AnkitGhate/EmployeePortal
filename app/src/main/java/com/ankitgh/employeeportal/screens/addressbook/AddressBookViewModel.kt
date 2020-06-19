package com.ankitgh.employeeportal.screens.addressbook

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class AddressBookViewModel @ViewModelInject constructor(firestoreDB: FirebaseFirestore) : ViewModel()