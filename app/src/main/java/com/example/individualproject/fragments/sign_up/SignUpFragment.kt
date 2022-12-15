package com.example.individualproject.fragments.sign_up

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.individualproject.R
import com.example.individualproject.database.Doctor
import com.example.individualproject.databinding.FragmentSignUpBinding
import com.example.individualproject.databinding.VerificationCodeLayoutBinding
import com.example.individualproject.helper_functions.leftPad
import com.example.individualproject.helper_functions.phoneNumberToId
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.database.*
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

    private var timer = 60
    private var handler = Handler()

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

        binding.phoneNumberEt.addTextChangedListener {
            if (it.toString()
                    .isNotEmpty()
            ) binding.phoneNumberEt.setBackgroundResource(R.drawable.correct_edit_text_background)
        }

        binding.saveBtn.setOnClickListener {
            Log.d(TAG, "onCreateView: save clicked")
            if (isPhoneVerified) {
                Log.d(TAG, "onCreateView: phone verified now create account")
                // create account
                // save data to firebase
                createAccount()
            } else {
                val str = binding.phoneNumberEt.text.toString()
                if (str.isEmpty()) {
                    binding.phoneNumberEt.setBackgroundResource(R.drawable.incorrect_edit_text_background)
                    Log.d(TAG, "onCreateView: phone number is empty")
                } else {
                    Log.d(TAG, "onCreateView: phone number is not empty")
                    phoneNumber = str
                    firebaseDatabase = FirebaseDatabase.getInstance()
                    reference = firebaseDatabase.getReference("doctors")
                    Log.d(TAG, "onCreateView: reference opened")
                    reference.addValueEventListener(object : ValueEventListener {
                        override fun onDataChange(snapshot: DataSnapshot) {
                            if (snapshot.exists()) {
                                val children = snapshot.children
                                children.forEach {
                                    val doctor = it.getValue(Doctor::class.java)
                                    if (doctor != null) {
                                        if (doctor.phoneNumber == phoneNumber) {
                                            Toast.makeText(requireContext(), getString(R.string.number_already_registered), Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            } else {
                                sendVerificationCode()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {

                        }

                    })
                }
            }
        }

        return binding.root
    }

    private fun createAccount() {
        reference
            .child(phoneNumberToId(phoneNumber))
    }

    private fun resendVerificationCode() {
        auth = FirebaseAuth.getInstance()
        val options = resendToken?.let {
            PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(callbacks)
                .setForceResendingToken(it)
                .build()
        }
        if (options != null) {
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
        timer = 60
        codeLayoutBinding.verificationLayout.visibility = View.VISIBLE
        codeLayoutBinding.refreshIv.visibility = View.GONE
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
            Toast.makeText(requireContext(), "Completed ${credential.smsCode}", Toast.LENGTH_SHORT)
                .show()
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
            codeLayoutBinding.progressbar.visibility = View.GONE
            codeLayoutBinding.verificationLayout.visibility = View.VISIBLE
            Toast.makeText(
                requireContext(),
                "Verification code sent to $phoneNumber",
                Toast.LENGTH_SHORT
            ).show()
            startTimer()
        }

    }

    private fun startTimer() {
        runnable.run()
    }

    private val runnable = object : Runnable {
        override fun run() {
            handler = Handler()
            handler.postDelayed(this, 1000)
            updateTimer()
            if (timer > 0)
                timer--
            else {
                timeUp()
            }
        }
    }

    private fun timeUp() {
        codeLayoutBinding.verificationLayout.visibility = View.GONE
        codeLayoutBinding.refreshIv.visibility = View.VISIBLE
    }

    private fun updateTimer() {
        val min = timer / 60
        val sec = timer - min * 60
        codeLayoutBinding.timerTv.text = "${leftPad(min)}:${leftPad(sec)}"
    }

    private fun showCodeDialog() {
        val builder = AlertDialog.Builder(requireContext())
        codeLayoutBinding = VerificationCodeLayoutBinding.inflate(layoutInflater)
        alertDialog = builder.create()
        alertDialog.setView(codeLayoutBinding.root)
        alertDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        codeLayoutBinding.codeSentTv.text =
            "${getString(R.string.code_sent_1)} $phoneNumber ${getString(R.string.code_sent_2)}"

        codeLayoutBinding.refreshIv.setOnClickListener {
            resendVerificationCode()
        }

        codeLayoutBinding.confirmBtn.setOnClickListener {
            val code = codeLayoutBinding.verificationCodeEt.text.toString()
            if (code.length != 6) {
                codeLayoutBinding.warningTv.visibility = View.VISIBLE
            } else {
                codeLayoutBinding.warningTv.visibility = View.GONE
                val credential = PhoneAuthProvider.getCredential(storedVerificationId ?: "", code)
                signInWithPhoneAuthCredential(credential)
            }
        }

        codeLayoutBinding.verificationCodeEt.addTextChangedListener {
            if (it.toString().length == 6) {
                codeLayoutBinding.warningTv.visibility = View.GONE
            }
        }

        alertDialog.setCancelable(false)
        alertDialog.show()
        Log.d(TAG, "showCodeDialog: dialog shown")
    }

    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "signInWithPhoneAuthCredential: task is successful")
                    isPhoneVerified = true
                    binding.saveBtn.text = getString(R.string.save_data)
                    Toast.makeText(requireContext(), "To'g'ri", Toast.LENGTH_SHORT).show()
                } else {
                    Log.e(TAG, "signInWithPhoneAuthCredential: ${task.exception}")
                    if (task.exception is FirebaseAuthInvalidCredentialsException) {
                        Log.e(TAG, "signInWithPhoneAuthCredential: ${task.exception}")
                        Toast.makeText(requireContext(), "Kiritilgan kod xato", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
                alertDialog.dismiss()
            }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private const val TAG = "SignUpFragment"
    }
}