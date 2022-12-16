package com.example.individualproject.fragments.sign_in

import android.app.AlertDialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.individualproject.R
import com.example.individualproject.const_values.BundleKeys.DOCTOR
import com.example.individualproject.const_values.FirebaseKeys.DOCTORS
import com.example.individualproject.databinding.FragmentSignInBinding
import com.example.individualproject.databinding.NoDoctorsDialogItemBinding
import com.example.individualproject.models.Doctor
import com.google.firebase.database.*

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding get() = _binding!!
    private lateinit var navOptions: NavOptions

    private lateinit var reference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase

    private lateinit var doctorList: ArrayList<Doctor>

    private var isLoaded = false
    private var isFieldsIncorrect = false

    private var doctor: Doctor? = null

    private var isDatabaseEmpty = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(layoutInflater)
        navOptions = NavOptions.Builder()
            .setEnterAnim(R.anim.enter)
            .setExitAnim(R.anim.exit)
            .setPopEnterAnim(R.anim.pop_enter)
            .setPopExitAnim(R.anim.pop_exit)
            .build()

        binding.signUpTv.setOnClickListener {
            findNavController().navigate(R.id.signUpFragment, null, navOptions)
        }

        firebaseDatabase = FirebaseDatabase.getInstance()
        reference = firebaseDatabase.getReference(DOCTORS)
        reference
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    doctorList = ArrayList()
                    if (snapshot.exists()) {
                        val children = snapshot.children
                        children.forEach {
                            val doctor = it.getValue(Doctor::class.java)
                            if (doctor != null) {
                                doctorList.add(doctor)
                            }
                        }
                        Log.d(TAG, "onDataChange: There is ${doctorList.size} doctors")
                        isLoaded = true
                    } else {
                        Log.d(TAG, "onDataChange: There is no any doctors yet")
                        isDatabaseEmpty = true
                    }
                    binding.progressbar.visibility = View.GONE
                    binding.loginLayout.visibility = View.VISIBLE
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.e(TAG, "onCancelled: Something went wrong")
                    Log.e(TAG, "onCancelled: ${error.message}")
                    Log.e(TAG, "onCancelled: ${error.toException().printStackTrace()}")
                    Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT)
                        .show()
                }

            })

        binding.signInBtn.setOnClickListener {
            if (isAllFieldsFilled()) {
                Log.d(TAG, "onCreateView: all fields filled")
                val phoneNumber = binding.phoneNumberEt.text.toString()
                val password = binding.passwordEt.text.toString()
                var found = false
                doctorList.forEach {
                    if (it.phoneNumber == phoneNumber && it.password == password) {
                        doctor = it
                        found = true
                        openMainPage()
                    }
                }
                if (!found) {
                    Toast.makeText(
                        requireContext(),
                        "Telefon raqam yoki parol xato",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.d(TAG, "onCreateView: incorrect phone number or password")
                    isFieldsIncorrect = true
                } else {
                    if (doctorList.isEmpty()) {
                        showNoOneRegisteredDialog()
                        Log.d(TAG, "onCreateView: there is no any doctors registered yet")
                    }
                }
            } else {
                Log.d(TAG, "onCreateView: some fields not filled")
            }
        }

        binding.forgotPasswordTv.setOnClickListener {
            showNoOneRegisteredDialog()
        }

        binding.phoneNumberEt.addTextChangedListener {
            if (isFieldsIncorrect) {
                if (it.toString().isNotEmpty())
                    binding.phoneNumberEt.setBackgroundResource(R.drawable.correct_edit_text_background)
                else
                    binding.phoneNumberEt.setBackgroundResource(R.drawable.incorrect_edit_text_background)
            }
        }

        binding.passwordEt.addTextChangedListener {
            if (isFieldsIncorrect) {
                if (it.toString().isNotEmpty())
                    binding.passwordEt.setBackgroundResource(R.drawable.correct_edit_text_background)
                else
                    binding.passwordEt.setBackgroundResource(R.drawable.incorrect_edit_text_background)
            }
        }

        return binding.root
    }

    private fun showNoOneRegisteredDialog() {
        if (isDatabaseEmpty) {
            val builder = AlertDialog.Builder(requireContext())
            val alertDialog = builder.create()
            val noDoctorsDialogItemBinding = NoDoctorsDialogItemBinding.inflate(layoutInflater)
            alertDialog.setView(noDoctorsDialogItemBinding.root)
            alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            noDoctorsDialogItemBinding.yesBtn.setOnClickListener {
                alertDialog.dismiss()
                findNavController().navigate(R.id.signUpFragment, null, navOptions)
            }
            noDoctorsDialogItemBinding.noBtn.setOnClickListener {
                alertDialog.dismiss()
            }
            alertDialog.show()
        }
    }

    private fun openMainPage() {
        val bundle = Bundle()
        bundle.putParcelable(DOCTOR, doctor)
        findNavController().navigate(R.id.homeFragment, bundle, navOptions)
    }

    private fun isAllFieldsFilled(): Boolean {
        if (binding.phoneNumberEt.text.toString().isEmpty()) {
            binding.phoneNumberEt.setBackgroundResource(R.drawable.incorrect_edit_text_background)
            isFieldsIncorrect = true
        } else {
            binding.phoneNumberEt.setBackgroundResource(R.drawable.correct_edit_text_background)
        }

        if (binding.passwordEt.text.toString().isEmpty()) {
            binding.passwordEt.setBackgroundResource(R.drawable.incorrect_edit_text_background)
            isFieldsIncorrect = true
        } else {
            binding.passwordEt.setBackgroundResource(R.drawable.correct_edit_text_background)
        }

        return !isFieldsIncorrect
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "SignInFragment"

        @JvmStatic
        fun newInstance() =
            SignInFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}