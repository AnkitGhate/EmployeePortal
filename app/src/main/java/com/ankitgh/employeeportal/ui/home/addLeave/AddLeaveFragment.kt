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

package com.ankitgh.employeeportal.ui.home.addLeave

import android.animation.Animator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.google.android.material.transition.MaterialArcMotion
import com.google.android.material.transition.MaterialContainerTransform
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.add_leave_fragment.*
import kotlinx.android.synthetic.main.titlebar.*
import timber.log.Timber
import java.util.*

@AndroidEntryPoint
class AddLeaveFragment : Fragment() {
    private var sharedElement: String? = null
    private val viewModel: AddLeaveViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedElementEnterTransition = MaterialContainerTransform().apply {
            setPathMotion(MaterialArcMotion())
        }
        arguments?.let {
            sharedElement = AddLeaveFragmentArgs.fromBundle(it).addleavesSharedMotionElement
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_leave_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ViewCompat.setTransitionName(addleaves_root, sharedElement)
        navController = Navigation.findNavController(view)
        title_tv.text = getString(R.string.title_text_leavesfragment)
        val date: Date = Calendar.getInstance().time
        calenderview.setDate(date)
        calenderview.setPreviousButtonImage(resources.getDrawable(R.drawable.ic_arrow_back, null))
        calenderview.setForwardButtonImage(resources.getDrawable(R.drawable.ic_arrow_forward, null))

        navBackButton.setOnClickListener {
            navController.popBackStack(R.id.homeFragment, false)
        }
        add_leaves_button.setOnClickListener {
            val selectedDates: List<Calendar> = calenderview.selectedDates
            Timber.d(selectedDates.toString())
            leavesdetailscardview.visibility = View.GONE
            loading_anim_view.visibility = View.VISIBLE
            loading_anim_view.setAnimation(R.raw.animation_loading_complete)

            loading_anim_view.addAnimatorUpdateListener {
                it.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                        Timber.d("onAnimatinRepeat")
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        navController.popBackStack(R.id.homeFragment, false)
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        Timber.d("onAnimatinCancel")
                    }

                    override fun onAnimationStart(animation: Animator?) {
                        Timber.d("onAnimatinStart")
                    }
                })
            }
        }
    }
}
