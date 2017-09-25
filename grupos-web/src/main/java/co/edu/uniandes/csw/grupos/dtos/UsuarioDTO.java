/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;

/**
 *
 * @author tefa
 */
public class UsuarioDTO {
    
    /**
     * Id del usuario
     */
    private Long id;
    
    /**
     * Nombre del usuario
     */
    private String nombre;
    
    /**
     * Apellido del usuario
     */
    private String apellido;

    /**
     * Apodo del usuario
     */
    private String nickname;
    
    /**
     * Contraseña del usuario
     */
    private String password;
    
    /**
     * Email del usuario
     */
    private String email;
    
    
    /**
     * Constructor vacio
     */
    public UsuarioDTO(){
    }
    
    /**
     * Constructor con usuario entity ya existente
     * @param ue Usuario Entity
     */
    public UsuarioDTO(UsuarioEntity ue){
        this.id = ue.getId();
        this.nombre = ue.getNombre();
        this.apellido = ue.getApellido();
        this.nickname = ue.getNickname();
        this.password = ue.getPassword();
        this.email = ue.getEmail();
    }
    
    /**
     * Dar id
     * @return id 
     */
    public Long getId(){
        return id;
    }
    
    /**
     * Dar nombre
     * @return nombre
     */
    public String getNombre(){
        return nombre;
    }
    
    /**
     * Da el apellido
     * @return apellido
     */
    public String getApellido(){
        return apellido;
    }
    
    /**
     * Da el apodo del usuario
     * @return nickname
     */
    public String getNickname(){
        return nickname;
    }
    
    /**
     * Da la contraseña del usuario
     * @return password
     */
    public String getPassword(){
        return password;
    }
    
    /**
     * Da el email del usuario
     * @return email
     */
    public String getEmail(){
        return email;
    }
    
    /**
     * Cambia el nombre de un usuario
     * @param n nombre 
     */
    public void setNombre(String n){
        nombre= n;
    }
    
    /**
     * Cambia el apellido de un usuario 
     * @param n apellido
     */
    public void setApellido(String n){
        apellido= n;
    }
    
    /**
     * Cambia el apodo de un usuario
     * @param n nickname
     */
    public void setNickname(String n){
        nickname= n;
    }
    
    /**
     * Cambia la contraseña de un usuario
     * @param n password
     */
    public void setPassword(String n){
        password= n;
    }
    
    /**
     * Cambia el email de un usuario
     * @param n email
     */
    public void setEmail(String n){
        email= n;
    }
    
    /**
     * Cambia el Id de un usuario
     * @param n id
     */
    public void setId(Long n){
        id= n;
    }
    
    /**
     * Convierte un UsuarioDTO a un UsuarioEntity
     * @return UsuarioEntity
     */
    public UsuarioEntity toEntity(){
        UsuarioEntity user = new UsuarioEntity();
        user.setId(id);
        user.setNombre(nombre);
        user.setApellido(apellido);
        user.setEmail(email);
        user.setNickname(nickname);
        user.setPassword(password);
        return user;
    }
    
}
