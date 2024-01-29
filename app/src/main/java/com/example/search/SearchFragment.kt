package com.example.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.search.databinding.FragmentSearchBinding

private const val ARG_PARAM1 = "param1"

class SearchFragment : Fragment() {

    private var param1: String? = null
    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private val api = NetworkClient.searchRetrofit.create(NetworkInterface::class.java)
    val items = mutableListOf<Document>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = SearchAdapter(items)

        with(binding) {
            recyclerview.adapter = adapter
            recyclerview.layoutManager = GridLayoutManager(requireContext(), 2)
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String) =
            SearchFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}