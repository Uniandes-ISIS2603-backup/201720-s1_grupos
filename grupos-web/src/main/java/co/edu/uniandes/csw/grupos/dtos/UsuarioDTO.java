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
    
    private Long id;
    private String nombre;
    private String apellido;
    private String nickname;
    private String password;
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
    
    //Getters
    public Long getId(){
        return id;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    public String getApellido(){
        return apellido;
    }
    
    public String getNickname(){
        return nickname;
    }
    
    public String getPassword(){
        return password;
    }
    
    public String getEmail(){
        return email;
    }
    
    //Setters
    
    public void setNombre(String n){
        nombre= n;
    }
    
    public void setApellido(String n){
        apellido= n;
    }
    
    public void setNickname(String n){
        nickname= n;
    }
    
    public void setPassword(String n){
        password= n;
    }
    
    public void setEmail(String n){
        email= n;
    }
    
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
