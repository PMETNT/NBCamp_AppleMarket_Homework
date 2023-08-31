package com.example.nbcamp_applemarket_homework

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.nbcamp_applemarket_homework.databinding.AddItemBinding

class AppleAdapter(private val appleList: MutableList<AppleDataClass>) :
    RecyclerView.Adapter<AppleAdapter.AppleHolder>() {
// :RecyclerView.Adapter<RecyclerView.ViewHolder>() 라 적었음, <AppleAdapter.AppleHolder> 으로 변경
// , 이렇게 바꾸니까 onBindViewHolder에 (holder: RecyclerView.ViewHolder, ... ) override 문제 해결
    /**
     *  아이템 클릭, 롱 클릭 구현 방법 (강의 보고 구현 )
     */


    interface ItemClick {
        fun onClick(view: View, position: Int)

    }

    interface ItemLongClick {
        fun onLongClick(view: View, position: Int)

    }

    var itemClick: ItemClick? = null
    var itemLongClick: ItemLongClick? = null

    /**
     *  홀더 바인딩, 홀더 구현
     */


    inner class AppleHolder(binding: AddItemBinding) : RecyclerView.ViewHolder(binding.root) {
        val holderImage = binding.additemImage
        val holderTitle = binding.additemName
        val holderAddress = binding.additemAddress
        val holderPrice = binding.additemPrice

        // 챗, 하트
        val holderChatText = binding.additemChatText
        val holderHeart = binding.additemHeart
        val holderHeartText = binding.additemHeartText
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AppleHolder {
        val binding = AddItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AppleHolder(binding)
    }


    override fun getItemCount(): Int {
        return appleList.size
    }

    override fun onBindViewHolder(holder: AppleHolder, position: Int) {
        val page = appleList[position]

        holder.itemView.setOnClickListener {
            itemClick?.onClick(it, position)}



        holder.apply {
            holderImage.setImageResource(page.appleDataImage)
            holderTitle.text = page.appleDataTitle
            holderAddress.text = page.appleDataAddress
            holderPrice.text = page.appleDataPrice.toString()
            holderChatText.text = page.appleDataChat.toString()
            holderHeartText.text = page.appleDataLike.toString()



        }


    }


}