package com.example.hbo_buddy_app.models

import java.util.*

class UserAuth (val studentID: Int, val password: String, val hash: String, val salt: String, val role: String) {
}

class Tokens (val tokenID: Int, val pwID: Int, val token: String, val created_at: Date) {
}