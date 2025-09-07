-- Mock data for testing Reports API integration
-- Based on CandidateReport.kt entity structure
-- Execute this in PostgreSQL to create test data

-- Insert reports for mock candidates (123, 124, 125, 126)

-- 1. Maria Petrova (ID: 123) - Senior Frontend Developer - HIRE
INSERT INTO reports (
    candidate_id, job_id, interview_id, 
    overall_score, overall_match_score, technical_skills_score,
    technical_details, communication_score, communication_details,
    experience_score, experience_details, total_questions, questions_answered,
    problem_solving_score, teamwork_score, leadership_score, adaptability_score,
    recommendation_decision, recommendation_confidence, recommendation_reasoning,
    created_at, updated_at
) VALUES (
    123, 456, 789,
    87, 92, 85,
    'Strong React and TypeScript skills with excellent system design knowledge. Demonstrates good understanding of modern frontend architecture.',
    90, 'Clear communication during technical discussions. Asks relevant questions and explains concepts well.',
    88, '5+ years of React development experience with focus on enterprise applications.',
    20, 18,
    85, 90, 80, 88,
    'HIRE', 0.87,
    'Strong technical fit with excellent React skills. Minor gaps in GraphQL can be addressed through training.',
    NOW(), NOW()
);

-- 2. Alexander Smirnov (ID: 124) - Backend Developer - MAYBE
INSERT INTO reports (
    candidate_id, job_id, interview_id,
    overall_score, overall_match_score, technical_skills_score,
    technical_details, communication_score, communication_details,
    experience_score, experience_details, total_questions, questions_answered,
    problem_solving_score, teamwork_score, leadership_score, adaptability_score,
    recommendation_decision, recommendation_confidence, recommendation_reasoning,
    created_at, updated_at
) VALUES (
    124, 457, 790,
    72, 78, 70,
    'Good Python and Django knowledge but lacks experience with microservices architecture and containerization.',
    75, 'Adequate communication skills. Sometimes struggles to explain complex concepts clearly.',
    75, '3 years of Python development experience, mostly monolithic applications.',
    15, 12,
    75, 70, 65, 80,
    'MAYBE', 0.72,
    'Solid Python skills but needs deeper knowledge of microservices and cloud technologies for this senior role.',
    NOW(), NOW()
);

-- 3. Elena Kozlova (ID: 125) - Product Manager - NO_HIRE
INSERT INTO reports (
    candidate_id, job_id, interview_id,
    overall_score, overall_match_score, technical_skills_score,
    technical_details, communication_score, communication_details,
    experience_score, experience_details, total_questions, questions_answered,
    problem_solving_score, teamwork_score, leadership_score, adaptability_score,
    recommendation_decision, recommendation_confidence, recommendation_reasoning,
    created_at, updated_at
) VALUES (
    125, 458, 791,
    65, 60, 68,
    'Limited technical knowledge. Struggles with API concepts and lacks understanding of software development lifecycle.',
    70, 'Good communication skills but lacks depth in technical discussions.',
    60, '2 years of product management experience, mostly in non-technical products.',
    12, 8,
    60, 65, 55, 70,
    'NO_HIRE', 0.65,
    'While communication skills are adequate, lacks the technical depth and strategic thinking required for senior product management role.',
    NOW(), NOW()
);

-- 4. Ivan Petrov (ID: 126) - DevOps Engineer - HIRE
INSERT INTO reports (
    candidate_id, job_id, interview_id,
    overall_score, overall_match_score, technical_skills_score,
    technical_details, communication_score, communication_details,
    experience_score, experience_details, total_questions, questions_answered,
    problem_solving_score, teamwork_score, leadership_score, adaptability_score,
    recommendation_decision, recommendation_confidence, recommendation_reasoning,
    created_at, updated_at
) VALUES (
    126, 459, 792,
    94, 96, 95,
    'Exceptional DevOps knowledge with deep expertise in Kubernetes, CI/CD pipelines, and AWS services. Demonstrates advanced understanding of infrastructure automation.',
    92, 'Excellent communication skills. Clearly explains complex infrastructure concepts and provides detailed technical solutions.',
    90, '7+ years of DevOps experience with focus on cloud-native applications and microservices architecture.',
    25, 24,
    95, 90, 85, 95,
    'HIRE', 0.94,
    'Outstanding technical expertise and practical experience. Perfect fit for senior DevOps role with strong leadership potential.',
    NOW(), NOW()
);

-- Insert confirmed skills for each candidate
INSERT INTO report_confirmed_skills (report_id, skill) VALUES
-- Maria Petrova skills
((SELECT id FROM reports WHERE candidate_id = 123), 'React'),
((SELECT id FROM reports WHERE candidate_id = 123), 'TypeScript'),
((SELECT id FROM reports WHERE candidate_id = 123), 'JavaScript'),
((SELECT id FROM reports WHERE candidate_id = 123), 'CSS'),
((SELECT id FROM reports WHERE candidate_id = 123), 'HTML'),

