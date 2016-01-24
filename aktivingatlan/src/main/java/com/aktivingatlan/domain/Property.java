package com.aktivingatlan.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Property.
 */
@Entity
@Table(name = "property")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Property extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "code", unique = true)
    private String code;

    @Column(name = "description_hu")
    private String descriptionHu;

    @Column(name = "description_en")
    private String descriptionEn;

    @Column(name = "description_de")
    private String descriptionDe;

    @Column(name = "room")
    private Integer room;

    @Column(name = "half_room")
    private Integer halfRoom;

    @Column(name = "floor_area")
    private Integer floorArea;

    @Column(name = "parcel_area")
    private Integer parcelArea;

    @Column(name = "pracel_number")
    private String pracelNumber;

    @Column(name = "address1")
    private String address1;

    @Column(name = "address2")
    private String address2;

    @Column(name = "active")
    private Boolean active;

    @Column(name = "kitchen")
    private Integer kitchen;

    @Column(name = "livingroom")
    private Integer livingroom;

    @Column(name = "floor")
    private Integer floor;

    @Column(name = "bathroom")
    private Integer bathroom;

    @Column(name = "toilet")
    private Integer toilet;

    @Column(name = "furnished")
    private Boolean furnished;

    @Column(name = "for_sale")
    private Boolean forSale;

    @Column(name = "sale_huf", precision=10, scale=2)
    private BigDecimal saleHuf;

    @Column(name = "sale_eur", precision=10, scale=2)
    private BigDecimal saleEur;

    @Column(name = "for_rent")
    private Boolean forRent;

    @Column(name = "rent_huf", precision=10, scale=2)
    private BigDecimal rentHuf;

    @Column(name = "rent_eur", precision=10, scale=2)
    private BigDecimal rentEur;

    @Column(name = "rent_peak_huf", precision=10, scale=2)
    private BigDecimal rentPeakHuf;

    @Column(name = "rent_peak_eur", precision=10, scale=2)
    private BigDecimal rentPeakEur;

    @Column(name = "for_medium_term")
    private Boolean forMediumTerm;

    @Column(name = "medium_term_huf", precision=10, scale=2)
    private BigDecimal mediumTermHuf;

    @Column(name = "medium_term_eur", precision=10, scale=2)
    private BigDecimal mediumTermEur;

    @Column(name = "for_long_term")
    private Boolean forLongTerm;

    @Column(name = "long_term_huf", precision=10, scale=2)
    private BigDecimal longTermHuf;

    @Column(name = "long_term_eur", precision=10, scale=2)
    private BigDecimal longTermEur;

    @Column(name = "featured")
    private Boolean featured;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "property")
    // TODO FIX cache eviction 
    // @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Photo> photos = new HashSet<>();

    @ManyToMany(mappedBy = "propertys")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Statement> statements = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "property_feature",
               joinColumns = @JoinColumn(name="propertys_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="features_id", referencedColumnName="ID"))
    private Set<Feature> features = new HashSet<>();

    @OneToMany(mappedBy = "property")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Ownership> ownerships = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    @OneToMany(mappedBy = "property")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Contract> contracts = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "property")
    // TODO FIX cache eviction 
    // @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Apartment> apartments = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Integer getRoom() {
        return room;
    }

    public void setRoom(Integer room) {
        this.room = room;
    }

    public Integer getHalfRoom() {
        return halfRoom;
    }

    public void setHalfRoom(Integer halfRoom) {
        this.halfRoom = halfRoom;
    }

    public Integer getFloorArea() {
        return floorArea;
    }

    public void setFloorArea(Integer floorArea) {
        this.floorArea = floorArea;
    }

    public Integer getParcelArea() {
        return parcelArea;
    }

    public void setParcelArea(Integer parcelArea) {
        this.parcelArea = parcelArea;
    }

    public String getPracelNumber() {
        return pracelNumber;
    }

    public void setPracelNumber(String pracelNumber) {
        this.pracelNumber = pracelNumber;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Integer getKitchen() {
        return kitchen;
    }

    public void setKitchen(Integer kitchen) {
        this.kitchen = kitchen;
    }

    public Integer getLivingroom() {
        return livingroom;
    }

    public void setLivingroom(Integer livingroom) {
        this.livingroom = livingroom;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public Integer getBathroom() {
        return bathroom;
    }

    public void setBathroom(Integer bathroom) {
        this.bathroom = bathroom;
    }

    public Integer getToilet() {
        return toilet;
    }

    public void setToilet(Integer toilet) {
        this.toilet = toilet;
    }

    public Boolean getFurnished() {
        return furnished;
    }

    public void setFurnished(Boolean furnished) {
        this.furnished = furnished;
    }

    public Boolean getForSale() {
        return forSale;
    }

    public void setForSale(Boolean forSale) {
        this.forSale = forSale;
    }

    public BigDecimal getSaleHuf() {
        return saleHuf;
    }

    public void setSaleHuf(BigDecimal saleHuf) {
        this.saleHuf = saleHuf;
    }

    public BigDecimal getSaleEur() {
        return saleEur;
    }

    public void setSaleEur(BigDecimal saleEur) {
        this.saleEur = saleEur;
    }

    public Boolean getForRent() {
        return forRent;
    }

    public void setForRent(Boolean forRent) {
        this.forRent = forRent;
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

    public Boolean getForMediumTerm() {
        return forMediumTerm;
    }

    public void setForMediumTerm(Boolean forMediumTerm) {
        this.forMediumTerm = forMediumTerm;
    }

    public BigDecimal getMediumTermHuf() {
        return mediumTermHuf;
    }

    public void setMediumTermHuf(BigDecimal mediumTermHuf) {
        this.mediumTermHuf = mediumTermHuf;
    }

    public BigDecimal getMediumTermEur() {
        return mediumTermEur;
    }

    public void setMediumTermEur(BigDecimal mediumTermEur) {
        this.mediumTermEur = mediumTermEur;
    }

    public Boolean getForLongTerm() {
        return forLongTerm;
    }

    public void setForLongTerm(Boolean forLongTerm) {
        this.forLongTerm = forLongTerm;
    }

    public BigDecimal getLongTermHuf() {
        return longTermHuf;
    }

    public void setLongTermHuf(BigDecimal longTermHuf) {
        this.longTermHuf = longTermHuf;
    }

    public BigDecimal getLongTermEur() {
        return longTermEur;
    }

    public void setLongTermEur(BigDecimal longTermEur) {
        this.longTermEur = longTermEur;
    }

    public Boolean getFeatured() {
        return featured;
    }

    public void setFeatured(Boolean featured) {
        this.featured = featured;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public Set<Statement> getStatements() {
        return statements;
    }

    public void setStatements(Set<Statement> statements) {
        this.statements = statements;
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }

    public Set<Ownership> getOwnerships() {
        return ownerships;
    }

    public void setOwnerships(Set<Ownership> ownerships) {
        this.ownerships = ownerships;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Set<Contract> getContracts() {
        return contracts;
    }

    public void setContracts(Set<Contract> contracts) {
        this.contracts = contracts;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Set<Apartment> getApartments() {
        return apartments;
    }

    public void setApartments(Set<Apartment> apartments) {
        this.apartments = apartments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Property property = (Property) o;
        return Objects.equals(id, property.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Property{" +
            "id=" + id +
            ", code='" + code + "'" +
            ", descriptionHu='" + descriptionHu + "'" +
            ", descriptionEn='" + descriptionEn + "'" +
            ", descriptionDe='" + descriptionDe + "'" +
            ", room='" + room + "'" +
            ", halfRoom='" + halfRoom + "'" +
            ", floorArea='" + floorArea + "'" +
            ", parcelArea='" + parcelArea + "'" +
            ", pracelNumber='" + pracelNumber + "'" +
            ", address1='" + address1 + "'" +
            ", address2='" + address2 + "'" +
            ", active='" + active + "'" +
            ", kitchen='" + kitchen + "'" +
            ", livingroom='" + livingroom + "'" +
            ", floor='" + floor + "'" +
            ", bathroom='" + bathroom + "'" +
            ", toilet='" + toilet + "'" +
            ", furnished='" + furnished + "'" +
            ", forSale='" + forSale + "'" +
            ", saleHuf='" + saleHuf + "'" +
            ", saleEur='" + saleEur + "'" +
            ", forRent='" + forRent + "'" +
            ", rentHuf='" + rentHuf + "'" +
            ", rentEur='" + rentEur + "'" +
            ", rentPeakHuf='" + rentPeakHuf + "'" +
            ", rentPeakEur='" + rentPeakEur + "'" +
            ", forMediumTerm='" + forMediumTerm + "'" +
            ", mediumTermHuf='" + mediumTermHuf + "'" +
            ", mediumTermEur='" + mediumTermEur + "'" +
            ", forLongTerm='" + forLongTerm + "'" +
            ", longTermHuf='" + longTermHuf + "'" +
            ", longTermEur='" + longTermEur + "'" +
            ", featured='" + featured + "'" +
            '}';
    }
}
