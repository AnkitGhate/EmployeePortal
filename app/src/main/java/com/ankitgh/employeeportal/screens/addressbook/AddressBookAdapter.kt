package com.ankitgh.employeeportal.screens.addressbook

import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.common.inflate
import kotlinx.android.synthetic.main.addressbook_item.view.*
import kotlinx.android.synthetic.main.feed_item.view.profile_imageview

class AddressBookAdapter(contactsList: ArrayList<ContactItem>) : RecyclerView.Adapter<AddressBookAdapter.ContactViewHolder>() {

    private val contactItemList: ArrayList<ContactItem> = contactsList

    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindContactItem(contactItem: ContactItem) {
            itemView.profile_imageview.hash = contactItem.name.hashCode()
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
}