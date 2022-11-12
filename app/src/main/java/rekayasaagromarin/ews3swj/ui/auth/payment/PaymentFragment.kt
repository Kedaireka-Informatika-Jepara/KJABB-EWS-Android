package rekayasaagromarin.ews3swj.ui.auth.payment

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.FragmentPaymentBinding
import rekayasaagromarin.ews3swj.ui.auth.login.LoginFragment
import rekayasaagromarin.ews3swj.ui.auth.register.RegisterFragment
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val paymentViewModel: PaymentViewModel by viewModels()
    private val binding get() = _binding
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toLogin()
        toRegistration()
        selectImage()
        uploadImage()
    }

    private fun uploadImage() {
        binding?.btnSend?.setOnClickListener {
            if (binding!!.edtEmail.text.toString().isEmpty()) {
                Toast.makeText(context, "Email is required", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (binding!!.edtEmail.text?.matches(emailPattern.toRegex()) != true) {
                Toast.makeText(context, "Invalid email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (selectedImageUri == null) {
                Toast.makeText(context, "Select an Image First", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            paymentViewModel.emailCheck(binding!!.edtEmail.text.toString())
        }

        with(paymentViewModel) {
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            message.observe(viewLifecycleOwner) {
                if (it != null) {
                    showMessage(it)
                }
            }

            status.observe(viewLifecycleOwner) {
                if (it == 1) {
                    val parcelFileDescriptor =
                        context?.contentResolver?.openFileDescriptor(selectedImageUri!!, "r", null)
                            ?: return@observe

                    val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
                    val file = File(
                        requireContext().cacheDir,
                        requireContext().contentResolver!!.getFileName(selectedImageUri!!)
                    )
                    val outputStream = FileOutputStream(file)
                    inputStream.copyTo(outputStream)

                    val body = UploadRequestBody(file, "image")

                    paymentViewModel.uploadPayment(
                        binding?.edtEmail?.text.toString()
                            .toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                        MultipartBody.Part.createFormData("image", file.name, body)
                    )
                }
            }
        }
    }

    private fun selectImage() {
        val startForGetImage =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    selectedImageUri = result.data?.data
                    val file = File(
                        requireContext().cacheDir,
                        requireContext().contentResolver.getFileName(selectedImageUri!!)
                    )

                    binding?.tvImageName?.apply {
                        visibility = View.VISIBLE
                        text = file.name
                    }
                }
            }

        binding?.btnChooseImage?.setOnClickListener {
            val intentSelectImage = Intent()
            intentSelectImage.also {
                it.type = "image/*"
                val mimeType = arrayOf("image/png", "image/jpg", "image/jpeg")
                it.action = Intent.ACTION_PICK
                it.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
            }

            startForGetImage.launch(intentSelectImage)

        }
    }

    private fun toLogin() {
        binding?.paymentToLogin?.setOnClickListener {
            val mLoginFragment = LoginFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.commit {
                addToBackStack(null)
                replace(
                    R.id.frame_container,
                    mLoginFragment,
                    LoginFragment::class.java.simpleName
                )
            }
        }
    }

    private fun toRegistration() {
        binding?.paymentToRegistration?.setOnClickListener {
            val mRegisterFragment = RegisterFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.commit {
                addToBackStack(null)
                replace(
                    R.id.frame_container,
                    mRegisterFragment,
                    RegisterFragment::class.java.simpleName
                )
            }
        }
    }

    private fun ContentResolver.getFileName(fileUri: Uri): String {
        var name = ""
        val returnCursor = this.query(fileUri, null, null, null, null)
        if (returnCursor != null) {
            val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            returnCursor.moveToFirst()
            name = returnCursor.getString(nameIndex)
            returnCursor.close()
        }
        return name
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.apply {
            if (isLoading) {
                loadingPayment.visibility = View.VISIBLE
            } else {
                loadingPayment.visibility = View.GONE
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }


}