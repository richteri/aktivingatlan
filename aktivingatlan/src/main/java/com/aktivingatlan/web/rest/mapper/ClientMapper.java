package com.aktivingatlan.web.rest.mapper;

import com.aktivingatlan.domain.*;
import com.aktivingatlan.web.rest.dto.ClientDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Client and its DTO ClientDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ClientMapper {

    ClientDTO clientToClientDTO(Client client);

    @Mapping(target = "ownerships", ignore = true)
    @Mapping(target = "statements", ignore = true)
    @Mapping(target = "contracts", ignore = true)
    Client clientDTOToClient(ClientDTO clientDTO);
}
