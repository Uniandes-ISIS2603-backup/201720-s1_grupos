/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.OneToMany;

/**
 *
 * @author s.guzmanm
 */
@Entity 
public class NoticiaEntity implements Serializable {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    
    private String titulo;
    
    private String informacion;
    /*
    @OneToMany
    private List<MultimediaEntity> multimedia;
    @Id
    private UsuarioEntity autor;*/

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    
   public Long getId()
   {
       return id;
   }
   
   public void setId(Long n)
   {
       id=n;
   }

    public String getInformacion() {
        return informacion;
    }

    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }
/*
    public List<MultimediaEntity> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<MultimediaEntity> multimedia) {
        this.multimedia = multimedia;
    }

    public UsuarioEntity getAutor() {
        return autor;
    }

    public void setAutor(UsuarioEntity autor) {
        this.autor = autor;
    }*/
}
