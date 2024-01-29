package com.example.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.search.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val tabTextList = listOf(R.string.main_list_first, R.string.main_list_second)
    private val tabIconList = listOf(R.drawable.ic_search2, R.drawable.ic_my)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViewPager()
        saveData()
        loadData()

        binding.btnSearch.setOnClickListener {
            val dataToSend = binding.etSearch.text.toString()
            val fragment = SearchFragment.newInstance(dataToSend)
        }

    }
    private fun saveData() {
        val pref = getSharedPreferences("pref", 0)
        val edit = pref.edit()
        edit.putString("name", binding.etSearch.text.toString())
        edit.apply()
    }

    private fun loadData() {
        val pref = getSharedPreferences("pref", 0)
        binding.etSearch.setText(pref.getString("name",""))
    }
    private fun initViewPager() {
        binding.viewpager2.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tab, binding.viewpager2) { tab, pos ->
            tab.text = getString(tabTextList[pos])
            tab.icon = getDrawable(tabIconList[pos])
        }.attach()
    }


}