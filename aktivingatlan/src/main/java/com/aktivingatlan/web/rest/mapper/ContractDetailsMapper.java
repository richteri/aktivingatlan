package com.aktivingatlan.web.rest.mapper;

import com.aktivingatlan.domain.*;
import com.aktivingatlan.web.rest.dto.ContractDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Contract and its DTO ContractDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class, PhotoMapper.class })
public interface ContractDetailsMapper {

    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "property.code", target = "propertyCode")
    @Mapping(source = "property.descriptionHu", target = "propertyDescriptionHu")
    @Mapping(source = "property.city.id", target = "propertyCityId")
    @Mapping(source = "property.city.name", target = "propertyCityName")
    @Mapping(source = "property.photos", target = "propertyPhotos")
    ContractDTO contractToContractDTO(Contract contract);

    @Mapping(source = "propertyId", target = "property")
    Contract contractDTOToContract(ContractDTO contractDTO);

//    default Property propertyFromId(Long id) {
//        if (id == null) {
//            return null;
//        }
//        Property property = new Property();
//        property.setId(id);
//        return property;
//    }

    default Client clientFromId(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }
}
