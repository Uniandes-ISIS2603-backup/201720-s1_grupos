/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.EventoEntity;
import java.util.Date;

/**
 * Representación minimum de evento.<br>
 * @author js.ramos14
 */
public class EventoDTO {
    /**
     * Id
     */
    private Long id;
    /**
     * Nombre
     */
    private String nombre;
    /**
     * Fecha de inicio
     */
    private Date fechaInicio;
   /**
    * Fecha de fin
    */
    private Date fechaFin;
    /**
     * Constructor vacío.<br>
     */
    public EventoDTO()
    {
        
    }
    /**
     * Construye un dto a partir de la entidad dada.<br>
     * @param entity Entidad dada.
     */
    public EventoDTO(EventoEntity entity)
    {
        this.id = entity.getId();
        this.nombre = entity.getNombre();
        this.fechaInicio = entity.getFechaInicio();
        this.fechaFin = entity.getFechaFin();
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
     * @return the fechaInicio
     */
    public Date getFechaInicio() {
        return fechaInicio;
    }

    /**
     * @param fechaInicio the fechaInicio to set
     */
    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * @return the fechaFin
     */
    public Date getFechaFin() {
        return fechaFin;
    }

    /**
     * @param fechaFin the fechaFin to set
     */
    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }
    /**
     * Transfiere el dto a una nueva entidad dada.<br>
     * @return  Entidad de evento.
     */
    public EventoEntity toEntity()
    {
        EventoEntity entity = new EventoEntity();
        entity.setId(this.id);
        entity.setNombre(this.nombre);
        entity.setFechaInicio(this.fechaInicio);
        entity.setFechaFin(this.fechaFin);
        return entity;
    }
}
