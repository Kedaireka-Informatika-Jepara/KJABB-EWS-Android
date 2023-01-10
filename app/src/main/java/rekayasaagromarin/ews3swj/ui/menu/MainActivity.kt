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
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import com.bumptech.glide.Glide
import rekayasaagromarin.ews3swj.R
import rekayasaagromarin.ews3swj.databinding.ActivityMainBinding
import rekayasaagromarin.ews3swj.preferences.PreferencesManager
import rekayasaagromarin.ews3swj.ui.auth.AuthActivity
import rekayasaagromarin.ews3swj.ui.notifications.NotificationsActivity
import rekayasaagromarin.ews3swj.ui.profile.ProfileActivity

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var viewHeader: View
    private lateinit var imgNavHeaderPhoto: ImageView
    private lateinit var tvNavHeaderName: TextView
    private lateinit var tvNavHeaderEmail: TextView
    private lateinit var imgProfile: ImageView

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private var role: Int? = 0
    private var userId: Int = 0

    private val mainViewModel: MainViewModel by viewModels()
    private val preferences: PreferencesManager by lazy { PreferencesManager(applicationContext) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHome.toolbar)

//        role = intent.getIntExtra(EXTRA_ROLE, 0);
        role = preferences.getRole()
        Toast.makeText(this, "MainActivity role id : $role", Toast.LENGTH_SHORT).show()

        initNavDrawer()
        initUser()
    }

    private fun initUser() {
        viewHeader = binding.navView.getHeaderView(0)
        imgNavHeaderPhoto = viewHeader.findViewById(R.id.img_nav_header_photo)
        tvNavHeaderName = viewHeader.findViewById(R.id.tv_nav_header_name)
        tvNavHeaderEmail = viewHeader.findViewById(R.id.tv_nav_header_email)
        imgProfile = viewHeader.findViewById(R.id.img_nav_header_photo)

        tvNavHeaderName.text = intent.getStringExtra(EXTRA_NAME)
        tvNavHeaderEmail.text = intent.getStringExtra(EXTRA_EMAIL)

        mainViewModel.setUser(intent.getIntExtra(EXTRA_ID, 0))
        mainViewModel.getUser().observe(this) { user ->
            userId = user.userId
            val url = "${BASE_URL}api/v1/images/profile/${user.image}"
            Glide.with(this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.ic_default_profile)
                .into(imgNavHeaderPhoto)

            tvNavHeaderName.text = user.name
            tvNavHeaderEmail.text = user.email
        }
    }

    private fun initNavDrawer() {
        drawerLayout = binding.drawerLayout
        navView = binding.navView

        val navController by lazy {
            val navHostFragment = supportFragmentManager
                .findFragmentById(R.id.nav_host_fragment_content_home) as NavHostFragment

            navHostFragment.navController.graph = navHostFragment.navController.navInflater.inflate(R.navigation.mobile_navigation).apply {
                val startDestination = when (role) {
                    1 -> R.id.nav_data_user
                    2 -> R.id.nav_input_data
                    3 -> R.id.nav_data_weight
                    else -> R.id.nav_data_user
                }
                this.setStartDestination(startDestination)
            }
            navHostFragment.navController
        }

        appBarConfiguration = AppBarConfiguration(setOf(
            R.id.nav_data_user, R.id.nav_data_payment, R.id.nav_input_notification,
            R.id.nav_data_weight, R.id.nav_data_family_biotic, R.id.nav_data_station, R.id.nav_parameter,
            R.id.nav_input_data, R.id.nav_input_history, R.id.nav_tutorial
        ), drawerLayout)

        when (role) {
            1 -> {
                navView.setCheckedItem(R.id.nav_data_user)
            }
            2 -> {
                navView.setCheckedItem(R.id.nav_input_data)
                navView.menu[0].isVisible = false
                navView.menu[1].isVisible = false
            }
            else -> {
                navView.setCheckedItem(R.id.nav_data_weight)
                navView.menu[0].isVisible = false
            }
        }

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_notifications -> {
                Toast.makeText(this, "Notification", Toast.LENGTH_SHORT).show()
                val intentNotifications = Intent(this, NotificationsActivity::class.java).apply {
                    putExtra(EXTRA_ID, userId)
                }
                startActivity(intentNotifications)
            }

            R.id.menu_profile -> {
                val intentProfile = Intent(this, ProfileActivity::class.java)
                startActivity(intentProfile)
            }

            R.id.menu_logout -> {
                preferences.removeData()

                Toast.makeText(this, "Logout Berhasil", Toast.LENGTH_SHORT).show()

                val intentLogin = Intent(this, AuthActivity::class.java)
                startActivity(intentLogin)
                finish()
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
//        const val BASE_URL = "http://10.101.2.64:3308/"
        const val BASE_URL = "http://mews2.cemebsa.com/"
        const val EXTRA_ID = "extra id"
        const val EXTRA_NAME = "extra name"
        const val EXTRA_EMAIL = "extra email"
        const val EXTRA_IMAGE = "extra image"
        const val EXTRA_ROLE = "extra role"
        var isUpdate = false
    }
}