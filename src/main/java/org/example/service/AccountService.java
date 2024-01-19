package org.example.service;

import org.example.model.Account;

public interface AccountService {
    void createAccount(Account account);
    Account getAccountById(Long accountId);
    void updateAccount(Account account);
    void deleteAccount(Long accountId);
}
