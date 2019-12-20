package com.evans.epermission

import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity

class EPermission (var activity: FragmentActivity){
    private var mDenyList :ArrayList<Permission> = ArrayList()
    private var mFragment : EFragment? = null
    private lateinit var mPermissionStateCallback: (denyPermissions :ArrayList<Permission>?) -> Unit
    fun requestPermissions(permissions : Array<String> , onPermissionStateCallback: (denyPermissions :ArrayList<Permission>?) -> Unit){
        if (permissions.isEmpty()) {
            onPermissionStateCallback(null)
            return
        }
        mPermissionStateCallback = onPermissionStateCallback
        if (mFragment == null) {
            mFragment = EFragment()
            activity.supportFragmentManager.beginTransaction().add(mFragment!!,"EPermission").commitNow()
        }
        mFragment?.request(permissions,mCallback)


    }

    var mCallback = fun(permissions: Array<out String>, grantReult: IntArray){
        mDenyList.clear()
        for (i in 0..permissions.size-1) {
            if (grantReult.get(i) != PackageManager.PERMISSION_GRANTED) {
                var permission = permissions[i]
                mDenyList.add(Permission(permission,mFragment!!.shouldShowRequestPermissionRationale(permission)))
            }
        }
        mPermissionStateCallback(if (mDenyList.size>0) mDenyList else null)

    }
    companion object{
        const val  CODE = 9256
    }
}