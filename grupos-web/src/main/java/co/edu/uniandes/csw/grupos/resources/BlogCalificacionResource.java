/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.BlogDetailDTO;
import co.edu.uniandes.csw.grupos.dtos.CalificacionDetailDTO;
import co.edu.uniandes.csw.grupos.dtos.MultimediaDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.BlogLogic;
import co.edu.uniandes.csw.grupos.ejb.CalificacionLogic;
import co.edu.uniandes.csw.grupos.entities.CalificacionEntity;
import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.CalificacionPersistence;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.WebApplicationException;


/**
 * Rwcurso de blog calificación.<br>
 * @author jc161
 */
public class BlogCalificacionResource {
    /**
     * Lógica del blog.
     */
    @Inject
    private BlogLogic blog;
    /**
     * Lógica de calificación
     */
    @Inject
    private CalificacionLogic logic;
    
    /**
     * Listar un dto en entidad.<br>
     * @param list Lista del dto.<br>
     * @return Lista de entidades.
     */
    private List<CalificacionEntity> listarDTO(List<CalificacionDetailDTO> list)
    {
        ArrayList<CalificacionEntity> m = new ArrayList<>();
        for(CalificacionDetailDTO d:list) m.add(d.toEntity());
        return m;
    }
    /**
     * Pasa una lista de entidades a dtos.<br>
     * @param list Lista de entidades.<br>
     * @return Lista de dtos
     */
    private List<CalificacionDetailDTO> toDTO(List<CalificacionEntity> list)
    {
        ArrayList<CalificacionDetailDTO> m = new ArrayList<>();
        for(CalificacionEntity d:list) m.add(new CalificacionDetailDTO(d));
        return m;
    }
    /**
     * Obtiene calificaciones con id.<br>
     * @param id Id del blog.<br>
     * @return Calificaciones<br>
     * @throws BusinessException Excepciónd e negocio.
     */
    @GET
    public List<CalificacionDetailDTO> getCalificaciones(@PathParam("blogId")Long id) throws BusinessException
    {
        return toDTO(blog.getCalificaciones(id));
    }
    /**
     * Obtiene una calificación.<br>
     * @param id Id del blog.<br>
     * @param calificacionId Id de la calificación.<br>
     * @return Calificación del blog.<br>
     * @throws BusinessException Excepción de negocio.
     */
    @GET 
    @Path("{id: \\d+}")
    public CalificacionDetailDTO getCalificacion(@PathParam("blogId") Long id,@PathParam("id") Long calificacionId) throws BusinessException
    {
        return new CalificacionDetailDTO(blog.getCalificacion(id, calificacionId));
    }
    /**
     * Agrega una calificación al blog.<br>
     * @param grupoId Id del grupo.<br>
     * @param id Id del blog.<br>
     * @param calificacion Id de la calificación.<br>
     * @return Calificación creada.<br>
     * @throws BusinessException Excepción de negocio.
     */
    @POST
    public CalificacionDetailDTO postCalificacion (@PathParam("grupoId") Long grupoId,@PathParam("blogId")Long id, CalificacionDetailDTO calificacion) throws BusinessException
    {
        calificacion.setFecha(new Date(System.currentTimeMillis()));
        if(calificacion.getCalificador()==null) throw new BusinessException("Su calificación debe tener a un usuario asociado");
        CalificacionEntity c =logic.createEntity(calificacion.toEntity()); 
        CalificacionEntity cal=blog.addCalificacion(grupoId, id, c);
        return new CalificacionDetailDTO(cal);
    }
    /**
     * Actualiza una calificación.<br>
     * @param grupoId Id del grupo.<br>
     * @param id Id del blog.<br>
     * @param cId Id del comentario.<br>
     * @param dto DTo a actualizar.<br>
     * @return Calificación actualizada.<br>
     * @throws BusinessException Excepción de negocio.
     */
    @PUT
    @Path("{id: \\d+}")
    public CalificacionDetailDTO updateCalificacion (@PathParam("grupoId") Long grupoId, @PathParam("blogId") Long id, @PathParam("id")Long cId, CalificacionDetailDTO dto) throws BusinessException
    {
        dto.setBlog(new BlogDetailDTO(blog.getBlog(id)));
        dto.setFecha(new Date(System.currentTimeMillis()));
       CalificacionEntity c=blog.updateCalificacion(grupoId, id, cId, dto.toEntity());
        return new CalificacionDetailDTO(c);
    }
    /**
     * Borra una calificación.<br>
     * @param grupoId Id del grupo.<br>
     * @param blogId Id del blog.<br>
     * @param id Id de la calificación.<br>
     * @throws BusinessException  Excepción de negocio.
     */
    @DELETE
    @Path("{id: \\d+}")
    public void deleteCalificacion(@PathParam("grupoId") Long grupoId, @PathParam("blogId") Long blogId, @PathParam("id")Long id) throws BusinessException
    {
        blog.removeCalificacino(grupoId, blogId, id);
    }
}
