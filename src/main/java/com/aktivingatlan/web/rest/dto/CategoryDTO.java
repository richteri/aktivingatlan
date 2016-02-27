package com.aktivingatlan.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Category entity.
 */
public class CategoryDTO implements Serializable {

    private Long id;

    private String nameHu;


    private String nameEn;


    private String nameDe;


    private Long parentId;

    private String parentNameHu;
    
    private String parentNameDe;
    
    private String parentNameEn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getNameHu() {
        return nameHu;
    }

    public void setNameHu(String nameHu) {
        this.nameHu = nameHu;
    }
    public String getNameEn() {
        return nameEn;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }
    public String getNameDe() {
        return nameDe;
    }

    public void setNameDe(String nameDe) {
        this.nameDe = nameDe;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long categoryId) {
        this.parentId = categoryId;
    }

    public String getParentNameHu() {
        return parentNameHu;
    }

    public void setParentNameHu(String categoryNameHu) {
        this.parentNameHu = categoryNameHu;
    }

    public String getParentNameDe() {
		return parentNameDe;
	}

	public void setParentNameDe(String parentNameDe) {
		this.parentNameDe = parentNameDe;
	}

	public String getParentNameEn() {
		return parentNameEn;
	}

	public void setParentNameEn(String parentNameEn) {
		this.parentNameEn = parentNameEn;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CategoryDTO categoryDTO = (CategoryDTO) o;

        if ( ! Objects.equals(id, categoryDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "CategoryDTO{" +
            "id=" + id +
            ", nameHu='" + nameHu + "'" +
            ", nameEn='" + nameEn + "'" +
            ", nameDe='" + nameDe + "'" +
            '}';
    }
}
