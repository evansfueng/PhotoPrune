package com.evans.photoprune.browse

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.evans.photoprune.ImageBean
import com.evans.photoprune.R
import com.evans.photoprune.utils.PixelUtil
import kotlinx.android.synthetic.main.item_image.view.*
import kotlinx.coroutines.*

class BrowseAdapter: RecyclerView.Adapter<BrowseAdapter.ViewHolder> {
    var mData:ArrayList<ImageBean>? = null
    private var mContext: Context
    constructor(context: Context) : super(){
        mContext = context
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_image, null, false))


    override fun getItemCount(): Int = if (mData.isNullOrEmpty()) 0 else mData!!.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mData!!.get(position))
    }
    fun update(data: ArrayList<ImageBean>) {
        mData = data
        notifyDataSetChanged()
    }
    class ViewHolder:RecyclerView.ViewHolder{
        constructor(itemView: View) : super(itemView)

        fun bind(bean: ImageBean) {
            itemView.tv_item_image.text = bean.name
            GlobalScope.launch(Dispatchers.Main) {
                var bitmap: Bitmap? = null
                withContext(Dispatchers.IO){
                    bitmap = loadBitmap(bean.url)
                }
                bitmap?.let {  itemView.iv_item_image.setImageBitmap(bitmap)}
            }
        }
        suspend fun loadBitmap(url: String) : Bitmap? {
            var opt = BitmapFactory.Options()
            var outSize = PixelUtil.dp2px(itemView.context,200.toFloat())
            opt.inJustDecodeBounds = true
            var boundMap = BitmapFactory.decodeFile(url , opt)
            if (boundMap == null) return null
            var inWidth = boundMap.width.toFloat()
            var inHeight = boundMap.height.toFloat()
            var widthScale = inWidth/outSize
            var heightScale = inHeight/outSize
            var scale = if (widthScale > heightScale) heightScale else widthScale
            if (scale == 0f) scale = 1f
            opt.inJustDecodeBounds = false
            opt.inSampleSize = scale.toInt()
            return BitmapFactory.decodeFile(url,opt)

        }
    }
}