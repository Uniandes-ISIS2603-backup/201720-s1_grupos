/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.Grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.CategoriaEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.CategoriaPersistence;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author cm.sarmiento10
 */
public class CategoriaLogic {
     @Inject
    private CategoriaPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    public CategoriaEntity createCategoria(CategoriaEntity entity, String nickname) throws BusinessException {
        // Verifica la regla de negocio que dice que no puede haber dos Categoriaes con el mismo nombre
        if (persistence.findByTipo(entity.getTipo()) != null) {
            throw new BusinessException("Ya existe una Categoria con el nombre \"" + entity.getTipo() + "\"");
        }
        // Invoca la persistencia para crear la Categoria
        persistence.create(entity);
        return entity;
    }

    public List<CategoriaEntity> getCategorias() {
       
        List<CategoriaEntity> cities = persistence.findAll();
        return cities;
    }

    public CategoriaEntity getCategoria(Long id) {
        CategoriaEntity Categoria = persistence.find(id);
        if(Categoria==null)
        {
            throw new NotFoundException("No se encontró una Categoria con el id: " + id);
        }
        return Categoria;
    }

    public CategoriaEntity updateCategoria(CategoriaEntity entity, String nickname) {
        CategoriaEntity Categoria= persistence.find(entity.getId());
        if(Categoria==null)
       {
           throw new NotFoundException("No se encontró una Categoria con el id: " + entity.getId());
       }
        CategoriaEntity newEntity = persistence.update(entity);
        return newEntity;
    }

    public void deleteCategoria(Long id, String nickname) {
        CategoriaEntity Categoria= persistence.find(id);
        if(Categoria==null)
       {
           throw new NotFoundException("No se encontró una Categoria con el id: " + id);
       }
        persistence.delete(id);
        
    }

    public CategoriaEntity getCategoria(String tipo) {
       CategoriaEntity entity= persistence.findByTipo(tipo);
       if(entity==null)
       {
           throw new NotFoundException("No se encontró una Categoria con el nombre: " + tipo);
       }
       return entity;
    }
}
