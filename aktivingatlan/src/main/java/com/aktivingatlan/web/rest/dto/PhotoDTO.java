package com.aktivingatlan.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.aktivingatlan.domain.AbstractAuditingEntity;

import java.util.Objects;


/**
 * A DTO for the Photo entity.
 */
public class PhotoDTO extends AbstractAuditingEntity implements Serializable {

    private Long id;

    private Boolean header;

    private String descriptionHu;

    private String descriptionEn;

    private String descriptionDe;

    private String filename;

    private Long propertyId;

    private String propertyCode;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getHeader() {
        return header;
    }

    public void setHeader(Boolean header) {
        this.header = header;
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

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
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

        PhotoDTO photoDTO = (PhotoDTO) o;

        if ( ! Objects.equals(id, photoDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PhotoDTO{" +
                "id=" + id +
                ", header='" + header + "'" +
                ", descriptionHu='" + descriptionHu + "'" +
                ", descriptionEn='" + descriptionEn + "'" +
                ", descriptionDe='" + descriptionDe + "'" +
                ", filename='" + filename + "'" +
                '}';
    }
}
