package rekayasaagromarin.ews3swj.ui.menu.admin.datauser.action.edit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityEditUserBinding
import rekayasaagromarin.ews3swj.model.User
import rekayasaagromarin.ews3swj.ui.menu.admin.datauser.action.UserActionActivity

class EditUserActivity : AppCompatActivity() {

    private val editUserViewModel: EditUserViewModel by viewModels()
    private lateinit var binding: ActivityEditUserBinding

    private val role = arrayListOf<String>()
    private var membership = arrayListOf<String>()
    private var roleId = 0
    private var membershipId = 0
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initEditField()
        backButton()
    }

    private fun initEditField() {
        val user = intent.getParcelableExtra<User>(UserActionActivity.EXTRA_USER)
        initRole(user)
        with(binding) {
            edtEditUserName.setText(user?.name)
            edtEditUserEmail.setText(user?.email)
            actionButton()
            if (user?.roleId == 2) {
                addUserTilMembership.visibility = View.VISIBLE
                initMembership(user)
            } else {
                addUserTilMembership.visibility = View.GONE
            }
        }
    }

    private fun actionButton() {
        editUser()
        backButton()
    }

    private fun initToolbar() {
        with(binding.actionEditUserToolbar.viewToolbar) {
            title = getString(R.string.edit_user)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun editUser() {

        binding.btnEditUserAdd.setOnClickListener {
            binding.apply {
                when {
                    edtEditUserName.text?.isEmpty() == true -> Toast.makeText(
                        baseContext,
                        "Name is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    edtEditUserEmail.text?.isEmpty() == true -> Toast.makeText(
                        baseContext,
                        "Email is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    roleId == 0 -> {
                        Toast.makeText(
                            baseContext,
                            "Role is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    roleId == 2 && membershipId == 0 -> {
                        Toast.makeText(
                            baseContext,
                            "Membership is required",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                    edtEditUserEmail.text?.matches(emailPattern.toRegex()) != true -> Toast.makeText(
                        baseContext,
                        "Invalid email address",
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> {
                        val user = User(
                            userId = intent.getIntExtra(UserActionActivity.EXTRA_ID, 0),
                            name = edtEditUserName.text.toString(),
                            email = edtEditUserEmail.text.toString(),
                            roleId = roleId,
                            membershipId = membershipId,
                        )

                        editUserViewModel.editUser(
                            user.userId,
                            user.name,
                            user.email,
                            user.membershipId,
                            user.roleId
                        )
                    }
                }
            }
        }

        with(editUserViewModel) {
            isLoading.observe(this@EditUserActivity) {
                showLoading(it)
            }

            message.observe(this@EditUserActivity) {
                showMessage(it)
            }

            isSuccess.observe(this@EditUserActivity) {
                if (it == 200) {
                    UserActionActivity.isUpdateUser = true
                    onBackPressed()
                }
            }
        }
    }

    private fun initRole(user: User?) {
        roleId = user!!.roleId
        with(editUserViewModel) {
            setRole()
            getRole().observe(this@EditUserActivity) { list ->
                role.addAll(list)
            }
        }

        val spinnerArrayAdapter = ArrayAdapter(
            baseContext,
            R.layout.item_text,
            role
        )
        (binding.editUserTilRole.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.editDropdownRole.setText(user.role, false)

        binding.editDropdownRole.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val item = parent?.getItemAtPosition(position).toString()
                roleId = position + 1

                if (item == "Member") {
                    initMembership(user)
                    membershipId = 1
                    binding.editDropdownMembership.setText(getString(R.string.trial), false)
                } else {
                    membershipId = 0
                    binding.addUserTilMembership.visibility = View.GONE
                }
            }
    }

    private fun initMembership(user: User?) {
        membershipId = user!!.membershipId
        binding.addUserTilMembership.visibility = View.VISIBLE
        with(editUserViewModel) {
            setMembership()
            getMembership().observe(this@EditUserActivity) { list ->
                membership.clear()
                membership.addAll(list)
            }
        }

        val spinnerArrayAdapter = ArrayAdapter(
            baseContext,
            R.layout.item_text,
            membership
        )
        (binding.addUserTilMembership.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.editDropdownMembership.setText(user.membership, false)

        binding.editDropdownMembership.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                membershipId = position + 1
            }
    }

    private fun backButton() {
        binding.btnEditUserCancel.setOnClickListener {
            onBackPressed()
        }
    }


    private fun showLoading(isLoading: Boolean) {
        binding.loadingAddUser.root.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}