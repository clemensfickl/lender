package at.kohlenrutsche.lender.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;

/**
 * A DTO for the Lending entity.
 */
public class LendingDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime start;

    @NotNull
    private ZonedDateTime plannedEnd;

    private ZonedDateTime end;

    private Boolean informedAboutEnd;

    private BigDecimal cost;

    private Boolean paid;


    private Long borrowerId;

    private String borrowerLogin;

    private Long itemId;

    private String itemName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ZonedDateTime getStart() {
        return start;
    }

    public void setStart(ZonedDateTime start) {
        this.start = start;
    }

    public ZonedDateTime getPlannedEnd() {
        return plannedEnd;
    }

    public void setPlannedEnd(ZonedDateTime plannedEnd) {
        this.plannedEnd = plannedEnd;
    }

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
    }

    public Boolean isInformedAboutEnd() {
        return informedAboutEnd;
    }

    public void setInformedAboutEnd(Boolean informedAboutEnd) {
        this.informedAboutEnd = informedAboutEnd;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Boolean isPaid() {
        return paid;
    }

    public void setPaid(Boolean paid) {
        this.paid = paid;
    }

    public Long getBorrowerId() {
        return borrowerId;
    }

    public void setBorrowerId(Long userId) {
        this.borrowerId = userId;
    }

    public String getBorrowerLogin() {
        return borrowerLogin;
    }

    public void setBorrowerLogin(String userLogin) {
        this.borrowerLogin = userLogin;
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

        LendingDTO lendingDTO = (LendingDTO) o;
        if (lendingDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), lendingDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "LendingDTO{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", plannedEnd='" + getPlannedEnd() + "'" +
            ", end='" + getEnd() + "'" +
            ", informedAboutEnd='" + isInformedAboutEnd() + "'" +
            ", cost=" + getCost() +
            ", paid='" + isPaid() + "'" +
            ", borrower=" + getBorrowerId() +
            ", borrower='" + getBorrowerLogin() + "'" +
            ", item=" + getItemId() +
            ", item='" + getItemName() + "'" +
            "}";
    }
}
