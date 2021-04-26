package com.mars.companymanagement.presentation.screens.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.mars.companymanagement.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint
import today.magnolia.android.screens.main.bottomnavigation.BottomNavigationController

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: MainViewModel by viewModels()

    companion object {
        fun start(context: Context) {
            val intent = Intent(context, MainActivity::class.java)
            context.startActivity(intent)
        }
    }

    private var bottomNavigationController: BottomNavigationController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ActivityMainBinding.inflate(layoutInflater).run {
            setContentView(this.root)
            initViews(this)
            initObservers(this)
        }
    }

    private fun initViews(binding: ActivityMainBinding) {
        bottomNavigationController =
            BottomNavigationController(supportFragmentManager, binding.fragmentContainer.id)
    }

    private fun initObservers(binding: ActivityMainBinding) {
        viewModel.accessLevelLiveData.observe(this) { level ->
            bottomNavigationController?.let {
                binding.bottomNavigationView.menu.clear()
                binding.bottomNavigationView.inflateMenu(level.provideMenuRes())
                it.setItems(level.provideItemsList())
                it.setup(binding.bottomNavigationView)
            }
        }
    }

    override fun onDestroy() {
        bottomNavigationController?.release()
        bottomNavigationController = null
        super.onDestroy()
    }
}