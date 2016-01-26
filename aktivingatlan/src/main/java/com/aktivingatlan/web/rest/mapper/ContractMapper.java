package com.aktivingatlan.web.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aktivingatlan.domain.Client;
import com.aktivingatlan.domain.Contract;
import com.aktivingatlan.domain.Property;
import com.aktivingatlan.web.rest.dto.ContractDTO;

/**
 * Mapper for the entity Contract and its DTO ContractDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class })
public interface ContractMapper {

    @Mapping(source = "property.id", target = "propertyId")
    @Mapping(source = "property.code", target = "propertyCode")
    @Mapping(source = "property.descriptionHu", target = "propertyDescriptionHu")
    @Mapping(source = "property.city.id", target = "propertyCityId")
    @Mapping(source = "property.city.name", target = "propertyCityName")
    ContractDTO contractToContractDTO(Contract contract);

    @Mapping(source = "propertyId", target = "property")
    Contract contractDTOToContract(ContractDTO contractDTO);

    default Client clientFromId(Long id) {
        if (id == null) {
            return null;
        }
        Client client = new Client();
        client.setId(id);
        return client;
    }
    
    default Property propertyFromId(Long id) {
        if (id == null) {
            return null;
        }
        Property property = new Property();
        property.setId(id);
        return property;
    }
    
}
