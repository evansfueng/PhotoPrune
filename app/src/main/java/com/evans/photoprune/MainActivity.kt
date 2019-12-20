package com.evans.photoprune

import android.Manifest
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.evans.epermission.EPermission
import com.evans.epermission.Permission
import com.evans.photoprune.browse.BrowseFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async

class MainActivity : AppCompatActivity() {
    var mEPermission: EPermission = EPermission(this)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mEPermission.requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_NETWORK_STATE),mPermissionCallback)

        bt_main_choose.setOnClickListener { supportFragmentManager.beginTransaction().add(R.id.fl_main_contain,
            BrowseFragment()
        ).commit() }
    }
    var mPermissionCallback = fun(denyPermissions : ArrayList<Permission>?){
        if (!denyPermissions.isNullOrEmpty()) {
            println(denyPermissions)
        } else {
            println("permission null")
        }
    }
}
