package com.example.mysubmissionawal.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mysubmissionawal.R
import com.example.mysubmissionawal.adapter.FollAdapter
import com.example.mysubmissionawal.databinding.FragmentFollowersBinding
import com.example.mysubmissionawal.model.ItemsItem
import com.example.mysubmissionawal.model.MainViewModel
import com.example.mysubmissionawal.model.UserModel

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class FollowersFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private  var _binding: FragmentFollowersBinding? = null
    private val mainViewModel by viewModels<MainViewModel>()
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(context)
        recyclerView = view.findViewById(R.id.rvUserFollowers)
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        val dataLogin = arguments?.getString("data")
        Log.d("Ahuy", "$dataLogin")

        if (dataLogin != null) {
            mainViewModel.findUserFollowers(dataLogin).observe(viewLifecycleOwner) { userDatas ->
                Log.d("Uhuy", "$userDatas")
                getUserFollowers(userDatas)
            }
        }

//        mainViewModel.isLoading.observe(viewLifecycleOwner) {
//            showLoading(it)
//        }
    }

    companion object {
        private const val TAG = "Followers Fragment"
        fun newInstance(param1: String, param2: String) =
            FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }

    private fun getUserFollowers(userDatas: List<ItemsItem>) {
        val listUser = ArrayList<UserModel>()
        for (user in userDatas) {
            listUser.add(
                UserModel(
                    "Unknown Name",
                    user.id,
                    user.avatarUrl,
                    user.followersUrl,
                    user.followingUrl,
                    user.login
                )
            )
        }

        val adapter = FollAdapter(listUser)
        binding.rvUserFollowers.adapter = adapter
    }
//
//    private fun showLoading(isLoading: Boolean) {
//        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
//    }
}