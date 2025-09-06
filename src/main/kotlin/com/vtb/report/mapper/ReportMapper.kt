package com.vtb.report.mapper

import com.vtb.report.dto.MLReportDTO
import com.vtb.report.model.CandidateReport
import com.vtb.report.model.CandidateRecommendation
import org.springframework.stereotype.Component

@Component
class ReportMapper {

    fun toCandidateReport(mlReportDTO: MLReportDTO): CandidateReport {
        return CandidateReport(
            candidateId = mlReportDTO.candidateId,
            jobId = mlReportDTO.jobId,
            interviewId = mlReportDTO.interviewId,
            overallScore = mlReportDTO.overallScore,
            overallMatchScore = mlReportDTO.overallMatchScore,
            technicalSkillsScore = mlReportDTO.technicalSkillsScore,
            confirmedSkills = mlReportDTO.confirmedSkills,
            missingSkills = mlReportDTO.missingSkills,
            technicalDetails = mlReportDTO.technicalDetails,
            communicationScore = mlReportDTO.communicationScore,
            communicationDetails = mlReportDTO.communicationDetails,
            experienceScore = mlReportDTO.experienceScore,
            relevantProjects = mlReportDTO.relevantProjects,
            experienceDetails = mlReportDTO.experienceDetails,
            totalQuestions = mlReportDTO.totalQuestions,
            questionsAnswered = mlReportDTO.questionsAnswered,
            problemSolvingScore = mlReportDTO.problemSolvingScore,
            teamworkScore = mlReportDTO.teamworkScore,
            leadershipScore = mlReportDTO.leadershipScore,
            adaptabilityScore = mlReportDTO.adaptabilityScore,
            redFlags = mlReportDTO.redFlags,
            strengths = mlReportDTO.strengths,
            gaps = mlReportDTO.gaps,
            recommendationDecision = mlReportDTO.recommendationDecision,
            recommendationConfidence = mlReportDTO.recommendationConfidence,
            recommendationReasoning = mlReportDTO.recommendationReasoning
        )
    }

    fun toCandidateRecommendation(mlReportDTO: MLReportDTO): CandidateRecommendation {
        return CandidateRecommendation(
            candidateId = mlReportDTO.candidateId,
            jobId = mlReportDTO.jobId,
            interviewId = mlReportDTO.interviewId,
            recommendationDecision = mlReportDTO.recommendationDecision,
            recommendationConfidence = mlReportDTO.recommendationConfidence,
            recommendationReasoning = mlReportDTO.recommendationReasoning
        )
    }
}
