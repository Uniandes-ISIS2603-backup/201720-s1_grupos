/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;

import java.io.Serializable;
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
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private String direccion;
    
    private String nombre;
    
    private Integer capacidad;

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
    
    /*@Override
    public boolean equals(Object obj) {
        if (this.getDireccion()!= null && ((LugarEntity) obj).getDireccion()!= null) {
            return this.getDireccion().equals(((LugarEntity) obj).getDireccion());
        }
        return super.equals(obj);
    }

    
    /**
     * 
     * @return el hashcode del nombre que define la entidad
     */
   /* @Override
    public int hashCode() {
        if (this.getDireccion() != null) {
            return this.getDireccion().hashCode();
        }
        return super.hashCode();
    }*/

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
}
