package com.aktivingatlan.web.rest.dto;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


import com.aktivingatlan.domain.AbstractAuditingEntity;


/**
 * A DTO for the Statement entity.
 */
public class StatementDTO extends AbstractAuditingEntity implements Serializable {

    private Long id;

    private LocalDate date;

    private String note;
    
    private Set<ClientDTO> clients = new HashSet<>();
    
    private Set<PropertyDTO> propertys = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Set<ClientDTO> getClients() {
        return clients;
    }

    public void setClients(Set<ClientDTO> clients) {
        this.clients = clients;
    }

    public Set<PropertyDTO> getPropertys() {
        return propertys;
    }

    public void setPropertys(Set<PropertyDTO> propertys) {
        this.propertys = propertys;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        StatementDTO statementDTO = (StatementDTO) o;

        if ( ! Objects.equals(id, statementDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "StatementDTO{" +
                "id=" + id +
                ", date='" + date + "'" +
                ", note='" + note + "'" +
                '}';
    }
}
