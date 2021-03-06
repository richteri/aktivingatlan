package com.aktivingatlan.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.aktivingatlan.domain.AbstractAuditingEntity;


/**
 * A DTO for the Ownership entity.
 */
public class OwnershipDTO extends AbstractAuditingEntity implements Serializable {

    private Long id;

    private String note;

    private Long propertyId;

    private String propertyCode;
    
    private String propertyDescriptionHu;
    
    private Long propertyCityId;

    private String propertyCityName;
    
    private Set<PhotoDTO> propertyPhotos = new HashSet<>();
    
    private Long clientId;

    private String clientName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    public String getPropertyCode() {
        return propertyCode;
    }

    public void setPropertyCode(String propertyCode) {
        this.propertyCode = propertyCode;
    }

    public String getPropertyDescriptionHu() {
		return propertyDescriptionHu;
	}

	public void setPropertyDescriptionHu(String propertyDescriptionHu) {
		this.propertyDescriptionHu = propertyDescriptionHu;
	}

	public Long getPropertyCityId() {
		return propertyCityId;
	}

	public void setPropertyCityId(Long propertyCityId) {
		this.propertyCityId = propertyCityId;
	}

	public String getPropertyCityName() {
		return propertyCityName;
	}

	public void setPropertyCityName(String propertyCityName) {
		this.propertyCityName = propertyCityName;
	}

	public Set<PhotoDTO> getPropertyPhotos() {
		return propertyPhotos;
	}

	public void setPropertyPhotos(Set<PhotoDTO> propertyPhotos) {
		this.propertyPhotos = propertyPhotos;
	}

	public Long getClientId() {
        return clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OwnershipDTO ownershipDTO = (OwnershipDTO) o;

        if ( ! Objects.equals(id, ownershipDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OwnershipDTO{" +
                "id=" + id +
                ", note='" + note + "'" +
                '}';
    }
}
