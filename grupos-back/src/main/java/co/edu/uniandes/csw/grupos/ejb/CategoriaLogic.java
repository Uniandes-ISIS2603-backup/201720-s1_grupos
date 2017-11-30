/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.CategoriaEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.CategoriaPersistence;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

/**
 *Lógica de categoría
 * @author cm.sarmiento10
 */
public class CategoriaLogic {
    /**
     * DB para almacenar la categoría
     */
     @Inject
    private CategoriaPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.
     
     private String err = "No se encontró una Categoria con el id: ";
     
     /**
      * 
      * @param entity, categoría a guardarse
      * @return entidad recien guardada
      * @throws BusinessException, excepción si ya hay una categoría con ese id
      */
    public CategoriaEntity createCategoria(CategoriaEntity entity) throws BusinessException {
        // Verifica la regla de negocio que dice que no puede haber dos Categoriaes con el mismo nombre
        if (persistence.findByTipo(entity.getTipo()) != null) {
            throw new BusinessException("Ya existe una Categoria con el nombre \"" + entity.getTipo() + "\"");
        }
        // Invoca la persistencia para crear la Categoria
        persistence.create(entity);
        return entity;
    }

    /**
     * 
     * @return todas las categorias almacenadas
     */
    public List<CategoriaEntity> getCategorias() {
       
        return persistence.findAll();
    }

    /**
     * 
     * @param id, id de la categoría a buscar
     * @return la categoría con el id indicado
     * @throws NotFoundException, excepción si no encuentra la categoría
     */
    public CategoriaEntity getCategoria(Long id) {
        CategoriaEntity categoria = persistence.find(id);
        if(categoria==null)
        {
            throw new NotFoundException(err + id);
        }
        return categoria;
    }

    /**
     * 
     * @param entity, entidad modificada
     * @return la entidad recién modificada
     * @throws NotFoundException, excepción si no encuentra la categoría
     */
    public CategoriaEntity updateCategoria(CategoriaEntity entity) {
        CategoriaEntity categoria= persistence.find(entity.getId());
        if(categoria==null)
       {
           throw new NotFoundException(err + entity.getId());
       }
        CategoriaEntity newEntity = persistence.update(entity);
        return newEntity;
    }

    /**
     * 
     * @param id, id de la categoría a borrar
     */
    public void deleteCategoria(Long id) {
        CategoriaEntity categoria= persistence.find(id);
        if(categoria==null)
       {
           throw new NotFoundException(err + id);
       }
        persistence.delete(id);
        
    }

    /**
     * 
     * @param tipo, tipo de la categoría a buscar
     * @return la categoría con el tipo a buscar
     * @throws NotFoundException, excepción si no encuentra la categoría
     */
    public CategoriaEntity getCategoria(String tipo) {
       CategoriaEntity entity= persistence.findByTipo(tipo);
       if(entity==null)
       {
           throw new NotFoundException("No se encontró una Categoria con el nombre: " + tipo);
       }
       return entity;
    }
    
    /**
     * Obtiene una colección de instancias de GrupoEntity asociadas a una
     * instancia de Categoria
     *
     * @param categoriaId Identificador de la instancia de Categoria
     * @return Colección de instancias de Grupos asociadas a la instancia
     * de Categoria
     */
    public List<GrupoEntity> listGrupos(Long categoriaId) {
        return getCategoria(categoriaId).getGrupos();
    }

    /**
     * 
     * @param grupoId, id del grupo a buscar, que se encuentra en la relación con categorias
     * @param categoriaId, id de la categoría donde se buscará el grupo
     * @return el grupo con el id dado con relacion a la categoría con el id dado
     */
    public GrupoEntity getGrupo(Long grupoId, Long categoriaId) {
        List<GrupoEntity> list = getCategoria(categoriaId).getGrupos();
        GrupoEntity grupoEntity = new GrupoEntity();
        grupoEntity.setId(grupoId);
        int index = list.indexOf(grupoEntity);
        if (index >= 0) {
            return list.get(index);
        }
        return null;
    }

}
