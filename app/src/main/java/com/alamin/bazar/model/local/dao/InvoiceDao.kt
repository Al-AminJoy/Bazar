package com.alamin.bazar.model.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.alamin.bazar.model.data.Invoice
import kotlinx.coroutines.flow.Flow

@Dao
interface InvoiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInvoice(invoice: Invoice)

    @Query("SELECT * FROM invoice")
    fun getAllInvoice(): Flow<List<Invoice>>

    @Update
    suspend fun updateInvoice(invoice: Invoice)
}