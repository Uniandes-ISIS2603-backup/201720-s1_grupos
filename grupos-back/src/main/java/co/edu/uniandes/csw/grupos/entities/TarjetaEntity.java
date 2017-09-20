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
 *
 * @author af.lopezf
 */
@Entity
public class TarjetaEntity implements Serializable{
    
    
    @Id
    private int numero;
    
    private double dineroDisponible;
    
    private String banco;
    
    private double maxCupo;

    /**
     * @return the numero
     */
    public int getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(int numero) {
        this.numero = numero;
    }

    /**
     * @return the dineroDisponible
     */
    public double getDineroDisponible() {
        return dineroDisponible;
    }

    /**
     * @param dineroDisponible the dineroDisponible to set
     */
    public void setDineroDisponible(double dineroDisponible) {
        this.dineroDisponible = dineroDisponible;
    }

    /**
     * @return the banco
     */
    public String getBanco() {
        return banco;
    }

    /**
     * @param banco the banco to set
     */
    public void setBanco(String banco) {
        this.banco = banco;
    }

    /**
     * @return the maxCupo
     */
    public double getMaxCupo() {
        return maxCupo;
    }

    /**
     * @param maxCupo the maxCupo to set
     */
    public void setMaxCupo(double maxCupo) {
        this.maxCupo = maxCupo;
    }
    
    
    
    
    
        
    
}
