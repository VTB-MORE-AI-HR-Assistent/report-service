package com.vtb.report.dto

data class EmailRequestDTO(
    val candidateId: Int,
    val jobId: Int,
    val recipientEmail: String,
    val recipientName: String? = null
)
