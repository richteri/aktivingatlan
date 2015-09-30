package com.aktivingatlan.web.rest.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import com.aktivingatlan.domain.Feature;
import com.aktivingatlan.domain.Photo;

import java.util.Objects;


/**
 * A DTO for the Property entity.
 */
public class PropertyDTO implements Serializable {

    private Long id;

    private String code;

    private String descriptionHu;

    private String descriptionEn;

    private String descriptionDe;

    private Integer room;

    private Integer halfRoom;

    private Integer floorArea;

    private Integer parcelArea;

    private String pracelNumber;

    private String address1;

    private String address2;

    private Boolean active;

    private Integer kitchen;

    private Integer livingroom;

    private Integer floor;

    private Integer bathroom;

    private Integer toilet;

    private Boolean furnished;

    private Boolean forSale;

    private BigDecimal saleHuf;

    private BigDecimal saleEur;

    private Boolean forRent;

    private BigDecimal rentHuf;

    private BigDecimal rentEur;

    private BigDecimal rentPeakHuf;

    private BigDecimal rentPeakEur;

    private Boolean forMediumTerm;

    private BigDecimal mediumTermHuf;

    private BigDecimal mediumTermEur;

    private Boolean forLongTerm;

    private BigDecimal longTermHuf;

    private BigDecimal longTermEur;

    private Boolean featured;

    private Long categoryId;

    private String categoryNameHu;
    
    private Set<Feature> features = new HashSet<>();

    private Long cityId;

    private String cityName;

    private Long userId;

    private String userLogin;
    
    private Set<PhotoDTO> photos = new HashSet<>();
    
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryNameHu() {
        return categoryNameHu;
    }

    public void setCategoryNameHu(String categoryNameHu) {
        this.categoryNameHu = categoryNameHu;
    }

    public Set<Feature> getFeatures() {
        return features;
    }

    public void setFeatures(Set<Feature> features) {
        this.features = features;
    }

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }
    
	public Set<PhotoDTO> getPhotos() {
		return photos;
	}

	public void setPhotos(Set<PhotoDTO> photos) {
		this.photos = photos;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        PropertyDTO propertyDTO = (PropertyDTO) o;

        if ( ! Objects.equals(id, propertyDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "PropertyDTO{" +
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
