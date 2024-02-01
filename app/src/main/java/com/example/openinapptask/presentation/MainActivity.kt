package com.example.openinapptask.presentation

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.openinapptask.R
import com.example.openinapptask.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.navHostContainerFrame.clipToOutline = true

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener(object :
            NavController.OnDestinationChangedListener {
            override fun onDestinationChanged(
                controller: NavController,
                destination: NavDestination,
                arguments: Bundle?
            ) {
                val appBarText = when (destination.id) {
                    R.id.dashboard_fragment -> "Dashboard"
                    R.id.courses_fragment -> "Courses"
                    R.id.campaign_fragment -> "Campaign"
                    R.id.profile_fragment -> "Profile"
                    else -> {
                        "Destination"
                    }
                }
                binding.customActionBar.findViewById<TextView>(R.id.destination_text).text =
                    appBarText
            }

        })
    }
}