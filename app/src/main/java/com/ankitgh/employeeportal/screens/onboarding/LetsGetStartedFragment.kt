package com.ankitgh.employeeportal.screens.onboarding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.toggleHideyBar
import kotlinx.android.synthetic.main.fragment_lets_get_started.*

class LetsGetStartedFragment : Fragment() {

    companion object {
        @JvmStatic
        fun launchFragment(
            containerView: Int,
            fragmentManager: FragmentManager
        ) {
            val letsGetStartedFragment = LetsGetStartedFragment()
            val transaction = fragmentManager.beginTransaction()
            transaction.replace(containerView, letsGetStartedFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_lets_get_started, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setFragmentToFullScreen()
        startButton.setOnClickListener {
        }
    }

    private fun setFragmentToFullScreen() {
        val decorView = activity!!.window.decorView
        decorView.setOnSystemUiVisibilityChangeListener {
            val height = decorView.height
            Log.i("TAG", "Current height: $height")
        }
        var uiOptions = activity!!.window.decorView.systemUiVisibility
        uiOptions = toggleHideyBar(uiOptions)
    }

    interface listner {
        fun navigateToFragment()
    }
}