package com.example.warehouset.main.transaction

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.warehouset.R
import com.example.warehouset.core.ResourceState
import com.example.warehouset.core.extentions.onClick
import com.example.warehouset.core.extentions.visibility
import com.example.warehouset.databinding.FragmentHistoryTransactionBinding
import com.example.warehouset.settings.Settings
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.*

class HistoryTransaction: Fragment(R.layout.fragment_history_transaction) {
    private var _binding: FragmentHistoryTransactionBinding? = null
    private val binding get() = _binding!!
    private val adapter: HistoryTransactionAdapter by inject()
    private val viewModel: HistoryTransactionViewModel by viewModel()
    private val settings: Settings by inject()
    private lateinit var navController: NavController
    private var startDate: String = ""
    private lateinit var setListener: DatePickerDialog.OnDateSetListener

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryTransactionBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        binding.apply {
            viewModel.getHistoryTransaction(
                Settings.HEADERXRW,
                Settings.HEADERACCEPT, "Bearer ${settings.token}",startDate)
            rvHistory.adapter = adapter
            tilTime.onClick {
                val calendar = Calendar.getInstance()
                val year = calendar.get((Calendar.YEAR))
                var month = calendar.get((Calendar.MONTH))
                val day = calendar.get((Calendar.DAY_OF_MONTH))
                setListener = DatePickerDialog.OnDateSetListener { _, year1, month1, dayOfMonth1 ->
                    month = month1+1
                    startDate = "$year1-$month-$dayOfMonth1"
                    binding.tilTime.text = startDate
                }
                val datePickerDialog = DatePickerDialog(
                    requireContext(),
                    android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                    setListener
                    ,year, month, day)
                datePickerDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                datePickerDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK") { dialog, which ->
                    datePickerDialog.onClick(dialog, DialogInterface.BUTTON_POSITIVE)
                    viewModel.getHistoryTransaction(
                        Settings.HEADERXRW,
                        Settings.HEADERACCEPT, "Bearer ${settings.token}", startDate)
                }
                datePickerDialog.show()
            }
        }
        setupObserver()
    }
    private fun setupObserver(){
        binding.apply {
            viewModel.historyTransaction.observe(viewLifecycleOwner, {
                when (it.status) {
                    ResourceState.LOADING->{progressBar.visibility(true)}
                    ResourceState.SUCCESS->{progressBar.visibility(false)
                        it.data?.let { data->
                            adapter.models = data.payload.toMutableList()
                        }}
                    ResourceState.ERROR->{progressBar.visibility(false)
                        Toast.makeText(requireContext(), it.data?.message, Toast.LENGTH_SHORT).show()}
                }
            })
        }
    }



}