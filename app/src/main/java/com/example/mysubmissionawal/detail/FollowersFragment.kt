package com.example.mysubmissionawal.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissionawal.adapter.FollAdapter
import com.example.mysubmissionawal.databinding.FragmentFollowersBinding
import com.example.mysubmissionawal.model.*

class FollowersFragment : Fragment() {

    private  var _binding: FragmentFollowersBinding? = null
    private val mainViewModel by viewModels<MainViewModel>()
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataValue = arguments?.getString("dataValue")

        mainViewModel.findUserFollowers(dataValue!!).observe(viewLifecycleOwner) { userDatas ->
                getUserFollowers(userDatas)
            }

        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun getUserFollowers(userDatas: List<Follow>) {
        binding.apply {
            rvUserFollowers.layoutManager = LinearLayoutManager(context)

            rvUserFollowers.adapter = FollAdapter(userDatas as ArrayList<Follow>)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}