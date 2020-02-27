package init;

import models.Account;
import models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import services.AccountService;
import services.UserService;

import java.math.BigDecimal;

public class AccountsRunner implements ApplicationRunner {
    @Autowired
    private UserService userService;

    @Autowired
    private AccountService accountService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        User gylabin = new User("Gylabin", 26);
        User stamat = new User("Stamat", 27);

        Account account1 = new Account(gylabin, new BigDecimal(46));
        gylabin.getAccounts().add(account1);
        Account account2 = new Account(stamat, new BigDecimal(92));
        stamat.getAccounts().add(account2);

        userService.registerUser(gylabin);
        userService.registerUser(stamat);

        System.out.println("!!! Initial balance for gylabin: {" +
                accountService.getAccount(account1.getId()) + "}");

        accountService.transfer(account1.getId(),account2.getId(),new BigDecimal(23));

        System.out.println("!!! After transfer balance for gylabin: {" +
                accountService.getAccount(account1.getId()) + "}");
        System.out.println("!!! After transfer balance for stamat: {" +
                accountService.getAccount(account2.getId()) + "}");
    }
}

