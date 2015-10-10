package com.aktivingatlan.domain;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Apartment.
 */
@Entity
@Table(name = "APARTMENT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName="apartment")
public class Apartment extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Column(name = "bed")
    private Integer bed;
    
    @Column(name = "bathroom")
    private Boolean bathroom;
    
    @Column(name = "toilet")
    private Boolean toilet;
    
    @Column(name = "rent_huf", precision=10, scale=2)
    private BigDecimal rentHuf;
    
    @Column(name = "rent_eur", precision=10, scale=2)
    private BigDecimal rentEur;
    
    @Column(name = "rent_peak_huf", precision=10, scale=2)
    private BigDecimal rentPeakHuf;
    
    @Column(name = "rent_peak_eur", precision=10, scale=2)
    private BigDecimal rentPeakEur;
    
    @Column(name = "description_hu")
    private String descriptionHu;
    
    @Column(name = "description_en")
    private String descriptionEn;
    
    @Column(name = "description_de")
    private String descriptionDe;

    @ManyToOne
    private Property property;

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

        Apartment apartment = (Apartment) o;

        if ( ! Objects.equals(id, apartment.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Apartment{" +
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
