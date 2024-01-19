package org.example.dao;

import org.example.model.Account;

public interface AccountDAO {
    void saveAccount(Account account);
    Account getAccountById(Long accountId);
    void updateAccount(Account account);
    void deleteAccount(Account account);

}
