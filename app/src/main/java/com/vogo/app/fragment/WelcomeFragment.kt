package com.vogo.app.fragment

import androidx.biometric.BiometricPrompt
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.vogo.app.R
import com.vogo.app.viewmodel.WelcomeViewModel
import com.vogo.app.databinding.FragmentWelcomeBinding
import java.util.concurrent.Executor

class WelcomeFragment : Fragment() {
    private lateinit var binding: FragmentWelcomeBinding
    private lateinit var viewModel: WelcomeViewModel
    private lateinit var executor:Executor
    private lateinit var biometricPrompt: BiometricPrompt
    private lateinit var prompt: BiometricPrompt.PromptInfo


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(WelcomeViewModel::class.java)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        onClickListener()
    }

    private fun init() {
        executor = ContextCompat.getMainExecutor(requireContext())
        biometricPrompt = BiometricPrompt(requireActivity(), executor, object : BiometricPrompt.AuthenticationCallback(){
            override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                super.onAuthenticationError(errorCode, errString)
                Toast.makeText(requireContext(), getString(R.string.authentication_error), Toast.LENGTH_SHORT).show()
            }

            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                super.onAuthenticationSucceeded(result)
                Toast.makeText(requireContext(), getString(R.string.authentication_success), Toast.LENGTH_SHORT).show()
                viewModel.navigateToResult(view!!)
            }

            override fun onAuthenticationFailed() {
                super.onAuthenticationFailed()
                Toast.makeText(requireContext(), getString(R.string.authentication_failed), Toast.LENGTH_SHORT).show()
            }

        })

        prompt = BiometricPrompt.PromptInfo.Builder()
            .setTitle(getString(R.string.please_authenticate))
            .setNegativeButtonText(getString(R.string.cancel))
            .build()
    }

    private fun onClickListener() {
        binding.ivFingerprint.setOnClickListener{
            biometricPrompt.authenticate(prompt)
        }
    }
}