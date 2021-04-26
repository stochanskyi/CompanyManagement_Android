package com.mars.companymanagement.presentation.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mars.companymanagement.data.repositories.user.UserRepository
import com.mars.companymanagement.data.repositories.user.models.levels.base.AccessLevel
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val userRepository: UserRepository
) : BaseViewModel() {

    private val _accessLevelLiveData: MutableLiveData<AccessLevel> = SingleLiveData()
    val accessLevelLiveData: LiveData<AccessLevel> = _accessLevelLiveData

    init {
        val user = userRepository.getUser()
        user?.accessLevel?.let { _accessLevelLiveData.value = it }
    }
}