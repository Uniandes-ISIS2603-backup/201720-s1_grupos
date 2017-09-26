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
     * Constructor vacio
     */
    public PatrocinioDetailDTO(){
    }
    
    /**
     * Constructor a partir de un entity
     */
    public PatrocinioDetailDTO(PatrocinioEntity pe){
        super(pe);
        if(pe!=null){
            //genera el usuario
            if(pe.getUsuario() != null)
            {
                usuario = new UsuarioDTO(pe.getUsuario());
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
     * Cambia el usuario que realiza el patrocinio
     * @param nuser 
     */
    public void setUsuario(UsuarioDTO nuser){
        usuario= nuser;
    }
    
    /**
     * Convierte un patrocinioDetailDTO a patrocinioEntity
     * @return 
     */
    public PatrocinioEntity toEntity(){
        PatrocinioEntity pe = super.toEntity();
        if(usuario!=null){
            pe.setUsuario(usuario.toEntity());
        }
        return pe;
    }
}
