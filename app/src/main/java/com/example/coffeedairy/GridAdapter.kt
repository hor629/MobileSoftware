package com.example.coffeedairy

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.example.coffeedairy.MyAuth.Companion.db
import com.example.coffeedairy.databinding.LogItemgridRecyclerviewBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.Calendar

class GridHolder(val binding: LogItemgridRecyclerviewBinding) :
    RecyclerView.ViewHolder(binding.root)

//val datas: MutableList<LogData>
class GridAdapter() :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var itemList = mutableListOf<LogData>()
    var db = FirebaseFirestore.getInstance()

    init{
        db.collection("log")
            .addSnapshotListener { snapshots, e ->
                itemList.clear()
                for(doc in snapshots!!.documents){
                    //val item = doc.document.toObject(LogData::class.java)
                    val item = doc.toObject<LogData>()
                    Log.d("log", "DocumentSnapshot data: ${item?.coffeeName}")
                    if (item != null) {
                        itemList.add(item)
                    }
                    notifyDataSetChanged()
                }
        }
    }
    override fun getItemCount(): Int = itemList.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):
            RecyclerView.ViewHolder = GridHolder(
        LogItemgridRecyclerviewBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val binding = (holder as GridHolder).binding

        val sdfDate = SimpleDateFormat("yyyy/MM/dd")
        val sdfThumbnail = SimpleDateFormat("yyyyMMdd")

        binding.logDate.text = itemList[position].date?.let { sdfDate.format(it) }
        binding.logCoffeeName.text = itemList[position].coffeeName
        binding.logCafeName.text = itemList[position].cafeName

        val storage: FirebaseStorage = FirebaseStorage.getInstance("gs://coffee-diary-18c06.appspot.com")
        val storageReference = storage.reference
        val pathReference = storageReference.child("images/${itemList[position].date?.let {
            sdfThumbnail.format(it)
        }}")

        val multiOption = MultiTransformation(
            CenterCrop(),
            RoundedCorners(20)
        )

        pathReference.downloadUrl.addOnSuccessListener { uri ->
            Glide.with(binding.logThumbnail)
                .load(uri)
                .apply(RequestOptions().transform(multiOption))
                .into(binding.logThumbnail)
        }

        binding.itemGrid.setOnClickListener {
            val intent = Intent(binding.root.context, DateLogActivity::class.java)
            val date = itemList[position].date
            val year = date?.year?.plus(1900)
            val month = date?.month?.plus(1)
            val dayOfMonth = date?.date
            intent.putExtra("year", year)
            intent.putExtra("month", month)
            intent.putExtra("dayOfMonth", dayOfMonth)
            startActivity(binding.root.context, intent, null)
        }
    }
}
