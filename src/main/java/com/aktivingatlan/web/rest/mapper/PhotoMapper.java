package com.aktivingatlan.web.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aktivingatlan.domain.Photo;
import com.aktivingatlan.domain.Property;
import com.aktivingatlan.web.rest.dto.PhotoDTO;

/**
 * Mapper for the entity Photo and its DTO PhotoDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface PhotoMapper {

    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "property.code", target = "propertyCode")
    PhotoDTO photoToPhotoDTO(Photo photo);

    @Mapping(source = "propertyId", target = "property")
    Photo photoDTOToPhoto(PhotoDTO photoDTO);

    default Property propertyFromId(Long id) {
        if (id == null) {
            return null;
        }
        Property property = new Property();
        property.setId(id);
        return property;
    }
}
