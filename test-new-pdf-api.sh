#!/bin/bash

echo "Testing New PDF API endpoints..."

# Test new PDF controller endpoints
echo "1. Testing new PDF controller - simple PDF..."
curl -X GET "http://localhost:8085/api/v1/pdf/simple" \
  -H "Accept: application/pdf" \
  -o "new_simple_test_report.pdf" \
  -w "HTTP Status: %{http_code}\nContent-Type: %{content_type}\nContent-Length: %{size_download}\n"

echo "New simple PDF saved as: new_simple_test_report.pdf"
echo ""

echo "2. Testing new PDF controller - test PDF..."
curl -X GET "http://localhost:8085/api/v1/pdf/test" \
  -H "Accept: application/pdf" \
  -o "new_test_report.pdf" \
  -w "HTTP Status: %{http_code}\nContent-Type: %{content_type}\nContent-Length: %{size_download}\n"

echo "New test PDF saved as: new_test_report.pdf"
echo ""

echo "3. Testing PDF service status..."
curl -X GET "http://localhost:8085/api/v1/pdf/status" \
  -H "Accept: application/json" \
  -w "HTTP Status: %{http_code}\n"

echo ""
echo ""

echo "4. Testing PDF debug endpoint..."
curl -X GET "http://localhost:8085/api/v1/pdf/debug" \
  -H "Accept: application/json" \
  -w "HTTP Status: %{http_code}\n"

echo ""
echo ""

# Test legacy endpoints for comparison
echo "5. Testing legacy PDF endpoints..."
curl -X GET "http://localhost:8085/api/legacy/reports/pdf/simple" \
  -H "Accept: application/pdf" \
  -o "legacy_simple_test_report.pdf" \
  -w "HTTP Status: %{http_code}\nContent-Type: %{content_type}\nContent-Length: %{size_download}\n"

echo "Legacy simple PDF saved as: legacy_simple_test_report.pdf"
echo ""

# Test existing v1 endpoints
echo "6. Testing existing v1 PDF endpoints..."
curl -X GET "http://localhost:8085/api/v1/reports/pdf/test" \
  -H "Accept: application/pdf" \
  -o "existing_v1_test_report.pdf" \
  -w "HTTP Status: %{http_code}\nContent-Type: %{content_type}\nContent-Length: %{size_download}\n"

echo "Existing v1 test PDF saved as: existing_v1_test_report.pdf"
echo ""

echo "All tests completed!"
echo "Check the generated PDF files to verify they open correctly."
echo ""
echo "Generated files:"
echo "- new_simple_test_report.pdf (new controller)"
echo "- new_test_report.pdf (new controller)"
echo "- legacy_simple_test_report.pdf (legacy controller)"
echo "- existing_v1_test_report.pdf (existing v1 controller)"
