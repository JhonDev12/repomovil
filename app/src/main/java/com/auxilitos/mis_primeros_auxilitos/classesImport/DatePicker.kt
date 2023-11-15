package com.auxilitos.mis_primeros_auxilitos.classesImport

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.DatePicker
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import java.util.*

class DatePicker: AppCompatActivity(), View.OnClickListener{

    class DatePickerFragment (val listener: (year:Int, month:Int, day:Int)-> Unit): DialogFragment(), DatePickerDialog.OnDateSetListener{

        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)
            return DatePickerDialog(requireActivity(), this, year, month, day)
        }
        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) { listener(year, month+1, day) }
    }

    override fun onClick(p0: View?) {}

}