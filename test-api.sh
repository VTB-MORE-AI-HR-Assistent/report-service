#!/bin/bash

# VTB AI HR Report Service API Test Script
# Tests all endpoints except email functionality

BASE_URL="http://localhost:8085"
API_BASE="$BASE_URL/api/reports"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Test data
CANDIDATE_ID=123
JOB_ID=456
INTERVIEW_ID=789

echo -e "${BLUE}🚀 Starting VTB AI HR Report Service API Tests${NC}"
echo "=================================================="

# Function to test endpoint
test_endpoint() {
    local method=$1
    local endpoint=$2
    local data=$3
    local description=$4
    
    echo -e "\n${YELLOW}Testing: $description${NC}"
    echo "Endpoint: $method $endpoint"
    
    if [ "$method" = "GET" ]; then
        response=$(curl -s -w "\n%{http_code}" "$endpoint")
    else
        response=$(curl -s -w "\n%{http_code}" -X "$method" -H "Content-Type: application/json" -d "$data" "$endpoint")
    fi
    
    http_code=$(echo "$response" | tail -n1)
    body=$(echo "$response" | head -n -1)
    
    if [ "$http_code" -ge 200 ] && [ "$http_code" -lt 300 ]; then
        echo -e "${GREEN}✅ SUCCESS (HTTP $http_code)${NC}"
        echo "Response: $body" | jq . 2>/dev/null || echo "Response: $body"
    else
        echo -e "${RED}❌ FAILED (HTTP $http_code)${NC}"
        echo "Response: $body"
    fi
}

# Test 1: Health Check
echo -e "\n${BLUE}1. Health Check${NC}"
test_endpoint "GET" "$BASE_URL/actuator/health" "" "Application Health Check"

# Test 2: Save ML Report
echo -e "\n${BLUE}2. Save ML Report${NC}"
ML_REPORT_DATA='{
  "candidateId": 123,
  "jobId": 456,
  "interviewId": 789,
  "overallScore": 85,
  "overallMatchScore": 90,
  "technicalSkillsScore": 88,
  "confirmedSkills": ["Java", "Spring Boot", "Kotlin", "PostgreSQL"],
  "missingSkills": ["Docker", "Kubernetes", "AWS"],
  "technicalDetails": "Strong technical background with excellent Java and Spring Boot skills. Demonstrated good understanding of microservices architecture.",
  "communicationScore": 82,
  "communicationDetails": "Good communication skills, clear explanations, asked relevant questions.",
  "experienceScore": 87,
  "relevantProjects": ["E-commerce Platform", "Banking System", "API Gateway"],
  "experienceDetails": "5+ years of experience in Java development with focus on enterprise applications.",
  "totalQuestions": 20,
  "questionsAnswered": 18,
  "problemSolvingScore": 85,
  "teamworkScore": 90,
  "leadershipScore": 75,
  "adaptabilityScore": 88,
  "redFlags": [],
  "strengths": ["Technical skills", "Problem solving", "Team collaboration"],
  "gaps": ["Cloud technologies", "Leadership experience"],
  "recommendationDecision": "HIRE",
  "recommendationConfidence": 0.85,
  "recommendationReasoning": "Strong technical fit with good communication skills. Minor gaps in cloud technologies can be addressed through training.",
  "nextSteps": ["Technical interview", "Reference check", "Offer preparation"],
  "candidatePositivePoints": ["Excellent Java skills", "Good team player", "Quick learner"],
  "candidateImprovementAreas": ["Cloud technologies", "Leadership skills"]
}'

test_endpoint "POST" "$API_BASE" "$ML_REPORT_DATA" "Save ML Report"

# Wait a moment for data to be processed
sleep 2

# Test 3: Get Candidate Report
echo -e "\n${BLUE}3. Get Candidate Report${NC}"
test_endpoint "GET" "$API_BASE/candidate?candidateId=$CANDIDATE_ID&jobId=$JOB_ID" "" "Get Candidate Report by ID"

# Test 4: Get Candidate Recommendation
echo -e "\n${BLUE}4. Get Candidate Recommendation${NC}"
test_endpoint "GET" "$API_BASE/recommendations?candidateId=$CANDIDATE_ID&jobId=$JOB_ID" "" "Get Candidate Recommendation by ID"

# Test 5: Download PDF Report
echo -e "\n${BLUE}5. Download PDF Report${NC}"
echo -e "${YELLOW}Testing: PDF Download${NC}"
echo "Endpoint: GET $API_BASE/pdf?candidateId=$CANDIDATE_ID&jobId=$JOB_ID"

pdf_response=$(curl -s -w "\n%{http_code}" "$API_BASE/pdf?candidateId=$CANDIDATE_ID&jobId=$JOB_ID")
pdf_http_code=$(echo "$pdf_response" | tail -n1)

if [ "$pdf_http_code" -ge 200 ] && [ "$pdf_http_code" -lt 300 ]; then
    echo -e "${GREEN}✅ SUCCESS (HTTP $pdf_http_code)${NC}"
    echo "PDF downloaded successfully (saved to candidate_report_${CANDIDATE_ID}_${JOB_ID}.pdf)"
    # Save PDF to file
    echo "$pdf_response" | head -n -1 > "candidate_report_${CANDIDATE_ID}_${JOB_ID}.pdf"
else
    echo -e "${RED}❌ FAILED (HTTP $pdf_http_code)${NC}"
fi

# Test 6: Test with non-existent data
echo -e "\n${BLUE}6. Test Error Handling${NC}"
test_endpoint "GET" "$API_BASE/candidate?candidateId=999&jobId=999" "" "Get Non-existent Report (Should return 404)"

# Test 7: Test invalid parameters
echo -e "\n${BLUE}7. Test Invalid Parameters${NC}"
test_endpoint "GET" "$API_BASE/candidate" "" "Get Report without parameters (Should return 400)"

# Summary
echo -e "\n${BLUE}=================================================="
echo -e "🎯 API Testing Complete!${NC}"
echo -e "=================================================="
echo -e "\n${YELLOW}Available Endpoints Tested:${NC}"
echo "✅ POST /api/reports - Save ML Report"
echo "✅ GET /api/reports/candidate - Get Candidate Report"
echo "✅ GET /api/reports/recommendations - Get Candidate Recommendation"
echo "✅ GET /api/reports/pdf - Download PDF Report"
echo "✅ GET /actuator/health - Health Check"
echo -e "\n${YELLOW}Skipped:${NC}"
echo "⏭️  POST /api/reports/email - Email functionality (requires Resend setup)"

echo -e "\n${BLUE}📋 Test Files Generated:${NC}"
if [ -f "candidate_report_${CANDIDATE_ID}_${JOB_ID}.pdf" ]; then
    echo "📄 candidate_report_${CANDIDATE_ID}_${JOB_ID}.pdf"
fi

echo -e "\n${GREEN}✨ All tests completed!${NC}"
