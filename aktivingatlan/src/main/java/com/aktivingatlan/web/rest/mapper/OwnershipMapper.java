package com.aktivingatlan.web.rest.mapper;

import com.aktivingatlan.domain.*;
import com.aktivingatlan.web.rest.dto.OwnershipDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Ownership and its DTO OwnershipDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface OwnershipMapper {

    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "property.code", target = "propertyCode")
    @Mapping(source = "client.id", target = "clientId")
    @Mapping(source = "client.name", target = "clientName")
    OwnershipDTO ownershipToOwnershipDTO(Ownership ownership);

    @Mapping(source = "propertyId", target = "property")
    @Mapping(source = "clientId", target = "client")
    Ownership ownershipDTOToOwnership(OwnershipDTO ownershipDTO);

    default Property propertyFromId(Long id) {
        if (id == null) {
            return null;
        }
        Property property = new Property();
        property.setId(id);
        return property;
    }

    default Client clientFromId(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }
}
