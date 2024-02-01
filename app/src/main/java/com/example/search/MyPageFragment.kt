package com.example.search

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.search.databinding.FragmentMyPageBinding

class MyPageFragment : Fragment() {
    private var binding: FragmentMyPageBinding? = null
    private lateinit var mContext: Context
    private lateinit var adapter: MyPageAdapter
    private var likedItems: List<SearchItem> = listOf()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val mainActivity = activity as MainActivity
        likedItems = mainActivity.likedItems

        adapter = MyPageAdapter(mContext).apply {
            items = likedItems.toMutableList() as ArrayList<SearchItem>
        }

        binding = FragmentMyPageBinding.inflate(inflater, container, false).apply {
            myrecyclerview.layoutManager = GridLayoutManager(context, 2)
            myrecyclerview.adapter = adapter
        }
        return binding?.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

}