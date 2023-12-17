package com.example.coffeedairy

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.coffeedairy.databinding.ActivityDateLogBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.util.Calendar


// 특정 일자의 기록 열람을 위한 액티비티
class DateLogActivity: AppCompatActivity() {
    lateinit var binding: ActivityDateLogBinding
    val db = Firebase.firestore
    var fbStorage : FirebaseStorage? = null
    var uriPhoto: Uri? = null
    var flag = "EditMode" // EditMode, ViewMode
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDateLogBinding.inflate(layoutInflater)
        setContentView(binding.root)

        fbStorage = FirebaseStorage.getInstance()

        val year = intent!!.getIntExtra("year", 0)
        val month = intent!!.getIntExtra("month", 0)
        val dayOfMonth = intent!!.getIntExtra("dayOfMonth", 0)

        val calendar: Calendar = Calendar.getInstance()
        calendar.set(year, month - 1, dayOfMonth)
        val sdf = java.text.SimpleDateFormat("yyyy / MM / dd")
        val sdfID = java.text.SimpleDateFormat("yyyyMMdd")

        val docRef = db.collection("log").document(sdfID.format(calendar.time))

        binding.logDate.text = sdf.format(calendar.time)

        val storage: FirebaseStorage =
            FirebaseStorage.getInstance("gs://coffee-diary-18c06.appspot.com")
        val storageReference = storage.reference
        val pathReference = storageReference.child("images/${sdfID.format(calendar.time)}")

        val multiOption = MultiTransformation(
            CenterCrop(),
            RoundedCorners(20)
        )

