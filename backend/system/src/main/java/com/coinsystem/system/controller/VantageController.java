package com.coinsystem.system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinsystem.system.DTO.VantageDTO;
import com.coinsystem.system.controller.ApiResponse.ApiResponse;
import com.coinsystem.system.model.Vantage;
import com.coinsystem.system.service.interfaces.IVantageService;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/vantage")
public class VantageController {

    @Autowired
    private IVantageService vantageService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<Vantage>> register(@RequestBody @Valid VantageDTO vantageDTO) {
        try {
            Vantage vantage = vantageService.register(vantageDTO);
            ApiResponse<Vantage> response = new ApiResponse<Vantage>(true, "User registered succesfully", vantage);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            ApiResponse<Vantage> errorResponse = new ApiResponse<Vantage>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Vantage>>> getAllVantage() {
        try {
            List<Vantage> vantage = vantageService.getAllVantage();
            ApiResponse<List<Vantage>> response = new ApiResponse<>(true, "All Vantage fetched successfully", vantage);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<Vantage>> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Vantage>> getVantageById(@PathVariable Long id) {
        try {
            Vantage vantage = vantageService.getVantageById(id);
            if (vantage != null) {
                ApiResponse<Vantage> response = new ApiResponse<>(true, "Vantage found successfully", vantage);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<Vantage> response = new ApiResponse<>(false, "Vantage not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse<Vantage> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/by-partner/{partnerCompanyId}")
    public ResponseEntity<ApiResponse<List<Vantage>>> getVantagesByPartnerCompany(@PathVariable Long partnerCompanyId) {
        try {
            List<Vantage> vantages = vantageService.getVantagesByPartnerCompanyId(partnerCompanyId);
            ApiResponse<List<Vantage>> response = new ApiResponse<>(true, "Vantages fetched successfully", vantages);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<Vantage>> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<Vantage>> update(@PathVariable Long id,
            @RequestBody @Valid VantageDTO VantageDTO) {
        try {
            Vantage updatedVantage = vantageService.update(id, VantageDTO);
            if (updatedVantage != null) {
                ApiResponse<Vantage> response = new ApiResponse<>(true, "Vantage updated successfully", updatedVantage);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<Vantage> response = new ApiResponse<>(false, "Vantage not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse<Vantage> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        try {
            boolean isDeleted = vantageService.delete(id);
            if (isDeleted) {
                ApiResponse<Void> response = new ApiResponse<>(true, "Vantage deleted successfully", null);
                return ResponseEntity.ok(response);
            } else {
                ApiResponse<Void> response = new ApiResponse<>(false, "Vantage not found", null);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
            }
        } catch (Exception e) {
            ApiResponse<Void> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

}