-- Alexander Smirnov skills  
((SELECT id FROM reports WHERE candidate_id = 124), 'Python'),
((SELECT id FROM reports WHERE candidate_id = 124), 'Django'),
((SELECT id FROM reports WHERE candidate_id = 124), 'PostgreSQL'),
((SELECT id FROM reports WHERE candidate_id = 124), 'REST API'),

-- Elena Kozlova skills
((SELECT id FROM reports WHERE candidate_id = 125), 'Analytics'),
((SELECT id FROM reports WHERE candidate_id = 125), 'Jira'),
((SELECT id FROM reports WHERE candidate_id = 125), 'Agile'),

-- Ivan Petrov skills
((SELECT id FROM reports WHERE candidate_id = 126), 'Kubernetes'),
((SELECT id FROM reports WHERE candidate_id = 126), 'Docker'),
((SELECT id FROM reports WHERE candidate_id = 126), 'AWS'),
((SELECT id FROM reports WHERE candidate_id = 126), 'Jenkins'),
((SELECT id FROM reports WHERE candidate_id = 126), 'Terraform'),
((SELECT id FROM reports WHERE candidate_id = 126), 'Monitoring');

-- Insert missing skills
INSERT INTO report_missing_skills (report_id, skill) VALUES
-- Maria Petrova missing
((SELECT id FROM reports WHERE candidate_id = 123), 'GraphQL'),
((SELECT id FROM reports WHERE candidate_id = 123), 'Testing'),

-- Alexander Smirnov missing
((SELECT id FROM reports WHERE candidate_id = 124), 'Docker'),
((SELECT id FROM reports WHERE candidate_id = 124), 'Kubernetes'),
((SELECT id FROM reports WHERE candidate_id = 124), 'Microservices'),

-- Elena Kozlova missing
((SELECT id FROM reports WHERE candidate_id = 125), 'Technical Knowledge'),
((SELECT id FROM reports WHERE candidate_id = 125), 'API Understanding'),

-- Ivan Petrov missing (none)
;

-- Insert strengths
INSERT INTO report_strengths (report_id, strength) VALUES
-- Maria Petrova strengths
((SELECT id FROM reports WHERE candidate_id = 123), 'Technical expertise'),
((SELECT id FROM reports WHERE candidate_id = 123), 'Problem solving'),
((SELECT id FROM reports WHERE candidate_id = 123), 'System design'),

-- Alexander Smirnov strengths
((SELECT id FROM reports WHERE candidate_id = 124), 'Python expertise'),
((SELECT id FROM reports WHERE candidate_id = 124), 'Problem solving'),

-- Elena Kozlova strengths
((SELECT id FROM reports WHERE candidate_id = 125), 'Communication'),
((SELECT id FROM reports WHERE candidate_id = 125), 'Analytics'),

-- Ivan Petrov strengths
((SELECT id FROM reports WHERE candidate_id = 126), 'DevOps expertise'),
((SELECT id FROM reports WHERE candidate_id = 126), 'Infrastructure automation'),
((SELECT id FROM reports WHERE candidate_id = 126), 'Problem solving'),
((SELECT id FROM reports WHERE candidate_id = 126), 'Leadership');

-- Insert gaps
INSERT INTO report_gaps (report_id, gap) VALUES
-- Maria Petrova gaps
((SELECT id FROM reports WHERE candidate_id = 123), 'GraphQL experience'),
((SELECT id FROM reports WHERE candidate_id = 123), 'Testing practices'),

-- Alexander Smirnov gaps
((SELECT id FROM reports WHERE candidate_id = 124), 'Cloud technologies'),
((SELECT id FROM reports WHERE candidate_id = 124), 'Microservices architecture'),
((SELECT id FROM reports WHERE candidate_id = 124), 'Leadership experience'),

-- Elena Kozlova gaps
((SELECT id FROM reports WHERE candidate_id = 125), 'Technical depth'),
((SELECT id FROM reports WHERE candidate_id = 125), 'Strategic thinking'),
((SELECT id FROM reports WHERE candidate_id = 125), 'Stakeholder management'),

-- Ivan Petrov gaps (minimal)
((SELECT id FROM reports WHERE candidate_id = 126), 'None identified');

-- Insert red flags (only for candidates with issues)
INSERT INTO report_red_flags (report_id, red_flag) VALUES
-- Alexander Smirnov red flags
((SELECT id FROM reports WHERE candidate_id = 124), 'Template answers detected'),
((SELECT id FROM reports WHERE candidate_id = 124), 'Avoided system design questions'),

-- Elena Kozlova red flags
((SELECT id FROM reports WHERE candidate_id = 125), 'Inconsistent answers'),
((SELECT id FROM reports WHERE candidate_id = 125), 'Lack of specific examples'),
((SELECT id FROM reports WHERE candidate_id = 125), 'Poor preparation');

-- Verify data was inserted
SELECT 
    candidate_id,
    overall_score,
    recommendation_decision,
    recommendation_confidence,
    created_at
FROM reports 
WHERE candidate_id IN (123, 124, 125, 126)
ORDER BY candidate_id;
