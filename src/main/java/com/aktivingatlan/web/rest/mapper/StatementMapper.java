package com.aktivingatlan.web.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aktivingatlan.domain.Statement;
import com.aktivingatlan.web.rest.dto.StatementDTO;

/**
 * Mapper for the entity Statement and its DTO StatementDTO.
 */
@Mapper(componentModel = "spring", uses = { })
public interface StatementMapper {

    @Mapping(target = "clients", ignore = true)
    @Mapping(target = "propertys", ignore = true)
    StatementDTO statementToStatementDTO(Statement statement);

    @Mapping(target = "clients", ignore = true)
    @Mapping(target = "propertys", ignore = true)
    Statement statementDTOToStatement(StatementDTO statementDTO);

}
