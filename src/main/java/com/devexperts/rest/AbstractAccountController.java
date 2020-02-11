package com.devexperts.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class AbstractAccountController {
    abstract ResponseEntity<Void> transfer(@RequestBody TransactionDTO transactionDTO);
}
