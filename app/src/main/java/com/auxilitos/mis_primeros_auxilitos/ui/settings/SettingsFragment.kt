package com.auxilitos.mis_primeros_auxilitos.ui.settings

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.auxilitos.mis_primeros_auxilitos.classesImport.ToastCustom
import com.auxilitos.mis_primeros_auxilitos.content.CreditosActivity
import com.auxilitos.mis_primeros_auxilitos.databinding.FragmentSettingsBinding
import com.auxilitos.mis_primeros_auxilitos.registro.Login
import com.auxilitos.mis_primeros_auxilitos.registro.Profile
import com.auxilitos.mis_primeros_auxilitos.settings.MisionVisionActivity

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val toast = ToastCustom()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val notificationsViewModel =
            ViewModelProvider(this)[SettingsViewModel::class.java]

        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textNotifications
        notificationsViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.btnProfile.setOnClickListener{
            toast.toastSuccess(this.requireActivity(), "Mis Primeros Auxilitos", "Perfil del usuario")
            startActivity(Intent(this.requireContext(), Profile::class.java))
        }

        binding.btnCredits.setOnClickListener {
            toast.toastSuccess(this.requireActivity(), "Mis Primeros Auxilitos", "Créditos 😊😊👌")
            startActivity(Intent(this.requireContext(), CreditosActivity::class.java))
        }

        binding.btnMisionVision.setOnClickListener{
            toast.toastSuccess(this.requireActivity(), "Mis Primeros Auxilitos", "Vista de la misión y visión")
            startActivity(Intent(this.requireContext(), MisionVisionActivity::class.java))
        }

        binding.btnLogout.setOnClickListener{
            toast.toastSuccess(this.requireActivity(), "Mis Primeros Auxilitos", "Logout existoso!!!")
            startActivity(Intent(this.requireContext(), Login::class.java))
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}