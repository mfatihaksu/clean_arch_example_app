package com.mehmetfatihaksu.sahibindencase.common

import android.content.Context
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mehmetfatihaksu.sahibindencase.R

class MaterialDialogHelper {

    fun showDialog(context: Context, message: String?){
        MaterialAlertDialogBuilder(context).setMessage(message)
            .setPositiveButton(context.getString(R.string.okay)){ dialogInterface, _->
                dialogInterface.dismiss()
            }
            .setCancelable(false)
            .show()
    }
}