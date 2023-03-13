package com.example.moviesexampleapp.ui.settings

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviesexampleapp.databinding.FragmentSelectorBinding
import com.example.moviesexampleapp.ui.settings.*

const val ARG_TYPE_ITEMS = "ARG_TYPE_ITEMS"
const val ARG_INDEX = "ARG_INDEX"
class SelectorFragment : Fragment() {

    private var _binding: FragmentSelectorBinding? = null
    private val binding get() = _binding!!
    private var adapter: SelectorAdapter? = null
    private var settingsIndex: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = SelectorAdapter(object : SelectorInterface {
            override fun onItemClicked(item: String) {
                when (settingsIndex) {
                    0 -> {
                        activity?.getPreferences(Context.MODE_PRIVATE)?.edit()?.putString(
                            argType, item
                        )?.apply()
                    }
                    1 -> {
                        activity?.getPreferences(Context.MODE_PRIVATE)?.edit()?.putString(
                            argGenre, item
                        )?.apply()
                    }
                    2 -> {
                        activity?.getPreferences(Context.MODE_PRIVATE)?.edit()?.putString(
                            argCountry, item
                        )?.apply()
                    }
                }
                parentFragmentManager.popBackStack()
            }

        })
        _binding = FragmentSelectorBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        settingsIndex = arguments?.getInt(ARG_INDEX)
        val lm = LinearLayoutManager(context)
        lm.orientation - LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = lm
        recyclerView.adapter = adapter?.apply {
            setItemsList(arguments?.getStringArrayList(ARG_TYPE_ITEMS) ?: ArrayList())
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        fun newInstance(items: java.util.ArrayList<String>, index: Int): SelectorFragment{
            val args = Bundle()
            args.putStringArrayList(ARG_TYPE_ITEMS, items)
            args.putInt(ARG_INDEX, index)
            val fragment = SelectorFragment()
            fragment.arguments = args
            return fragment
        }
    }
}