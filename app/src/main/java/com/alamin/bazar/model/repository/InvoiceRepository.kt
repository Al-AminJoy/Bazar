package com.alamin.bazar.model.repository

import com.alamin.bazar.model.data.Invoice
import com.alamin.bazar.model.local.LocalDatabase
import com.alamin.bazar.model.network.APIInterface
import javax.inject.Inject

class InvoiceRepository @Inject constructor(private val apiInterface: APIInterface, private val localDatabase: LocalDatabase) {

    private val invoiceDao = localDatabase.invoiceDao()

    val invoiceList = invoiceDao.getAllInvoice()

    suspend fun insertInvoice(invoice: Invoice){
        invoiceDao.insertInvoice(invoice)
    }

    suspend fun updateInvoice(invoice: Invoice){
        invoiceDao.updateInvoice(invoice)
    }
}