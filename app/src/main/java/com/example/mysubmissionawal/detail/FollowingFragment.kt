package com.example.mysubmissionawal.detail

import android.app.Application
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissionawal.adapter.FollAdapter
import com.example.mysubmissionawal.databinding.FragmentFollowingBinding
import com.example.mysubmissionawal.detail.favorite.FavoriteFactory
import com.example.mysubmissionawal.model.Follow
import com.example.mysubmissionawal.model.MainViewModel

class FollowingFragment : Fragment() {

    private  var _binding: FragmentFollowingBinding? = null
    private lateinit var mainViewModel: MainViewModel
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dataValue = arguments?.getString("dataValue")

        mainViewModel = ViewModelProvider(
            this,
            FavoriteFactory.getInstance(activity?.applicationContext as Application)
        )[MainViewModel::class.java]

        mainViewModel.findUserFollowing(dataValue!!).observe(viewLifecycleOwner) { userDatas ->
            getUserFollowing(userDatas)
        }

        mainViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }
    }

    private fun getUserFollowing(userDatas: List<Follow>) {
        binding.apply {
            rvUserFollowing.layoutManager = LinearLayoutManager(context)

            rvUserFollowing.adapter = FollAdapter(userDatas as ArrayList<Follow>)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}