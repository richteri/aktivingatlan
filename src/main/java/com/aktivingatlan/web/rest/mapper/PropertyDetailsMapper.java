package com.aktivingatlan.web.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aktivingatlan.domain.Category;
import com.aktivingatlan.domain.City;
import com.aktivingatlan.domain.Feature;
import com.aktivingatlan.domain.Property;
import com.aktivingatlan.domain.User;
import com.aktivingatlan.web.rest.dto.PropertyDTO;

/**
 * Mapper for the entity Property and its DTO PropertyDTO.
 */
@Mapper(componentModel = "spring", uses = { PhotoMapper.class, ApartmentMapper.class, OwnershipMapper.class, ContractMapper.class, StatementDetailsMapper.class })
public interface PropertyDetailsMapper {

    @Mapping(source = "category.id", target = "categoryId")
    @Mapping(source = "category.nameHu", target = "categoryNameHu")
    @Mapping(source = "city.id", target = "cityId")
    @Mapping(source = "city.name", target = "cityName")
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "user.login", target = "userLogin")
    PropertyDTO propertyToPropertyDTO(Property property);

    @Mapping(source = "categoryId", target = "category")
    @Mapping(target = "photos", ignore = false)
    @Mapping(target = "statements", ignore = true)
    @Mapping(target = "ownerships", ignore = true)
    @Mapping(source = "cityId", target = "city")
    @Mapping(target = "contracts", ignore = true)
    @Mapping(source = "userId", target = "user")
    @Mapping(target = "apartments", ignore = true)
    Property propertyDTOToProperty(PropertyDTO propertyDTO);

    default Category categoryFromId(Long id) {
        if (id == null) {
            return null;
        }
        Category category = new Category();
        category.setId(id);
        return category;
    }

    default Feature featureFromId(Long id) {
        if (id == null) {
            return null;
        }
        Feature feature = new Feature();
        feature.setId(id);
        return feature;
    }

    default City cityFromId(Long id) {
        if (id == null) {
            return null;
        }
        City city = new City();
        city.setId(id);
        return city;
    }

    default User userFromId(Long id) {
        if (id == null) {
            return null;
        }
        User user = new User();
        user.setId(id);
        return user;
    }

}
