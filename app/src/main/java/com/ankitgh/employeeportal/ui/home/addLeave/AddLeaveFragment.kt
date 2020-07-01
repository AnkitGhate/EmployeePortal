package com.ankitgh.employeeportal.ui.home.addLeave

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ankitgh.employeeportal.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.add_leave_fragment.*
import kotlinx.android.synthetic.main.titlebar.*
import java.util.*

@AndroidEntryPoint
class AddLeaveFragment : Fragment() {

    private val viewModel: AddLeaveViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_leave_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        title_tv.text = "Leaves"
        val date: Date = Calendar.getInstance().time
        calenderview.setDate(date)

        add_leaves_button.setOnClickListener {
            val selectedDates: List<Calendar> = calenderview.selectedDates
            Log.e("SELECTED DATES", selectedDates.toString())
        }

    }

}