package com.mycompany.myapp.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;

import java.io.Serializable;

/**
 * A MyTransaction.
 */
@Entity
@Table(name = "my_transaction")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class MyTransaction implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email")
    private String email;

    @Column(name = "amount")
    private Long amount;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JsonIgnoreProperties(value = "myTransactions", allowSetters = true)
    private Buddy buddy;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public MyTransaction email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getAmount() {
        return amount;
    }

    public MyTransaction amount(Long amount) {
        this.amount = amount;
        return this;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public MyTransaction description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Buddy getBuddy() {
        return buddy;
    }

    public MyTransaction buddy(Buddy buddy) {
        this.buddy = buddy;
        return this;
    }

    public void setBuddy(Buddy buddy) {
        this.buddy = buddy;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof MyTransaction)) {
            return false;
        }
        return id != null && id.equals(((MyTransaction) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "MyTransaction{" +
            "id=" + getId() +
            ", email='" + getEmail() + "'" +
            ", amount=" + getAmount() +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
