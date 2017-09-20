/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.GrupoPersistence;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.NotFoundException;

/**
 *
 * @author cm.sarmiento10
 */
public class GrupoLogic {
    @Inject
    private GrupoPersistence persistence; // Variable para acceder a la persistencia de la aplicación. Es una inyección de dependencias.

    public GrupoEntity createGrupo(GrupoEntity entity, String nickname) throws BusinessException {
        // Verifica la regla de negocio que dice que no puede haber dos Grupoes con el mismo nombre
        if (persistence.findByNombre(entity.getNombre()) != null) {
            throw new BusinessException("Ya existe un grupo con el nombre \"" + entity.getNombre() + "\"");
        }
        // Invoca la persistencia para crear la Grupo
        persistence.create(entity);
        return entity;
    }

    public List<GrupoEntity> getGrupos() {
       
        List<GrupoEntity> cities = persistence.findAll();
        return cities;
    }

    public GrupoEntity getGrupo(Long id) {
        GrupoEntity grupo = persistence.find(id);
        if(grupo==null)
        {
            throw new NotFoundException("No se encontró un grupo con el id: " + id);
        }
        return grupo;
    }

    public GrupoEntity updateGrupo(GrupoEntity entity, String nickname) {
        GrupoEntity grupo= persistence.find(entity.getId());
        if(grupo==null)
       {
           throw new NotFoundException("No se encontró un grupo con el id: " + entity.getId());
       }
        GrupoEntity newEntity = persistence.update(entity);
        return newEntity;
    }

    public void deleteGrupo(Long id, String nickname) {
        GrupoEntity grupo= persistence.find(id);
        if(grupo==null)
       {
           throw new NotFoundException("No se encontró un grupo con el id: " + id);
       }
        persistence.delete(id);
        
    }

    public GrupoEntity getGrupo(String nombre) {
       GrupoEntity entity= persistence.findByNombre(nombre);
       if(entity==null)
       {
           throw new NotFoundException("No se encontró un grupo con el nombre: " + nombre);
       }
       return entity;
    }
}
