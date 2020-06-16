package com.ankitgh.employeeportal.screens.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.utils.Status
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.create_post_fragment.*

@AndroidEntryPoint
class CreatePostFragment : BottomSheetDialogFragment() {

    private val viewModel: CreatePostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.create_post_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        send_button.setOnClickListener {
            if (post_input_editext.text.toString().isBlank()) {
                post_textinputlayout.error = "Please enter something before sending"
            }
            progressBar.visibility = View.VISIBLE
            viewModel.sendPostToFirebaseDB(post_input_editext.text.toString()).observe(this, Observer {
                when (it.status) {
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        dismiss()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    Status.SUCCESS -> {
                        progressBar.visibility = View.GONE
                        dismiss()
                    }
                }
            })
        }
    }

}