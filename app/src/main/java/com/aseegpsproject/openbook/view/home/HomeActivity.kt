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
import com.aseegpsproject.openbook.data.model.Worklist
import com.aseegpsproject.openbook.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(), DiscoverFragment.OnWorkClickListener, AuthorsFragment.OnAuthorClickListener, ProfileFragment.OnWorklistClickListener {
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

        // Hide bottom navigation when in details, settings or work list fragments
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if ((destination.id == R.id.workDetailFragment) ||
                (destination.id == R.id.settingsFragment) ||
                (destination.id == R.id.authorDetailsFragment) ||
                (destination.id == R.id.workListFragment)) {
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }

    override fun onWorkClick(work: Work) {
        val action = DiscoverFragmentDirections.actionWorkListFragmentToWorkDetailFragment(work)
        navController.navigate(action)
    }

    override fun onAuthorClick(author: Author) {
        val action = AuthorsFragmentDirections.actionAuthorsFragmentToAuthorDetailsFragment(author)
        navController.navigate(action)
    }

    override fun onWorklistClick(worklist: Worklist) {
        val action = ProfileFragmentDirections.actionProfileFragmentToWorkListFragment(worklist)
        navController.navigate(action)
    }

    override fun onSettingsClick() {
        val action = ProfileFragmentDirections.actionProfileFragmentToSettingsFragment()
        navController.navigate(action)
    }
}