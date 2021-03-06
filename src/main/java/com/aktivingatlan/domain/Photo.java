package com.aktivingatlan.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Photo.
 */
@Entity
@Table(name = "photo")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Photo extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "header")
    private Boolean header;

    @Column(name = "description_hu")
    private String descriptionHu;

    @Column(name = "description_en")
    private String descriptionEn;

    @Column(name = "description_de")
    private String descriptionDe;

    @Column(name = "filename")
    private String filename;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

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

    public Property getProperty() {
        return property;
    }

    public void setProperty(Property property) {
        this.property = property;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Photo photo = (Photo) o;
        return Objects.equals(id, photo.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Photo{" +
            "id=" + id +
            ", header='" + header + "'" +
            ", descriptionHu='" + descriptionHu + "'" +
            ", descriptionEn='" + descriptionEn + "'" +
            ", descriptionDe='" + descriptionDe + "'" +
            ", filename='" + filename + "'" +
            '}';
    }
}
