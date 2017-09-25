/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;


import co.edu.uniandes.csw.grupos.entities.EventoEntity;
import co.edu.uniandes.csw.grupos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author js.ramos14
 */
public class EventoDetailDTO extends EventoDTO {
    
    /**
     * Relacion a un grupo
     */
    private GrupoDTO grupo;
    /**
     * Relacion a un lugar
     */
    private LugarDTO lugar;
    /**
     * Relacion cero o muchos patrocinios
     */
    private List<PatrocinioDTO> patrocinios;
    /**
     * Relacion cero o muchos usuarios
     */
    private List<UsuarioDTO> usuarios;
    public EventoDetailDTO()
    {
        super();
    }
    
    public EventoDetailDTO(EventoEntity entity)
    {
        super(entity);
        if(entity.getGrupo() != null)
        {
            this.grupo = new GrupoDTO(entity.getGrupo());
        }
        else
        {
        entity.setGrupo(null);
        }
        if(entity.getLugar() != null)
        {
            this.lugar = new LugarDTO(entity.getLugar());
        }
        else
        {
            entity.setLugar(null);
        }
        if(entity.getPatrocinios() != null)
        {
            patrocinios = new ArrayList<>();
            for (PatrocinioEntity entityPatrocinio : entity.getPatrocinios()) {
                patrocinios.add(new PatrocinioDTO(entityPatrocinio));
            }
        }
        if(entity.getUsuarios() != null)
        {
            usuarios = new ArrayList<>();
            for (UsuarioEntity entityUsuario : entity.getUsuarios()) {
                usuarios.add(new UsuarioDTO(entityUsuario));
            }
        }
        
    }
    
    @Override
    public EventoEntity toEntity()
    {
    EventoEntity eventoEntity = super.toEntity();
    if(this.getLugar() != null)
    {
        eventoEntity.setLugar(this.getLugar().toEntity());
    }
    if(this.getGrupo() != null)
    {
        eventoEntity.setGrupo(this.getGrupo().toEntity());
    }
    if (getPatrocinios() != null) 
    {
            List<PatrocinioEntity> patrociniosEntity = new ArrayList<PatrocinioEntity>();
            for (PatrocinioDTO dtoPatrocinio : getPatrocinios()) {
                patrociniosEntity.add(dtoPatrocinio.toEntity());
            }
            eventoEntity.setPatrocinios(patrociniosEntity);
        }
    if (getUsuarios() != null)
    {
        List<UsuarioEntity> usuariosEntity = new ArrayList<UsuarioEntity>();
            for (UsuarioDTO dtoUsuario : getUsuarios()) {
                usuariosEntity.add(dtoUsuario.toEntity());
            }
            eventoEntity.setUsuarios(usuariosEntity);
    }
        return eventoEntity;
    }

    /**
     * @return the grupo
     */
    public GrupoDTO getGrupo() {
        return grupo;
    }

    /**
     * @param grupo the grupo to set
     */
    public void setGrupo(GrupoDTO grupo) {
        this.grupo = grupo;
    }

    /**
     * @return the lugar
     */
    public LugarDTO getLugar() {
        return lugar;
    }

    /**
     * @param lugar the lugar to set
     */
    public void setLugar(LugarDTO lugar) {
        this.lugar = lugar;
    }

    /**
     * @return the patrocinios
     */
    public List<PatrocinioDTO> getPatrocinios() {
        return patrocinios;
    }

    /**
     * @param patrocinios the patrocinios to set
     */
    public void setPatrocinios(List<PatrocinioDTO> patrocinios) {
        this.patrocinios = patrocinios;
    }

    /**
     * @return the usuarios
     */
    public List<UsuarioDTO> getUsuarios() {
        return usuarios;
    }

    /**
     * @param usuarios the usuarios to set
     */
    public void setUsuarios(List<UsuarioDTO> usuarios) {
        this.usuarios = usuarios;
    }
}