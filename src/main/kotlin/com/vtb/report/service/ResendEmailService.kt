package com.vtb.report.service

import com.vtb.report.dto.EmailRequestDTO
import com.vtb.report.model.CandidateRecommendation
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate
import java.time.format.DateTimeFormatter

@Service
class ResendEmailService(
    @Value("\${resend.api-key:}") private val apiKey: String,
    @Value("\${resend.from-email:noreply@hraiassistant.ru}") private val fromEmail: String
) {

    private val restTemplate = RestTemplate()

    fun sendRecommendationEmail(emailRequest: EmailRequestDTO, recommendation: CandidateRecommendation) {
        val emailData = mapOf(
            "from" to fromEmail,
            "to" to listOf(emailRequest.recipientEmail),
            "subject" to "Candidate Recommendation - VTB AI HR System",
            "html" to generateRecommendationEmailHtml(recommendation, emailRequest.recipientName)
        )

        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.set("Authorization", "Bearer $apiKey")

        val entity = HttpEntity(emailData, headers)

        try {
            restTemplate.postForObject(
                "https://api.resend.com/emails",
                entity,
                Map::class.java
            )
        } catch (e: Exception) {
            throw RuntimeException("Failed to send email via Resend: ${e.message}", e)
        }
    }

    private fun generateRecommendationEmailHtml(recommendation: CandidateRecommendation, recipientName: String?): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        val createdDate = recommendation.createdAt.format(formatter)
        val confidencePercentage = (recommendation.recommendationConfidence * 100).toInt()

        return """
        <!DOCTYPE html>
        <html>
        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Candidate Recommendation</title>
            <style>
                body {
                    font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    margin: 0;
                    padding: 0;
                    background-color: #f4f6f8;
                    color: #333;
                }
                .email-container {
                    max-width: 600px;
                    margin: 0 auto;
                    background: white;
                    border-radius: 8px;
                    overflow: hidden;
                    box-shadow: 0 4px 12px rgba(0,0,0,0.1);
                }
                .header {
                    background: linear-gradient(135deg, #2c5aa0, #4a7bb7);
                    color: white;
                    padding: 30px;
                    text-align: center;
                }
                .header h1 {
                    margin: 0;
                    font-size: 28px;
                    font-weight: 300;
                }
                .header p {
                    margin: 10px 0 0 0;
                    opacity: 0.9;
                    font-size: 16px;
                }
                .content {
                    padding: 40px 30px;
                }
                .greeting {
                    font-size: 18px;
                    margin-bottom: 20px;
                    color: #2c5aa0;
                }
                .recommendation-card {
                    background: linear-gradient(135deg, #f8f9fa, #e9ecef);
                    border-radius: 12px;
                    padding: 30px;
                    margin: 25px 0;
                    border-left: 5px solid #2c5aa0;
                    text-align: center;
                }
                .decision {
                    font-size: 32px;
                    font-weight: bold;
                    color: #2c5aa0;
                    margin: 15px 0;
                    text-transform: uppercase;
                    letter-spacing: 1px;
                }
                .confidence {
                    font-size: 18px;
                    color: #666;
                    margin: 10px 0;
                }
                .confidence-bar {
                    width: 100%;
                    height: 8px;
                    background-color: #e0e0e0;
                    border-radius: 4px;
                    margin: 15px 0;
                    overflow: hidden;
                }
                .confidence-fill {
                    height: 100%;
                    background: linear-gradient(90deg, #2c5aa0, #4a7bb7);
                    width: ${confidencePercentage}%;
                    border-radius: 4px;
                    transition: width 0.3s ease;
                }
                .details {
                    background: white;
                    border-radius: 8px;
                    padding: 25px;
                    margin: 25px 0;
                    border: 1px solid #e0e0e0;
                }
                .details h3 {
                    color: #2c5aa0;
                    margin: 0 0 15px 0;
                    font-size: 20px;
                }
                .info-grid {
                    display: grid;
                    grid-template-columns: 1fr 1fr;
                    gap: 20px;
                    margin: 20px 0;
                }
                .info-item {
                    text-align: center;
                    padding: 15px;
                    background: #f8f9fa;
                    border-radius: 6px;
                }
                .info-label {
                    font-size: 14px;
                    color: #666;
                    margin-bottom: 5px;
                }
                .info-value {
                    font-size: 18px;
                    font-weight: bold;
                    color: #2c5aa0;
                }
                .reasoning {
                    background: #f8f9fa;
                    border-radius: 8px;
                    padding: 20px;
                    margin: 20px 0;
                    border-left: 4px solid #2c5aa0;
                }
                .reasoning h4 {
                    color: #2c5aa0;
                    margin: 0 0 10px 0;
                    font-size: 16px;
                }
                .reasoning p {
                    margin: 0;
                    line-height: 1.6;
                    color: #555;
                }
                .footer {
                    background: #f8f9fa;
                    padding: 30px;
                    text-align: center;
                    border-top: 1px solid #e0e0e0;
                }
                .footer p {
                    margin: 5px 0;
                    color: #666;
                    font-size: 14px;
                }
                .logo {
                    font-size: 24px;
                    font-weight: bold;
                    margin-bottom: 10px;
                }
                .cta-button {
                    display: inline-block;
                    background: linear-gradient(135deg, #2c5aa0, #4a7bb7);
                    color: white;
                    padding: 12px 30px;
                    text-decoration: none;
                    border-radius: 6px;
                    font-weight: bold;
                    margin: 20px 0;
                }
                @media (max-width: 600px) {
                    .info-grid {
                        grid-template-columns: 1fr;
                    }
                    .content {
                        padding: 20px;
                    }
                }
            </style>
        </head>
        <body>
            <div class="email-container">
                <div class="header">
                    <div class="logo">VTB AI HR</div>
                    <h1>Candidate Recommendation</h1>
                    <p>AI-Powered Hiring Decision</p>
                </div>
                
                <div class="content">
                    <div class="greeting">
                        ${if (recipientName != null) "Dear $recipientName," else "Dear Hiring Manager,"}
                    </div>
                    
                    <p>We are pleased to present the AI-generated recommendation for the candidate evaluation process. Our advanced machine learning system has analyzed the candidate's performance and provided the following assessment:</p>
                    
                    <div class="recommendation-card">
                        <h2>Hiring Decision</h2>
                        <div class="decision">${recommendation.recommendationDecision}</div>
                        <div class="confidence">Confidence Level: ${confidencePercentage}%</div>
                        <div class="confidence-bar">
                            <div class="confidence-fill"></div>
                        </div>
                    </div>
                    
                    <div class="info-grid">
                        <div class="info-item">
                            <div class="info-label">Candidate ID</div>
                            <div class="info-value">${recommendation.candidateId}</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Job ID</div>
                            <div class="info-value">${recommendation.jobId}</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Interview ID</div>
                            <div class="info-value">${recommendation.interviewId}</div>
                        </div>
                        <div class="info-item">
                            <div class="info-label">Generated Date</div>
                            <div class="info-value">$createdDate</div>
                        </div>
                    </div>
                    
                    ${if (!recommendation.recommendationReasoning.isNullOrEmpty()) """
                    <div class="reasoning">
                        <h4>AI Analysis & Reasoning</h4>
                        <p>${recommendation.recommendationReasoning}</p>
                    </div>
                    """ else ""}
                    
                    <div class="details">
                        <h3>Next Steps</h3>
                        <p>Based on this recommendation, please proceed with the appropriate next steps in your hiring process. This recommendation was generated using advanced machine learning algorithms that analyze multiple factors including technical skills, communication abilities, experience, and cultural fit.</p>
                        
                        <p>For any questions regarding this recommendation or to access the full candidate report, please contact the HR team or access the VTB AI HR system.</p>
                    </div>
                    
                    <div style="text-align: center;">
                        <a href="#" class="cta-button">View Full Report</a>
                    </div>
                </div>
                
                <div class="footer">
                    <p><strong>VTB AI HR System</strong></p>
                    <p>Powered by Advanced Machine Learning</p>
                    <p>Recommendation ID: ${recommendation.id}</p>
                    <p>This is an automated message. Please do not reply to this email.</p>
                </div>
            </div>
        </body>
        </html>
        """.trimIndent()
    }
}
