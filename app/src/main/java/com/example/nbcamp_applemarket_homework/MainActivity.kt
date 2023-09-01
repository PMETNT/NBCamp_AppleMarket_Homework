package com.example.nbcamp_applemarket_homework

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AlphaAnimation
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.nbcamp_applemarket_homework.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var itemList: MutableList<AppleDataClass>

    lateinit var activityResultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 리사이클러뷰 시작
        binding.mainTopbarBell.setOnClickListener {
            notification()
        }


        itemList = AppleDB.appleDBList

        val adapter = AppleAdapter(itemList)


        binding.appleRecyclerView.adapter = adapter


        binding.appleRecyclerView.layoutManager = LinearLayoutManager(this)

        /**
         * onClick 구현
         */
        adapter.itemClick = object : AppleAdapter.ItemClick {
            override fun onClick(view: View, position: Int) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(ConstantsKey.ITEM_INDEX, position)
                intent.putExtra(ConstantsKey.ITEM_OBJECT, itemList[position])
                activityResultLauncher.launch(intent)
            }
        }

        activityResultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {


            }

        /**
         * onLongClick
         */

        adapter.itemLongClick = object : AppleAdapter.ItemLongClick {
            override fun onLongClick(view: View, position: Int) {
                val ad = AlertDialog.Builder(this@MainActivity)
                ad.setIcon(R.drawable.chat)
                ad.setTitle("상품 삭제")
                ad.setMessage("상품을 정말로 삭제하시겠습니까?")
                ad.setPositiveButton("확인") { dialog, _ ->
                    itemList.removeAt(position)
                    adapter.notifyItemRemoved(position)
                }

                ad.setNegativeButton("취소") { dialog, _ ->
                    dialog.dismiss()
                }
                ad.show()
            }
        }

        /**
         *  스크롤 상단 이동
         */
        binding.mainScrollupArrow.setOnClickListener {
            binding.appleRecyclerView.smoothScrollToPosition(0)
        }
        val fadeIn = AlphaAnimation(0f, 1f).apply { duration = 500 }
        val fadeOut = AlphaAnimation(1f, 0f).apply { duration = 500}
        var isTop = true

        binding.appleRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (!binding.appleRecyclerView.canScrollVertically(-1)
                    && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    binding.mainScrollupArrow.startAnimation(fadeOut)
                    binding.mainScrollupArrow.visibility = View.GONE
                    isTop = true
                } else {
                    if(isTop) {
                        binding.mainScrollupArrow.visibility = View.VISIBLE
                        binding.mainScrollupArrow.startAnimation(fadeIn)
                        isTop = false
                    }
                }
            }
        })






    }//onCreate

// ---------------------
    /** 다이얼로그
     */
    override fun onBackPressed() {
        val ad = AlertDialog.Builder(this)
        ad.setIcon(R.drawable.chat)
        ad.setTitle("종료")
        ad.setMessage("정말 종료하시겠습니까?")

        ad.setPositiveButton("확인") { dialog, _ ->
            finish()

        }
        ad.setNegativeButton("취소") { dialog, _ ->
            dialog.dismiss()
        }
        ad.show()

    }

    /** 알람
     *
     * */
    fun notification() {
        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        val builder: NotificationCompat.Builder
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // 26 버전 이상
            val channelId = "one-channel"
            val channelName = "My Channel One"
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                // 채널에 다양한 정보 설정
                description = "My Channel One Description"
                setShowBadge(true)
                val uri: Uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
                val audioAttributes = AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                    .setUsage(AudioAttributes.USAGE_ALARM)
                    .build()
                setSound(uri, audioAttributes)
                enableVibration(true)
            }
            // 채널을 NotificationManager에 등록
            manager.createNotificationChannel(channel)

            // 채널을 이용하여 builder 생성
            builder = NotificationCompat.Builder(this, channelId)

        } else {
            // 26 버전 이하
            builder = NotificationCompat.Builder(this)
        }
        builder.run {
            setSmallIcon(R.mipmap.ic_launcher)
            setWhen(System.currentTimeMillis())
            setContentTitle("키워드 알림")
            setContentText("설정한 키워드에 대한 알림이 도착했습니다!!")
        }
        manager.notify(11, builder.build())

    }


}