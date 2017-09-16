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
 * @author tefa
 */
@Entity
public class PatrocinioEntity implements Serializable{
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private double pago;
    
    public Long getId(){
        return id;
    }
    
    public double getPago(){
        return pago;
    }
    
    public void setId(Long pId){
        id= pId;
    }
    
    public void setPago(double nPago){
        pago= nPago;
    }

}