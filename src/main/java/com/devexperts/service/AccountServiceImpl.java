package com.devexperts.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
    	
    	synchronized (this) {
			
    		if(source.getBalance().compareTo(amount) < 0) {
    			throw new InsufficientFundsException();
    		}
    		
    		accounts.get(source.getAccountKey()).debit(amount);
    		accounts.get(target.getAccountKey()).credit(amount);
		}
    }
}
