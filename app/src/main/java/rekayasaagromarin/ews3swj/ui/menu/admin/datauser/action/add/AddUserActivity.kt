package rekayasaagromarin.ews3swj.ui.menu.admin.datauser.action.add

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.activity.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityAddUserBinding
import rekayasaagromarin.ews3swj.model.User
import rekayasaagromarin.ews3swj.ui.menu.admin.datauser.DataUserFragment

class AddUserActivity : AppCompatActivity() {

    private val addUserViewModel: AddUserViewModel by viewModels()
    private lateinit var binding: ActivityAddUserBinding

    private val role = arrayListOf<String>()
    private var membership = arrayListOf<String>()
    private var roleId = 0
    private var membershipId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initRole()
        actionButton()
    }

    private fun actionButton() {
        addUser()
        backButton()
    }

    private fun initRole() {
        with(addUserViewModel) {
            setRole()
            getRole().observe(this@AddUserActivity) { list ->
                role.addAll(list)
            }
        }

        val spinnerArrayAdapter = ArrayAdapter(
            baseContext,
            R.layout.item_text,
            role
        )

        (binding.tilAddUserRole.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownAddUserRole.onItemClickListener =
            AdapterView.OnItemClickListener { parent, _, position, _ ->
                val item = parent?.getItemAtPosition(position).toString()
                roleId = position + 1

                if (item == "Member") {
                    initMembership()
                } else {
                    membershipId = 0
                    binding.tilAddUserMembership.visibility = View.GONE
                }
            }
    }

    private fun initMembership() {
        binding.tilAddUserMembership.visibility = View.VISIBLE
        with(addUserViewModel) {
            setMembership()
            getMembership().observe(this@AddUserActivity) { list ->
                membership.clear()
                membership.addAll(list)
            }
        }

        val spinnerArrayAdapter = ArrayAdapter(
            baseContext,
            R.layout.item_text,
            membership
        )
        (binding.tilAddUserMembership.editText as? AutoCompleteTextView)?.setAdapter(
            spinnerArrayAdapter
        )

        binding.dropdownAddUserMembership.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                membershipId = position + 1
            }
    }

    private fun initToolbar() {
        with(binding.actionAddUserToolbar.viewToolbar) {
            title = getString(R.string.add_user)
            setNavigationOnClickListener {
                onBackPressed()
            }
        }
    }

    private fun addUser() {

        binding.btnAddUserAdd.setOnClickListener {
            binding.apply {
                when {
                    edtAddUserName.text?.isEmpty() == true -> Toast.makeText(
                        baseContext,
                        "Name is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    edtAddUserEmail.text?.isEmpty() == true -> Toast.makeText(
                        baseContext,
                        "Email is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    edtAddUserPassword.text?.isEmpty() == true -> Toast.makeText(
                        baseContext,
                        "Password is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    edtAddUserRepeatPassword.text?.isEmpty() == true -> Toast.makeText(
                        baseContext,
                        "Repeat password is required",
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

                    edtAddUserPassword.text.toString() != edtAddUserRepeatPassword.text.toString() -> Toast.makeText(
                        baseContext,
                        "Password dan repeat password tidak sama",
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> {
                        val user = User(
                            name = edtAddUserName.text.toString(),
                            email = edtAddUserEmail.text.toString(),
                            password = edtAddUserPassword.text.toString(),
                            roleId = roleId,
                            membershipId = membershipId
                        )

                        addUserViewModel.addUser(
                            user.name,
                            user.email,
                            user.password,
                            user.membershipId,
                            user.roleId
                        )
                    }
                }
            }
        }

        with(addUserViewModel) {
            isLoading.observe(this@AddUserActivity) {
                showLoading(it)
            }

            message.observe(this@AddUserActivity) {
                showMessage(it)
            }

            isSuccess.observe(this@AddUserActivity) {
                if (it == 200) {
                    DataUserFragment.isUpdateItem = true
                    onBackPressed()
                }
            }
        }
    }

    private fun backButton() {
        binding.btnAddUserCancel.setOnClickListener {
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
