package com.vtb.report.repository

import com.vtb.report.model.CandidateReport
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CandidateReportRepository : JpaRepository<CandidateReport, Long> {
    fun findByCandidateIdAndJobId(candidateId: Int, jobId: Int): List<CandidateReport>
}