package rekayasaagromarin.ews3swj.ui.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.ui.auth.login.LoginFragment

class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        val mFragmentManager = supportFragmentManager
        val mLoginFragment = LoginFragment()
        val fragment = mFragmentManager.findFragmentByTag(LoginFragment::class.java.simpleName)

        if(fragment !is LoginFragment){
            mFragmentManager.commit {
                add(R.id.frame_container, mLoginFragment, LoginFragment::class.java.simpleName)
            }
        }
    }
}