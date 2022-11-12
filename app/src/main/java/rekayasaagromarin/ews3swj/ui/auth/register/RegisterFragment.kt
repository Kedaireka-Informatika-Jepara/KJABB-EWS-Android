package rekayasaagromarin.ews3swj.ui.auth.register

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.FragmentRegisterBinding
import rekayasaagromarin.ews3swj.model.User
import rekayasaagromarin.ews3swj.ui.auth.login.LoginFragment
import rekayasaagromarin.ews3swj.ui.auth.payment.PaymentFragment

class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding

    private val registerViewModel: RegisterViewModel by viewModels()
    private val membership = arrayListOf("Select membership")
    private var membershipId: Int = 0
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMembership()
        registerAccess()
        toLogin()
        toPayment()
    }

    private fun registerAccess() {

        binding?.btnRegister?.setOnClickListener {
            binding?.apply {
                when {
                    regEdtName.text?.isEmpty() == true -> Toast.makeText(
                        context,
                        "Name is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    regEdtEmail.text?.isEmpty() == true -> Toast.makeText(
                        context,
                        "Email is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    regEdtPassword.text?.isEmpty() == true -> Toast.makeText(
                        context,
                        "Password is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    regEdtRepeatPassword.text?.isEmpty() == true -> Toast.makeText(
                        context,
                        "Repeat password is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    regSpinMembership.selectedItemPosition == 0 -> Toast.makeText(
                        context,
                        "Membership is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    regEdtPassword.text.toString() != regEdtRepeatPassword.text.toString() -> Toast.makeText(
                        context,
                        "Password dan repeat password tidak sama",
                        Toast.LENGTH_SHORT
                    ).show()

                    regEdtEmail.text?.matches(emailPattern.toRegex()) != true -> Toast.makeText(
                        context,
                        "Invalid email address",
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> {
                        val user = User(
                            name = regEdtName.text.toString(),
                            email = regEdtEmail.text.toString(),
                            password = regEdtPassword.text.toString(),
                            membershipId = membershipId
                        )

                        registerViewModel.registerUser(
                            user.name,
                            user.email,
                            user.password,
                            user.membershipId
                        )
                    }
                }
            }
        }

        with(registerViewModel) {
            isLoading.observe(viewLifecycleOwner) {
                showLoading(it)
            }

            message.observe(viewLifecycleOwner) {
                if (it != null) {
                    showMessage(it)
                }
            }
        }
    }

    private fun initMembership() {

        with(registerViewModel) {
            setMembership()
            getMembership().observe(viewLifecycleOwner) { list ->
                membership.addAll(list)
            }
        }

        val spinnerArrayAdapter = ArrayAdapter(
            requireContext(),
            R.layout.support_simple_spinner_dropdown_item,
            membership
        )
        spinnerArrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item)
        binding?.regSpinMembership?.adapter = spinnerArrayAdapter
        binding?.regSpinMembership?.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (position > 0) {
                        val item = parent?.getItemAtPosition(position).toString()
                        Toast.makeText(parent?.context, "Membership: $item", Toast.LENGTH_SHORT)
                            .show()
                        membershipId = position
                    }
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
    }

    private fun toLogin() {
        binding?.registrationToLogin?.setOnClickListener {
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

    private fun toPayment() {
        binding?.registrationToPayment?.setOnClickListener {
            val mPaymentFragment = PaymentFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.commit {
                addToBackStack(null)
                replace(
                    R.id.frame_container,
                    mPaymentFragment,
                    PaymentFragment::class.java.simpleName
                )
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding?.loadingRegister?.root?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}