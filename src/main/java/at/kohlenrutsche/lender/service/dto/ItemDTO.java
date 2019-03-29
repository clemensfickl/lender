package at.kohlenrutsche.lender.service.dto;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the Item entity.
 */
public class ItemDTO implements Serializable {

    private Long id;

    @NotNull
    private String name;

    @Size(max = 4000)
    private String locationDetail;

    @Size(max = 4000)
    private String description;


    private Long ownerId;

    private String ownerLogin;

    private Long locationId;

    private String locationName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(Long userId) {
        this.ownerId = userId;
    }

    public String getOwnerLogin() {
        return ownerLogin;
    }

    public void setOwnerLogin(String userLogin) {
        this.ownerLogin = userLogin;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long roomId) {
        this.locationId = roomId;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String roomName) {
        this.locationName = roomName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ItemDTO itemDTO = (ItemDTO) o;
        if (itemDTO.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), itemDTO.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "ItemDTO{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", locationDetail='" + getLocationDetail() + "'" +
            ", description='" + getDescription() + "'" +
            ", owner=" + getOwnerId() +
            ", owner='" + getOwnerLogin() + "'" +
            ", location=" + getLocationId() +
            ", location='" + getLocationName() + "'" +
            "}";
    }
}
