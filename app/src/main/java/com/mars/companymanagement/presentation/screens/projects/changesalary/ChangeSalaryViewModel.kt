package com.mars.companymanagement.presentation.screens.projects.changesalary

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mars.companymanagement.presentation.screens.base.BaseViewModel
import com.mars.companymanagement.presentation.screens.projects.changesalary.models.EmployeeSalaryViewData
import com.mars.companymanagement.presentation.screens.projects.changesalary.params.ChangeSalaryArgs
import dagger.hilt.android.lifecycle.HiltViewModel
import okhttp3.internal.format
import java.text.DecimalFormat
import javax.inject.Inject

@HiltViewModel
class ChangeSalaryViewModel @Inject constructor() : BaseViewModel() {


    private val moneyFormatter = DecimalFormat("0.00").apply {
        this.decimalFormatSymbols.currencySymbol = ","
    }

    private val _closeDialogLiveData: MutableLiveData<Float?> = MutableLiveData()
    val closeDialogLiveData: LiveData<Float?> = _closeDialogLiveData

    private val _employeeSalaryLiveData: MutableLiveData<EmployeeSalaryViewData> = MutableLiveData()
    val employeeSalaryLiveData: LiveData<EmployeeSalaryViewData> = _employeeSalaryLiveData

    private var budget: Float? = null

    fun setup(args: ChangeSalaryArgs) {
        _employeeSalaryLiveData.value = EmployeeSalaryViewData(args.name, args.position, moneyFormatter.format(args.salary))
    }

    fun budgetChanged(budget: String) {
        this.budget = budget.toFloatOrNull()
    }

    fun submit() {
        _closeDialogLiveData.value = budget
    }
}