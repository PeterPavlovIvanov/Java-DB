package paymentSystem;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import java.math.BigInteger;

@Entity
@DiscriminatorValue("bank_account")
public class BankAccount extends BillingDetail {
    private String name;
    private String swiftCode;

    public BankAccount() {
    }

    public BankAccount(BigInteger number, User owner, String name, String swiftCode) {
        super(number, owner);
        this.name = name;
        this.swiftCode = swiftCode;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "swift_code")
    public String getSwiftCode() {
        return swiftCode;
    }

    public void setSwiftCode(String swiftCode) {
        this.swiftCode = swiftCode;
    }
}