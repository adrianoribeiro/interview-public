package com.devexperts.service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import org.springframework.stereotype.Service;

import com.devexperts.account.Account;
import com.devexperts.account.AccountKey;
import com.devexperts.exception.InsufficientFundsException;

@Service
public class AccountServiceImpl implements AccountService {

	private final Map<AccountKey, Account> accounts = new ConcurrentHashMap<>();

	@Override
	public void clear() {
		accounts.clear();
	}

	@Override
	public void createAccount(Account account) {
		accounts.put(account.getAccountKey(), account);
	}

	@Override
	public Account getAccount(long id) {
		return accounts.get(AccountKey.valueOf(id));
	}

	@Override
	public void transfer(Account source, Account target, double amount) {

		if (source.getBalance().compareTo(amount) < 0) {
			throw new InsufficientFundsException();
		}

		CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {

			// System.out.println("Currently Executing thread name - " + Thread.currentThread().getName());

			accounts.get(source.getAccountKey()).debit(amount);
			accounts.get(target.getAccountKey()).credit(amount);
		}, Executors.newCachedThreadPool());

		//Note that: newCachedThreadPool, Creates a thread pool that creates new threads as needed, but will 
		//reuse previously constructed threads when they are available. (So this is enough for my example
		
		try {
			// Block until complete
			future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}
