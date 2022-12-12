package com.macalester.exercisego.dialog

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.macalester.exercisego.MapsActivity
import com.macalester.exercisego.databinding.ImageDialogBinding

/**
 * Code written by Linda Lor (LindaLor028 on GitHub).
 */
class ImageDialog : DialogFragment() {

    /**
     * Creates ImageDialog and updates UI as needed.
     */
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialogBuilder = AlertDialog.Builder(requireContext())

        val dialogViewBinding = ImageDialogBinding.inflate(
            requireActivity().layoutInflater)
        dialogBuilder.setView(dialogViewBinding.root)


        if (arguments != null && requireArguments().containsKey(MapsActivity.URL_KEY)){
            val url = requireArguments().get(MapsActivity.URL_KEY)
            Glide.with(this).load(url).into (
                dialogViewBinding.iv
            )
        }

        dialogBuilder.setPositiveButton("Exit") {
                dialog, which ->
        }

        return dialogBuilder.create()
    }
}