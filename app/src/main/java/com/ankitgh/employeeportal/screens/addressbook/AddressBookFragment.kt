package com.ankitgh.employeeportal.screens.addressbook

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.common.getPlaceHolderListOfContacts
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.address_book_fragment.*

@AndroidEntryPoint
class AddressBookFragment : Fragment() {

    private val viewModel: AddressBookViewModel by viewModels()
    private lateinit var addressBookAdapter: AddressBookAdapter
    private var contactsList = ArrayList<ContactItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.address_book_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        contacts_recyclerview.layoutManager = linearLayoutManager
        addressBookAdapter = AddressBookAdapter(getPlaceHolderListOfContacts())
        contacts_recyclerview.adapter = addressBookAdapter
    }
}