package com.example.coffeedairy

import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class RequestPermissionsUtil(private val context: Context) {

    private val REQUEST_LOCATION = 1

    private val permissionsLocationDownApi29Impl = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )

    fun checkAndRequestLocationPermissions(activity: Activity, requestCode: Int) {
        val permissionsToCheck =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) permissionsLocationDownApi29Impl
            else permissionsLocationDownApi29Impl

        checkAndRequestPermissions(activity, permissionsToCheck, requestCode)
    }

    private fun checkAndRequestPermissions(activity: Activity, permissions: Array<String>, requestCode: Int) {
        val missingPermissions = getMissingPermissions(permissions)
        if (missingPermissions.isNotEmpty()) {
            requestPermissions(activity, missingPermissions, requestCode)
        }
    }

    private fun getMissingPermissions(permissions: Array<String>): List<String> {
        val missingPermissions = mutableListOf<String>()
        for (permission in permissions) {
            if (ContextCompat.checkSelfPermission(
                    context,
                    permission
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                missingPermissions.add(permission)
            }
        }
        return missingPermissions
    }

    private fun requestPermissions(activity: Activity, permissions: List<String>, requestCode: Int) {
        ActivityCompat.requestPermissions(
            activity,
            permissions.toTypedArray(),
            requestCode
        )
    }
}

