package com.vtb.report.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "recommendations")
data class CandidateRecommendation(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "candidate_id", nullable = false)
    val candidateId: Int,

    @Column(name = "job_id", nullable = false)
    val jobId: Int,

    @Column(name = "interview_id", nullable = false)
    val interviewId: Int,

    @Column(name = "recommendation_decision", nullable = false)
    val recommendationDecision: String,

    @Column(name = "recommendation_confidence", nullable = false)
    val recommendationConfidence: Double,

    @Column(name = "recommendation_reasoning", columnDefinition = "TEXT")
    val recommendationReasoning: String? = null,

    @Column(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)