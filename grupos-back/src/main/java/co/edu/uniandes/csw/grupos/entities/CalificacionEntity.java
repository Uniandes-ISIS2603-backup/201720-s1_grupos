/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;

/**
 * Crea la entidad de la calificación.<br>
 * @author s.guzmanm
 */
@Entity
public class CalificacionEntity implements Serializable {
    /**
     * Valor de la calificación
     */
    private Double calificacion;
    /**
     * Identificación de la calificación
     */
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Fecha de la calificación
     */
    @Temporal(TemporalType.DATE)
    private Date fecha;
    /**
     * Calificador
     */
    @OneToOne 
    @PodamExclude
    private UsuarioEntity calificador;
    /**
     * Relación con el blog de la calificación
     */
    @ManyToOne 
    @PodamExclude
    private BlogEntity blog;
    /**
     * Obtiene el calificador.<br>
     * @return  calificador
     */
    public UsuarioEntity getCalificador() {
        return calificador;
    }
    /**
     * Modifica el calificador al valor dado por parámetro.<br>
     * @param calificador 
     */
    public void setCalificador(UsuarioEntity calificador) {
        this.calificador = calificador;
    }
    /**
     * Obtiene el blog al que pertence la calificación.<br>
     * @return blog
     */
    public BlogEntity getBlog() {
        return blog;
    }
    /**
     * Modifica el blog al valor dado por parámetro.<br>
     * @param blog 
     */
    public void setBlog(BlogEntity blog) {
        this.blog = blog;
    }
    /**
     * Obtiene el valor de la calificación.<br>
     * @return calificacion
     */

    public Double getCalificacion() {
        return calificacion;
    }
    

    /**
     * Modifica el valor de la calificación al dado por parámetro.<br>
     * @param calificacion 
     */
    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }
    /**
     * Obtiene la identifiación del blog.<br>
     * @return id
     */
    public Long getId() {
        return id;
    }
    /**
     * Modifica el id al dado por parámetro.<br>
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Obtiene la fecha de la calificación.<br>
     * @return fecha
     */
    public Date getFecha() {
        return fecha;
    }
    /**
     * Modifica la fecha de la calificación al valor dado por parámetro.<bR>
     * @param fecha 
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    
}
