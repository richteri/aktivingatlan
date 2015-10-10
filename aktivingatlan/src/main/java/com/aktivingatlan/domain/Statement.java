package com.aktivingatlan.domain;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.aktivingatlan.domain.util.CustomLocalDateSerializer;
import com.aktivingatlan.domain.util.ISO8601LocalDateDeserializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.LocalDate;
import org.springframework.data.elasticsearch.annotations.Document;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;


/**
 * A Statement.
 */
@Entity
@Table(name = "STATEMENT")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
@Document(indexName="statement")
public class Statement extends AbstractAuditingEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    
    
    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentLocalDate")
    @JsonSerialize(using = CustomLocalDateSerializer.class)
    @JsonDeserialize(using = ISO8601LocalDateDeserializer.class)
    @Column(name = "date")
    private LocalDate date;
    
    @Column(name = "note")
    private String note;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "STATEMENT_CLIENT",
               joinColumns = @JoinColumn(name="statements_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="clients_id", referencedColumnName="ID"))
    private Set<Client> clients = new HashSet<>();

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    @JoinTable(name = "STATEMENT_PROPERTY",
               joinColumns = @JoinColumn(name="statements_id", referencedColumnName="ID"),
               inverseJoinColumns = @JoinColumn(name="propertys_id", referencedColumnName="ID"))
    private Set<Property> propertys = new HashSet<>();

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

    public Set<Client> getClients() {
        return clients;
    }

    public void setClients(Set<Client> clients) {
        this.clients = clients;
    }

    public Set<Property> getPropertys() {
        return propertys;
    }

    public void setPropertys(Set<Property> propertys) {
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

        Statement statement = (Statement) o;

        if ( ! Objects.equals(id, statement.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Statement{" +
                "id=" + id +
                ", date='" + date + "'" +
                ", note='" + note + "'" +
                '}';
    }
}
