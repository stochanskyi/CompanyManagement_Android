package com.mars.companymanagement.presentation.screens.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.*
import androidx.navigation.NavController
import androidx.navigation.ui.NavigationUI
import com.mars.companymanagement.databinding.ActivityMainBinding
import com.mars.companymanagement.presentation.screens.main.toolbar.EmptyToolbarConfigurationChanges
import com.mars.companymanagement.presentation.screens.main.toolbar.ToolbarConfigurationChanges
import com.mars.companymanagement.presentation.screens.main.toolbar.ToolbarConfigurator
import com.mars.companymanagement.presentation.screens.main.toolbar.ToolbarMenuListener
import dagger.hilt.android.AndroidEntryPoint
import today.magnolia.android.screens.main.bottomnavigation.BottomNavigationController

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), ToolbarConfigurator {
    private val viewModel: MainViewModel by viewModels()

    private var binding: ActivityMainBinding? = null

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var bottomNavigationController: BottomNavigationController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater).also {
            setContentView(it.root)
            setSupportActionBar(it.toolbar)
            initViews(it)
            initObservers(it)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        return onBackPressed().let { true }
    }

    override fun onBackPressed() {
        when {
            bottomNavigationController?.canHandleBackPressInternal() == true -> super.onBackPressed()
            bottomNavigationController?.handleBackPressed() == true -> return
            //TODO Should be replaced by displaying message if user wants to exit from the app
            else -> super.onBackPressed()
        }
    }

    private fun initViews(binding: ActivityMainBinding) {
        bottomNavigationController =
            BottomNavigationController(supportFragmentManager, binding.fragmentContainer.id)
    }

    private fun initObservers(binding: ActivityMainBinding) {
        bottomNavigationController?.currentNavControllerLiveData?.observe(this) {
            onNavControllerChanged(it)
        }
        viewModel.accessLevelLiveData.observe(this) { level ->
            bottomNavigationController?.let {
                binding.bottomNavigationView.menu.clear()
                binding.bottomNavigationView.inflateMenu(level.provideMenuRes())
                it.setItems(level.provideItemsList())
                it.setup(binding.bottomNavigationView)
            }
        }
    }

    private fun onNavControllerChanged(navController: NavController) {
        NavigationUI.setupActionBarWithNavController(this, navController)
        navController.addOnDestinationChangedListener { _, _, _ ->  clearToolbar()}
    }

    private fun clearToolbar() {
        binding?.toolbar?.apply {
            setOnMenuItemClickListener(null)
            menu.clear()
        }
    }

    override fun modifyToolbarConfiguration(modifyAction: ToolbarConfigurationChanges.() -> Unit) {
        val configurationChanges = EmptyToolbarConfigurationChanges()
        configurationChanges.modifyAction()

        binding?.toolbar?.applyToolbarChanges(configurationChanges)
    }

    override fun setMenuItemsListener(
        lifecycleOwner: LifecycleOwner,
        listener: ToolbarMenuListener
    ) {
        binding?.toolbar?.setOnMenuItemClickListener {
            listener.onItemSelected(it.itemId)
            true
        }

        lifecycleOwner.lifecycle.addObserver(object : LifecycleObserver {
            @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
            fun removeListener() {
                binding?.toolbar?.setOnMenuItemClickListener(null)
            }
        })

    }

    private fun Toolbar.applyToolbarChanges(changes: ToolbarConfigurationChanges) {
        changes.name?.let { title = it }
        changes.showNavigateBack?.let { supportActionBar?.setDisplayHomeAsUpEnabled(it) }
        changes.menuId?.let { inflateMenu(it) }
    }

    override fun onDestroy() {
        bottomNavigationController?.release()
        bottomNavigationController = null
        super.onDestroy()
    }
}