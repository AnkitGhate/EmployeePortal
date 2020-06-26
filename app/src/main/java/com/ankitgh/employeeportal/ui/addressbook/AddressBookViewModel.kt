package com.ankitgh.employeeportal.ui.addressbook

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore

class AddressBookViewModel @ViewModelInject constructor(firestoreDB: FirebaseFirestore) : ViewModel()
