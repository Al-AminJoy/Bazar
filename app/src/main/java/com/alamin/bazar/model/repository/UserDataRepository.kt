package com.alamin.bazar.model.repository

import com.alamin.bazar.model.local.LocalDatabase
import com.alamin.bazar.model.network.APIInterface
import javax.inject.Inject

class UserDataRepository @Inject constructor(apiInterface: APIInterface, localDatabase: LocalDatabase) {
}