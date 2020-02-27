package services;

import models.Account;

import java.math.BigDecimal;

public interface AccountService {
    Account getAccount(long accountId);

    void withdraw(long accountId, BigDecimal amount);

    void deposit(long accountId, BigDecimal amount);

    void transfer(long fromAccountId, long toAccountId, BigDecimal amount);
}
