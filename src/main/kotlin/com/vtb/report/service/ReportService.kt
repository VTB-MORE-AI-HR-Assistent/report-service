package com.vtb.report.service

import com.vtb.report.dto.MLReportDTO
import com.vtb.report.model.CandidateReport
import com.vtb.report.model.CandidateRecommendation
import com.vtb.report.repository.CandidateReportRepository
import com.vtb.report.repository.CandidateRecommendationRepository
import com.vtb.report.mapper.ReportMapper
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional
class ReportService(
    private val candidateReportRepository: CandidateReportRepository,
    private val candidateRecommendationRepository: CandidateRecommendationRepository,
    private val reportMapper: ReportMapper
) {

    fun saveMLReport(mlReportDTO: MLReportDTO): Pair<CandidateReport, CandidateRecommendation> {
        // Convert MLReportDTO to entities using mapper
        val candidateReport = reportMapper.toCandidateReport(mlReportDTO)
        val candidateRecommendation = reportMapper.toCandidateRecommendation(mlReportDTO)

        // Save both entities
        val savedReport = candidateReportRepository.save(candidateReport)
        val savedRecommendation = candidateRecommendationRepository.save(candidateRecommendation)

        return Pair(savedReport, savedRecommendation)
    }

    @Transactional(readOnly = true)
    fun getCandidateReport(candidateId: Int, jobId: Int): CandidateReport? {
        return candidateReportRepository.findByCandidateIdAndJobId(candidateId, jobId)
    }

    @Transactional(readOnly = true)
    fun getCandidateRecommendation(candidateId: Int, jobId: Int): CandidateRecommendation? {
        return candidateRecommendationRepository.findByCandidateIdAndJobId(candidateId, jobId)
    }
}
