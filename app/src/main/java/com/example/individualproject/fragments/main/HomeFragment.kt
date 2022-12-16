package com.example.individualproject.fragments.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.individualproject.R
import com.example.individualproject.const_values.BundleKeys.DOCTOR
import com.example.individualproject.const_values.BundleKeys.PHONE_NUMBER
import com.example.individualproject.databinding.FragmentHomeBinding
import com.example.individualproject.models.Doctor
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var reference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase

    private var doctor: Doctor? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        doctor = arguments?.getParcelable(DOCTOR)

        Toast.makeText(requireContext(), "${doctor?.firstName} ${doctor?.lastName}", Toast.LENGTH_SHORT).show()


        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "HomeFragment"
        @JvmStatic
        fun newInstance() =
            HomeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}