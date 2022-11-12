package rekayasaagromarin.ews3swj.ui.menu

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityMainBinding
import rekayasaagromarin.ews3swj.preferences.PreferencesManager
import rekayasaagromarin.ews3swj.ui.auth.AuthActivity
import rekayasaagromarin.ews3swj.ui.profile.ProfileActivity

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewHeader: View
    private lateinit var tvNavHeaderName: TextView
    private lateinit var tvNavHeaderEmail: TextView
    private lateinit var imgProfile: ImageView

    private val mainViewModel: MainViewModel by viewModels()
    private val preferences: PreferencesManager by lazy { PreferencesManager(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

        initNavDrawer()
        initUser()
    }

    private fun initUser() {
        viewHeader = binding.navView.getHeaderView(0)
        tvNavHeaderName = viewHeader.findViewById(R.id.tv_nav_header_name)
        tvNavHeaderEmail = viewHeader.findViewById(R.id.tv_nav_header_email)
        imgProfile = viewHeader.findViewById(R.id.img_nav_header_photo)

        tvNavHeaderName.text = intent.getStringExtra(EXTRA_NAME)
        tvNavHeaderEmail.text = intent.getStringExtra(EXTRA_EMAIL)

        mainViewModel.setUser(intent.getIntExtra(EXTRA_ID, 0))
        mainViewModel.getUser().observe(this) { user ->
            tvNavHeaderName.text = user.name
            tvNavHeaderEmail.text = user.email
        }
    }

    private fun initNavDrawer() {
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController by lazy {
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_content_home) as NavHostFragment
            navHostFragment.navController
        }

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_data_user, R.id.nav_data_payment,
                R.id.nav_data_weight, R.id.nav_data_family_biotic, R.id.nav_data_station,
                R.id.nav_input_data, R.id.nav_input_history
            ), drawerLayout
        )

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                preferences.removeData()

                Toast.makeText(this, "Logout Berhasil", Toast.LENGTH_SHORT).show()

                val intentLogin = Intent(this, AuthActivity::class.java)
                startActivity(intentLogin)
                finish()
            }

            R.id.menu_profile -> {
                val intentProfile = Intent(this, ProfileActivity::class.java)
                startActivity(intentProfile)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        if (isUpdate) {
            initUser()
            isUpdate = false
        }
    }

    companion object {
        const val BASE_URL = "http://192.168.10.191:3308" //http://mews.cemebsa.com/
        const val EXTRA_ID = "extra id"
        const val EXTRA_NAME = "extra name"
        const val EXTRA_EMAIL = "extra email"
        const val EXTRA_IMAGE = "extra image"
        var isUpdate = false
    }
}