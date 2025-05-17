package com.bridgelab.hiringapp.controller;

import com.bridgelab.hiringapp.dto.*;
import com.bridgelab.hiringapp.entity.Candidate;
import com.bridgelab.hiringapp.entity.Document;
import com.bridgelab.hiringapp.service.CandidateService;
import com.bridgelab.hiringapp.service.DocumentService;
import com.bridgelab.hiringapp.utils.BuildResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/api/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private DocumentService documentService;

    @GetMapping
    public ResponseEntity<ApiResponseDto> getAllCandidates(HttpServletRequest request,
                                                           @RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size,
                                                           @RequestParam(defaultValue = "name") String sortBy) {
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy));
        Page<Candidate> candidates = candidateService.getCandidateData(pageable);
        return BuildResponse.success(candidates, "List of all candidates", request.getRequestURI());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto> getCandidateById(@PathVariable Long id, HttpServletRequest request) {
        Candidate candidate = candidateService.getCandidateDataById(id);
        return BuildResponse.success(candidate, "Candidate by ID", request.getRequestURI());
    }

    @GetMapping("/count")
    public ResponseEntity<ApiResponseDto> getCandidateCount(HttpServletRequest request) {
        int count = candidateService.getTotalCount();
        return BuildResponse.success(count, "Total candidate count", request.getRequestURI());
    }

    @GetMapping("/hired")
    public ResponseEntity<ApiResponseDto> getHiredCandidates(HttpServletRequest request) {
        List<Candidate> hiredCandidates = candidateService.hiredCandidate();
        return BuildResponse.success(hiredCandidates, "List of hired candidates", request.getRequestURI());
    }


    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto> createCandidate(@Valid @RequestBody CandidateDto candidateDto, HttpServletRequest request) {
        Candidate createdCandidate = candidateService.createCandidateData(candidateDto);
        return BuildResponse.success(createdCandidate, "Candidate created successfully", request.getRequestURI());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ApiResponseDto> updateCandidateStatus(@PathVariable Long id,
                                                                @Valid @RequestBody StatusUpdateDto statusDto,
                                                                HttpServletRequest request) {
        Candidate updatedCandidate = candidateService.updateStatusByid(id, request.getRequestURI(), statusDto);
        return BuildResponse.success(updatedCandidate, "Candidate status updated successfully", request.getRequestURI());
    }

    @PutMapping("/{id}/onboard-status")
    public ResponseEntity<ApiResponseDto> updateCandidateOnboardStatus(@PathVariable Long id,
                                                                       @RequestBody OnboardStatusDto onboardStatusDto,
                                                                       HttpServletRequest request) {
        Candidate updatedCandidate = candidateService.updateOnboardStatusByid(id, request.getRequestURI(), onboardStatusDto);
        return BuildResponse.success(updatedCandidate, "Candidate onboard status updated successfully", request.getRequestURI());
    }

    @PutMapping("/{id}/verify-document")
    public ResponseEntity<ApiResponseDto> updateVerifyDocument(@PathVariable Long id,
                                                                       HttpServletRequest request) {
        Document documentVerified = documentService.updateVerifiedDocumentByid(id, request.getRequestURI());
        return BuildResponse.success(documentVerified, "Candidate Document Verification status updated successfully", request.getRequestURI());
    }

}
