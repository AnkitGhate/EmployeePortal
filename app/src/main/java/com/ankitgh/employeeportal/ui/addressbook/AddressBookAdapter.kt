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

import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.utils.inflate
import kotlinx.android.synthetic.main.addressbook_item.view.*

class AddressBookAdapter(contactsList: ArrayList<ContactItem>) : RecyclerView.Adapter<AddressBookAdapter.ContactViewHolder>() {

    private var contactItemList: ArrayList<ContactItem> = contactsList

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindContactItem(contactItem: ContactItem) {
            itemView.username_tv.text = contactItem.name
            itemView.designation_tv.text = contactItem.designation
            itemView.contact_container.animation = AnimationUtils.loadAnimation(itemView.context, R.anim.card_transition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val rootView = parent.inflate(R.layout.addressbook_item, false)
        return ContactViewHolder(rootView)
    }

    override fun getItemCount(): Int {
        return contactItemList.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val contact = contactItemList[position]
        holder.bindContactItem(contact)
    }

    fun filterList(filteredList: ArrayList<ContactItem>) {
        contactItemList = filteredList
        notifyDataSetChanged()
    }
}
