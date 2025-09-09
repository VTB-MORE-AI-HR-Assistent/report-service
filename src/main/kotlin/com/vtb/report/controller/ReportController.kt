package com.vtb.report.controller

import com.vtb.report.dto.MLReportDTO
import com.vtb.report.model.CandidateReport
import com.vtb.report.model.CandidateRecommendation
import com.vtb.report.service.PdfService
import com.vtb.report.service.ReportService
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.Resource
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/reports")
class ReportController(
    private val reportService: ReportService,
    private val pdfService: PdfService
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
        @RequestParam candidateId: Int
    ): ResponseEntity<CandidateReport> {
        val report = reportService.getCandidateReport(candidateId)
                ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(report)
    }

    @GetMapping("/recommendations")
    fun getCandidateRecommendation(
        @RequestParam candidateId: Int
    ): ResponseEntity<CandidateRecommendation> {
        val recommendation = reportService.getCandidateRecommendation(candidateId)
                ?: return ResponseEntity.notFound().build()

        return ResponseEntity.ok(recommendation)
    }

    @GetMapping("/pdf/test")
    fun downloadTestPdf(): ResponseEntity<Resource> {
        return try {
            println("Starting PDF generation...")
            val pdfBytes = pdfService.generateCandidateReportPdf(
                CandidateReport(
                    candidateId = 999,
                    jobId = 999,
                    interviewId = 999,
                    overallScore = 85,
                    overallMatchScore = 90,
                    technicalSkillsScore = 80,
                    communicationScore = 85,
                    experienceScore = 75,
                    totalQuestions = 10,
                    questionsAnswered = 8,
                    problemSolvingScore = 85,
                    teamworkScore = 80,
                    leadershipScore = 75,
                    adaptabilityScore = 90,
                    recommendationDecision = "HIRE",
                    recommendationConfidence = 0.85
                )
            )
            
            println("PDF generated successfully, size: ${pdfBytes.size} bytes")
            println("First 10 bytes: ${pdfBytes.take(10).joinToString(", ")}")

            val resource = ByteArrayResource(pdfBytes)
            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_PDF
            headers.add("Content-Disposition", "attachment; filename=\"test_report.pdf\"")
            headers.cacheControl = "no-cache, no-store, must-revalidate"
            headers.setPragma("no-cache")
            headers.setExpires(0)
            headers.contentLength = pdfBytes.size.toLong()

            println("Returning PDF resource with headers: $headers")
            ResponseEntity.ok()
                .headers(headers)
                .body(resource)
        } catch (e: Exception) {
            println("Error generating PDF: ${e.message}")
            e.printStackTrace()
            val errorBytes = "Failed to generate PDF: ${e.message}".toByteArray(Charsets.UTF_8)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ByteArrayResource(errorBytes))
        }
    }

    @GetMapping("/pdf")
    fun downloadCandidateReportPdf(
        @RequestParam candidateId: Int
    ): ResponseEntity<ByteArrayResource> {
        return try {
            val report = reportService.getCandidateReport(candidateId)
                ?: return ResponseEntity.notFound().build()

            val pdfBytes = pdfService.generateCandidateReportPdf(report)
            val resource = ByteArrayResource(pdfBytes)

            val headers = HttpHeaders()
            headers.contentType = MediaType.APPLICATION_PDF
            headers.setContentDispositionFormData("attachment", "candidate_report_${candidateId}.pdf")
            headers.cacheControl = "no-cache, no-store, must-revalidate"
            headers.setPragma("no-cache")
            headers.setExpires(0)
            headers.contentLength = pdfBytes.size.toLong()

            ResponseEntity.ok()
                .headers(headers)
                .body(resource)
        } catch (e: Exception) {
            val errorBytes = "Failed to generate PDF: ${e.message}".toByteArray(Charsets.UTF_8)
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ByteArrayResource(errorBytes))
        }
    }

    @GetMapping("/pdf/debug")
    fun debugPdf(): ResponseEntity<Map<String, Any>> {
        return try {
            val pdfBytes = pdfService.generateCandidateReportPdf(
                CandidateReport(
                    candidateId = 999,
                    jobId = 999,
                    interviewId = 999,
                    overallScore = 85,
                    overallMatchScore = 90,
                    technicalSkillsScore = 80,
                    communicationScore = 85,
                    experienceScore = 75,
                    totalQuestions = 10,
                    questionsAnswered = 8,
                    problemSolvingScore = 85,
                    teamworkScore = 80,
                    leadershipScore = 75,
                    adaptabilityScore = 90,
                    recommendationDecision = "HIRE",
                    recommendationConfidence = 0.85
                )
            )
            
            val base64Pdf = java.util.Base64.getEncoder().encodeToString(pdfBytes)
            val firstBytes = pdfBytes.take(20).joinToString(", ")
            val header = String(pdfBytes.take(4).toByteArray())
            
            val debug = mapOf(
                "pdfSize" to pdfBytes.size,
                "first20Bytes" to firstBytes,
                "pdfHeader" to header,
                "base64Length" to base64Pdf.length,
                "base64Start" to base64Pdf.take(50)
            )
            
            ResponseEntity.ok(debug)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(mapOf("error" to (e.message ?: "Unknown error")))
        }
    }

    @GetMapping
    fun getReports(
        @RequestParam(required = false) limit: Int?,
        @RequestParam(required = false) sortBy: String?,
        @RequestParam(required = false) sortOrder: String?
    ): ResponseEntity<List<Map<String, Any>>> {
        // Возвращаем mock данные для reports
        val mockReports = listOf(
            mapOf(
                "id" to 1,
                "candidateId" to 101,
                "candidateName" to "Иван Петров",
                "position" to "Senior Frontend Developer",
                "overallScore" to 85,
                "status" to "COMPLETED",
                "createdAt" to "2025-09-08T10:30:00Z"
            ),
            mapOf(
                "id" to 2,
                "candidateId" to 102,
                "candidateName" to "Анна Сидорова", 
                "position" to "Backend Developer",
                "overallScore" to 78,
                "status" to "COMPLETED",
                "createdAt" to "2025-09-07T15:20:00Z"
            )
        )
        
        val limitedReports = if (limit != null) mockReports.take(limit) else mockReports
        return ResponseEntity.ok(limitedReports)
    }
}

