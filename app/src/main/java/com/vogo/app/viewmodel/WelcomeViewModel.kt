package com.vogo.app.viewmodel

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.navigation.Navigation
import com.vogo.app.fragment.WelcomeFragmentDirections

class WelcomeViewModel : ViewModel() {

    fun navigateToResult(view: View){
        Navigation.findNavController(view)
            .navigate(WelcomeFragmentDirections.actionWelcomeFragmentToResultFragment())
    }
}