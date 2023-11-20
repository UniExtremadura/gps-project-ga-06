package com.aseegpsproject.openbook.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.data.model.Author
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.databinding.ActivityHomeBinding

interface SearchHandler {
    fun handleSearch(query: String)
    fun clearSearch()
}

class HomeActivity : AppCompatActivity(), DiscoverFragment.OnWorkClickListener, AuthorsFragment.OnAuthorClickListener {
    private lateinit var binding: ActivityHomeBinding
    private val navController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }

    companion object {
        const val USER_INFO = "USER_INFO"

        fun start(context: Context, user: User) {
            val intent =
                Intent(context, HomeActivity::class.java).apply { putExtra(USER_INFO, user) }
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupUI()
    }

    private fun setupUI() {
        binding.bottomNavigation.setupWithNavController(navController)

        // Hide toolbar and bottom navigation when in detail or settings fragment
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if ((destination.id == R.id.workDetailFragment) ||
                (destination.id == R.id.settingsFragment)) {
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }

    override fun onWorkClick(work: Work) {
        val action = DiscoverFragmentDirections.actionDiscoverFragmentToWorkDetailFragment(work.key)
        navController.navigate(action)
    }

    override fun onAuthorClick(author: Author) {

    }
}