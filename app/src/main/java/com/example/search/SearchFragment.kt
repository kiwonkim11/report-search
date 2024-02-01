package com.example.search

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.KeyEvent.KEYCODE_ENTER
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.search.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var mContext: Context
    private lateinit var adapter: SearchAdapter
    private var mItems: ArrayList<SearchItem> = ArrayList()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            adapter = SearchAdapter(mContext)
            recyclerview.adapter = adapter
            recyclerview.layoutManager = GridLayoutManager(context, 2)
            recyclerview.itemAnimator = null

            val lastSearch = PreferenceManager.loadData(requireContext())
            etSearch.setText(lastSearch)

            etSearch.setOnKeyListener { v, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KEYCODE_ENTER) {
                    val keyword = etSearch.text.toString()
                    callKeyword(keyword)
                    hideKeyboard()
                    true
                } else false
            }

            btnSearch.setOnClickListener {
                val keyword = etSearch.text.toString()
                if (keyword.isNotEmpty()){
                    PreferenceManager.saveData(requireContext(), keyword)
                    adapter.clearItem()
                    callKeyword(keyword)
                } else {
                    Toast.makeText(mContext, "검색어를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                }

                hideKeyboard()
            }

        }

    }

    private fun callKeyword(keyword: String) {
        NetworkClient.searchApi.getSearchResult("KakaoAK $REST_API_KEY", query = keyword)
            .enqueue(object : Callback<Search> {
                @SuppressLint("NotifyDataSetChanged")
                override fun onResponse(call: Call<Search>, response: Response<Search>) {
                    val body = response.body()

                    body?.let {
                        response.body()!!.documents.forEach{document ->
                            val title = document.siteName
                            val datetime = document.datetime
                            val url = document.thumbnailUrl
                            mItems.add(SearchItem(title, datetime, url))
                        }
                    }
                    adapter.items = mItems
                    adapter.notifyDataSetChanged()
                    Log.d("api검사", "$mItems")
                }

                override fun onFailure(call: Call<Search>, t: Throwable) {
                    Log.d("api검사", "네트워크 오류 / 데이터변환 오류.")
                }
            })
    }

    private fun hideKeyboard() {
        val inputMethodManager =
            requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken, 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}