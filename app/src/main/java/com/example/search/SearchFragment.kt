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
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.search.databinding.FragmentSearchBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchFragment : Fragment() {

    private var _binding: FragmentSearchBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: SearchAdapter
    private val items = mutableListOf<Document>()

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
            adapter = SearchAdapter(items)
            recyclerview.adapter = adapter
            recyclerview.layoutManager = GridLayoutManager(context, 2)

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
                callKeyword(keyword)
                hideKeyboard()
                saveData()
                loadData()
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
                        items.addAll(it.documents)
                    }
                    adapter.notifyDataSetChanged()
                    Log.d("api검사", "$items")
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

    private fun saveData() {
        val pref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        val edit = pref.edit()
        edit.putString("name", binding.etSearch.text.toString())
        edit.apply()
    }

    private fun loadData() {
        val pref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
        binding.etSearch.setText(pref.getString("name", ""))
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}