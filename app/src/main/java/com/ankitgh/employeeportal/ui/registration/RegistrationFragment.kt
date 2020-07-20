package com.ankitgh.employeeportal.ui.registration

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
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.ankitgh.employeeportal.R
import com.ankitgh.employeeportal.data.model.firestoremodel.UserSchema
import com.ankitgh.employeeportal.utils.Status
import com.ankitgh.employeeportal.utils.isValidEmail
import com.ankitgh.employeeportal.utils.isValidPassword
import com.google.android.material.snackbar.Snackbar
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
    }

    private lateinit var navController: NavController
    private val mViewModel: RegistrationViewModel by viewModels()
    private var mPickedImageURI: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registration_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        add_profile_imageview.setOnClickListener(this)
        register_button.setOnClickListener(this)
    }

    private fun validateUser(userSchema: UserSchema): Boolean {
        var result = true

        if (userSchema.employeeid.isEmpty()) {
            password_inputlayout.error = "Please enter a valid employeeID"
            result = false
        }
        if (userSchema.username.isEmpty()) {
            password_inputlayout.error = "Please enter a valid username"
            result = false
        }
        if (userSchema.designation.isEmpty()) {
            password_inputlayout.error = "Please enter a valid designation"
            result = false
        }
        if (!userSchema.email.isValidEmail()) {
            email_inputlayout.error = "Please enter a valid email"
            result = false
        }
        if (!userSchema.password.isValidPassword()) {
            password_inputlayout.error = "Please enter a valid password"
            result = false
        }
        if (userSchema.photoUrl == null) {
            Timber.e("Profile URI is null")
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
            mPickedImageURI = data.data
            add_profile_imageview.setImageURI(mPickedImageURI)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            add_profile_imageview -> {
                externalStorageRequestTask()
            }
            register_button -> {
                registerUser()
            }
        }
    }

    private fun openGallery() {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        startActivityForResult(galleryIntent, REQUEST_CODE_GALLERY_INTENT)
    }

    private fun registerUser() {
        val user = UserSchema(
            employeeid = employeeid_editext.text.toString(),
            username = username_editext.text.toString(),
            designation = designation_editext.text.toString(),
            email = email_editext.text.toString(),
            password = password_editext.text.toString(),
            photoUrl = mPickedImageURI
        )
        if (validateUser(user)) {
            progressBar.visibility = View.VISIBLE
            mViewModel.registerUser(user).observe(viewLifecycleOwner, Observer {
                when (it.status) {
                    Status.ERROR -> {
                        progressBar.visibility = View.GONE
                        Timber.e("Error while registering user : Exception - ${it.message}")
                        Snackbar.make(
                            requireView(), "[Error] : There seems to be some issue while creating user.Please try again later",
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                    Status.SUCCESS -> {
                        Timber.d("User is registered")
                        progressBar.visibility = View.GONE
                        navigateToHomeActivity()
                    }
                    Status.LOADING -> {
                        progressBar.visibility = View.VISIBLE
                    }
                    Status.UNKNOWN -> TODO()
                }
            })
        }
    }

    private fun navigateToHomeActivity() {
        navController.navigate(R.id.action_registrationFragment_to_homeActivity)
        requireActivity().finish()
    }
}
