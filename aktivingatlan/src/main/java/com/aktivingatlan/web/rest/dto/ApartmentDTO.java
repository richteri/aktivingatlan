package com.aktivingatlan.web.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Apartment entity.
 */
public class ApartmentDTO implements Serializable {

    private Long id;

    private Integer bed;


    private Boolean bathroom;


    private Boolean toilet;


    private BigDecimal rentHuf;


    private BigDecimal rentEur;


    private BigDecimal rentPeakHuf;


    private BigDecimal rentPeakEur;


    private String descriptionHu;


    private String descriptionEn;


    private String descriptionDe;


    private Long propertyId;

    private String propertyCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Integer getBed() {
        return bed;
    }

    public void setBed(Integer bed) {
        this.bed = bed;
    }
    public Boolean getBathroom() {
        return bathroom;
    }

    public void setBathroom(Boolean bathroom) {
        this.bathroom = bathroom;
    }
    public Boolean getToilet() {
        return toilet;
    }

    public void setToilet(Boolean toilet) {
        this.toilet = toilet;
    }
    public BigDecimal getRentHuf() {
        return rentHuf;
    }

    public void setRentHuf(BigDecimal rentHuf) {
        this.rentHuf = rentHuf;
    }
    public BigDecimal getRentEur() {
        return rentEur;
    }

    public void setRentEur(BigDecimal rentEur) {
        this.rentEur = rentEur;
    }
    public BigDecimal getRentPeakHuf() {
        return rentPeakHuf;
    }

    public void setRentPeakHuf(BigDecimal rentPeakHuf) {
        this.rentPeakHuf = rentPeakHuf;
    }
    public BigDecimal getRentPeakEur() {
        return rentPeakEur;
    }

    public void setRentPeakEur(BigDecimal rentPeakEur) {
        this.rentPeakEur = rentPeakEur;
    }
    public String getDescriptionHu() {
        return descriptionHu;
    }

    public void setDescriptionHu(String descriptionHu) {
        this.descriptionHu = descriptionHu;
    }
    public String getDescriptionEn() {
        return descriptionEn;
    }

    public void setDescriptionEn(String descriptionEn) {
        this.descriptionEn = descriptionEn;
    }
    public String getDescriptionDe() {
        return descriptionDe;
    }

    public void setDescriptionDe(String descriptionDe) {
        this.descriptionDe = descriptionDe;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ApartmentDTO apartmentDTO = (ApartmentDTO) o;

        if ( ! Objects.equals(id, apartmentDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ApartmentDTO{" +
            "id=" + id +
            ", bed='" + bed + "'" +
            ", bathroom='" + bathroom + "'" +
            ", toilet='" + toilet + "'" +
            ", rentHuf='" + rentHuf + "'" +
            ", rentEur='" + rentEur + "'" +
            ", rentPeakHuf='" + rentPeakHuf + "'" +
            ", rentPeakEur='" + rentPeakEur + "'" +
            ", descriptionHu='" + descriptionHu + "'" +
            ", descriptionEn='" + descriptionEn + "'" +
            ", descriptionDe='" + descriptionDe + "'" +
            '}';
    }
}
