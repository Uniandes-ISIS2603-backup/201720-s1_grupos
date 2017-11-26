/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.CalificacionEntity;
import java.util.Date;

/**
 * Calificación en DTO
 * @author jc161
 */
public class CalificacionDTO {
    /**
     * Calificación
     */
    private Double calificacion;
    /**
     * Id del objeto
     */
    private Long id;
    /**
     * Fecha del objeto
     */
    private Date fecha;
    /**
     * Crea una DTO vacío.
     */
    public CalificacionDTO()
    {
        //Constructor vacío para usos de pruebas y recursos REST
    }

    /**
     * Crea un DTO usando la entidad pasada por parámetro.<br>
     * @param e Entidad por parámetro.
     */
    public CalificacionDTO(CalificacionEntity e)
    {
        calificacion=e.getCalificacion();
        id=e.getId();
        fecha=e.getFecha();
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
    /**
     * Crea una entidad a partir del DTO.<br>
     * @return entidadCreada
     */
    public CalificacionEntity toEntity()
    {
        CalificacionEntity e= new CalificacionEntity();
        e.setCalificacion(calificacion);
        e.setFecha(fecha);
        e.setId(id);
        return e;
    }
}
