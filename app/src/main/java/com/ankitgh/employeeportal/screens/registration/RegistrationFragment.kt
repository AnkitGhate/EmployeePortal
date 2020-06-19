package com.ankitgh.employeeportal.screens.registration

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.common.isValidEmail
import com.ankitgh.employeeportal.common.isValidPassword
import com.ankitgh.employeeportal.data.firestoremodel.User
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.login_fragment.*
import kotlinx.android.synthetic.main.login_fragment.password_inputlayout
import kotlinx.android.synthetic.main.registration_fragment.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    companion object {
        private const val RC_STORAGE_PERMISSION: Int = 12523
        private const val REQUEST_CODE_GALLERY_INTENT: Int = 12527
    }

    private lateinit var navController: NavController
    private var mPickedImageURI: Uri? = null
    private val mViewModel: RegistrationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration_fragment, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        add_profile_imageview.setOnClickListener {
            externalStorageRequestTask()
        }
        register_button.setOnClickListener {
            val user = User(
                employeeid = employeeid_editext.text.toString(),
                username = username_editext.text.toString(),
                designation = designation_editext.text.toString(),
                email = email_editext.text.toString(),
                password = password_editext.text.toString()
            )
            if (validateUser(user)) {
                mViewModel.registerUser(user)
            }
        }
    }

    private fun validateUser(user: User): Boolean {
        var result = true

        if (user.employeeid.isEmpty()) {
            password_inputlayout.error = "Please enter a valid employeeID"
            result = false
        }
        if (user.username.isEmpty()) {
            password_inputlayout.error = "Please enter a valid username"
            result = false
        }
        if (user.designation.isEmpty()) {
            password_inputlayout.error = "Please enter a valid designation"
            result = false
        }
        if (!user.email.isValidEmail()) {
            email_input_layout.error = "Please enter a valid email"
            result = false
        }
        if (!user.password.isValidPassword()) {
            password_inputlayout.error = "Please enter a valid password"
            result = false
        }
        return result
    }

    @AfterPermissionGranted(RC_STORAGE_PERMISSION)
    fun externalStorageRequestTask() {
        if (hasCameraPermission()) {
            openGallery()
        } else {
            // Ask for one permission
            EasyPermissions.requestPermissions(
                this,
                "",
                RC_STORAGE_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY_INTENT)
    }

    private fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_GALLERY_INTENT && data != null) {
            mPickedImageURI = data.data
            add_profile_imageview.setImageURI(mPickedImageURI)
        }
    }
}