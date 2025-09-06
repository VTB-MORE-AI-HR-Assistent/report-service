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
        return convertHtmlToPdf(html)
    }

    private fun generateReportHtml(report: CandidateReport): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val createdDate = report.createdAt.format(formatter)

        return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <title>Candidate Report - ${report.candidateId}</title>
            <style>
                body {
                    font-family: 'Arial', sans-serif;
                    margin: 0;
                    padding: 20px;
                    background-color: #f5f5f5;
                    color: #333;
                }
                .container {
                    max-width: 800px;
                    margin: 0 auto;
                    background: white;
                    padding: 30px;
                    border-radius: 8px;
                    box-shadow: 0 2px 10px rgba(0,0,0,0.1);
                }
                .header {
                    text-align: center;
                    border-bottom: 3px solid #2c5aa0;
                    padding-bottom: 20px;
                    margin-bottom: 30px;
                }
                .header h1 {
                    color: #2c5aa0;
                    margin: 0;
                    font-size: 28px;
                }
                .header h2 {
                    color: #666;
                    margin: 10px 0 0 0;
                    font-size: 18px;
                    font-weight: normal;
                }
                .section {
                    margin-bottom: 25px;
                    padding: 15px;
                    background-color: #f9f9f9;
                    border-radius: 5px;
                    border-left: 4px solid #2c5aa0;
                }
                .section h3 {
                    color: #2c5aa0;
                    margin: 0 0 15px 0;
                    font-size: 18px;
                }
                .score-grid {
                    display: grid;
                    grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
                    gap: 15px;
                    margin: 15px 0;
                }
                .score-item {
                    background: white;
                    padding: 15px;
                    border-radius: 5px;
                    text-align: center;
                    border: 1px solid #ddd;
                }
                .score-value {
                    font-size: 24px;
                    font-weight: bold;
                    color: #2c5aa0;
                }
                .score-label {
                    font-size: 14px;
                    color: #666;
                    margin-top: 5px;
                }
                .skills-section {
                    display: grid;
                    grid-template-columns: 1fr 1fr;
                    gap: 20px;
                }
                .skills-list {
                    background: white;
                    padding: 15px;
                    border-radius: 5px;
                    border: 1px solid #ddd;
                }
                .skills-list h4 {
                    margin: 0 0 10px 0;
                    color: #2c5aa0;
                }
                .skills-list ul {
                    margin: 0;
                    padding-left: 20px;
                }
                .skills-list li {
                    margin-bottom: 5px;
                }
                .recommendation {
                    background: linear-gradient(135deg, #2c5aa0, #4a7bb7);
                    color: white;
                    padding: 20px;
                    border-radius: 8px;
                    text-align: center;
                    margin: 20px 0;
                }
                .recommendation h3 {
                    color: white;
                    margin: 0 0 10px 0;
                }
                .recommendation-decision {
                    font-size: 24px;
                    font-weight: bold;
                    margin: 10px 0;
                }
                .confidence {
                    font-size: 16px;
                    opacity: 0.9;
                }
                .details {
                    background: white;
                    padding: 15px;
                    border-radius: 5px;
                    margin-top: 15px;
                    border: 1px solid #ddd;
                }
                .details h4 {
                    margin: 0 0 10px 0;
                    color: #2c5aa0;
                }
                .footer {
                    text-align: center;
                    margin-top: 30px;
                    padding-top: 20px;
                    border-top: 1px solid #ddd;
                    color: #666;
                    font-size: 12px;
                }
                .badge {
                    display: inline-block;
                    padding: 4px 8px;
                    background-color: #e8f4fd;
                    color: #2c5aa0;
                    border-radius: 4px;
                    font-size: 12px;
                    margin: 2px;
                }
            </style>
        </head>
        <body>
            <div class="container">
                <div class="header">
                    <h1>Candidate Evaluation Report</h1>
                    <h2>Candidate ID: ${report.candidateId} | Job ID: ${report.jobId} | Interview ID: ${report.interviewId}</h2>
                    <p>Generated on: $createdDate</p>
                </div>

                <div class="section">
                    <h3>Overall Assessment</h3>
                    <div class="score-grid">
                        <div class="score-item">
                            <div class="score-value">${report.overallScore}</div>
                            <div class="score-label">Overall Score</div>
                        </div>
                        <div class="score-item">
                            <div class="score-value">${report.overallMatchScore}</div>
                            <div class="score-label">Job Match Score</div>
                        </div>
                        <div class="score-item">
                            <div class="score-value">${report.technicalSkillsScore}</div>
                            <div class="score-label">Technical Skills</div>
                        </div>
                        <div class="score-item">
                            <div class="score-value">${report.communicationScore}</div>
                            <div class="score-label">Communication</div>
                        </div>
                        <div class="score-item">
                            <div class="score-value">${report.experienceScore}</div>
                            <div class="score-label">Experience</div>
                        </div>
                        <div class="score-item">
                            <div class="score-value">${report.problemSolvingScore}</div>
                            <div class="score-label">Problem Solving</div>
                        </div>
                    </div>
                </div>

                <div class="section">
                    <h3>Skills Assessment</h3>
                    <div class="skills-section">
                        <div class="skills-list">
                            <h4>Confirmed Skills</h4>
                            <ul>
                                ${report.confirmedSkills.joinToString("") { "<li>$it</li>" }}
                            </ul>
                        </div>
                        <div class="skills-list">
                            <h4>Missing Skills</h4>
                            <ul>
                                ${report.missingSkills.joinToString("") { "<li>$it</li>" }}
                            </ul>
                        </div>
                    </div>
                    ${if (!report.technicalDetails.isNullOrEmpty()) """
                    <div class="details">
                        <h4>Technical Details</h4>
                        <p>${report.technicalDetails}</p>
                    </div>
                    """ else ""}
                </div>

                <div class="section">
                    <h3>Soft Skills & Experience</h3>
                    <div class="score-grid">
                        <div class="score-item">
                            <div class="score-value">${report.teamworkScore}</div>
                            <div class="score-label">Teamwork</div>
                        </div>
                        <div class="score-item">
                            <div class="score-value">${report.leadershipScore}</div>
                            <div class="score-label">Leadership</div>
                        </div>
                        <div class="score-item">
                            <div class="score-value">${report.adaptabilityScore}</div>
                            <div class="score-label">Adaptability</div>
                        </div>
                    </div>
                    ${if (report.relevantProjects.isNotEmpty()) """
                    <div class="details">
                        <h4>Relevant Projects</h4>
                        <ul>
                            ${report.relevantProjects.joinToString("") { "<li>$it</li>" }}
                        </ul>
                    </div>
                    """ else ""}
                    ${if (!report.experienceDetails.isNullOrEmpty()) """
                    <div class="details">
                        <h4>Experience Details</h4>
                        <p>${report.experienceDetails}</p>
                    </div>
                    """ else ""}
                </div>

                <div class="section">
                    <h3>Interview Performance</h3>
                    <div class="score-grid">
                        <div class="score-item">
                            <div class="score-value">${report.questionsAnswered}/${report.totalQuestions}</div>
                            <div class="score-label">Questions Answered</div>
                        </div>
                    </div>
                    ${if (!report.communicationDetails.isNullOrEmpty()) """
                    <div class="details">
                        <h4>Communication Assessment</h4>
                        <p>${report.communicationDetails}</p>
                    </div>
                    """ else ""}
                </div>

                ${if (report.strengths.isNotEmpty()) """
                <div class="section">
                    <h3>Strengths</h3>
                    <div>
                        ${report.strengths.joinToString("") { "<span class='badge'>$it</span>" }}
                    </div>
                </div>
                """ else ""}

                ${if (report.gaps.isNotEmpty()) """
                <div class="section">
                    <h3>Areas for Improvement</h3>
                    <div>
                        ${report.gaps.joinToString("") { "<span class='badge'>$it</span>" }}
                    </div>
                </div>
                """ else ""}

                ${if (report.redFlags.isNotEmpty()) """
                <div class="section">
                    <h3>Red Flags</h3>
                    <div>
                        ${report.redFlags.joinToString("") { "<span class='badge' style='background-color: #ffe6e6; color: #d32f2f;'>$it</span>" }}
                    </div>
                </div>
                """ else ""}

                <div class="recommendation">
                    <h3>Hiring Recommendation</h3>
                    <div class="recommendation-decision">${report.recommendationDecision}</div>
                    <div class="confidence">Confidence: ${(report.recommendationConfidence * 100).toInt()}%</div>
                    ${if (report.recommendationReasoning?.isNotEmpty() == true) """
                    <div class="details" style="background: rgba(255,255,255,0.1); border: none; margin-top: 15px;">
                        <h4 style="color: white;">Reasoning</h4>
                        <p style="color: white; margin: 0;">${report.recommendationReasoning}</p>
                    </div>
                    """ else ""}
                </div>

                <div class="footer">
                    <p>This report was generated automatically by the VTB AI HR System</p>
                    <p>Report ID: ${report.id} | Generated on: $createdDate</p>
                </div>
            </div>
        </body>
        </html>
        """.trimIndent()
    }

    private fun convertHtmlToPdf(html: String): ByteArray {
        val outputStream = ByteArrayOutputStream()
        HtmlConverter.convertToPdf(html, outputStream)
        return outputStream.toByteArray()
    }
}
