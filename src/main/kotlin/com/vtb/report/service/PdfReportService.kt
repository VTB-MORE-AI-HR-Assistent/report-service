package com.vtb.report.service

import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Paragraph
import com.vtb.report.model.CandidateReport
import org.springframework.stereotype.Service
import java.io.ByteArrayOutputStream

@Service
class PdfReportService {

    fun generateCandidateReportPdf(report: CandidateReport): ByteArray {
        return try {
            println("Creating PDF for candidate ${report.candidateId}, job ${report.jobId}")
            val outputStream = ByteArrayOutputStream()
            val pdfWriter = PdfWriter(outputStream)
            val pdfDocument = PdfDocument(pdfWriter)
            val document = Document(pdfDocument)
            
            println("PDF document created, adding content...")
            
            // Add simple content
            document.add(Paragraph("Candidate Report"))
            document.add(Paragraph("Candidate ID: ${report.candidateId}"))
            document.add(Paragraph("Job ID: ${report.jobId}"))
            document.add(Paragraph("Overall Score: ${report.overallScore}"))
            document.add(Paragraph("Generated at: ${java.time.LocalDateTime.now()}"))
            
            println("Content added, closing document...")
            document.close()
            pdfDocument.close()
            
            val pdfBytes = outputStream.toByteArray()
            println("PDF generated successfully, size: ${pdfBytes.size} bytes")
            println("PDF starts with: ${pdfBytes.take(10).joinToString(", ")}")
            
            // Verify PDF header
            if (pdfBytes.size > 4) {
                val header = String(pdfBytes.take(4).toByteArray())
                println("PDF header: '$header' (should start with '%PDF')")
            }
            
            pdfBytes
        } catch (e: Exception) {
            println("Error creating PDF: ${e.message}")
            e.printStackTrace()
            throw RuntimeException("Failed to create PDF: ${e.message}", e)
        }
    }
}