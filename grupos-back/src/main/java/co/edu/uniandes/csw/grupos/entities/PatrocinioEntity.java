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
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author tefa
 */
@Entity
public class PatrocinioEntity implements Serializable{
    
    /**
     * Id del patrocinio
     */
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    /**
     * Cantidad del patrocinio
     */
    private double pago;
    
    /**
     * Usuario que realizo el patrocinio
     */
    @PodamExclude
    @ManyToOne
    private UsuarioEntity usuario;
    
    /**
     * Da el id del patrocinio
     * @return id
     */
    public Long getId(){
        return id;
    }
    
    /**
     * Da el id del pago
     * @return pago
     */
    public double getPago(){
        return pago;
    }
    
    /**
     * Da el usuario que realizo el patrocinio
     * @return usuario
     */
    public UsuarioEntity getUsuario(){
        return usuario;
    }
    
    /**
     * Cambia el id del patrocinio
     * @param pId 
     */
    public void setId(Long pId){
        id= pId;
    }
    
    /**
     * Cambia el valor del pago del patrocinio
     * @param nPago 
     */
    public void setPago(double nPago){
        pago= nPago;
    }
    
    /**
     * Cambia el usuario que realiza el patrocinio
     * @param nuser 
     */
    public void setUsuario(UsuarioEntity nuser){
        usuario= nuser;
    }

}
