package com.aktivingatlan.web.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.aktivingatlan.domain.Client;
import com.aktivingatlan.web.rest.dto.ClientDTO;

/**
 * Mapper for the entity Client and its DTO ClientDTO.
 */
@Mapper(componentModel = "spring", uses = {OwnershipMapper.class, StatementMapper.class, ContractMapper.class})
public interface ClientDetailsMapper {

    ClientDTO clientToClientDTO(Client client);

    @Mapping(target = "ownerships", ignore = true)
    @Mapping(target = "statements", ignore = true)
    @Mapping(target = "contracts", ignore = true)
    Client clientDTOToClient(ClientDTO clientDTO);
}
