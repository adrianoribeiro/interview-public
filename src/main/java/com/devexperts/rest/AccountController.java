package com.devexperts.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.devexperts.service.AccountService;

@RestController
@RequestMapping("/api")
public class AccountController extends AbstractAccountController {

	@Autowired
	private AccountService accountService;
	
	@PostMapping("/operations/transfer")
    public ResponseEntity<Void> transfer(@RequestBody TransactionDTO transactionDTO) {
		
		if(transactionDTO.getSourceId() == null || transactionDTO.getTargetId() == null) {
			return new ResponseEntity<Void>(HttpStatus.BAD_REQUEST);
		}
		
		accountService.transfer(transactionDTO.getSourceId(), 
				transactionDTO.getTargetId(), 
				transactionDTO.getAmount());
		
		return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
