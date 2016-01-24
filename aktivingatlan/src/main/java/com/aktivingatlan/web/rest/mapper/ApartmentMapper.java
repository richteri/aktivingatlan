package com.aktivingatlan.web.rest.mapper;

import com.aktivingatlan.domain.*;
import com.aktivingatlan.web.rest.dto.ApartmentDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Apartment and its DTO ApartmentDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ApartmentMapper {

    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "property.code", target = "propertyCode")
    ApartmentDTO apartmentToApartmentDTO(Apartment apartment);

    @Mapping(source = "propertyId", target = "property")
    Apartment apartmentDTOToApartment(ApartmentDTO apartmentDTO);

    default Property propertyFromId(Long id) {
        if (id == null) {
            return null;
        }
        Property property = new Property();
        property.setId(id);
        return property;
    }
}
