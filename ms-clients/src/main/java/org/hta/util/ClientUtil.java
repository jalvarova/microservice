package org.hta.util;

import org.hta.domain.entity.Client;
import org.hta.dto.ClientDTO;
import org.hta.exceptions.NotFoundException;

import java.util.Optional;

public final class ClientUtil {

    public static Client build(ClientDTO api, Client found) {
        found.setDocument(api.getDocument());
        found.setName(api.getName());
        found.setLastName(api.getLastName());
        found.setBusinessName(api.getBusinessName());
        //found.setBirdDate(api.getBirdDate());
        //found.setContact(api.getContact());
        found.setAddress(api.getAddress());
        return found;
    }

    public static void validateNotFound(Optional<Client> found) throws NotFoundException {
        if (found.isEmpty()) {
            throw new NotFoundException("Cliente no econtrado");
        }
    }
}
