package com.youngbrains.testandroid

import UserAdapter
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mvvmdemomaster.apiInstance.ApiInstance
import com.example.mvvmdemomaster.apiService.UserService
import com.youngbrains.testandroid.databinding.UserListBinding

import com.youngbrains.testandroid.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class UserListFragment : Fragment() {

    private lateinit var mBinding: UserListBinding

    val USER_KEY = "userKey"

    private val userItem: ArrayList<User> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        mBinding = UserListBinding.inflate(layoutInflater, container, false)
        // Inflate the layout for this fragment
        return mBinding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = UserAdapter(userItem, object {
            var fragment = UserListFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(USER_KEY, arguments)
                }
            }
        })

        mBinding.rvDashboard.layoutManager = LinearLayoutManager(requireContext())
        mBinding.rvDashboard.adapter = adapter
        val apiInterFace = ApiInstance.getApiData().create(UserService::class.java)
        val userDao = AppDatabase.getInstance(requireContext()).userDao()
        if (Network.checkConnectivity(requireContext())){
            lifecycleScope.launch {
                val result = apiInterFace.getData()
                if(result.body() != null){
                    if (result.isSuccessful){
                        val users = result.body()?.data
                        if (users != null) {
                            CoroutineScope(Dispatchers.IO).launch {
                                userDao.deleteData()
                                userDao.insertUser(*users.toTypedArray())
                            }
                            adapter.userItem.clear()
                            adapter.userItem.addAll(result.body()!!.data)
                            adapter.notifyDataSetChanged()
                        }
                    }

                }
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                adapter.userItem.clear()
                adapter.userItem.addAll(userDao.getAll())
                adapter.notifyDataSetChanged()
            }

        }
        //No internet connectivity


    }
}