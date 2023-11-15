package com.auxilitos.mis_primeros_auxilitos.model.response

object UserManager {
    private var userId: Int = -1

    fun getUserId(): Int {
        return userId
    }

    fun setUserId(id: Int) {
        userId = id
    }
}
