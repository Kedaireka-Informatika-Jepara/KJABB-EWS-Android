package rekayasaagromarin.ews3swj.ui.profile.editprofile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityEditProfileBinding
import rekayasaagromarin.ews3swj.model.User
import rekayasaagromarin.ews3swj.preferences.PreferencesManager
import rekayasaagromarin.ews3swj.ui.menu.MainActivity
import rekayasaagromarin.ews3swj.ui.menu.admin.datauser.DataUserFragment
import rekayasaagromarin.ews3swj.ui.profile.ProfileActivity

class EditProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditProfileBinding
    private val editProfileViewModel: EditProfileViewModel by viewModels()
    private val preferencesManager: PreferencesManager by lazy { PreferencesManager(this) }

    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initField()
        editUser()
    }

    private fun initToolbar() {
        with(binding.editProfileToolbar.viewToolbar) {
            title = getString(R.string.edit_profile)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initField() {
        val name = intent.getStringExtra(EXTRA_NAME)
        val email = intent.getStringExtra(EXTRA_EMAIL)

        binding.edtInputEditName.setText(name)
        binding.edtInputEditEmail.setText(email)
    }

    private fun editUser() {
        binding.btnInputEditProfile.setOnClickListener {
            binding.apply {
                when {
                    edtInputEditName.text?.isEmpty() == true -> Toast.makeText(
                        this@EditProfileActivity,
                        "Name is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    edtInputEditEmail.text?.isEmpty() == true -> Toast.makeText(
                        this@EditProfileActivity,
                        "Email is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    edtInputEditEmail.text?.matches(emailPattern.toRegex()) != true -> Toast.makeText(
                        this@EditProfileActivity,
                        "Invalid email address",
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> {
                        val user = User(
                            userId = preferencesManager.getId()!!,
                            name = edtInputEditName.text.toString(),
                            email = edtInputEditEmail.text.toString()
                        )

                        editProfileViewModel.editProfile(
                            user.userId,
                            user.name,
                            user.email
                        )
                    }
                }
            }
        }

        with(editProfileViewModel) {
            isLoading.observe(this@EditProfileActivity) {
                showLoading(it)
            }

            message.observe(this@EditProfileActivity) {
                showMessage(it)
            }

            isSuccess.observe(this@EditProfileActivity) {
                if (it == 200) {
                    ProfileActivity.isUpdate = true
                    DataUserFragment.isUpdateItem = true
                    MainActivity.isUpdate = true
                    onBackPressed()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingInputEditProfile.root.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object{
        const val EXTRA_NAME = "Extra Name"
        const val EXTRA_EMAIL = "Extra Email"
    }
}