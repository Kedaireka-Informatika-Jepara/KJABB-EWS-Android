package rekayasaagromarin.ews3swj.ui.menu.admin.datauser.action

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityUserActionBinding
import rekayasaagromarin.ews3swj.model.User
import rekayasaagromarin.ews3swj.preferences.PreferencesManager
import rekayasaagromarin.ews3swj.ui.auth.AuthActivity
import rekayasaagromarin.ews3swj.ui.menu.MainActivity
import rekayasaagromarin.ews3swj.ui.menu.admin.datauser.DataUserFragment
import rekayasaagromarin.ews3swj.ui.menu.admin.datauser.action.activate.ActivateUserFragment
import rekayasaagromarin.ews3swj.ui.menu.admin.datauser.action.delete.DeleteUserFragment
import rekayasaagromarin.ews3swj.ui.menu.admin.datauser.action.edit.EditUserActivity

class UserActionActivity : AppCompatActivity(), View.OnClickListener {

    private val preferences: PreferencesManager by lazy { PreferencesManager(this) }
    private val userActionViewModel: UserActionViewModel by viewModels()
    private lateinit var binding: ActivityUserActionBinding

    private var idUser = 0
    private var roleId = 0
    private var membershipId = 0
    private var isActivate = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserActionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initUser()
        actionButton()
        refresh()
    }

    private fun initToolbar() {
        with(binding.actionUserToolbar.viewToolbar) {
            title = intent.getStringExtra(EXTRA_NAME)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun initUser() {
        setUser()
        getUser()
    }

    private fun setUser() {
        with(userActionViewModel) {
            setUser(intent.getIntExtra(EXTRA_ID, 0))
            Log.d("Test1", "setUser: ${intent.getIntExtra(EXTRA_ID, 0)}")

            isLoading.observe(this@UserActionActivity) {
                showLoading(it)
            }

            message.observe(this@UserActionActivity) {
                if (it != "") {
                    showMessage(it)
                }
            }

            isError.observe(this@UserActionActivity) {
                showError(it)
            }
        }
    }

    private fun getUser() {
        userActionViewModel.getUser().observe(this) { user ->
            if (user != User()) {
                with(binding) {
                    this@UserActionActivity.idUser = user.userId
                    this@UserActionActivity.isActivate = user.isActive

                    val url = "${MainActivity.BASE_URL}api/v1/images/profile/${user.image}"

                    Glide.with(this@UserActionActivity)
                        .load(url)
                        .placeholder(R.drawable.ic_default_profile)
                        .error(R.drawable.ic_default_profile)
                        .into(imgUser)

                    tvUserName.text = user.name
                    tvUserEmail.text = user.email
                    tvUserId.text = user.userId.toString()
                    tvUserRole.text = user.role
                    tvUserMembership.text = user.membership
                    tvUserStatus.text = if (user.isActive == 0) "Not active" else "Active"
                    setActivateButton(user.isActive)

                    roleId = user.roleId
                    membershipId = user.membershipId
                }
            } else {
                finish()
                DataUserFragment.isUpdateItem = true
                Toast.makeText(this, "Data user tidak ditemukan", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun deleteUser() {
        with(userActionViewModel) {
            deleteUser(this@UserActionActivity.idUser)

            isLoading.observe(this@UserActionActivity) {
                showLoading(it)
            }

            deleteMessage.observe(this@UserActionActivity) {
                if (it != "") {
                    showMessage(it)
                }
            }

            isDelete.observe(this@UserActionActivity) {
                if (it) {
                    if (this@UserActionActivity.idUser == preferences.getId()) {
                        logOut()
                    } else {
                        DataUserFragment.isUpdateItem = true
                        onBackPressed()
                    }
                }
            }

        }
    }

    fun activateUser() {
        userActionViewModel.activateUser(this@UserActionActivity.idUser)
    }

    fun deactivateUser() {
        userActionViewModel.deactivateUser(this@UserActionActivity.idUser)
        if (this@UserActionActivity.idUser == preferences.getId()) {
            logOut()
        }
    }

    private fun refresh() {
        userActionViewModel.deactivateMessage.observe(this@UserActionActivity) {
            if (it != null) {
                DataUserFragment.isUpdateItem = true
                initUser()
                showMessage(it)
            }
        }

        userActionViewModel.activateMessage.observe(this@UserActionActivity) {
            if (it != null) {
                DataUserFragment.isUpdateItem = true
                initUser()
                showMessage(it)
            }
        }
    }

    private fun actionButton() {
        with(binding) {
            btnUserActivate.setOnClickListener(this@UserActionActivity)
            btnUserDelete.setOnClickListener(this@UserActionActivity)
            btnUserEdit.setOnClickListener(this@UserActionActivity)
        }
    }

    private fun setActivateButton(isActive: Int) {
        with(binding) {
            if (isActive == 0) {
                tvUserStatus.setText(R.string.not_active)
                btnUserActivate.setText(R.string.activate)
                btnUserActivate.setBackgroundColor(
                    ContextCompat.getColor(
                        baseContext,
                        R.color.caribbean_green
                    )
                )
            } else {
                tvUserStatus.setText(R.string.active)
                btnUserActivate.setText(R.string.deactivate)
                btnUserActivate.setBackgroundColor(
                    ContextCompat.getColor(
                        baseContext,
                        R.color.ripe_mango
                    )
                )
            }
        }
    }

    private fun logOut() {
        preferences.removeData()
        val intentLogin = Intent(this, AuthActivity::class.java)
        startActivity(intentLogin)
        finish()
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                cardUserInfo.visibility = View.GONE
                cardUserAction.visibility = View.GONE
                loadingUser.visibility = View.VISIBLE
            } else {
                loadingUser.visibility = View.GONE
            }
        }
    }

    private fun showError(isError: Boolean) {
        with(binding) {
            if (isError) {
                loadingUser.visibility = View.GONE
                cardUserInfo.visibility = View.GONE
                cardUserAction.visibility = View.GONE
                tvUserError.visibility = View.VISIBLE
            } else {
                cardUserInfo.visibility = View.VISIBLE
                cardUserAction.visibility = View.VISIBLE
                tvUserError.visibility = View.GONE
            }
        }
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    fun isActivate(): Int {
        return isActivate
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_user_activate -> {
                val mActivateUserFragment = ActivateUserFragment()
                val mFragmentManager = this.supportFragmentManager
                mActivateUserFragment.show(
                    mFragmentManager,
                    ActivateUserFragment::class.java.simpleName
                )
            }

            R.id.btn_user_delete -> {
                val mDeleteUserFragment = DeleteUserFragment()
                val mFragmentManager = this.supportFragmentManager
                mDeleteUserFragment.show(
                    mFragmentManager,
                    DeleteUserFragment::class.java.simpleName
                )
            }
            R.id.btn_user_edit -> {
                val intentEditUser = Intent(this, EditUserActivity::class.java)
                val user = User(
                    name = binding.tvUserName.text.toString(),
                    email = binding.tvUserEmail.text.toString(),
                    roleId = roleId,
                    membershipId = membershipId,
                    role = binding.tvUserRole.text.toString(),
                    membership = binding.tvUserMembership.text.toString()
                )
                intentEditUser.putExtra(EXTRA_ID, idUser)
                intentEditUser.putExtra(EXTRA_USER, user)
                startActivity(intentEditUser)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isUpdateUser) {
            DataUserFragment.isUpdateItem = true
            initUser()
            isUpdateUser = false
        }
    }

    companion object {
        const val EXTRA_ID = "extra_id"
        const val EXTRA_NAME = "extra_name"
        const val EXTRA_USER = "extra_user"
        var isUpdateUser = false
    }
}