@RestController
@RequestMapping("/api/v1/interviews")
class InterviewController {
    
    @GetMapping("/stats")
    fun getInterviewStats(): ResponseEntity<Map<String, Any>> {
        val stats = mapOf(
            "totalInterviews" to 24,
            "completedInterviews" to 18,
            "scheduledInterviews" to 6,
            "averageScore" to 82.5,
            "passRate" to 0.75,
            "todayInterviews" to 3,
            "weekInterviews" to 12
        )
        return ResponseEntity.ok(stats)
    }
    
    @GetMapping
    fun getInterviews(
        @RequestParam(required = false) dateFrom: String?,
        @RequestParam(required = false) dateTo: String?,
        @RequestParam(required = false) sortBy: String?,
        @RequestParam(required = false) sortOrder: String?
    ): ResponseEntity<List<Map<String, Any>>> {
        // Mock данные для интервью
        val mockInterviews = listOf(
            mapOf(
                "id" to 1,
                "candidateId" to 101,
                "candidateName" to "Александр Иванов",
                "position" to "Senior Frontend Developer",
                "scheduledAt" to "2025-09-09T14:00:00Z",
                "status" to "SCHEDULED",
                "interviewer" to "HR Менеджер"
            ),
            mapOf(
                "id" to 2,
                "candidateId" to 102,
                "candidateName" to "Мария Петрова",
                "position" to "Backend Developer", 
                "scheduledAt" to "2025-09-09T16:30:00Z",
                "status" to "SCHEDULED",
                "interviewer" to "Технический лидер"
            ),
            mapOf(
                "id" to 3,
                "candidateId" to 103,
                "candidateName" to "Дмитрий Смирнов",
                "position" to "DevOps Engineer",
                "scheduledAt" to "2025-09-09T11:00:00Z",
                "status" to "COMPLETED",
                "interviewer" to "Senior Engineer",
                "score" to 88
            )
        )
        
        return ResponseEntity.ok(mockInterviews)
    }
}
