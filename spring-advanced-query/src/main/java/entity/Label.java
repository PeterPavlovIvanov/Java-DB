package entity;

import Base.BaseEntity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "labels")
public class Label extends BaseEntity {
    private String title;
    private String subtitle;

    private Set<Shampoo> shampoos;

    public Label() {
    }

    @Column(name = "title",unique = true)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Column(name = "subtitle")
    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    @OneToMany(mappedBy = "label",fetch = FetchType.EAGER)
    public Set<Shampoo> getShampoos() {
        return shampoos;
    }

    public void setShampoos(Set<Shampoo> shampoos) {
        this.shampoos = shampoos;
    }
}
