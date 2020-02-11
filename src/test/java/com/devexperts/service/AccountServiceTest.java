package com.devexperts.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import com.devexperts.account.Account;
import com.devexperts.account.AccountKey;
import com.devexperts.exception.AccountSourceIsNotFoundException;
import com.devexperts.exception.AccountTargetIsNotFoundException;
import com.devexperts.exception.InsufficientFundsException;
import com.devexperts.exception.InvalidAmountException;

public class AccountServiceTest {

	private AccountService accountService = new AccountServiceImpl();

	@Test
	void createAccount() {
		AccountKey accountKey1 = AccountKey.valueOf(1l);
		Account account1 = new Account(accountKey1, "Adriano", "Ribeiro", 1000d);
		accountService.createAccount(account1);
		assertEquals(account1, accountService.getAccount(1l));
	}

	@Test
	void transfer() {

		AccountKey accountKey1 = AccountKey.valueOf(1l);
		Account account1 = new Account(accountKey1, "Adriano", "Ribeiro", 1000d);

		AccountKey accountKey2 = AccountKey.valueOf(2l);
		Account account2 = new Account(accountKey2, "Juca", "Ribas", 500d);

		accountService.createAccount(account1);
		accountService.createAccount(account2);

		accountService.transfer(account1, account2, 300);

		assertEquals(700, account1.getBalance());
		assertEquals(800, account2.getBalance());
	}

	@Test
	void transferWithInsufficientFunds() {

		AccountKey accountKey1 = AccountKey.valueOf(1l);
		Account account1 = new Account(accountKey1, "Adriano", "Ribeiro", 1000d);

		AccountKey accountKey2 = AccountKey.valueOf(2l);
		Account account2 = new Account(accountKey2, "Juca", "Ribas", 500d);

		accountService.createAccount(account1);
		accountService.createAccount(account2);

		Exception exception = assertThrows(InsufficientFundsException.class, () -> {
			accountService.transfer(account1, account2, 1200);
		});

		assertEquals("InsufficientFundsException", exception.getMessage());
	}

	@Test
	void transferTenThousandTimes() {

		AccountKey accountKey1 = AccountKey.valueOf(1l);
		Account account1 = new Account(accountKey1, "Adriano", "Ribeiro", 10000d);

		AccountKey accountKey2 = AccountKey.valueOf(2l);
		Account account2 = new Account(accountKey2, "Juca", "Ribas", 500d);

		accountService.createAccount(account1);
		accountService.createAccount(account2);

		// Transfer
		for (int i = 0; i < 10000; i++) {
			accountService.transfer(account1, account2, 1);
		}

		assertEquals(0, account1.getBalance());
		assertEquals(10500, account2.getBalance());
	}

	@Test
	void transferSourceIdNotFound() {

		long id1 = 1l;
		AccountKey accountKey1 = AccountKey.valueOf(id1);
		Account account1 = new Account(accountKey1, "Adriano", "Ribeiro", 1000d);

		accountService.createAccount(account1);

		long inexistentID = 3l;

		Exception exception = assertThrows(AccountSourceIsNotFoundException.class, () -> {
			accountService.transfer(inexistentID, id1, 100);
		});

		assertEquals("AccountSourceIsNotFoundException", exception.getMessage());
	}

	@Test
	void transferTargetIdNotFound() {

		long id1 = 1l;
		AccountKey accountKey1 = AccountKey.valueOf(id1);
		Account account1 = new Account(accountKey1, "Adriano", "Ribeiro", 1000d);

		accountService.createAccount(account1);

		long inexistentID = 3l;

		Exception exception = assertThrows(AccountTargetIsNotFoundException.class, () -> {
			accountService.transfer(id1, inexistentID, 100);
		});

		assertEquals("AccountTargetIsNotFoundException", exception.getMessage());
	}

	@Test
	void transferInvalidAmount() {

		long id1 = 1l;
		AccountKey accountKey1 = AccountKey.valueOf(id1);
		Account account1 = new Account(accountKey1, "Adriano", "Ribeiro", 10000d);

		long id2 = 2l;
		AccountKey accountKey2 = AccountKey.valueOf(2l);
		Account account2 = new Account(accountKey2, "Juca", "Ribas", 500d);

		accountService.createAccount(account1);
		accountService.createAccount(account2);

		Exception exception = assertThrows(InvalidAmountException.class, () -> {
			accountService.transfer(id1, id2, -100);
		});

		assertEquals("InvalidAmountException", exception.getMessage());
	}
}
