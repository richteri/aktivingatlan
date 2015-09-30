package com.aktivingatlan.web.rest.mapper;

import com.aktivingatlan.domain.*;
import com.aktivingatlan.web.rest.dto.StatementDTO;

import org.mapstruct.*;

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
