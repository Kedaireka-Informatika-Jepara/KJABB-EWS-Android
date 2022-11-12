package rekayasaagromarin.ews3swj.ui.profile

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityProfileBinding
import rekayasaagromarin.ews3swj.preferences.PreferencesManager
import rekayasaagromarin.ews3swj.ui.profile.changepassword.ChangePasswordActivity
import rekayasaagromarin.ews3swj.ui.profile.editprofile.EditProfileActivity

class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    private val preferencesManager: PreferencesManager by lazy { PreferencesManager(this) }
    private val profileViewModel: ProfileViewModel by viewModels()
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

    private fun actionButton() {
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

    companion object {
        var isUpdate = false
    }
}