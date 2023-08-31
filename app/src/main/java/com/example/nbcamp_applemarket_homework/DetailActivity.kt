package com.example.nbcamp_applemarket_homework

import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.example.nbcamp_applemarket_homework.databinding.ActivityDetailBinding
import java.text.DecimalFormat

class DetailActivity : AppCompatActivity() {

    lateinit var binding: ActivityDetailBinding

    private val item: AppleDataClass? by lazy {
        intent.getParcelableExtra<AppleDataClass>(ConstantsKey.ITEM_OBJECT)
    }

    private val itemPosition: Int by lazy {
        intent.getIntExtra(ConstantsKey.ITEM_INDEX, 0)
    }





    override fun onCreate(savedInstanceState: Bundle?) {

        binding = ActivityDetailBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        /**
         * 바인딩
         */

        binding.apply {
            detailImage.setImageDrawable(item?.let {
                ResourcesCompat.getDrawable(
                    resources,
                    it.appleDataImage,
                    null
                )
            })
            detailSeller.text = item?.appleDataSeller
            detailAddress.text = item?.appleDataAddress


            detailTitle.text = item?.appleDataTitle
            detailDetail.text = item?.appleDataDetail

            detailBottombarPrice.text = DecimalFormat("#,###").format(item?.appleDataPrice) + "원"
            detailBackarrow.setOnClickListener {
                finish()
            }






        }






    }
}