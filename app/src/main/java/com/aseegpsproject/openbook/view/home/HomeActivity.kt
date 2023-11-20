package com.aseegpsproject.openbook.view.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.aseegpsproject.openbook.R
import com.aseegpsproject.openbook.data.model.Author
import com.aseegpsproject.openbook.data.model.User
import com.aseegpsproject.openbook.data.model.Work
import com.aseegpsproject.openbook.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity(), DiscoverFragment.OnWorkClickListener, AuthorsFragment.OnAuthorClickListener {
    private lateinit var binding: ActivityHomeBinding

    private lateinit var discoverFragment: DiscoverFragment
    private lateinit var booksFragment: BooksFragment
    private lateinit var authorsFragment: AuthorsFragment
    private lateinit var profileFragment: ProfileFragment

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
        setContentView(R.layout.activity_home)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getSerializableExtra(USER_INFO) as User

        setupUI(user)
        setUpListeners()
    }

    private fun setupUI(user: User) {
        discoverFragment = DiscoverFragment()
        booksFragment = BooksFragment()
        authorsFragment = AuthorsFragment()
        profileFragment = ProfileFragment()

        setCurrentFragment(discoverFragment)
    }

    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
    }

    private fun setUpListeners() {
        with(binding) {
            bottomNavigation.setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.action_discover -> setCurrentFragment(discoverFragment)
                    R.id.action_books -> setCurrentFragment(booksFragment)
                    R.id.action_authors -> setCurrentFragment(authorsFragment)
                    R.id.action_profile -> setCurrentFragment(profileFragment)
                }
                true
            }
        }
    }

    override fun onWorkClick(work: Work) {
        // TODO: Implement onBookClick
    }

    override fun onAuthorClick(author: Author) {
        // TODO: Implement onAuthorClick
    }
}