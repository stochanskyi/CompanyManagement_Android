package com.mars.companymanagement.presentation.screens.employees.modify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.mars.companymanagement.R
import com.mars.companymanagement.data.common.StringProvider
import com.mars.companymanagement.data.repositories.employees.EmployeesRepository
import com.mars.companymanagement.data.repositories.employees.models.EmployeePosition
import com.mars.companymanagement.data.repositories.taxonomies.TaxonomyRepository
import com.mars.companymanagement.ext.launchSafeCall
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.employees.modify.behaviour.ChangeEmployeeBehaviour
import com.mars.companymanagement.presentation.screens.employees.modify.models.EmployeeChanges
import com.mars.companymanagement.presentation.screens.employees.modify.models.PreliminaryEmployeeViewData
import com.mars.companymanagement.presentation.screens.employees.modify.models.ValidationErrorViewData
import com.mars.companymanagement.presentation.views.IdentifiableAdapterItem
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangeEmployeeViewModel @Inject constructor(
    private val taxonomyRepository: TaxonomyRepository,
    private val employeesRepository: EmployeesRepository,
    private val stringProvider: StringProvider
) : BaseViewModel() {

    private val changes = EmployeeChanges()

    private lateinit var behaviour: ChangeEmployeeBehaviour

    private lateinit var positions: List<EmployeePosition>

    private val _positionsLiveData: MutableLiveData<List<IdentifiableAdapterItem>> = MutableLiveData()
    val positionsLiveData: LiveData<List<IdentifiableAdapterItem>> = _positionsLiveData

    private val _preliminaryEmployeeLiveData: MutableLiveData<PreliminaryEmployeeViewData> = SingleLiveData()
    val preliminaryEmployeeLiveData: LiveData<PreliminaryEmployeeViewData> = _preliminaryEmployeeLiveData

    private val _closeChangeScreenLiveData: SingleLiveData<Unit> = SingleLiveData()
    val closeChangeScreenLiveData: LiveData<Unit> = _closeChangeScreenLiveData

    private val _validationErrorLiveData: MutableLiveData<ValidationErrorViewData> = SingleLiveData()
    val validationErrorLiveData: LiveData<ValidationErrorViewData> = _validationErrorLiveData

    private val _savingChangesLiveData: MutableLiveData<Boolean> = MutableLiveData()
    val savingChangesLiveData: LiveData<Boolean> = _savingChangesLiveData

    fun setup(behaviour: ChangeEmployeeBehaviour) {
        this.behaviour = behaviour

        positions = taxonomyRepository.getPositions()
        _positionsLiveData.value = positions.map { IdentifiableAdapterItem(it.id, it.name) }

        applyPreliminaryData()
    }

    private fun applyPreliminaryData() {
        val preliminaryData = behaviour.getPreliminaryData() ?: return

        changes.apply {
            firstName = preliminaryData.firstName
            lastName = preliminaryData.lastName
            email = preliminaryData.email
            positionId = preliminaryData.position.id
        }

        _preliminaryEmployeeLiveData.value = PreliminaryEmployeeViewData(
            preliminaryData.firstName,
            preliminaryData.lastName,
            preliminaryData.email,
            preliminaryData.position.name
        )
    }

    fun firstNameChanged(firstName: String) {
        changes.firstName = firstName
    }

    fun lastNameChanged(lastName: String) {
        changes.lastName = lastName
    }

    fun emailChanged(email: String) {
        changes.email = email
    }

    fun positionChanged(positionName: String) {
        changes.positionId = positions.firstOrNull { it.name == positionName }?.id
    }

    fun saveChanges() {
        val validationData = getValidationErrorData()

        if (validationData != null) {
            _validationErrorLiveData.value = validationData
            return
        }

        viewModelScope.launchSafeCall(_savingChangesLiveData) {
            val result = safeRequestCall { behaviour.applyChanges(employeesRepository, changes) }
            if (result != null) _closeChangeScreenLiveData.call()
        }
    }

    private fun getValidationErrorData(): ValidationErrorViewData? {
        val firstNameError = if (changes.firstName.isNullOrBlank()) R.string.first_name_error else null
        val lastNameError = if (changes.lastName.isNullOrBlank()) R.string.last_name_error else null
        val emailError = if (changes.email.isNullOrBlank()) R.string.email_error else null
        val positionError = if (changes.email.isNullOrBlank()) R.string.email_error else null

        if (firstNameError == null && lastNameError == null && emailError == null && positionError == null) return null

        return ValidationErrorViewData(
            firstNameError?.let { stringProvider.getString(it) },
            lastNameError?.let { stringProvider.getString(it) },
            emailError?.let { stringProvider.getString(it) },
            positionError?.let { stringProvider.getString(it) }
        )
    }
}