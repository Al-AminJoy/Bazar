package com.alamin.bazar.view.adapter

import com.alamin.bazar.model.data.Invoice

interface OrderClickListener {

    fun onOrderClick(invoice: Invoice)

}