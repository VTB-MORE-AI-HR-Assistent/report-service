package com.vtb.report.controller

import com.vtb.report.dto.MLReportDTO
import com.vtb.report.model.CandidateReport
import com.vtb.report.model.CandidateRecommendation
import com.vtb.report.service.ReportService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reports")
class ReportController(
    private val reportService: ReportService
) {

    @PostMapping
    fun saveMLReport(@RequestBody mlReportDTO: MLReportDTO): ResponseEntity<Map<String, Any>> {
        return try {
            val (savedReport, savedRecommendation) = reportService.saveMLReport(mlReportDTO)
            val response = mapOf(
                "message" to "ML Report saved successfully",
                "reportId" to savedReport.id,
                "recommendationId" to savedRecommendation.id
            )
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("error" to "Failed to save ML Report: ${e.message}"))
        }
    }

    @GetMapping("/candidate")
    fun getCandidateReport(
        @RequestParam candidateId: Int,
        @RequestParam jobId: Int
    ): ResponseEntity<CandidateReport> {
        val report = reportService.getCandidateReport(candidateId, jobId)
                ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(report)
    }

    @GetMapping("/recommendations")
    fun getCandidateRecommendation(
        @RequestParam candidateId: Int,
        @RequestParam jobId: Int
    ): ResponseEntity<CandidateRecommendation> {
        val recommendation = reportService.getCandidateRecommendation(candidateId, jobId)
                ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(recommendation)
    }
}
