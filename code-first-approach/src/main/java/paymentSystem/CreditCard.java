package paymentSystem;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import java.math.BigInteger;
import java.time.Month;
import java.time.Year;
import java.util.Date;

@Entity
@DiscriminatorValue("credit_card")
public class CreditCard extends BillingDetail {
    private String type;
    private Month expirationMonth;
    private Year expirationYear;

    public CreditCard() {
    }

    public CreditCard(BigInteger number, User owner, String type, Month expirationMonth, Year expirationYear) {
        super(number, owner);
        this.type = type;
        this.expirationMonth = expirationMonth;
        this.expirationYear = expirationYear;
    }

    @Column(name = "type")
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    @Column(name = "expiration_month")
    public Month getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(Month expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    @Column(name = "expiration_year")
    public Year getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(Year expirationYear) {
        this.expirationYear = expirationYear;
    }
}