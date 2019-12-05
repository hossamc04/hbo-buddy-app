package com.example.hbo_buddy_app

import java.util.*

class UserAuth (val student: Student, val password: String, val hash: String, val salt: String) {
}

class Tokens (val tokenID: Int, val pwID: Int, val token: String, val created_at: Date) {
}