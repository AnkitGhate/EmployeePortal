package com.ankitgh.employeeportal.ui.home.addLeave

import android.animation.Animator
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.add_leave_fragment.*
import kotlinx.android.synthetic.main.titlebar.*
import java.util.*

@AndroidEntryPoint
class AddLeaveFragment : Fragment() {

    private val viewModel: AddLeaveViewModel by viewModels()
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.add_leave_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        title_tv.text = "Leaves"
        val date: Date = Calendar.getInstance().time
        calenderview.setDate(date)
        calenderview.setPreviousButtonImage(resources.getDrawable(R.drawable.ic_arrow_back, null))
        calenderview.setForwardButtonImage(resources.getDrawable(R.drawable.ic_arrow_forward, null))

        navBackButton.setOnClickListener {
            navController.popBackStack(R.id.homeFragment, false)
        }
        add_leaves_button.setOnClickListener {
            val selectedDates: List<Calendar> = calenderview.selectedDates
            Log.e("SELECTED DATES", selectedDates.toString())
            leavesdetailscardview.visibility = View.GONE
            loading_anim_view.visibility = View.VISIBLE
            loading_anim_view.setAnimation(R.raw.animation_loading_complete)

            loading_anim_view.addAnimatorUpdateListener {
                it.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationRepeat(animation: Animator?) {
                        Log.e("tttt", "onAnimatinRepeat")
                    }

                    override fun onAnimationEnd(animation: Animator?) {
                        navController.popBackStack(R.id.homeFragment, false)
                    }

                    override fun onAnimationCancel(animation: Animator?) {
                        Log.e("tttt", "onAnimatinCancel")
                    }

                    override fun onAnimationStart(animation: Animator?) {
                        Log.e("tttt", "onAnimatinStart")
                    }
                })
            }
        }
    }
}