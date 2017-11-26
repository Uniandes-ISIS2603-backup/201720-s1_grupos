/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Entidad de empresa.<br>
 * @author af.lopezf
 */
@Entity
public class EmpresaEntity implements Serializable {
    /**
     * Nit
     */
    @Id
    private int nit;
    /**
     * Nombre de la empresa
     */
    private String nombre;
    /**
     * Logo de la empresa.
     */
    private String logo;

    /**
     * @return the nit
     */
    public int getNit() {
        return nit;
    }

    /**
     * @param nit the nit to set
     */
    public void setNit(int nit) {
        this.nit = nit;
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
     * @return the logo
     */
    public String getLogo() {
        return logo;
    }

    /**
     * @param logo the logo to set
     */
    public void setLogo(String logo) {
        this.logo = logo;
    }
    /**
     * Override del equals.<br>
     * @param o Objeto a comparar.<br>
     * @return si son iguales.
     */
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof EmpresaEntity))
        {
            return false;
        }
        EmpresaEntity u=(EmpresaEntity) o;
        return nit==(u.getNit());
    }
    /**
     * Override del hashcode.<br>
     * @return hashcode.
     */
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + this.nit;
        return hash;
    }
    
    
    
    
}
