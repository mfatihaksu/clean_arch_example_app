package com.mehmetfatihaksu.sahibindencase.presentation.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.mehmetfatihaksu.sahibindencase.R
import com.mehmetfatihaksu.sahibindencase.common.MaterialDialogHelper
import com.mehmetfatihaksu.sahibindencase.databinding.LoginFragmentBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val viewModel: LoginViewModel by viewModels()
    private var _binding : LoginFragmentBinding? = null
    private val binding get() = _binding
    private lateinit var callbackManager : CallbackManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callbackManager = CallbackManager.Factory.create()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = LoginFragmentBinding.inflate(inflater , container , false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding?.loginButton?.fragment = this
        if (viewModel.isLoggedInWithFacebook()){
            navigateHome()
        }
        registerFacebookCallBack()
    }

    private fun navigateHome(){
        Navigation.findNavController(requireView()).navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun registerFacebookCallBack() {
        binding?.loginButton?.registerCallback(callbackManager , object : FacebookCallback<LoginResult>{
            override fun onCancel() {
                MaterialDialogHelper().showDialog(requireContext() , getString(R.string.facebook_login_canceled))
            }

            override fun onError(error: FacebookException) {
                MaterialDialogHelper().showDialog(requireContext() , getString(R.string.facebook_login_failed))
            }

            override fun onSuccess(result: LoginResult) {
                navigateHome()
            }
        })
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}