/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author js.ramos14
 */
@Entity
public class LugarEntity implements Serializable{
    /**
     * Id del lugar.
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    /**
     * Dirección del lugar.
     */
    private String direccion;
    /**
     * Nombre del lugar.
     */
    private String nombre;
    /**
     * Capacidad
     */
    private Integer capacidad;
    /**
     * Disponibilidad del lugar
     */
    private boolean disponibilidad;

    /**
     * @return the direccion
     */
    public String getDireccion()
    {
        
        return direccion;
    }

    /**
     * @param direccion the direccion to set
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the capacidad
     */
    public Integer getCapacidad() {
        return capacidad;
    }

    /**
     * @param capacidad the capacidad to set
     */
    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }
    

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Override del equals.
     * @param o Objeto a igualar.
     * @return Si son iguales o no.
     */
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof LugarEntity))
        {
            return false;
        }
        LugarEntity u=(LugarEntity) o;
        return id.equals(u.getId());
    }
    /**
     * Override del hashcode.<br>
     * @return hashcode
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 61 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * @return the disponibilidad
     */
    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    /**
     * @param disponibilidad the disponibilidad to set
     */
    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }
}
