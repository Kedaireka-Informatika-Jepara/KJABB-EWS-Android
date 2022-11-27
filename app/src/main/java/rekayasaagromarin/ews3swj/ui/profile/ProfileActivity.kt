package rekayasaagromarin.ews3swj.ui.profile

import android.app.Activity
import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityProfileBinding
import rekayasaagromarin.ews3swj.preferences.PreferencesManager
import rekayasaagromarin.ews3swj.ui.auth.payment.UploadRequestBody
import rekayasaagromarin.ews3swj.ui.profile.changepassword.ChangePasswordActivity
import rekayasaagromarin.ews3swj.ui.profile.editprofile.EditProfileActivity
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val preferencesManager: PreferencesManager by lazy { PreferencesManager(this) }
    private val profileViewModel: ProfileViewModel by viewModels()
    private var selectedImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        actionButton()
        setUser()
        getUser()
    }

    private fun initToolbar() {
        with(binding.actionProfileToolbar.viewToolbar) {
            title = getString(R.string.user_profile)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun uploadImage() {
        val parcelFileDescriptor =
            this.contentResolver?.openFileDescriptor(selectedImageUri!!, "r", null) ?: return

        val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
        val file = File(
            this.cacheDir,
            this.contentResolver!!.getFileName(selectedImageUri!!)
        )
        val outputStream = FileOutputStream(file)
        inputStream.copyTo(outputStream)

        val body = UploadRequestBody(file, "image")

        profileViewModel.uploadPhoto(
            binding.tvProfileEmail.text.toString().toRequestBody("multipart/form-data".toMediaTypeOrNull()),
            MultipartBody.Part.createFormData("image", file.name, body)
        )
    }

    private fun actionButton() {
        val startForGetImage =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    selectedImageUri = result.data?.data
//                    val file = File(
//                        this.cacheDir,
//                        this.contentResolver.getFileName(selectedImageUri!!)
//                    )

                    binding.imgProfile.apply {
                        visibility = View.VISIBLE
                        setImageURI(selectedImageUri)
                    }
                    uploadImage()
                }
            }

        binding.imgProfile.setOnClickListener {
            val intentSelectImage = Intent()
            intentSelectImage.also {
                it.type = "image/*"
                val mimeType = arrayOf("image/png", "image/jpg", "image/jpeg")
                it.action = Intent.ACTION_PICK
                it.putExtra(Intent.EXTRA_MIME_TYPES, mimeType)
            }
            startForGetImage.launch(intentSelectImage)
        }

        binding.btnEditProfile.setOnClickListener {
            val name = binding.tvProfileName.text
            val email = binding.tvProfileEmail.text

            val intentEditProfile = Intent(this, EditProfileActivity::class.java)
            intentEditProfile.putExtra(EditProfileActivity.EXTRA_NAME, name)

            with(intentEditProfile) {
                putExtra(EditProfileActivity.EXTRA_NAME, name)
                putExtra(EditProfileActivity.EXTRA_EMAIL, email)
            }

            startActivity(intentEditProfile)
        }

        binding.btnChangePassword.setOnClickListener {
            val intentChangePassword = Intent(this, ChangePasswordActivity::class.java)
            startActivity(intentChangePassword)
        }
    }

    private fun setUser() {
        val userId = preferencesManager.getId()
        userId?.let { profileViewModel.setUser(it) }

        with(profileViewModel) {
            isLoading.observe(this@ProfileActivity) {
                showLoading(it)
            }

            isError.observe(this@ProfileActivity) {
                showError(it)
            }

            message.observe(this@ProfileActivity) {
                if (it != null) {
                    showMessage(it)
                }
            }
        }
    }

    private fun getUser() {
        profileViewModel.getUser().observe(this) {
            with(binding) {
//                imgProfile.setImageResource(it.image)
                tvProfileName.text = it.name
                tvProfileEmail.text = it.email
                tvProfileRole.text = it.role
                tvProfileMembership.text = it.membership
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                layoutProfile.visibility = View.GONE
                layoutActionProfile.visibility = View.GONE
                imgProfile.visibility = View.GONE
                loadingProfile.visibility = View.VISIBLE
            } else {
                layoutProfile.visibility = View.VISIBLE
                layoutActionProfile.visibility = View.VISIBLE
                imgProfile.visibility = View.VISIBLE
                loadingProfile.visibility = View.GONE
            }
        }
    }

    private fun showError(isError: Boolean) {
        with(binding) {
            if (isError) {
                layoutProfile.visibility = View.GONE
                layoutActionProfile.visibility = View.GONE
                imgProfile.visibility = View.GONE
                tvProfileError.visibility = View.VISIBLE
            } else {
                tvProfileError.visibility = View.GONE
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    override fun onResume() {
        super.onResume()
        if (isUpdate) {
            setUser()
            getUser()
            isUpdate = false
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

    companion object {
        var isUpdate = false
    }
}