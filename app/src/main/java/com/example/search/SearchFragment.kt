package com.example.search

import android.content.Context
import android.os.Bundle
import android.util.Log
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
            val adapter = SearchAdapter(items)
            recyclerview.adapter = adapter
            recyclerview.layoutManager = GridLayoutManager(context, 2)

            btnSearch.setOnClickListener {
                val keyword = etSearch.text.toString()
                callKakaoKeyword(keyword, size = 80)
                hideKeyboard()
                saveData()
                loadData()
            }
        }

    }

    private fun callKakaoKeyword(keyword: String, size: Int) {
        NetworkClient.searchNetwork.getSearchResult(query = keyword, size = size)
            .enqueue(object : Callback<Search> {
                override fun onResponse(call: Call<Search>, response: Response<Search>) {
                    val body = response.body()

                    body?.let {
                        items.addAll(it.documents!!)
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
        val inputMethodManager = requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view?.windowToken,0)
    }

    private fun saveData() {
        val pref = requireActivity().getSharedPreferences("pref", 0)
        val edit = pref.edit()
        edit.putString("name", binding.etSearch.text.toString())
        edit.apply()
    }

    private fun loadData() {
        val pref = requireActivity().getSharedPreferences("pref", 0)
        binding.etSearch.setText(pref.getString("name", ""))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}