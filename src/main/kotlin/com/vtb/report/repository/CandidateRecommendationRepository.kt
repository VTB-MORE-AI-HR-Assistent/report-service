package com.vtb.report.repository

import com.vtb.report.model.CandidateRecommendation
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CandidateRecommendationRepository : JpaRepository<CandidateRecommendation, Long> {
    fun findByCandidateId(candidateId: Int): List<CandidateRecommendation>
}