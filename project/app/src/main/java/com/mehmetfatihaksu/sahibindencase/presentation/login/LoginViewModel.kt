package com.mehmetfatihaksu.sahibindencase.presentation.login

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.facebook.AccessToken

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {

    fun isLoggedInWithFacebook():Boolean{
        val accessToken = AccessToken.getCurrentAccessToken()
        return accessToken!=null && !accessToken.isExpired
    }
}