package com.aktivingatlan.web.rest.dto;

import org.joda.time.LocalDate;

import com.aktivingatlan.domain.Photo;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Contract entity.
 */
public class ContractDTO implements Serializable {

    private Long id;

    private String idNo;

    private Boolean exclusive;

    private LocalDate startDate;

    private LocalDate endDate;

    private String note;

    private Long propertyId;

    private String propertyCode;
    
    private String propertyDescriptionHu;
    
    private Set<Photo> propertyPhotos = new HashSet<>();
    
    private Set<ClientDTO> clients = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Boolean getExclusive() {
        return exclusive;
    }

    public void setExclusive(Boolean exclusive) {
        this.exclusive = exclusive;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
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

	public Set<Photo> getPropertyPhotos() {
		return propertyPhotos;
	}

	public void setPropertyPhotos(Set<Photo> propertyPhotos) {
		this.propertyPhotos = propertyPhotos;
	}

	public Set<ClientDTO> getClients() {
        return clients;
    }

    public void setClients(Set<ClientDTO> clients) {
        this.clients = clients;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ContractDTO contractDTO = (ContractDTO) o;

        if ( ! Objects.equals(id, contractDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ContractDTO{" +
                "id=" + id +
                ", idNo='" + idNo + "'" +
                ", exclusive='" + exclusive + "'" +
                ", startDate='" + startDate + "'" +
                ", endDate='" + endDate + "'" +
                ", note='" + note + "'" +
                '}';
    }
}
