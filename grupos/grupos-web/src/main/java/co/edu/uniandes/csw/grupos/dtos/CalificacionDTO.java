/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.CalificacionEntity;
import java.util.Date;

/**
 *
 * @author jc161
 */
public class CalificacionDTO {
    protected Double calificacion;
    
    protected Long id;
    
    protected Date fecha;
    
    public CalificacionDTO()
    {
        
    }
    
    public CalificacionDTO(CalificacionEntity e)
    {
        calificacion=e.getCalificacion();
        id=e.getId();
        fecha=e.getFecha();
    }
    public Double getCalificacion() {
        return calificacion;
    }
    

    public void setCalificacion(Double calificacion) {
        this.calificacion = calificacion;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }
    
    public CalificacionEntity toEntity()
    {
        CalificacionEntity e= new CalificacionEntity();
        e.setCalificacion(calificacion);
        e.setFecha(fecha);
        e.setId(id);
        return e;
    }
}
