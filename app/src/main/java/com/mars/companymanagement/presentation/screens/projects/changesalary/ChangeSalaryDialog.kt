package com.mars.companymanagement.presentation.screens.projects.changesalary

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.mars.companymanagement.R
import com.mars.companymanagement.databinding.DialogChangeSalaryBinding
import com.mars.companymanagement.presentation.screens.projects.changesalary.params.ChangeSalaryArgs
import com.mars.companymanagement.utils.inputfilter.DecimalDigitsInputFilter
import dagger.hilt.android.AndroidEntryPoint

private const val CHANGE_SALARY_ARGS_KEY = "change_salary_args_key"

@AndroidEntryPoint
class ChangeSalaryDialog : DialogFragment() {
    private val viewModel: ChangeSalaryViewModel by viewModels()

    private val parentListener: ChangeSalaryListener?
        get() =
            (parentFragment as? ChangeSalaryListener) ?: (activity as? ChangeSalaryListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setup(arguments?.getParcelable(CHANGE_SALARY_ARGS_KEY) ?: return)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.dialog_change_salary, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        DialogChangeSalaryBinding.bind(view).run {
            initViews(this)
            initObservers(this)
        }
    }

    private fun initViews(binding: DialogChangeSalaryBinding) {
        binding.salaryEditText.apply {
            filters += DecimalDigitsInputFilter(10, 2)
            doAfterTextChanged { viewModel.budgetChanged(it.toString()) }
        }
        binding.cancelButton.setOnClickListener {
            parentListener?.salaryChangeDismissed()
            dismiss()
        }
        binding.submitButton.setOnClickListener { viewModel.submit() }
    }

    private fun initObservers(binding: DialogChangeSalaryBinding) {
        viewModel.closeDialogLiveData.observe(viewLifecycleOwner) {
            if (it == null) {
                parentListener?.salaryChangeDismissed()
                dismiss()
                return@observe
            }
            parentListener?.salaryChanged(it)
            dismiss()
        }
        viewModel.employeeSalaryLiveData.observe(viewLifecycleOwner) {
            binding.nameTextView.text = it.name
            binding.positionTextView.text = it.position
            binding.salaryEditText.setText(it.salary)
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            window?.decorView?.background?.alpha = 0
        }
    }

    override fun onResume() {
        super.onResume()

        val currentHeight = dialog?.window?.attributes?.height

        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            currentHeight ?: WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onCancel(dialog: DialogInterface) {
        parentListener?.salaryChangeDismissed()
    }

    companion object {
        fun newInstance(args: ChangeSalaryArgs) = ChangeSalaryDialog().apply {
            arguments = Bundle().apply {
                putParcelable(CHANGE_SALARY_ARGS_KEY, args)
            }
        }
    }
}