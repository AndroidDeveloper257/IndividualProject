package com.example.individualproject.fragments.sign_up

import android.app.AlertDialog
import android.app.AlertDialog.Builder
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.example.individualproject.R
import com.example.individualproject.databinding.FragmentSignUpBinding
import com.example.individualproject.databinding.VerificationCodeLayoutBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.util.concurrent.TimeUnit

class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding get() = _binding!!

    private lateinit var reference: DatabaseReference
    private lateinit var firebaseDatabase: FirebaseDatabase
    private var isPhoneVerified = false

    private lateinit var auth: FirebaseAuth
    private var storedVerificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    private lateinit var phoneNumber: String

    /**
     * for verification code dialog
     */
    private lateinit var alertDialog: AlertDialog
    private lateinit var codeLayoutBinding: VerificationCodeLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(layoutInflater)

        // TODO: send verification code and get this in dialog, and then create account on firebase

        binding.phoneNumberEt.addTextChangedListener {
            if (it.toString()
                    .isNotEmpty()
            ) binding.phoneNumberEt.setBackgroundResource(R.drawable.correct_edit_text_background)
        }

        binding.saveBtn.setOnClickListener {
            if (isPhoneVerified) {
                // create account
                // save data to firebase
//                createAccount()
            } else {
                val str = binding.phoneNumberEt.text.toString()
                if (str.isEmpty()) {
                    binding.phoneNumberEt.setBackgroundResource(R.drawable.incorrect_edit_text_background)
                } else {
                    phoneNumber = str
                    sendVerificationCode()
                }
            }
        }

        return binding.root
    }

    private fun sendVerificationCode() {
        showCodeDialog()
        auth = FirebaseAuth.getInstance()
        val authOptions = PhoneAuthOptions.newBuilder(auth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(requireActivity())
            .setCallbacks(callbacks)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(authOptions)
    }

    private val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        override fun onVerificationCompleted(credential: PhoneAuthCredential) {
            Log.d(TAG, "onVerificationCompleted: ${credential.smsCode}")
            Log.d(TAG, "onVerificationCompleted: $credential")
        }

        override fun onVerificationFailed(e: FirebaseException) {
            Log.e(TAG, "onVerificationFailed: ${e.message}")
            Log.e(TAG, "onVerificationFailed: ${e.printStackTrace()}")
            Log.e(TAG, "onVerificationFailed: $e")
            Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            Toast.makeText(requireContext(), "e.message", Toast.LENGTH_SHORT).show()
        }

        override fun onCodeSent(
            verificationId: String,
            token: PhoneAuthProvider.ForceResendingToken
        ) {
            super.onCodeSent(verificationId, token)
            storedVerificationId = verificationId
            resendToken = token
        }

    }

    private fun showCodeDialog() {
        val builder = Builder(requireContext())
        codeLayoutBinding = VerificationCodeLayoutBinding.inflate(layoutInflater)
        alertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "SignUpFragment"

        @JvmStatic
        fun newInstance() =
            SignUpFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}