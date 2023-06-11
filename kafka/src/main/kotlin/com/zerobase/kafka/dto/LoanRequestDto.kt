package com.zerobase.kafka.dto

// 심사 보낼 DTO 생성
data class LoanRequestDto(
    val userKey: String,
    val userName: String,
    val userIncomeAmount: Long,
    var userRegistrationNumber: String
)
