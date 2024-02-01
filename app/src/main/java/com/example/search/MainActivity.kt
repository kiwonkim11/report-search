package com.example.search

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.search.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val tabTextList = listOf(R.string.main_list_first, R.string.main_list_second)
    private val tabIconList = listOf(R.drawable.ic_search2, R.drawable.ic_my)
    var likedItems: ArrayList<SearchItem> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initViewPager()
    }

    private fun initViewPager() {
        binding.viewpager2.adapter = ViewPagerAdapter(this)
        TabLayoutMediator(binding.tab, binding.viewpager2) { tab, pos ->
            tab.text = getString(tabTextList[pos])
            tab.icon = getDrawable(tabIconList[pos])
        }.attach()
    }

    fun addLikedItems (item: SearchItem) {
        if (!likedItems.contains(item)) likedItems.add(item)
    }

    fun removeLikedItems (item: SearchItem) {
        likedItems.remove(item)
    }
}