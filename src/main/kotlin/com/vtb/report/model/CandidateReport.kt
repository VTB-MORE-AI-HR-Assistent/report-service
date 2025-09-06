package com.vtb.report.model

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "reports")
data class CandidateReport(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(name = "candidate_id", nullable = false)
    val candidateId: Int,

    @Column(name = "job_id", nullable = false)
    val jobId: Int,

    @Column(name = "interview_id", nullable = false)
    val interviewId: Int,

    @Column(name = "overall_score", nullable = false)
    val overallScore: Int,

    @Column(name = "overall_match_score", nullable = false)
    val overallMatchScore: Int,

    @Column(name = "technical_skills_score", nullable = false)
    val technicalSkillsScore: Int,

    @ElementCollection
    @CollectionTable(name = "report_confirmed_skills", joinColumns = [JoinColumn(name = "report_id")])
    @Column(name = "skill")
    val confirmedSkills: List<String> = emptyList(),

    @ElementCollection
    @CollectionTable(name = "report_missing_skills", joinColumns = [JoinColumn(name = "report_id")])
    @Column(name = "skill")
    val missingSkills: List<String> = emptyList(),

    @Column(name = "technical_details", columnDefinition = "TEXT")
    val technicalDetails: String? = null,

    @Column(name = "communication_score", nullable = false)
    val communicationScore: Int,

    @Column(name = "communication_details", columnDefinition = "TEXT")
    val communicationDetails: String? = null,

    @Column(name = "experience_score", nullable = false)
    val experienceScore: Int,

    @ElementCollection
    @CollectionTable(name = "report_relevant_projects", joinColumns = [JoinColumn(name = "report_id")])
    @Column(name = "project")
    val relevantProjects: List<String> = emptyList(),

    @Column(name = "experience_details", columnDefinition = "TEXT")
    val experienceDetails: String? = null,

    @Column(name = "total_questions", nullable = false)
    val totalQuestions: Int,

    @Column(name = "questions_answered", nullable = false)
    val questionsAnswered: Int,

    @Column(name = "problem_solving_score", nullable = false)
    val problemSolvingScore: Int,

    @Column(name = "teamwork_score", nullable = false)
    val teamworkScore: Int,

    @Column(name = "leadership_score", nullable = false)
    val leadershipScore: Int,

    @Column(name = "adaptability_score", nullable = false)
    val adaptabilityScore: Int,

    @ElementCollection
    @CollectionTable(name = "report_red_flags", joinColumns = [JoinColumn(name = "report_id")])
    @Column(name = "red_flag")
    val redFlags: List<String> = emptyList(),

    @ElementCollection
    @CollectionTable(name = "report_strengths", joinColumns = [JoinColumn(name = "report_id")])
    @Column(name = "strength")
    val strengths: List<String> = emptyList(),

    @ElementCollection
    @CollectionTable(name = "report_gaps", joinColumns = [JoinColumn(name = "report_id")])
    @Column(name = "gap")
    val gaps: List<String> = emptyList(),

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