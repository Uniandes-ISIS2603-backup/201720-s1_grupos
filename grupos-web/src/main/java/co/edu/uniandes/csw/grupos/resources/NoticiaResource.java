/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.NoticiaDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.NoticiaLogic;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.exceptions.*;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * Recurso de la noticia.<br>
 * @author jc161
 */
@Path("noticias")
@Produces("application/json")
@Consumes("application/json")
@Stateless
public class NoticiaResource {
    /**
     * Lógica de la noticia
     */
    @Inject
     NoticiaLogic logic;
    /**
     * Obtiene la lista de todas las noticias del sistema.<br>
     * @return  noticias
     */
    @GET
    public List<NoticiaDetailDTO> getNoticias()
    {
        
        return listEntity2DetailDTO(logic.getAll());
    }
    /**
     * Obtiene una noticia del sistema.<br>
     * @param id Id de la noticia.<br>
     * @return Representación de la noticia del sistema.<br>
     * @throws BusinessException Si el id es nulo
     */
    @GET
    @Path("{id: \\d+}")
    public NoticiaDetailDTO getNoticia(@PathParam("id") Long id) throws BusinessException
    {
        NoticiaEntity entity=null;
          try
        {
            entity=logic.getEntity(id);
        }
          catch(javax.ejb.EJBTransactionRolledbackException e)
        {
            throw new NotFoundException("No se encontró la noticia con id: "+ id);
        }
        catch(javax.ejb.EJBException e)
        {
            throw e;
        }
        return new NoticiaDetailDTO(entity);
    }
    /**
     * Acceso al subrecurso de multimedia de la noticia.<br>
     * @param id Id de la noticia.<br>
     * @return Class de subrecurso NoticiaMultimedia
     */
    @Path("{noticiaid: \\d+}/multimedia")
    public Class<NoticiaMultimediaResource> getNoticiaMultimediaResource(@PathParam("noticiaid") Long id)
    {
        return NoticiaMultimediaResource.class;
    }
    /**
     * Convierte en DetailDTO una lista de entidades pasadas por parámetro.<br>
     * @param entityList
     * @return Lista de NoticiaDetailDTO
     */
    private List<NoticiaDetailDTO> listEntity2DetailDTO(List<NoticiaEntity> entityList) {
        List<NoticiaDetailDTO> list = new ArrayList<>();
        for (NoticiaEntity entity : entityList) {
            list.add(new NoticiaDetailDTO(entity));
        }
        return list;
    }
    /**
     * Conexión con el subrecurso de NoticiaComentario.<br>
     * @param id Id de la noticia.<br>
     * @return Clase de subrecurso.<br>
     * @throws BusinessException Si se paso un id nulo.
     */
    @Path("{noticiaId: \\d+}/comentarios")
    public Class<NoticiaComentarioResource> getComentarios(@PathParam("noticiaId") Long id) throws BusinessException {
        logic.getEntity( id);
        return NoticiaComentarioResource.class;
    }
}
