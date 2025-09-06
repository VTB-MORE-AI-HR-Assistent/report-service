package com.vtb.report.service

import com.itextpdf.html2pdf.HtmlConverter
import com.vtb.report.model.CandidateReport
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.time.format.DateTimeFormatter

@Service
class PdfReportService {

    fun generateCandidateReportPdf(report: CandidateReport): ByteArray {
        val html = generateReportHtml(report)
        println("Generated HTML length: ${html.length}")
        return convertHtmlToPdf(html)
    }

    private fun generateReportHtml(report: CandidateReport): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val createdDate = report.createdAt.format(formatter)

        return """
        <html>
        <head>
            <meta charset="UTF-8">
            <title>Candidate Report</title>
        </head>
        <body>
            <h1>Candidate Assessment Report</h1>
            <p>Candidate ID: ${report.candidateId} | Job ID: ${report.jobId} | Generated: $createdDate</p>
            
            <h2>Overall Assessment</h2>
            <p>Overall Score: ${report.overallScore}</p>
            <p>Match Score: ${report.overallMatchScore}</p>
            <p>Technical Skills: ${report.technicalSkillsScore}</p>
            <p>Communication: ${report.communicationScore}</p>
            
            <h2>Detailed Assessment</h2>
            <p><strong>Technical Details:</strong> ${report.technicalDetails ?: "No technical details provided"}</p>
            <p><strong>Communication Details:</strong> ${report.communicationDetails ?: "No communication details provided"}</p>
            <p><strong>Experience Details:</strong> ${report.experienceDetails ?: "No experience details provided"}</p>
            
            <h2>Interview Performance</h2>
            <p>Questions Answered: ${report.questionsAnswered}/${report.totalQuestions}</p>
            <p>Teamwork Score: ${report.teamworkScore}</p>
            <p>Leadership Score: ${report.leadershipScore}</p>
            <p>Adaptability Score: ${report.adaptabilityScore}</p>
            
            <h2>Recommendation</h2>
            <p><strong>Decision:</strong> ${report.recommendationDecision}</p>
            <p><strong>Confidence:</strong> ${String.format("%.1f", report.recommendationConfidence * 100)}%</p>
            <p><strong>Reasoning:</strong> ${report.recommendationReasoning ?: "No additional reasoning provided"}</p>
        </body>
        </html>
        """.trimIndent()
    }

    private fun convertHtmlToPdf(html: String): ByteArray {
        return try {
            val outputStream = ByteArrayOutputStream()
            HtmlConverter.convertToPdf(html, outputStream)
            val pdfBytes = outputStream.toByteArray()
            println("Generated PDF size: ${pdfBytes.size} bytes")
            pdfBytes
        } catch (e: Exception) {
            println("Error converting HTML to PDF: ${e.message}")
            e.printStackTrace()
            throw RuntimeException("Failed to convert HTML to PDF: ${e.message}", e)
        }
    }
}