        pathReference.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(binding.logImage)
                .load(uri)
                .apply(RequestOptions().transform(multiOption))
                .into(binding.logImage)
        }

        docRef.get()
            .addOnSuccessListener { document ->
                if (document.data?.get("coffeeName") != null) { // view mode
                    Log.d("log", "DocumentSnapshot data: ${document.data}")
                    binding.logSave.visibility = android.view.View.INVISIBLE
                    binding.logEdit.visibility = android.view.View.VISIBLE
                    binding.logDelete.visibility = android.view.View.VISIBLE
                    binding.logCoffeeName.setText(document.data?.get("coffeeName").toString())
                    binding.logCoffeeName.isEnabled = false
                    binding.logCafeName.setText(document.data?.get("cafeName").toString())
                    binding.logCafeName.isEnabled = false
                    binding.logRating.rating = document.data?.get("rating").toString().toFloat()
                    //binding.logRating.isIndicator = true
                    binding.logDiary.setText(document.data?.get("diary").toString())
                    binding.logDiary.isEnabled = false
                    binding.logImage.isClickable = false
                    flag = "ViewMode"

                } else { // edit mode
                    binding.logSave.visibility = android.view.View.VISIBLE
                    binding.logEdit.visibility = android.view.View.INVISIBLE
                    binding.logDelete.visibility = android.view.View.INVISIBLE
                    binding.logDate.text = sdf.format(calendar.time)
                    binding.logCoffeeName.isEnabled = true
                    binding.logCafeName.isEnabled = true
                    binding.logRating.isEnabled = true
                    binding.logDiary.isEnabled = true
                    binding.logImage.isClickable = true
                    flag = "EditMode"
                    Log.d("log", "No such document")
                }
            }

        val requestGalleryLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        )
        {
            try {
                val calRatio = calculateInSampleSize(
                    it.data!!.data!!,
                    resources.getDimensionPixelSize(R.dimen.imgSize),
                    resources.getDimensionPixelSize(R.dimen.imgSize)
                )

                uriPhoto = it.data!!.data!!

                var imgFileName = sdfID.format(calendar.time)
                var storageRef = fbStorage?.reference?.child("images/$imgFileName")

                storageRef?.putFile(uriPhoto!!)?.addOnSuccessListener {
                    Toast.makeText(this, "이미지 업로드 성공", Toast.LENGTH_SHORT).show()
                }?.addOnFailureListener {
                    Toast.makeText(this, "이미지 업로드 실패", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        binding.logImage.setOnClickListener {
            // gallery app
            val uploadIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            intent.type = "image/*"
            requestGalleryLauncher.launch(uploadIntent)
        }

        // 수정 버튼
        binding.logEdit.setOnClickListener {
            binding.logSave.visibility = android.view.View.VISIBLE
            binding.logEdit.visibility = android.view.View.INVISIBLE
            binding.logDelete.visibility = android.view.View.INVISIBLE
            binding.logCoffeeName.isEnabled = true
            binding.logCafeName.isEnabled = true
            binding.logRating.isEnabled = true
            binding.logDiary.isEnabled = true
            flag = "EditMode"
        }

        // 삭제 버튼
        binding.logDelete.setOnClickListener {
            db.collection("log").document(sdfID.format(calendar.time)).delete()
                .addOnSuccessListener {
                    fbStorage?.reference?.child("images/${sdfID.format(calendar.time)}")?.delete()
                    Toast.makeText(this, "삭제되었습니다", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "삭제 실패", Toast.LENGTH_SHORT).show()
                }

            finish()
        }

        // 저장 버튼
        binding.logSave.setOnClickListener {
            if (binding.logCoffeeName.text.toString() == "") {
                Toast.makeText(this, "커피 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                //return@setOnClickListener
            } else if (binding.logCafeName.text.toString() == "") {
                Toast.makeText(this, "카페 이름을 입력해주세요", Toast.LENGTH_SHORT).show()
                //return@setOnClickListener
            } else {
                val logData = LogData(
                    date = calendar.time,
                    coffeeName = binding.logCoffeeName.text.toString(),
                    cafeName = binding.logCafeName.text.toString(),
                    rating = binding.logRating.rating,
                    imageName = binding.logImage.id.toString(),
                    diary = binding.logDiary.text.toString()
                )
                Toast.makeText(this, "저장되었습니다", Toast.LENGTH_SHORT).show()
                db.collection("log").document(sdfID.format(calendar.time)).set(logData.toMap())
                binding.logSave.visibility = android.view.View.INVISIBLE
                binding.logEdit.visibility = android.view.View.VISIBLE
                binding.logDelete.visibility = android.view.View.VISIBLE
                binding.logCoffeeName.isEnabled = false
                binding.logCafeName.isEnabled = false
                binding.logRating.isEnabled = false
                binding.logDiary.isEnabled = false
                flag = "ViewMode"
            }
        }

        val eventHandler = DialogInterface.OnClickListener { dialog, which ->
            when (which) {
                Dialog.BUTTON_POSITIVE -> { // 저장하지 않고 나가기
                    binding.logSave.visibility = android.view.View.INVISIBLE
                    binding.logEdit.visibility = android.view.View.VISIBLE
                    binding.logDelete.visibility = android.view.View.VISIBLE
                    binding.logCoffeeName.isEnabled = false
                    binding.logCafeName.isEnabled = false
                    binding.logDiary.isEnabled = false
                    flag = "ViewMode"
                }

                Dialog.BUTTON_NEGATIVE -> { // 취소 (이전 화면으로)
                    dialog?.dismiss()
                }
            }
        }

        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // 뒤로 버튼 이벤트 처리
                if (flag == "EditMode") {
                    AlertDialog.Builder(this@DateLogActivity)
                        .setMessage("저장하지 않고 나가시겠습니까?")
                        .setPositiveButton("저장하지 않고 나가기", eventHandler)
                        .setNegativeButton("계속 작성하기", eventHandler)
                        .show()
                }
                else if (flag == "ViewMode") {
                    finish()
                }
            }
        }

        this.onBackPressedDispatcher.addCallback(this, callback)

    }

    private fun calculateInSampleSize(fileUri: Uri, reqWidth: Int, reqHeight: Int): Int {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        try {
            var inputStream = contentResolver.openInputStream(fileUri)

            //inJustDecodeBounds 값을 true 로 설정한 상태에서 decodeXXX() 를 호출.
            //로딩 하고자 하는 이미지의 각종 정보가 options 에 설정 된다.
            BitmapFactory.decodeStream(inputStream, null, options)
            inputStream!!.close()
            inputStream = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
        //비율 계산........................
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1
        //inSampleSize 비율 계산
        if (height > reqHeight || width > reqWidth) {

            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            while (halfHeight / inSampleSize >= reqHeight && halfWidth / inSampleSize >= reqWidth) {
                inSampleSize *= 2
            }
        }
        return inSampleSize
    }

}