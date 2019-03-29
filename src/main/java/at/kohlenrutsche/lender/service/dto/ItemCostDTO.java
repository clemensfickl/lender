package at.kohlenrutsche.lender.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import at.kohlenrutsche.lender.domain.enumeration.TimeFrame;

/**
 * A DTO for the ItemCost entity.
 */
public class ItemCostDTO implements Serializable {

    private Long id;

    @NotNull
    private TimeFrame timeFrame;

    @NotNull
    private BigDecimal costPerTimeFrame;


    private Long itemId;

    private String itemName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TimeFrame getTimeFrame() {
        return timeFrame;
    }

    public void setTimeFrame(TimeFrame timeFrame) {
        this.timeFrame = timeFrame;
    }

    public BigDecimal getCostPerTimeFrame() {
        return costPerTimeFrame;
    }

    public void setCostPerTimeFrame(BigDecimal costPerTimeFrame) {
        this.costPerTimeFrame = costPerTimeFrame;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemCostDTO itemCostDTO = (ItemCostDTO) o;
        if (itemCostDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemCostDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemCostDTO{" +
            "id=" + getId() +
            ", timeFrame='" + getTimeFrame() + "'" +
            ", costPerTimeFrame=" + getCostPerTimeFrame() +
            ", item=" + getItemId() +
            ", item='" + getItemName() + "'" +
            "}";
    }
}
