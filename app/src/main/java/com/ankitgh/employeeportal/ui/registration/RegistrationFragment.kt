package com.ankitgh.employeeportal.ui.registration

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import com.ankitgh.employeeportal.utils.Status
import com.ankitgh.employeeportal.utils.isValidEmail
import com.ankitgh.employeeportal.utils.isValidPassword
import com.ankitgh.employeeportal.utils.showSnackBar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.registration_fragment.*
import pub.devrel.easypermissions.AfterPermissionGranted
import pub.devrel.easypermissions.EasyPermissions
import timber.log.Timber

@AndroidEntryPoint
class RegistrationFragment : Fragment(), View.OnClickListener {

    companion object {
        private const val RC_STORAGE_PERMISSION: Int = 101
        private const val REQUEST_CODE_GALLERY_INTENT: Int = 102
        private const val GALLERY_INTENT_TYPE: String = "image/*"
    }

    private lateinit var navController: NavController
    private val viewModel: RegistrationViewModel by viewModels()
    private var pickedImageURI: Uri = Uri.EMPTY

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        view.clearFocus()
        navController = Navigation.findNavController(view)
        register_profile_imageview.setOnClickListener(this)
        register_button.setOnClickListener(this)
        observeEditFieldsAndClearOnEdit()
    }

    private fun observeEditFieldsAndClearOnEdit() {
        register_employeeid_editext.doAfterTextChanged {
            if (register_employeeId_inputLayout.isErrorEnabled) register_employeeId_inputLayout.error = null
        }
        register_username_editext.doAfterTextChanged {
            if (register_username_inputlayout.isErrorEnabled) register_username_inputlayout.error = null
        }
        register_designation_editext.doAfterTextChanged {
            if (register_designation_inputlayout.isErrorEnabled) register_designation_inputlayout.error = null
        }
        register_email_editext.doAfterTextChanged {
            if (register_email_inputlayout.isErrorEnabled) register_email_inputlayout.error = null
        }
        register_password_editext.doAfterTextChanged {
            if (register_password_inputlayout.isErrorEnabled) register_password_inputlayout.error = null
        }
    }

    private fun validateUser(userSchema: UserSchema): Boolean {
        var result = true
        if (userSchema.employeeid.isEmpty()) {
            register_employeeId_inputLayout.error = getString(R.string.error_enter_valid_employeeid)
            result = false
        }
        if (userSchema.username.isEmpty()) {
            register_username_inputlayout.error = getString(R.string.error_enter_valid_username)
            result = false
        }
        if (userSchema.designation.isEmpty()) {
            register_designation_inputlayout.error = getString(R.string.error_enter_valid_designation)
            result = false
        }
        if (!userSchema.email.isValidEmail()) {
            register_email_inputlayout.error = getString(R.string.error_enter_valid_email)
            result = false
        }
        if (!userSchema.password.isValidPassword()) {
            register_password_inputlayout.error = getString(R.string.error_enter_valid_password)
            result = false
        }
        if (userSchema.photoUrl == null || userSchema.photoUrl == Uri.EMPTY) {
            Timber.e("Profile image is not set")
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
                getString(R.string.storage_rationale),
                RC_STORAGE_PERMISSION,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        }
    }

    private fun hasCameraPermission(): Boolean {
        return EasyPermissions.hasPermissions(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // EasyPermissions handles the request result.
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_GALLERY_INTENT && data != null) {
            pickedImageURI = data.data ?: Uri.EMPTY
            register_profile_imageview.setImageURI(pickedImageURI)
        }
    }

    override fun onClick(view: View?) {
        when (view) {
            register_profile_imageview -> externalStorageRequestTask()
            register_button -> registerUser()
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = GALLERY_INTENT_TYPE
        startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY_INTENT)
    }

    private fun registerUser() {
        val user = UserSchema(
            employeeid = register_employeeid_editext.text.toString(),
            username = register_username_editext.text.toString(),
            designation = register_designation_editext.text.toString(),
            email = register_email_editext.text.toString(),
            password = register_password_editext.text.toString(),
            photoUrl = pickedImageURI
        )
        if (validateUser(user)) {
            viewModel.registerUser(user).observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Status.ERROR -> {
                        Timber.e("Error while registering user : Exception - ${it.message}")
                        showSnackBar(requireView(), getString(R.string.error_in_user_creation))
                    }
                    Status.SUCCESS -> {
                        Timber.d("User is registered")
                        navigateToHomeActivity()
                    }
                    Status.LOADING -> {
                        if (it.isloading) register_login_progress.visibility = View.VISIBLE else register_login_progress.visibility = View.GONE
                    }
                }
            })
        }
    }

    private fun navigateToHomeActivity() {
        Timber.d("Navigating to HomeActivity from RegistrationActivity")
        navController.navigate(R.id.action_registrationFragment_to_homeActivity)
        requireActivity().finish()
    }
}