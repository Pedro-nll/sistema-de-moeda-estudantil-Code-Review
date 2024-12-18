package com.coinsystem.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coinsystem.system.DTO.TransferRequestDTO;
import com.coinsystem.system.controller.ApiResponse.ApiResponse;
import com.coinsystem.system.model.Transaction;
import com.coinsystem.system.model.Wallet;
import com.coinsystem.system.repository.WalletRepository;
import com.coinsystem.system.service.interfaces.IWalletService;

@RestController
@RequestMapping("/wallet")
public class WalletController {

    @Autowired
    private IWalletService walletService;
    @Autowired
    private WalletRepository walletRepository;

    @PostMapping("/transfer")
    public ResponseEntity<ApiResponse<String>> transferCoins(@RequestBody TransferRequestDTO request) {
        try {
            boolean success = walletService.transferCoins(
                    request.sourceWalletId(),
                    request.destinationWalletId(),
                    request.amount(),
                    request.description());

            if (success) {
                ApiResponse<String> response = new ApiResponse<>(true, "Transfer completed successfully!", null);
                return ResponseEntity.status(HttpStatus.OK).body(response);
            } else {
                ApiResponse<String> errorResponse = new ApiResponse<>(false,
                        "Transfer failed. Insufficient balance.", null);
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
            }

        } catch (Exception e) {
            ApiResponse<String> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Wallet>>> getAllWallet() {
        try {
            List<Wallet> wallet = walletService.getAllWallet();
            ApiResponse<List<Wallet>> response = new ApiResponse<>(true, "All Wallet fetched successfully", wallet);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiResponse<List<Wallet>> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }

    @GetMapping("/{walletId}/transactions")
    public ResponseEntity<ApiResponse<List<Transaction>>> getTransactionHistory(@PathVariable Long walletId) {
        try {
            Wallet wallet = walletRepository.findById(walletId)
                    .orElseThrow(() -> new IllegalArgumentException("Wallet not found."));
            List<Transaction> transactions = wallet.getTransactions();
            ApiResponse<List<Transaction>> response = new ApiResponse<>(true,
                    "Transaction history retrieved successfully.", transactions);
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<List<Transaction>> errorResponse = new ApiResponse<>(false, e.getMessage(), null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        } catch (Exception e) {
            ApiResponse<List<Transaction>> errorResponse = new ApiResponse<>(false,
                    "Error retrieving transaction history.", null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

}
