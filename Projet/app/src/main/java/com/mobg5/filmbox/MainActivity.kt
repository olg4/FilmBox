package com.mobg5.filmbox

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.mobg5.filmbox.service.SearchResultsFragmentDirections

/*
The whole activity is visible in each fragment. It is the common part.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var floatingActionButton: FloatingActionButton
    private lateinit var bottomNavigationView: BottomNavigationView
    private lateinit var navController: NavController
    private lateinit var viewModel: MainViewModel
    private lateinit var searchView: androidx.appcompat.widget.SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this, MainViewModelFactory())
            .get(MainViewModel::class.java)

        bottomNavigationView = findViewById(R.id.bottom_nav)
        navController = findNavController(R.id.nav_host_fragment)

        this.setUpToolBar()
        this.setUpSearchView()
        this.setUpDrawerLayout()
        this.setUpNavigationView()
        this.setUpAddButton()
        this.setUpBottomNavigationView()
    }

    override fun onBackPressed() {

        /* Used to change the item color from the bottom navigation
        When the user presses back, the previous fragment id is fetched to know which item has to be colored
        The navigation is managed in the nav_graph file but the bottom navigation depends on the setUpBottomNavigationView
        function since it belongs to the main activity.
        */
        when(navController.previousBackStackEntry?.destination?.id) {
            R.id.homeFragment -> {
                bottomNavigationView.menu.findItem(R.id.nav_home).isChecked = true
            }
            R.id.exploreFragment -> {
                bottomNavigationView.menu.findItem(R.id.nav_explore).isChecked = true
            }
            R.id.randomFragment -> {
                bottomNavigationView.menu.findItem(R.id.nav_random).isChecked = true
            }
            R.id.profileFragment -> {
                bottomNavigationView.menu.findItem(R.id.nav_profile).isChecked = true
            }
        }

        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    private fun setUpToolBar() {

        this.toolbar = findViewById(R.id.activity_main_toolbar)
        setSupportActionBar(toolbar)
    }

    private fun setUpSearchView() {
        searchView = findViewById(R.id.search_view)
        val searchIcon = searchView.findViewById<ImageView>(androidx.appcompat.R.id.search_button)
        searchIcon.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_search))

        searchView.setOnQueryTextListener(object: androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchInTmdb(newText.toString())
                return true
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(searchView.windowToken, 0)
                viewModel.searchInTmdb(query.toString())
                return true
            }
        })

        viewModel.results.observe(this, {
            if(null != it) {
                navController.navigate(
                    SearchResultsFragmentDirections.actionHostToSearchResults(it))
            }
        })
    }

    private fun setUpBottomNavigationView() {

        bottomNavigationView.setupWithNavController(navController)
        bottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_home -> {
                    navController.navigate(R.id.action_host_to_home)
                    true
                }

                R.id.nav_explore -> {
                    navController.navigate(R.id.action_host_to_explore)
                    true
                }

                R.id.nav_random -> {
                    navController.navigate(R.id.action_host_to_random)
                    true
                }

                R.id.nav_profile -> {
                    navController.navigate(R.id.action_host_to_profile)
                    true
                } else -> false
            }
        }
    }

    private fun setUpAddButton() {

        this.floatingActionButton = findViewById(R.id.fab)
        floatingActionButton.setOnClickListener {
            navController.navigate(R.id.action_host_to_create)
        }
    }

    private fun setUpDrawerLayout() {

        this.drawerLayout = findViewById(R.id.drawer_layout)
        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }

    private fun setUpNavigationView() {

        this.navigationView = findViewById(R.id.activity_main_nav_view)
        navigationView.setNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.nav_about -> {
                    navController.navigate(R.id.action_host_to_about)
                    drawerLayout.closeDrawers()
                    true
                } else -> false
            }
        }
    }

}
