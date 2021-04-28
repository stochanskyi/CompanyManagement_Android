package com.mars.companymanagement.presentation.screens.customers.modify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.R
import com.mars.companymanagement.data.common.StringProvider
import com.mars.companymanagement.data.repositories.customers.CustomersRepository
import com.mars.companymanagement.ext.launchSafeCall
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.customers.modify.behaviour.ChangeCustomerBehaviour
import com.mars.companymanagement.presentation.screens.customers.modify.models.CustomerChanges
import com.mars.companymanagement.presentation.screens.customers.modify.models.CustomerValidationErrorViewData
import com.mars.companymanagement.presentation.screens.customers.modify.models.PreliminaryCustomerViewData
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangeCustomerViewModel @Inject constructor(
    private val customersRepository: CustomersRepository,
    private val stringProvider: StringProvider
) : BaseViewModel() {

    private val changes = CustomerChanges()

    private lateinit var behaviour: ChangeCustomerBehaviour

    private val _preliminaryCustomerLiveData: MutableLiveData<PreliminaryCustomerViewData> = SingleLiveData()
    val preliminaryCustomerLiveData: LiveData<PreliminaryCustomerViewData> = _preliminaryCustomerLiveData

    private val _closeChangeScreenLiveData: SingleLiveData<Unit> = SingleLiveData()
    val closeChangeScreenLiveData: LiveData<Unit> = _closeChangeScreenLiveData

    private val _validationErrorLiveData: MutableLiveData<CustomerValidationErrorViewData> = SingleLiveData()
    val validationErrorLiveData: LiveData<CustomerValidationErrorViewData> = _validationErrorLiveData

    private val _savingChangesLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val savingChangesLiveData: LiveData<Boolean> = _savingChangesLiveData

    fun setup(behaviour: ChangeCustomerBehaviour) {
        this.behaviour = behaviour
        applyPreliminaryData()
    }

    private fun applyPreliminaryData() {
        val preliminaryData = behaviour.getPreliminaryData() ?: return

        changes.apply {
            firstName = preliminaryData.firstName
            lastName = preliminaryData.lastName
            country = preliminaryData.country
            email = preliminaryData.email
            phoneNumber = preliminaryData.phoneNumber
        }

        _preliminaryCustomerLiveData.value = PreliminaryCustomerViewData(
            preliminaryData.firstName,
            preliminaryData.lastName,
            preliminaryData.country,
            preliminaryData.email,
            preliminaryData.phoneNumber
        )
    }

    fun firstNameChanged(firstName: String) {
        changes.firstName = firstName
    }

    fun lastNameChanged(lastName: String) {
        changes.lastName = lastName
    }

    fun countryChanged(country: String) {
        changes.country = country
    }

    fun emailChanged(email: String) {
        changes.email = email
    }

    fun phoneNumberChanged(phoneNumber: String) {
        changes.phoneNumber = phoneNumber
    }

    fun saveChanges() {
        val validationData = getValidationErrorData()

        if (validationData != null) {
            _validationErrorLiveData.value = validationData
            return
        }

        viewModelScope.launchSafeCall(_savingChangesLiveData) {
            val result = safeRequestCall { behaviour.applyChanges(customersRepository, changes) }
            if (result != null) _closeChangeScreenLiveData.call()
        }
    }

    private fun getValidationErrorData(): CustomerValidationErrorViewData? {
        val firstNameError = if (changes.firstName.isNullOrBlank()) R.string.first_name_error else null
        val lastNameError = if (changes.lastName.isNullOrBlank()) R.string.last_name_error else null
        val countryError = if (changes.country.isNullOrBlank()) R.string.country_error else null
        val emailError = if (changes.email.isNullOrBlank()) R.string.email_error else null
        val phoneNumberError = if (changes.phoneNumber.isNullOrBlank()) R.string.phone_number_error else null

        if (firstNameError == null &&
            lastNameError == null &&
            countryError == null &&
            emailError == null &&
            phoneNumberError == null
        ) {
            return null
        }

        return CustomerValidationErrorViewData(
            firstNameError?.let { stringProvider.getString(it) },
            lastNameError?.let { stringProvider.getString(it) },
            countryError?.let { stringProvider.getString(it) },
            emailError?.let { stringProvider.getString(it) },
            phoneNumberError?.let { stringProvider.getString(it) }
        )
    }
}