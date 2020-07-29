/*
 * Copyright 2020 Ankit Ghate
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ankitgh.employeeportal.ui.addressbook

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.utils.getPlaceHolderListOfContacts
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.address_book_fragment.*
import java.util.*
import kotlin.collections.ArrayList


@AndroidEntryPoint
class AddressBookFragment : Fragment() {

    private val mViewModel: AddressBookViewModel by viewModels()
    private lateinit var mAdapter: AddressBookAdapter
    private var mContactList = ArrayList<ContactItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.address_book_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val photouri: Uri = Uri.parse(FirebaseAuth.getInstance().currentUser?.photoUrl.toString())
        Glide.with(this).load(photouri).into(profile_image)
        setupRecyclerView()
        handleSearchRequest()
    }

    private fun handleSearchRequest() {
        search_input_editext.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                //do nothing
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // do nothing
            }
        })
    }

    private fun filter(queryString: String) {
        val filteredList: ArrayList<ContactItem> = mContactList.filter { it.name.toLowerCase(Locale.ROOT).startsWith(queryString, ignoreCase = true) } as ArrayList<ContactItem>
        mAdapter.filterList(filteredList)
    }

    private fun setupRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(activity)
        contacts_recyclerview.layoutManager = linearLayoutManager
        // TODO : Remove dummy list later
        val mDividerItemDecoration = DividerItemDecoration(
            contacts_recyclerview.context,
            linearLayoutManager.orientation
        )
        contacts_recyclerview.addItemDecoration(mDividerItemDecoration)
        mContactList = getPlaceHolderListOfContacts()
        mAdapter = AddressBookAdapter(mContactList)
        contacts_recyclerview.adapter = mAdapter
    }
}
