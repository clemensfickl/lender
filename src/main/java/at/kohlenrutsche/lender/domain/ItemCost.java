package at.kohlenrutsche.lender.domain;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

import at.kohlenrutsche.lender.domain.enumeration.TimeFrame;

/**
 * A ItemCost.
 */
@Entity
@Table(name = "item_cost")
public class ItemCost implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "time_frame", nullable = false)
    private TimeFrame timeFrame;

    @NotNull
    @Column(name = "cost_per_time_frame", precision = 10, scale = 2, nullable = false)
    private BigDecimal costPerTimeFrame;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("costs")
    private Item item;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeFrame getTimeFrame() {
        return timeFrame;
    }

    public ItemCost timeFrame(TimeFrame timeFrame) {
        this.timeFrame = timeFrame;
        return this;
    }

    public void setTimeFrame(TimeFrame timeFrame) {
        this.timeFrame = timeFrame;
    }

    public BigDecimal getCostPerTimeFrame() {
        return costPerTimeFrame;
    }

    public ItemCost costPerTimeFrame(BigDecimal costPerTimeFrame) {
        this.costPerTimeFrame = costPerTimeFrame;
        return this;
    }

    public void setCostPerTimeFrame(BigDecimal costPerTimeFrame) {
        this.costPerTimeFrame = costPerTimeFrame;
    }

    public Item getItem() {
        return item;
    }

    public ItemCost item(Item item) {
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
        ItemCost itemCost = (ItemCost) o;
        if (itemCost.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemCost.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemCost{" +
            "id=" + getId() +
            ", timeFrame='" + getTimeFrame() + "'" +
            ", costPerTimeFrame=" + getCostPerTimeFrame() +
            "}";
    }
}
