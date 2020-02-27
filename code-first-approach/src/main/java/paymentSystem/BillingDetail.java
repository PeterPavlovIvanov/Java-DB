package paymentSystem;
import Base.BaseEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;


@Entity
@Table(name = "billing_details",schema = "paymentSystem_db",catalog = "paymentSystem_db")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "billing_detail_type")
public class BillingDetail extends BaseEntity {
    private BigInteger number;
    private User owner;

    public BillingDetail() {
    }

    public BillingDetail(BigInteger number, User owner) {
        this.number = number;
        this.owner = owner;
    }

    @Column(name = "number")
    public BigInteger getNumber() {
        return number;
    }

    public void setNumber(BigInteger number) {
        this.number = number;
    }

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
}
