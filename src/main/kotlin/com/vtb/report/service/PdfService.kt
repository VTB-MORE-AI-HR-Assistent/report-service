package com.vtb.report.service

import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.UnitValue
import com.vtb.report.dto.PdfRequestDTO
import com.vtb.report.model.CandidateReport
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Service
class PdfService(
    private val reportService: ReportService
)  {

    fun generateCandidateReportPdf(candidateId: Int): ByteArray {
        val report = reportService.getCandidateReport(candidateId)
            ?: throw IllegalArgumentException("Candidate report not found for ID: $candidateId")
        return generateCandidateReportPdf(report)
    }

    fun generateCandidateReportPdf(report: CandidateReport): ByteArray {
        return try {
            println("Creating PDF for candidate ${report.candidateId}, job ${report.jobId}")
            val outputStream = ByteArrayOutputStream()
            val pdfWriter = PdfWriter(outputStream)
            val pdfDocument = PdfDocument(pdfWriter)
            val document = Document(pdfDocument)
            
            println("PDF document created, adding content...")
            
            // Add header
            addHeader(document, report)
            
            // Add candidate information
            addCandidateInfo(document, report)
            
            // Add scores section
            addScoresSection(document, report)
            
            // Add skills section
            addSkillsSection(document, report)
            
            // Add recommendation section
            addRecommendationSection(document, report)
            
            // Add footer
            addFooter(document)
            
            println("Content added, closing document...")
            document.close()
            pdfDocument.close()
            
            val pdfBytes = outputStream.toByteArray()
            println("PDF generated successfully, size: ${pdfBytes.size} bytes")
            
            pdfBytes
        } catch (e: Exception) {
            println("Error creating PDF: ${e.message}")
            e.printStackTrace()
            throw RuntimeException("Failed to create PDF: ${e.message}", e)
        }
    }

    fun generateCandidateReportPdf(request: PdfRequestDTO): ByteArray {
        val report = reportService.getCandidateReport(request.candidateId)
            ?: throw IllegalArgumentException("Candidate report not found for ID: ${request.candidateId}")
        
        return when (request.reportType) {
            "SUMMARY" -> generateSummaryReport(report, request)
            "TECHNICAL" -> generateTechnicalReport(report, request)
            else -> generateCandidateReportPdf(report)
        }
    }

    fun generateTestPdf(): ByteArray {
        val testReport = CandidateReport(
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
        return generateCandidateReportPdf(testReport)
    }

    fun generateSimplePdf(): ByteArray {
        return try {
            val outputStream = ByteArrayOutputStream()
            val pdfWriter = PdfWriter(outputStream)
            val pdfDocument = PdfDocument(pdfWriter)
            val document = Document(pdfDocument)
            
            // Simple content
            document.add(Paragraph("VTB AI HR - Test Report"))
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(16f)
                .setBold()
            
            document.add(Paragraph(""))
            
            document.add(Paragraph("Привет, мир!"))
            document.add(Paragraph("Это красивый PDF отчет"))
            
            document.add(Paragraph(""))
            
            document.add(Paragraph("Generated at: ${LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)}"))
                .setTextAlignment(TextAlignment.CENTER)
                .setFontSize(10f)
                .setItalic()
            
            document.close()
            pdfDocument.close()
            
            outputStream.toByteArray()
        } catch (e: Exception) {
            println("Error creating simple PDF: ${e.message}")
            e.printStackTrace()
            throw RuntimeException("Failed to create simple PDF: ${e.message}", e)
        }
    }

    private fun addHeader(document: Document, report: CandidateReport) {
        val title = Paragraph("VTB AI HR - Отчет о кандидате")
            .setTextAlignment(TextAlignment.CENTER)
            .setFontSize(20f)
            .setBold()
        document.add(title)
        
        document.add(Paragraph(""))
        
        val subtitle = Paragraph("Кандидат ID: ${report.candidateId} | Позиция ID: ${report.jobId} | Интервью ID: ${report.interviewId}")
            .setTextAlignment(TextAlignment.CENTER)
            .setFontSize(12f)
        document.add(subtitle)
        
        document.add(Paragraph(""))
    }

    private fun addCandidateInfo(document: Document, report: CandidateReport) {
        val infoTitle = Paragraph("Информация о кандидате")
            .setFontSize(14f)
            .setBold()
        document.add(infoTitle)
        
        val table = Table(UnitValue.createPercentArray(2)).useAllAvailableWidth()
        
        table.addHeaderCell("Параметр")
        table.addHeaderCell("Значение")
        
        table.addCell("Общий балл")
        table.addCell("${report.overallScore}/100")
        
        table.addCell("Соответствие позиции")
        table.addCell("${report.overallMatchScore}/100")
        
        table.addCell("Всего вопросов")
        table.addCell("${report.totalQuestions}")
        
        table.addCell("Отвечено вопросов")
        table.addCell("${report.questionsAnswered}")
        
        table.addCell("Дата создания")
        table.addCell(report.createdAt.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm")))
        
        document.add(table)
        document.add(Paragraph(""))
    }

    private fun addScoresSection(document: Document, report: CandidateReport) {
        val scoresTitle = Paragraph("Детальная оценка")
            .setFontSize(14f)
            .setBold()
        document.add(scoresTitle)
        
        val scoresTable = Table(UnitValue.createPercentArray(2)).useAllAvailableWidth()
        
        scoresTable.addHeaderCell("Критерий")
        scoresTable.addHeaderCell("Балл")
        
        scoresTable.addCell("Технические навыки")
        scoresTable.addCell("${report.technicalSkillsScore}/100")
        
        scoresTable.addCell("Коммуникация")
        scoresTable.addCell("${report.communicationScore}/100")
        
        scoresTable.addCell("Опыт работы")
        scoresTable.addCell("${report.experienceScore}/100")
        
        scoresTable.addCell("Решение задач")
        scoresTable.addCell("${report.problemSolvingScore}/100")
        
        scoresTable.addCell("Командная работа")
        scoresTable.addCell("${report.teamworkScore}/100")
        
        scoresTable.addCell("Лидерство")
        scoresTable.addCell("${report.leadershipScore}/100")
        
        scoresTable.addCell("Адаптивность")
        scoresTable.addCell("${report.adaptabilityScore}/100")
        
        document.add(scoresTable)
        document.add(Paragraph(""))
    }

    private fun addSkillsSection(document: Document, report: CandidateReport) {
        if (report.confirmedSkills.isNotEmpty() || report.missingSkills.isNotEmpty()) {
            val skillsTitle = Paragraph("Навыки")
                .setFontSize(14f)
                .setBold()
            document.add(skillsTitle)
            
            if (report.confirmedSkills.isNotEmpty()) {
                document.add(Paragraph("Подтвержденные навыки:"))
                report.confirmedSkills.forEach { skill ->
                    document.add(Paragraph("• $skill"))
                }
                document.add(Paragraph(""))
            }
            
            if (report.missingSkills.isNotEmpty()) {
                document.add(Paragraph("Отсутствующие навыки:"))
                report.missingSkills.forEach { skill ->
                    document.add(Paragraph("• $skill"))
                }
                document.add(Paragraph(""))
            }
        }
    }

    private fun addRecommendationSection(document: Document, report: CandidateReport) {
        val recTitle = Paragraph("Рекомендация")
            .setFontSize(14f)
            .setBold()
        document.add(recTitle)
        
        val recTable = Table(UnitValue.createPercentArray(2)).useAllAvailableWidth()
        
        recTable.addCell("Решение")
        recTable.addCell(report.recommendationDecision)
        
        recTable.addCell("Уверенность")
        recTable.addCell("${(report.recommendationConfidence * 100).toInt()}%")
        
        document.add(recTable)
        
        if (!report.recommendationReasoning.isNullOrBlank()) {
            document.add(Paragraph(""))
            document.add(Paragraph("Обоснование:"))
            document.add(Paragraph(report.recommendationReasoning))
        }
        
        document.add(Paragraph(""))
    }

    private fun addFooter(document: Document) {
        val footer = Paragraph("Отчет сгенерирован системой VTB AI HR")
            .setTextAlignment(TextAlignment.CENTER)
            .setFontSize(10f)
            .setItalic()
        document.add(footer)
        
        val timestamp = Paragraph("Дата генерации: ${LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss"))}")
            .setTextAlignment(TextAlignment.CENTER)
            .setFontSize(8f)
        document.add(timestamp)
    }

    private fun generateSummaryReport(report: CandidateReport, request: PdfRequestDTO): ByteArray {
        // Simplified version for summary reports
        return generateCandidateReportPdf(report)
    }

    private fun generateTechnicalReport(report: CandidateReport, request: PdfRequestDTO): ByteArray {
        // Technical-focused version
        return generateCandidateReportPdf(report)
    }
}
