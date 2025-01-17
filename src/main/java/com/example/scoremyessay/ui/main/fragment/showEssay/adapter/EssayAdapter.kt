package com.example.scoremyessay.ui.main.fragment.showEssay.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scoremyessay.data.model.orderAttribute.result.OrderResultAPI
import com.example.scoremyessay.data.model.orderAttribute.type.OrderType
import com.example.scoremyessay.data.model.orders.OrderItem
import com.example.scoremyessay.data.model.user.AccountData
import com.example.scoremyessay.databinding.*
import com.example.scoremyessay.utils.extension.getNameByID

class EssayAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mListOrders = mutableListOf<OrderItem>()
    private var mListType = listOf<OrderType>()
    private var mListTeacher = listOf<AccountData>()
    private var mListResultAPI = listOf<OrderResultAPI>()

    private var mOnItemClickListener: ((OrderItem) -> Unit)? = null

    fun updateData(liOrder: List<OrderItem>) {
        mListOrders.clear()
        mListOrders.addAll(liOrder)
        notifyDataSetChanged()
    }

    fun updateTypes(liType: List<OrderType>) {
        mListType = liType
    }

    fun updateTeacher(liTeacher: List<AccountData>) {
        mListTeacher = liTeacher
    }

    fun updateResultOrder(liResultOrder: List<OrderResultAPI>) {
        mListResultAPI = liResultOrder
    }

    fun setOnItemClick(onItemClick: ((OrderItem) -> Unit)) {
        mOnItemClickListener = onItemClick
    }

    inner class WaitingViewHolder(private val binding: ItemEssayWaitingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderItem) {
            binding.txtTitle.setText(item.essay.title)
            binding.txtContent.text = item.essay.content
            binding.txtType.setText(mListType.getNameByID(item.essay.type_id))
        }
    }

    inner class OngoingViewHolder(private val binding: ItemEssayOngoingBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderItem) {
            binding.txtTitle.setText(item.essay.title)
            binding.txtContent.text = item.essay.content
            binding.txtType.setText(mListType.getNameByID(item.essay.type_id))
//            Glide.with(binding.imgAvatar)
//                .load(item.)
            //them thong tin giao vien
        }
    }

    inner class DoneViewHolder(private val binding: ItemEssayDoneBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: OrderItem) {
            binding.txtTitle.text = item.essay.title
            binding.txtContent.text = item.essay.content
            binding.txtType.text = mListType.getNameByID(item.essay.type_id)
//            mListResultAPI.find { it. }
            val score = item.result?.grade?.toString()
            if (score == null) {
                binding.txtGrade.text = "Không có điểm"
            } else {
                binding.txtGrade.text = "Điểm $score"
            }
        }
    }

    inner class CancelViewHolder(private val binding: ItemEssayCancelBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: OrderItem) {
            binding.txtTitle.setText(item.essay.title)
            binding.txtContent.text = item.essay.content
            binding.txtType.setText(mListType.getNameByID(item.essay.type_id))
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            1 -> return WaitingViewHolder(
                ItemEssayWaitingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            2 -> return OngoingViewHolder(
                ItemEssayOngoingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            3 -> return DoneViewHolder(
                ItemEssayDoneBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            4 -> return CancelViewHolder(
                ItemEssayCancelBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> return WaitingViewHolder(
                ItemEssayWaitingBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = mListOrders[position]

        holder.itemView.setOnClickListener {
            mOnItemClickListener?.invoke(mListOrders[position])
        }

        when (holder) {
            is WaitingViewHolder -> {
                holder.bind(item)
            }
            is OngoingViewHolder -> {
                holder.bind(item)
            }
            is DoneViewHolder -> {
                holder.bind(item)
            }
            is CancelViewHolder -> {
                holder.bind(item)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return mListOrders[position].status_id
    }

    override fun getItemCount(): Int = mListOrders.size
}

