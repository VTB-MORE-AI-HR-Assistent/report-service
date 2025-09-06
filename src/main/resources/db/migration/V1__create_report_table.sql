-- Create reports table
CREATE TABLE reports (
    id BIGSERIAL PRIMARY KEY,
    candidate_id INTEGER NOT NULL,
    job_id INTEGER NOT NULL,
    interview_id INTEGER NOT NULL,
    overall_score INTEGER NOT NULL,
    overall_match_score INTEGER NOT NULL,
    technical_skills_score INTEGER NOT NULL,
    confirmed_skills TEXT[],
    missing_skills TEXT[],
    technical_details TEXT,
    communication_score INTEGER NOT NULL,
    communication_details TEXT,
    experience_score INTEGER NOT NULL,
    relevant_projects TEXT[],
    experience_details TEXT,
    total_questions INTEGER NOT NULL,
    questions_answered INTEGER NOT NULL,
    problem_solving_score INTEGER NOT NULL,
    teamwork_score INTEGER NOT NULL,
    leadership_score INTEGER NOT NULL,
    adaptability_score INTEGER NOT NULL,
    red_flags TEXT[],
    strengths TEXT[],
    gaps TEXT[],
    recommendation_decision VARCHAR(255) NOT NULL,
    recommendation_confidence DOUBLE PRECISION NOT NULL,
    recommendation_reasoning TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create recommendations table
CREATE TABLE recommendations (
    id BIGSERIAL PRIMARY KEY,
    candidate_id INTEGER NOT NULL,
    job_id INTEGER NOT NULL,
    interview_id INTEGER NOT NULL,
    recommendation_decision VARCHAR(255) NOT NULL,
    recommendation_confidence DOUBLE PRECISION NOT NULL,
    recommendation_reasoning TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better query performance
CREATE INDEX idx_reports_candidate_job ON reports(candidate_id, job_id);
CREATE INDEX idx_recommendations_candidate_job ON recommendations(candidate_id, job_id);
