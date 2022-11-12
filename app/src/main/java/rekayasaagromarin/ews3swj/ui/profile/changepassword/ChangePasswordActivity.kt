package rekayasaagromarin.ews3swj.ui.profile.changepassword

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityChangePasswordBinding
import rekayasaagromarin.ews3swj.preferences.PreferencesManager

class ChangePasswordActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChangePasswordBinding
    private val preferencesManager: PreferencesManager by lazy { PreferencesManager(this) }
    private val changePasswordViewModel: ChangePasswordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        changePassword()
    }

    private fun initToolbar() {
        with(binding.editPasswordToolbar.viewToolbar) {
            title = getString(R.string.change_password)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun changePassword() {
        binding.btnInputEditPassword.setOnClickListener {
            binding.apply {
                when {
                    edtInputEditOldPassword.text?.isEmpty() == true -> Toast.makeText(
                        this@ChangePasswordActivity,
                        "Current password is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    edtInputEditNewPassword.text?.isEmpty() == true -> Toast.makeText(
                        this@ChangePasswordActivity,
                        "New password is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    edtInputEditConfirmNewPassword.text?.isEmpty() == true -> Toast.makeText(
                        this@ChangePasswordActivity,
                        "Confirm new password is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    edtInputEditNewPassword.text.toString() != edtInputEditConfirmNewPassword.text.toString() -> Toast.makeText(
                        this@ChangePasswordActivity,
                        "Confirm new password is wrong",
                        Toast.LENGTH_SHORT
                    ).show()

                    edtInputEditOldPassword.text.toString() == edtInputEditNewPassword.text.toString() -> Toast.makeText(
                        this@ChangePasswordActivity,
                        "New password and old password can't be the same",
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> {
                        val id = preferencesManager.getId()!!
                        val currentPassword = binding.edtInputEditOldPassword.text.toString()
                        val newPassword = binding.edtInputEditNewPassword.text.toString()

                        changePasswordViewModel.changePassword(id, currentPassword, newPassword)
                    }
                }
            }
        }

        with(changePasswordViewModel) {
            isLoading.observe(this@ChangePasswordActivity) {
                showLoading(it)
            }

            message.observe(this@ChangePasswordActivity) {
                showMessage(it)
            }

            isSuccess.observe(this@ChangePasswordActivity) {
                if (it == 200) {
                    onBackPressed()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.loadingInputEditPassword.root.visibility =
            if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}