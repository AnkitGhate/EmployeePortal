package com.ankitgh.employeeportal.ui.home.awhdetail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ankitgh.employeeportal.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.awh_detail_fragment.*
import kotlinx.android.synthetic.main.titlebar.*

@AndroidEntryPoint
class AWHDetailFragment : Fragment() {

    private val viewModel: AWHDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.awh_detail_fragment, container, false)
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        title_tv.text = "AWH Details"

        setupGraph()
    }

    @SuppressLint("Range")
    private fun setupGraph() {
        linechartlayout.gradientFillColors =
            intArrayOf(
                Color.parseColor("#00ADB5"),
                Color.TRANSPARENT
            )
        linechartlayout.animation.duration = animationDuration
        linechartlayout.onDataPointTouchListener = { index, _, _ ->
            lineChartValue.text =
                lineSet.toList()[index]
                    .second
                    .toString()
        }
        linechartlayout.animate(lineSet)
    }

    companion object {
        private const val animationDuration = 600L

        private val lineSet = linkedMapOf(
            "JAN" to 7.41f,
            "FEB" to 5.41f,
            "MAR" to 6.41f,
            "APR" to 8.50f,
            "MAY" to 5.63f,
            "JUNE" to 4.23f,
            "JULY" to 7.45f
//            "AUG" to 00.00f,
//            "SEPT" to 00.00f,
//            "OCT" to 00.00f,
//            "NOV" to 00.00f,
//            "DEC" to 00.00f
        )
    }

}