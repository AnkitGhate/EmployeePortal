package com.ankitgh.employeeportal.ui.onboarding

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.utils.toggleHideyBar
import kotlinx.android.synthetic.main.fragment_lets_get_started.*

class LetsGetStartedFragment : Fragment() {

    lateinit var onBoardingNavController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_lets_get_started, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBoardingNavController = Navigation.findNavController(view)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setFragmentToFullScreen()
        startButton.setOnClickListener {
            onBoardingNavController.navigate(R.id.action_letsGetStartedFragment_to_loginFragment)
        }
    }

    private fun setFragmentToFullScreen() {
        val decorView = requireActivity().window.decorView
        decorView.setOnSystemUiVisibilityChangeListener {
            val height = decorView.height
            Log.i("TAG", "Current height: $height")
        }
        var uiOptions = requireActivity().window.decorView.systemUiVisibility
        uiOptions = toggleHideyBar(uiOptions)
    }
}
