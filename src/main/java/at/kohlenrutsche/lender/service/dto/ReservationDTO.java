package at.kohlenrutsche.lender.service.dto;
import java.time.ZonedDateTime;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Reservation entity.
 */
public class ReservationDTO implements Serializable {

    private Long id;

    @NotNull
    private ZonedDateTime start;

    private ZonedDateTime end;


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

    public ZonedDateTime getEnd() {
        return end;
    }

    public void setEnd(ZonedDateTime end) {
        this.end = end;
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

        ReservationDTO reservationDTO = (ReservationDTO) o;
        if (reservationDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), reservationDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ReservationDTO{" +
            "id=" + getId() +
            ", start='" + getStart() + "'" +
            ", end='" + getEnd() + "'" +
            ", borrower=" + getBorrowerId() +
            ", borrower='" + getBorrowerLogin() + "'" +
            ", item=" + getItemId() +
            ", item='" + getItemName() + "'" +
            "}";
    }
}
