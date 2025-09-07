#!/bin/bash

echo "Testing PDF API endpoints..."

# Test simple PDF generation
echo "1. Testing simple PDF generation..."
curl -X GET "http://localhost:8085/api/reports/pdf/simple" \
  -H "Accept: application/pdf" \
  -o "simple_test_report.pdf" \
  -w "HTTP Status: %{http_code}\nContent-Type: %{content_type}\nContent-Length: %{size_download}\n"

echo "Simple PDF saved as: simple_test_report.pdf"
echo ""

# Test detailed PDF generation
echo "2. Testing detailed PDF generation..."
curl -X GET "http://localhost:8085/api/reports/pdf" \
  -H "Accept: application/pdf" \
  -o "detailed_test_report.pdf" \
  -w "HTTP Status: %{http_code}\nContent-Type: %{content_type}\nContent-Length: %{size_download}\n"

echo "Detailed PDF saved as: detailed_test_report.pdf"
echo ""

# Test existing PDF endpoint
echo "3. Testing existing test PDF endpoint..."
curl -X GET "http://localhost:8085/api/v1/reports/pdf/test" \
  -H "Accept: application/pdf" \
  -o "existing_test_report.pdf" \
  -w "HTTP Status: %{http_code}\nContent-Type: %{content_type}\nContent-Length: %{size_download}\n"

echo "Existing test PDF saved as: existing_test_report.pdf"
echo ""

echo "All tests completed!"
echo "Check the generated PDF files to verify they open correctly."
