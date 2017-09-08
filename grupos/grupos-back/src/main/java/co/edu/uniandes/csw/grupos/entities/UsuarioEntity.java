/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 *
 * @author tefa
 */
@Entity
public class UsuarioEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String apellido;
    private String nickname;
    private String password;
    private String email;
    
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
    
    public void setId(Long pId){
        id= pId;
    }
    
    public void setNombre(String nNombre){
        nombre= nNombre;
    }
    
    public void setApellido(String nApellido){
        apellido= nApellido;
    }
    
    public void setNickname(String nNickname){
        nickname= nNickname;
    }
    
    public void setPassword(String nPassword){
        password= nPassword;
    }
    
    public void setEmail(String nEmail){
        email= nEmail;
    }

}
