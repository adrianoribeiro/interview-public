package com.devexperts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.devexperts.account.Account;
import com.devexperts.account.AccountKey;

public class AccountServiceTest {

	private AccountService accountService = new AccountServiceImpl();
	private Account account1;
	private AccountKey accountKey1;
	
	@Test
	void createAccount() {
		accountKey1 = AccountKey.valueOf(1l);
		account1 = new Account( accountKey1, "Adriano", "Ribeiro", 1000d);
		accountService.createAccount(account1);
		assertEquals(account1, accountService.getAccount(1l));
	}
}
