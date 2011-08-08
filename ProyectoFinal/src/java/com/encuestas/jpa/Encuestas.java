/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.encuestas.jpa;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Viper
 */
@Entity
@Table(name = "ENCUESTAS", catalog = "", schema = "APP")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Encuestas.findAll", query = "SELECT e FROM Encuestas e"),
    @NamedQuery(name = "Encuestas.findByNombre", query = "SELECT e FROM Encuestas e WHERE e.nombre = :nombre"),
    @NamedQuery(name = "Encuestas.findByTelefono", query = "SELECT e FROM Encuestas e WHERE e.telefono = :telefono"),
    @NamedQuery(name = "Encuestas.findByDireccion", query = "SELECT e FROM Encuestas e WHERE e.direccion = :direccion"),
    @NamedQuery(name = "Encuestas.findByIntra", query = "SELECT e FROM Encuestas e WHERE e.intra = :intra"),
    @NamedQuery(name = "Encuestas.findByAcceso", query = "SELECT e FROM Encuestas e WHERE e.acceso = :acceso"),
    @NamedQuery(name = "Encuestas.findById", query = "SELECT e FROM Encuestas e WHERE e.id = :id")})
public class Encuestas implements Serializable {
    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(name = "NOMBRE")
    private String nombre;
    @Size(max = 20)
    @Column(name = "TELEFONO")
    private String telefono;
    @Size(max = 255)
    @Column(name = "DIRECCION")
    private String direccion;
    @Column(name = "INTRA")
    private Short intra;
    @Column(name = "ACCESO")
    private Short acceso;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "ID")
    private Long id;

    public Encuestas() {
    }

    public Encuestas(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public Short getIntra() {
        return intra;
    }

    public void setIntra(Short intra) {
        this.intra = intra;
    }

    public Short getAcceso() {
        return acceso;
    }

    public void setAcceso(Short acceso) {
        this.acceso = acceso;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Encuestas)) {
            return false;
        }
        Encuestas other = (Encuestas) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.encuestas.jpa.Encuestas[ id=" + id + " ]";
    }
    
}
