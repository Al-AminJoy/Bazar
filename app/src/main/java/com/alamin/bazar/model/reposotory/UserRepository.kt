package com.alamin.bazar.model.reposotory

import com.alamin.bazar.model.local.LocalDatabase
import com.alamin.bazar.model.network.APIInterface
import javax.inject.Inject

class UserRepository @Inject constructor(private val apiInterface: APIInterface, private val localDatabase: LocalDatabase) {

}