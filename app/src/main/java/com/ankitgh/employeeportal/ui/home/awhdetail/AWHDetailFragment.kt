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

package com.ankitgh.employeeportal.ui.home.awhdetail

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.db.williamchart.ExperimentalFeature
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.awh_detail_fragment.*
import kotlinx.android.synthetic.main.titlebar.*

@ExperimentalFeature
@AndroidEntryPoint
class AWHDetailFragment : Fragment() {
    private var sharedElement: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            setPathMotion(MaterialArcMotion())
        }
        arguments?.let {
            sharedElement = AWHDetailFragmentArgs.fromBundle(it).awhSharedMotionElement
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.awh_detail_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        ViewCompat.setTransitionName(awh_root, sharedElement)
        title_tv.text = getString(R.string.awhfragment_title_text)
        setupGraph()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navBackButton.setOnClickListener {
            Navigation.findNavController(view).navigateUp()
        }
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
