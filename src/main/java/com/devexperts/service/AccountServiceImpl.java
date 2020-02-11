package com.devexperts.service;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.devexperts.account.Account;
import com.devexperts.account.AccountKey;
import com.devexperts.exception.AccountSourceIsNotFoundException;
import com.devexperts.exception.AccountTargetIsNotFoundException;
import com.devexperts.exception.InsufficientFundsException;
import com.devexperts.exception.InvalidAmountException;

@Service
public class AccountServiceImpl implements AccountService {

	private final Map<AccountKey, Account> accounts = new ConcurrentHashMap<>();

	@PostConstruct
	private void init() {
		AccountKey accountKey1 = AccountKey.valueOf(1l);
		Account account1 = new Account(accountKey1, "Adriano", "Ribeiro", 1000d);

		AccountKey accountKey2 = AccountKey.valueOf(2l);
		Account account2 = new Account(accountKey2, "Juca", "Ribas", 500d);
		
		accounts.put(accountKey1, account1);
		accounts.put(accountKey2, account2);
	}
	
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

	@Override
	public void transfer(long sourceId, long targetId, double amount) {
		Account source = accounts.get(AccountKey.valueOf(sourceId));		
		if (source==null) {
			throw new AccountSourceIsNotFoundException();
		}

		Account target = accounts.get(AccountKey.valueOf(targetId));
		if (target==null) {
			throw new AccountTargetIsNotFoundException();
		}
		
		if (amount<0) {
			throw new InvalidAmountException();
		}
		
		transfer(source, target, amount);
	}
}
