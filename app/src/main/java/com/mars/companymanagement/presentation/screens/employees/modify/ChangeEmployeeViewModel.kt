package com.mars.companymanagement.presentation.screens.employees.modify

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mars.companymanagement.data.repositories.employees.EmployeesRepository
import com.mars.companymanagement.data.repositories.employees.models.Employee
import com.mars.companymanagement.data.repositories.employees.models.EmployeePosition
import com.mars.companymanagement.data.repositories.taxonomies.TaxonomyRepository
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.employees.modify.behaviour.ChangeEmployeeBehaviour
import com.mars.companymanagement.presentation.screens.employees.modify.models.EmployeeChanges
import com.mars.companymanagement.presentation.screens.employees.modify.models.PreliminaryEmployeeViewData
import com.mars.companymanagement.presentation.screens.employees.modify.models.ValidationErrorViewData
import com.mars.companymanagement.presentation.views.IdentifiableAdapterItem
import com.mars.companymanagement.utils.liveData.SingleLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChangeEmployeeViewModel @Inject constructor(
    private val taxonomyRepository: TaxonomyRepository,
    private val employeesRepository: EmployeesRepository
) : BaseViewModel() {

    private val changes =  EmployeeChanges()

    private lateinit var behaviour: ChangeEmployeeBehaviour

    private lateinit var positions: List<EmployeePosition>

    private val _positionsLiveData: MutableLiveData<List<IdentifiableAdapterItem>> = MutableLiveData()
    val positionsLiveData: LiveData<List<IdentifiableAdapterItem>> = _positionsLiveData

    private val _preliminaryEmployeeLiveData: MutableLiveData<PreliminaryEmployeeViewData> = SingleLiveData()
    val preliminaryEmployeeLiveData: LiveData<PreliminaryEmployeeViewData> = _preliminaryEmployeeLiveData


    fun setup(behaviour: ChangeEmployeeBehaviour) {
        this.behaviour = behaviour

        positions = taxonomyRepository.getPositions()
        _positionsLiveData.value = positions.map { IdentifiableAdapterItem(it.id, it.name) }

        applyPreliminaryData()
    }

    private fun applyPreliminaryData() {
        val preliminaryData = behaviour.getPreliminaryData() ?: return
        _preliminaryEmployeeLiveData.value = PreliminaryEmployeeViewData(
            preliminaryData.firstName,
            preliminaryData.lastName,
            preliminaryData.email,
            preliminaryData.position.name
        )
    }

    fun onNothingSelected() {
        //TODO
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
        //TODO
    }

//    override fun validateChanges(changes: EmployeeChanges): Boolean {
//        return changes.firstName.isNullOrBlank().not() &&
//            changes.lastName.isNullOrBlank().not() &&
//            changes.email.isNullOrBlank().not() &&
//            changes.positionId.isNullOrBlank().not()
//    }
}