package com.evans.photoprune.browse

import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.evans.photoprune.ImageBean
import com.evans.photoprune.R
import kotlinx.android.synthetic.main.layout_browsefragment.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class BrowseFragment : Fragment {
    var imageList = ArrayList<ImageBean>()

    constructor() : super()



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =LayoutInflater.from(activity).inflate(R.layout.layout_browsefragment,null,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_browse_imagelist.adapter = BrowseAdapter(activity!!)
        rv_browse_imagelist.layoutManager = GridLayoutManager(activity!!,3,GridLayoutManager.VERTICAL,false)
        GlobalScope.async(Dispatchers.IO) {
            var cursor = activity!!.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null
            )
            while (cursor != null && cursor.moveToNext()) {
                var pathColIndex = cursor.getColumnIndex(MediaStore.MediaColumns.DATA)
                var nameColIndex = cursor.getColumnIndex(MediaStore.MediaColumns.TITLE)
                if (pathColIndex == -1 || nameColIndex == -1) break
                var imageBean = ImageBean(cursor.getString(pathColIndex),cursor.getString(nameColIndex))
                imageList.add(imageBean)
            }
            withContext(Dispatchers.Main){
                (rv_browse_imagelist.adapter as BrowseAdapter).update(imageList)
            }
        }
    }
}