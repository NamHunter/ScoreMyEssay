package com.example.scoremyessay.ui.main.fragment.showEssay.cancelEssay

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.scoremyessay.base.BaseFragmentWithViewBinding
import com.example.scoremyessay.data.UserPreferences
import com.example.scoremyessay.data.model.orderAttribute.type.OrderType
import com.example.scoremyessay.databinding.FragmentViewEssayCancelBinding
import com.example.scoremyessay.ui.main.ActivityMain
import com.example.scoremyessay.ui.viewModel.MainViewModel
import com.example.scoremyessay.ui.main.fragment.showEssay.adapter.EssayAdapter
import com.example.scoremyessay.utils.Const
import com.google.gson.Gson


class CancelEssayFragment : BaseFragmentWithViewBinding<FragmentViewEssayCancelBinding>() {

    lateinit var viewModel: MainViewModel

    private val mAdapter: EssayAdapter by lazy { EssayAdapter() }
    private val mUserPreferences by lazy { UserPreferences(requireContext()) }

    private fun initObserver(){
        viewModel.allEssay.observe(requireActivity(), {
            Log.e("TAG", "initObserver: cancel nghe", )
            val listDoneEssay = it.filter { item ->
                item.status_id == Const.STATUS_CANCELLED}

            mAdapter.updateData(listDoneEssay)
        })
    }

    override fun handleTask() {
        initViewModel()

        initView()
        initObserver()
    }

    private fun initView() {
        mUserPreferences.orderType.asLiveData().observe(viewLifecycleOwner,{
            it?.let {
                val liTypes = Gson().fromJson(it, Array<OrderType>::class.java).asList()
                mAdapter.updateTypes(liTypes)
                Log.e("TAG", "initView: ${liTypes.toString()}", )
            }
        })

        binding.recyclerViewCancelEssay.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = mAdapter
        }
    }

    private fun initViewModel() {
        viewModel = (activity as ActivityMain).viewModel
    }

    override fun getFragmentBinding(
        inflater: LayoutInflater,
        container: ViewGroup?,
        attachToRoot: Boolean?
    ): FragmentViewEssayCancelBinding = FragmentViewEssayCancelBinding.inflate(inflater,container, false)
}