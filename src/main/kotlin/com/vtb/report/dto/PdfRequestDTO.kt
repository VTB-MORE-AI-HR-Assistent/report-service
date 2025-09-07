package com.vtb.report.dto

data class PdfRequestDTO(
    val candidateId: Int,
    val reportType: String = "FULL", // FULL, SUMMARY, TECHNICAL
    val includeRecommendations: Boolean = true,
    val includeCharts: Boolean = false,
    val language: String = "ru" // ru, en
)

data class PdfResponseDTO(
    val success: Boolean,
    val message: String,
    val pdfSize: Long? = null,
    val fileName: String? = null,
    val generatedAt: String? = null
)
