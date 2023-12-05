package com.example.mystoryapp.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mystoryapp.R
import com.example.mystoryapp.StoryAdapter
import com.example.mystoryapp.ViewModelFactory
import com.example.mystoryapp.WelcomeActivity
import com.example.mystoryapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel by viewModels<MainViewModel> {
        ViewModelFactory.getInstance(this)
    }
    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: StoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.rvStory.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvStory.addItemDecoration(itemDecoration)

        showLoading(true)
        viewModel.getSession().observe(this) { user ->
            if (!user.isLogin) {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
            else{
                showLoading(false)
                val adapter = StoryAdapter()
                binding.rvStory.adapter = adapter
                viewModel.getStory(user.token).observe(this) {
                    if (it != null) {
                        adapter.submitData(lifecycle, it)

                    }
                    Log.e("token", "onCreate: ${user.token}", )
                }
            }
        }

        binding.toolbar.inflateMenu(R.menu.menu)
        binding.toolbar.setOnMenuItemClickListener{menuItem ->
            when(menuItem.itemId){
                R.id.logout -> {
                    viewModel.logout()
                    val intent = Intent(this@MainActivity, WelcomeActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }



    }

    fun showLoading(isLoading: Boolean){
        binding.progress.visibility =if(isLoading) View.VISIBLE else View.GONE
    }


}