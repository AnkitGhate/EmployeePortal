package com.ankitgh.employeeportal.screens.addressbook

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.common.getPlaceHolderListOfContacts
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.address_book_fragment.*
import kotlinx.android.synthetic.main.home_fragment.*

@AndroidEntryPoint
class AddressBookFragment : Fragment() {

    private val mViewModel: AddressBookViewModel by viewModels()
    private lateinit var mAdapter: AddressBookAdapter
    private var mContactList = ArrayList<ContactItem>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.address_book_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Glide.with(this).load(FirebaseAuth.getInstance().currentUser?.photoUrl.toString()).into(profile_image)
        setupRecyclerView()
        handleSearchRequest()
    }

    private fun handleSearchRequest() {
        search_input_editext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })
    }

    private fun filter(queryString: String) {
        val filteredList: ArrayList<ContactItem> = mContactList.filter { it.name.toLowerCase().startsWith(queryString, ignoreCase = true) } as ArrayList<ContactItem>
        mAdapter.filterList(filteredList)
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        contacts_recyclerview.layoutManager = linearLayoutManager
        //TODO : Remove dummy list later
        mContactList = getPlaceHolderListOfContacts()
        mAdapter = AddressBookAdapter(mContactList)
        contacts_recyclerview.adapter = mAdapter
    }
}