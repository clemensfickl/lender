package at.kohlenrutsche.lender.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Objects;

/**
 * A Lending.
 */
@Entity
@Table(name = "lending")
public class Lending implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "jhi_start", nullable = false)
    private ZonedDateTime start;

    @NotNull
    @Column(name = "planned_end", nullable = false)
    private ZonedDateTime plannedEnd;

    @Column(name = "jhi_end")
    private ZonedDateTime end;

    @Column(name = "informed_about_end")
    private Boolean informedAboutEnd;

    @Column(name = "jhi_cost", precision = 10, scale = 2)
    private BigDecimal cost;

    @Column(name = "paid")
    private Boolean paid;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("lendings")
    private User borrower;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("lendings")
    private Item item;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public Lending start(ZonedDateTime start) {
        this.start = start;
        return this;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getPlannedEnd() {
        return plannedEnd;
    }

    public Lending plannedEnd(ZonedDateTime plannedEnd) {
        this.plannedEnd = plannedEnd;
        return this;
    }

    public void setPlannedEnd(ZonedDateTime plannedEnd) {
        this.plannedEnd = plannedEnd;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public Lending end(ZonedDateTime end) {
        this.end = end;
        return this;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public Boolean isInformedAboutEnd() {
        return informedAboutEnd;
    }

    public Lending informedAboutEnd(Boolean informedAboutEnd) {
        this.informedAboutEnd = informedAboutEnd;
        return this;
    }

    public void setInformedAboutEnd(Boolean informedAboutEnd) {
        this.informedAboutEnd = informedAboutEnd;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public Lending cost(BigDecimal cost) {
        this.cost = cost;
        return this;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Boolean isPaid() {
        return paid;
    }

    public Lending paid(Boolean paid) {
        this.paid = paid;
        return this;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public User getBorrower() {
        return borrower;
    }

    public Lending borrower(User user) {
        this.borrower = user;
        return this;
    }

    public void setBorrower(User user) {
        this.borrower = user;
    }

    public Item getItem() {
        return item;
    }

    public Lending item(Item item) {
        this.item = item;
        return this;
    }

    public void setItem(Item item) {
        this.item = item;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Lending lending = (Lending) o;
        if (lending.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lending.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Lending{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", plannedEnd='" + getPlannedEnd() + "'" +
            ", end='" + getEnd() + "'" +
            ", informedAboutEnd='" + isInformedAboutEnd() + "'" +
            ", cost=" + getCost() +
            ", paid='" + isPaid() + "'" +
            "}";
    }
}
