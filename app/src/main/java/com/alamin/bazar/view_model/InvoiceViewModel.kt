package com.alamin.bazar.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.data.Invoice
import com.alamin.bazar.model.repository.InvoiceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class InvoiceViewModel @Inject constructor(private val invoiceRepository: InvoiceRepository): ViewModel() {
    val invoiceList = invoiceRepository.invoiceList

    fun insertInvoice(invoice: Invoice){
        viewModelScope.launch (Dispatchers.IO){
            invoiceRepository.insertInvoice(invoice)
        }
    }
}