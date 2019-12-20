package com.evans.epermission

import androidx.fragment.app.Fragment

class EFragment : Fragment{
    private lateinit var mCallback : (permissions: Array<out String>, grantReult: IntArray) -> Unit
    constructor() : super()

    fun request(permissions: Array<String>,onResultCallback: (permissions: Array<out String>, grantReult: IntArray) -> Unit){
        mCallback = onResultCallback
        requestPermissions(permissions,EPermission.CODE)

    }
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == EPermission.CODE) {
            mCallback(permissions,grantResults)
        }
    }
}