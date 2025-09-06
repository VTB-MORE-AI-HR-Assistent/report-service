package com.vtb.report.controller

import com.vtb.report.dto.MLReportDTO
import com.vtb.report.dto.EmailRequestDTO
import com.vtb.report.model.CandidateReport
import com.vtb.report.model.CandidateRecommendation
import com.vtb.report.service.ReportService
import com.vtb.report.service.PdfReportService
import com.vtb.report.service.ResendEmailService
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/reports")
class ReportController(
    private val reportService: ReportService,
    private val pdfReportService: PdfReportService,
    private val resendEmailService: ResendEmailService
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

    @GetMapping("/pdf")
    fun downloadCandidateReportPdf(
        @RequestParam candidateId: Int,
        @RequestParam jobId: Int
    ): ResponseEntity<ByteArray> {
        return try {
            val report = reportService.getCandidateReport(candidateId, jobId)
                ?: return ResponseEntity.notFound().build()

            val pdfBytes = pdfReportService.generateCandidateReportPdf(report)

            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_PDF
            headers.setContentDispositionFormData("attachment", "candidate_report_${candidateId}_${jobId}.pdf")
            headers.cacheControl = "no-cache, no-store, must-revalidate"
            headers.setPragma("no-cache")
            headers.setExpires(0)

            ResponseEntity.ok()
                .headers(headers)
                .body(pdfBytes)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Failed to generate PDF: ${e.message}".toByteArray())
        }
    }

    @PostMapping("/email")
    fun sendRecommendationEmail(@RequestBody emailRequest: EmailRequestDTO): ResponseEntity<Map<String, Any>> {
        return try {
            val recommendation = reportService.getCandidateRecommendation(
                emailRequest.candidateId, 
                emailRequest.jobId
            ) ?: return ResponseEntity.notFound().build()

            resendEmailService.sendRecommendationEmail(emailRequest, recommendation)

            val response = mapOf(
                "message" to "Recommendation email sent successfully",
                "recipientEmail" to emailRequest.recipientEmail,
                "candidateId" to emailRequest.candidateId,
                "jobId" to emailRequest.jobId
            )
            ResponseEntity.ok(response)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("error" to "Failed to send email: ${e.message}"))
        }
    }
}
