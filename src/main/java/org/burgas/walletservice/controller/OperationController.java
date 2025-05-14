package org.burgas.walletservice.controller;

import org.burgas.walletservice.dto.OperationRequest;
import org.burgas.walletservice.dto.OperationResponse;
import org.burgas.walletservice.service.OperationService;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.TEXT_PLAIN;

@RestController
@RequestMapping(value = "/api/v1/operations")
public class OperationController {

    private final OperationService operationService;

    public OperationController(OperationService operationService) {
        this.operationService = operationService;
    }

    @GetMapping(value = "/by-wallet")
    public ResponseEntity<List<OperationResponse>> getOperationsByWallet(@RequestParam UUID walletId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(this.operationService.findByWalletId(walletId));
    }

    @PostMapping(value = "/perform")
    public ResponseEntity<String> performOperation(@RequestBody OperationRequest operationRequest) throws ExecutionException, InterruptedException {
        return ResponseEntity
                .status(OK)
                .contentType(new MediaType(TEXT_PLAIN, UTF_8))
                .body(this.operationService.performOperation(operationRequest).get());
    }
}
