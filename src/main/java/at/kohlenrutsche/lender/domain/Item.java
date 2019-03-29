package at.kohlenrutsche.lender.domain;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Item.
 */
@Entity
@Table(name = "item")
public class Item implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name", nullable = false)
    private String name;

    @Size(max = 4000)
    @Column(name = "location_detail", length = 4000)
    private String locationDetail;

    @Size(max = 4000)
    @Column(name = "description", length = 4000)
    private String description;

    @OneToMany(mappedBy = "item")
    private Set<ItemCost> costs = new HashSet<>();
    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("items")
    private User owner;

    @ManyToOne
    @JsonIgnoreProperties("items")
    private Room location;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Item name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocationDetail() {
        return locationDetail;
    }

    public Item locationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
        return this;
    }

    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }

    public String getDescription() {
        return description;
    }

    public Item description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<ItemCost> getCosts() {
        return costs;
    }

    public Item costs(Set<ItemCost> itemCosts) {
        this.costs = itemCosts;
        return this;
    }

    public Item addCosts(ItemCost itemCost) {
        this.costs.add(itemCost);
        itemCost.setItem(this);
        return this;
    }

    public Item removeCosts(ItemCost itemCost) {
        this.costs.remove(itemCost);
        itemCost.setItem(null);
        return this;
    }

    public void setCosts(Set<ItemCost> itemCosts) {
        this.costs = itemCosts;
    }

    public User getOwner() {
        return owner;
    }

    public Item owner(User user) {
        this.owner = user;
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    public Room getLocation() {
        return location;
    }

    public Item location(Room room) {
        this.location = room;
        return this;
    }

    public void setLocation(Room room) {
        this.location = room;
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
        Item item = (Item) o;
        if (item.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), item.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Item{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", locationDetail='" + getLocationDetail() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
