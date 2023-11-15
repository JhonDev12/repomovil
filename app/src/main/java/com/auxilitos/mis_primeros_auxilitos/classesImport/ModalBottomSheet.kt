package com.auxilitos.mis_primeros_auxilitos.classesImport

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.auxilitos.mis_primeros_auxilitos.R
import com.auxilitos.mis_primeros_auxilitos.registro.Login
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.jetbrains.annotations.Nullable

class ModalBottomSheet : BottomSheetDialogFragment() {

    private var toast = ToastCustom()

    private var mContext: Context? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    @SuppressLint("KotlinNullnessAnnotation")
    @androidx.annotation.Nullable
    @Override
    override fun onCreateView(inflater: LayoutInflater, @Nullable container: ViewGroup?, @Nullable savedInstanceState: Bundle?): View {

        val v:View = inflater.inflate(R.layout.bottom_sheet, container, false)

        val btn_cerrar_sesion = v.findViewById<Button>(R.id.btn_cerrar_sesion)

        val btn_cancelar = v.findViewById<Button>(R.id.btn_cancelar)

        btn_cancelar.setOnClickListener{
            dismiss()
        }

        btn_cerrar_sesion.setOnClickListener{
            toast.toastSuccess(mContext!!, "Cerrar sesión", "Sesión cerrada con exito!!!")
            dismiss()
            startActivity(Intent(mContext, Login::class.java))
        }


        return v

    }


    companion object {
        const val TAG = "ModalBottomSheet"
    }


}
