package com.example.individualproject.fragments.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.individualproject.adapters.HomeSpecialityAdapter
import com.example.individualproject.const_values.BundleKeys.DOCTOR
import com.example.individualproject.const_values.FirebaseKeys.SPECIALITIES
import com.example.individualproject.databinding.FragmentHomeBinding
import com.example.individualproject.models.Doctor
import com.example.individualproject.models.Speciality
import com.google.firebase.database.*
import kotlin.system.exitProcess

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var reference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase

    private var doctor: Doctor? = null
    private lateinit var specialityList: ArrayList<Speciality>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(layoutInflater)
        doctor = arguments?.getParcelable(DOCTOR)

        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference(SPECIALITIES)
        reference
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    specialityList = ArrayList()
                    if (snapshot.exists()) {
                        val children = snapshot.children
                        children.forEach {
                            val value = it.getValue(Speciality::class.java)
                            if (value != null) {
                                specialityList.add(value)
                            }
                        }
                        val specialityAdapter = HomeSpecialityAdapter(
                            specialityList
                        ) {
                            openSpeciality(it)
                        }
                        binding.rv.adapter = specialityAdapter
                        binding.rv.visibility = View.VISIBLE
                        Log.d(TAG, "onDataChange: ${specialityList.size} specialities found")
                    } else {
                        binding.emptyTv.visibility = View.VISIBLE
                        Log.d(TAG, "onDataChange: there is no any specialities")
                    }
                    binding.progressbar.visibility = View.GONE
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "onCancelled: ${error.toException().message}")
                    Log.e(TAG, "onCancelled: ${error.toException().printStackTrace()}")
                    Log.e(TAG, "onCancelled: ${error.message}")
                    Log.e(TAG, "onCancelled: $error")
                    Toast.makeText(requireContext(), "Error -> onCancelled", Toast.LENGTH_SHORT).show()
                }

            })

        val callback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                requireActivity().finishAffinity()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)

        return binding.root
    }

    private fun openSpeciality(speciality: Speciality) {

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