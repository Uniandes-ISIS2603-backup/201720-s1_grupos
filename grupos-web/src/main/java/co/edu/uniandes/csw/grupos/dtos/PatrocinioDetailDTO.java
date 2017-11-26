/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.PatrocinioEntity;

/**
 * Representación detallada del patrocinio.<br>
 * @author tefa
 */
public class PatrocinioDetailDTO extends PatrocinioDTO{
    
    /**
     * Usuario que realizo el patrocinio
     */
    private UsuarioDTO usuario;
    
    /**
     * Evento del patrocinio
     */
    private EventoDTO evento;
    
    /**
     * Constructor vacio
     */
    public PatrocinioDetailDTO(){
        //Constructor vacío para usos de pruebas y recursos REST
    }
    
    /**
     * Constructor a partir de un entity
     * @param pe Entidad de patrocinio
     */
    public PatrocinioDetailDTO(PatrocinioEntity pe){
        super(pe);
        if(pe!=null){
            //genera el usuario
            if(pe.getUsuario() != null)
            {
                usuario = new UsuarioDTO(pe.getUsuario());
            }
            if(pe.getEvento() != null)
            {
                evento =  new EventoDTO(pe.getEvento());
            }
        }
    }
    
    /**
     * Da el usuario que realizo el patrocinio
     * @return usuario
     */
    public UsuarioDTO darUsuario(){
        return usuario;
    }
    
    /**
     * Da el evento
     */
    public EventoDTO darEvento(){
        return evento;
    }
    
    /**
     * Cambia el usuario que realiza el patrocinio
     * @param nuser 
     */
    public void setUsuario(UsuarioDTO nuser){
        usuario= nuser;
    }
    
    /**
     * Cambia el evento que realiza el patrocinio
     * @param nevent Evento a agregar
     */
    public void setEvento(EventoDTO nevent){
        evento = nevent;
    }
    
    /**
     * Convierte un patrocinioDetailDTO a patrocinioEntity
     * @return 
     */
    @Override
    public PatrocinioEntity toEntity(){
        PatrocinioEntity pe = super.toEntity();
        if(usuario!=null){
            pe.setUsuario(usuario.toEntity());
        }
        if(evento!=null){
            pe.setEvento(evento.toEntity());
        }
        return pe;
    }
}
