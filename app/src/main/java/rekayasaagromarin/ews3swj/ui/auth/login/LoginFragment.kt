package rekayasaagromarin.ews3swj.ui.auth.login

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.FragmentLoginBinding
import rekayasaagromarin.ews3swj.preferences.PreferencesManager
import rekayasaagromarin.ews3swj.ui.menu.MainActivity
import rekayasaagromarin.ews3swj.ui.auth.payment.PaymentFragment
import rekayasaagromarin.ews3swj.ui.auth.register.RegisterFragment

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding
    private val loginViewModel: LoginViewModel by viewModels()
    private val preferences: PreferencesManager by lazy { PreferencesManager(context) }
    private lateinit var email: String
    private lateinit var password: String
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        checkLogin()
        toRegistration()
        toPayment()
        loginAccess()
    }

    private fun checkLogin() {
        if (preferences.isLogin()!!) {
            val intentMain = Intent(context, MainActivity::class.java)
            with(intentMain){
                putExtra(MainActivity.EXTRA_ID, preferences.getId())
                putExtra(MainActivity.EXTRA_NAME, preferences.getName())
                putExtra(MainActivity.EXTRA_EMAIL, preferences.getEmail())
                putExtra(MainActivity.EXTRA_IMAGE, preferences.getImage())
            }
            startActivity(intentMain)
            activity?.finish()
        }
    }

    private fun toRegistration() {
        binding?.loginToRegistration?.setOnClickListener {
            val mRegisterFragment = RegisterFragment()
            val mFragmentManager = parentFragmentManager
            mFragmentManager.commit {
                addToBackStack(null)
                replace(
                    R.id.frame_container,
                    mRegisterFragment,
                    RegisterFragment::class.java.simpleName,
                )
            }
        }
    }

    private fun toPayment() {
        binding?.loginToPayment?.setOnClickListener {
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

    private fun loginAccess() {
        binding?.btnLogin?.setOnClickListener {
            binding?.apply {
                when {
                    logEdtEmail.text?.isEmpty() == true -> Toast.makeText(
                        context,
                        "Email is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    logEdtPassword.text?.isEmpty() == true -> Toast.makeText(
                        context,
                        "Password is required",
                        Toast.LENGTH_SHORT
                    ).show()

                    logEdtEmail.text?.matches(emailPattern.toRegex()) != true -> Toast.makeText(
                        context,
                        "Invalid email address",
                        Toast.LENGTH_SHORT
                    ).show()

                    else -> {
                        email = logEdtEmail.text.toString()
                        password = logEdtPassword.text.toString()
                        loginViewModel.connectUser(email, password)
                    }
                }
            }
        }

        with(loginViewModel) {
            authUser.observe(viewLifecycleOwner) { user ->
                if (user.id != 0 && user.isActive == 1) {

                    with(preferences) {
                        setLogin(true)
                        setId(user.id)
                        setName(user.name)
                        setEmail(user.email)
                        setImage(user.image)
                    }

                    Toast.makeText(context, user.message, Toast.LENGTH_SHORT).show()

                    val intentMain = Intent(context, MainActivity::class.java)
                    intentMain.putExtra(MainActivity.EXTRA_ID, user.id)
                    startActivity(intentMain)
                    activity?.finish()
                } else {
                    Toast.makeText(context, user.message, Toast.LENGTH_SHORT).show()
                }
            }

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

    private fun showLoading(isLoading: Boolean) {
        binding?.loadingLogin?.root?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun showMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}