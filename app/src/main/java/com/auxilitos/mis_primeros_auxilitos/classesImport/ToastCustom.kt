package com.auxilitos.mis_primeros_auxilitos.classesImport

import android.app.Activity
import android.content.Context
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.auxilitos.mis_primeros_auxilitos.R
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle

class ToastCustom {

    fun toastSuccess(context: Context, title: String, message: String)
    {
        MotionToast.createColorToast(
            context as Activity,
            title,
            message,
            MotionToastStyle.SUCCESS,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(context, R.font.dynapuff)
        )
    }

    fun toastError(context: Context, title: String, message: String)
    {
        MotionToast.createColorToast(
            context as Activity,
            title,
            message,
            MotionToastStyle.ERROR,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(context, R.font.dynapuff)
        )
    }

    fun toastErrorFragment(fragment: Fragment, title: String, message: String) {
        MotionToast.createColorToast(
            fragment.requireContext() as Activity,
            title,
            message,
            MotionToastStyle.ERROR,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(fragment.requireContext(), R.font.dynapuff)
        )
    }


    fun toastWarning(context: Context, title: String, message: String)
    {
        MotionToast.darkToast(
            context as Activity,
            title,
            message,
            MotionToastStyle.WARNING,
            MotionToast.GRAVITY_BOTTOM,
            MotionToast.LONG_DURATION,
            ResourcesCompat.getFont(context, R.font.dynapuff)
        )
    }

}