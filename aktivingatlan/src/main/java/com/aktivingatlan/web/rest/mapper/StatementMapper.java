package com.aktivingatlan.web.rest.mapper;

import org.mapstruct.Mapper;

import com.aktivingatlan.domain.Client;
import com.aktivingatlan.domain.Property;
import com.aktivingatlan.domain.Statement;
import com.aktivingatlan.web.rest.dto.StatementDTO;

/**
 * Mapper for the entity Statement and its DTO StatementDTO.
 */
@Mapper(componentModel = "spring", uses = {ClientMapper.class, PropertyMapper.class, })
public interface StatementMapper {

    StatementDTO statementToStatementDTO(Statement statement);

    Statement statementDTOToStatement(StatementDTO statementDTO);

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
