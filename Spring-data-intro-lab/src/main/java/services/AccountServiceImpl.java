package services;

import exceptions.IllegalBankOperationExcepion;
import models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.AccountRepository;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service("accountService")// name =
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public Account getAccount(long accountId) {
        return accountRepository.findById(accountId);
    }

    @Transactional
    @Override
    public void withdraw(long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId);
        if (account.getBalance().compareTo(amount) < 0) {
            throw new IllegalBankOperationExcepion("Current balance: $" +
                    account.getBalance() + " is not sufficient to withdraw amount: " +
                    amount);
        }
        account.setBalance(account.getBalance().subtract(amount));
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void deposit(long accountId, BigDecimal amount) {
        Account account = accountRepository.findById(accountId);
        account.setBalance(account.getBalance().add(amount));
        accountRepository.save(account);
    }

    @Transactional
    @Override
    public void transfer(long fromAccountId, long toAccountId, BigDecimal amount) {
        withdraw(fromAccountId, amount);
        deposit(toAccountId, amount);
    }
}
