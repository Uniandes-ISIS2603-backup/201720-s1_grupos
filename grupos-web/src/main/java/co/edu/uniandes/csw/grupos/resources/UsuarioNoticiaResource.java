/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.NoticiaDTO;
import co.edu.uniandes.csw.grupos.dtos.NoticiaDetailDTO;
import co.edu.uniandes.csw.grupos.ejb.UsuarioLogic;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

/**
 *
 * @author jc161
 */
public class UsuarioNoticiaResource {
    /**
     * Lógica del grupo
     */
    @Inject
    private UsuarioLogic usuarioLogic;
    
    /**
     * Convierte una lista de NoticiaEntity a una lista de NoticiaDetailDTO.
     *
     * @param entityList Lista de NoticiaEntity a convertir.
     * @return Lista de NoticiaDetailDTO convertida.
     *
     */
    private List<NoticiaDTO> NoticiasListEntity2DTO(List<NoticiaEntity> entityList) {
        List<NoticiaDTO> list = new ArrayList<>();
        for (NoticiaEntity entity : entityList) {
            list.add(new NoticiaDTO(entity));
        }
        return list;
    }
    
    /**
     * Convierte una lista de NoticiaDetailDTO a una lista de NoticiaEntity.
     *
     * @param dtos Lista de NoticiaDetailDTO a convertir.
     * @return Lista de NoticiaEntity convertida.
     *
     */
    private List<NoticiaEntity> NoticiasListDTO2Entity(List<NoticiaDetailDTO> dtos) {
        List<NoticiaEntity> list = new ArrayList<>();
        for (NoticiaDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
    /**
     * Obtiene una colección de instancias de NoticiaDetailDTO asociadas a una
     * instancia de Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @return Colección de instancias de NoticiaDetailDTO asociadas a la
     * instancia de Grupo
     *
     */
    @GET
    public List<NoticiaDTO> listNoticias(@PathParam("grupoId") Long id) throws BusinessException {
        return NoticiasListEntity2DTO(usuarioLogic.getNoticias(id));
    }
    
    /**
     * Obtiene una instancia de Noticia asociada a una instancia de Grupo
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param NoticiasId Identificador de la instancia de Noticia
     * @return
     *
     */
    @GET
    @Path("{NoticiaId: \\d+}")
    public NoticiaDetailDTO getNoticias(@PathParam("grupoId") Long grupoId, @PathParam("NoticiaId") Long NoticiaId) throws BusinessException {
        NoticiaEntity e =usuarioLogic.getNoticia(grupoId, NoticiaId);
        if(e==null) throw new NotFoundException("No existe lo buscado");
        return new NoticiaDetailDTO(e);
    }
    
    @POST
    public NoticiaDetailDTO addNoticia(@PathParam("grupoId") Long grupoId, NoticiaDetailDTO noticia) throws BusinessException {
        return new NoticiaDetailDTO(usuarioLogic.addNoticia(grupoId, noticia.toEntity()));
    }
   
    
    /**
     * Desasocia un Noticia existente de un Grupo existente
     *
     * @param GruposId Identificador de la instancia de Grupo
     * @param NoticiasId Identificador de la instancia de Noticia
     *
     */
    @DELETE
    @Path("{NoticiaId: \\d+}")
    public void removeNoticias(@PathParam("grupoId") Long grupoId, @PathParam("NoticiaId") Long NoticiaId) throws BusinessException {
        usuarioLogic.removeNoticia(grupoId, NoticiaId);
    }
    
    @PUT
    @Path("{NoticiaId: \\d+}")
    public NoticiaDetailDTO updateNoticia(@PathParam("grupoId") Long grupoId, @PathParam("NoticiaId") Long NoticiaId, NoticiaDetailDTO newNoticia) throws BusinessException {
        NoticiaEntity e = newNoticia.toEntity();
        return new NoticiaDetailDTO(usuarioLogic.updateNoticia(grupoId, NoticiaId,e));
    }
    
    @Path("{noticiaid:\\d+}/multimedia")
    public Class<NoticiaMultimediaResource> getMultimedia(@PathParam("grupoId") Long grupoId, @PathParam("noticiaid")Long idNoticia) throws BusinessException
    {
        if(usuarioLogic.getNoticia( grupoId, idNoticia)==null) throw new NotFoundException("No existe el grupo con este id");
        return NoticiaMultimediaResource.class;
    }
}
