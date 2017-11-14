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
import javax.persistence.ManyToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *Entidad de patrocinio
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
    
    @PodamExclude
    @ManyToOne
    private EventoEntity evento;
    
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
     * Da el evento al que patrocinio se hizo
     * @return evento
     */
    public EventoEntity getEvento(){
        return evento;
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
    
    /**
     * Cambia el evento al que pertence el patrocinio
     * @param nEvent
     */
    public void setEvento(EventoEntity nEvent){
        evento= nEvent;
    }
    
    /**
     * Override del equals.<br>
     * @param o Objeto a igualar.<br>
     * @return Si son iguales o no.
     */
    @Override
    public boolean equals(Object o)
    {
        if (!(o instanceof PatrocinioEntity))
            return false;
        PatrocinioEntity u=(PatrocinioEntity) o;
        return id.equals(u.getId());
    }
    /**
     * Override del hashcode.<br>
     * @return hashcode.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }
}
