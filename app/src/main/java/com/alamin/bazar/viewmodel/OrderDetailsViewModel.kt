package com.alamin.bazar.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alamin.bazar.model.data.Invoice
import com.alamin.bazar.model.repository.CartRepository
import com.alamin.bazar.model.repository.InvoiceRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderDetailsViewModel @Inject constructor(private val invoiceRepository: InvoiceRepository,
    ): ViewModel() {

    fun updateInvoice(invoice: Invoice){
        viewModelScope.launch {
            withContext(IO){
                invoiceRepository.updateInvoice(invoice)
            }
        }
    }

}