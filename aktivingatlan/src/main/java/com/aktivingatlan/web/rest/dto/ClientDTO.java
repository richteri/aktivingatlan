package com.aktivingatlan.web.rest.dto;

import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;


/**
 * A DTO for the Client entity.
 */
public class ClientDTO implements Serializable {

    private Long id;

    private String name;

    private String email;

    private String phone1;

    private String phone2;

    private String address1;

    private String address2;

    private String idNo;

    private String note;
    
    private Set<OwnershipDTO> ownerships;
    
    private Set<ContractDTO> contracts;
    
    private Set<StatementDTO> statements;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone1() {
        return phone1;
    }

    public void setPhone1(String phone1) {
        this.phone1 = phone1;
    }

    public String getPhone2() {
        return phone2;
    }

    public void setPhone2(String phone2) {
        this.phone2 = phone2;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

	public Set<OwnershipDTO> getOwnerships() {
		return ownerships;
	}

	public void setOwnerships(Set<OwnershipDTO> ownerships) {
		this.ownerships = ownerships;
	}

	public Set<ContractDTO> getContracts() {
		return contracts;
	}

	public void setContracts(Set<ContractDTO> contracts) {
		this.contracts = contracts;
	}

	public Set<StatementDTO> getStatements() {
		return statements;
	}

	public void setStatements(Set<StatementDTO> statements) {
		this.statements = statements;
	}

	@Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ClientDTO clientDTO = (ClientDTO) o;

        if ( ! Objects.equals(id, clientDTO.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "id=" + id +
                ", name='" + name + "'" +
                ", email='" + email + "'" +
                ", phone1='" + phone1 + "'" +
                ", phone2='" + phone2 + "'" +
                ", address1='" + address1 + "'" +
                ", address2='" + address2 + "'" +
                ", idNo='" + idNo + "'" +
                ", note='" + note + "'" +
                '}';
    }
}
