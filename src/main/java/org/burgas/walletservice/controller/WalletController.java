package org.burgas.walletservice.controller;

import org.burgas.walletservice.entity.Wallet;
import org.burgas.walletservice.service.WalletService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

import static org.springframework.http.HttpStatus.FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON;

@RestController
@RequestMapping(value = "/api/v1/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @GetMapping(value = "/{walletId}")
    public ResponseEntity<Wallet> getWalletById(@PathVariable UUID walletId) {
        return ResponseEntity
                .status(OK)
                .contentType(APPLICATION_JSON)
                .body(this.walletService.findById(walletId));
    }

    @PostMapping(value = "/create")
    public ResponseEntity<Wallet> createWallet(@RequestParam(required = false) Long money) {
        Wallet wallet = this.walletService.create(money);
        return ResponseEntity
                .status(FOUND)
                .contentType(APPLICATION_JSON)
                .location(URI.create("/api/v1/wallets/" + wallet.getId().toString()))
                .body(wallet);
    }
}
