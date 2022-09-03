package com.alamin.bazar.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alamin.bazar.model.data.Invoice

@Dao
interface InvoiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvoice(invoice: Invoice)

    @Query("SELECT * FROM invoice")
    fun getAllInvoice(): LiveData<List<Invoice>>

    @Update
    suspend fun updateInvoice(invoice: Invoice)
}