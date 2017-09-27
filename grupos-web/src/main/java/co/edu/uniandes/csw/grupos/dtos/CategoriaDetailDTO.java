/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.CategoriaEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import java.util.ArrayList;
import java.util.List;

/**
 * Representación detail de categoría.<br>
 * @author cm.sarmiento10
 */
public class CategoriaDetailDTO extends CategoriaDTO{
    
    /**
     * Lista de grupos que contienen la categoría actual
     */
    private List<GrupoDTO> grupos;
    
    
    /**
     * Constructor por defecto
     */
    public CategoriaDetailDTO() {
        super();
        
    }
    
    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param entity, entidad a convertir a DTO
     */
    public CategoriaDetailDTO(CategoriaEntity entity) {
        super(entity);
        if (entity != null) {
            grupos = new ArrayList<>();
            for (GrupoEntity entityGrupos : entity.getGrupos()) {
                grupos.add(new GrupoDTO(entityGrupos));
            }

        }

    }

    /**
     * Transformar un DTO a un Entity
     *
     * @return una entidad en base al DTO actual
     */
    @Override
    public CategoriaEntity toEntity() {
        CategoriaEntity categoria = super.toEntity();
        if (grupos != null) {
            List<GrupoEntity> gruposEntity = new ArrayList<>();
            for (GrupoDTO dtoGrupo : grupos) {
               gruposEntity.add(dtoGrupo.toEntity());
            }
            categoria.setGrupos(gruposEntity);
        }
        return categoria;
    }

    /**
     * 
     * @return lista de grupos de la categoría
     */
    public List<GrupoDTO> getGrupos() {
        return grupos;
    }

    /**
     * 
     * @param grupos, grupos a ser puestos en el DTO
     */
    public void setGrupos(List<GrupoDTO> grupos) {
        this.grupos = grupos;
    }
    
    
}
