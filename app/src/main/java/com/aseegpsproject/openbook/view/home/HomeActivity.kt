package com.aseegpsproject.openbook.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.data.model.Author
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.data.model.WorkList
import com.aseegpsproject.openbook.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()
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

        viewModel.userInSession = intent.getSerializableExtra(USER_INFO) as User
        viewModel.navigateToWork.observe(this) { work ->
            work?.let {
                onWorkClick(work)
            }
        }
        viewModel.navigateToAuthor.observe(this) { author ->
            author?.let {
                onAuthorClick(author)
            }
        }
        viewModel.navigateToSettings.observe(this) { navigate ->
            if (navigate) {
                onSettingsClick()
            }
        }
        viewModel.navigateToWorkList.observe(this) { worklist ->
            worklist?.let {
                onWorkListClick(worklist)
            }
        }

        setupUI()
    }

    private fun setupUI() {
        binding.bottomNavigation.setupWithNavController(navController)

        // Hide bottom navigation when in details, settings or work list fragments
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if ((destination.id == R.id.workDetailFragment) ||
                (destination.id == R.id.settingsFragment) ||
                (destination.id == R.id.authorDetailFragment) ||
                (destination.id == R.id.workListFragment)) {
                binding.bottomNavigation.visibility = View.GONE
            } else {
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }

    private fun onWorkClick(work: Work) {
        val action = DiscoverFragmentDirections.actionWorkListFragmentToWorkDetailFragment(work)
        navController.navigate(action)
    }

    private fun onAuthorClick(author: Author) {
        val action = AuthorsFragmentDirections.actionAuthorsFragmentToAuthorDetailsFragment(author)
        navController.navigate(action)
    }

    private fun onWorkListClick(workList: WorkList) {
        val action = ProfileFragmentDirections.actionProfileFragmentToWorkListFragment(workList)
        navController.navigate(action)
    }

    private fun onSettingsClick() {
        val action = ProfileFragmentDirections.actionProfileFragmentToSettingsFragment()
        navController.navigate(action)
    }
}