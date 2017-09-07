/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author s.guzmanm
 */
@Embeddable
public class NoticiaId implements Serializable {
     String titulo;
     //Long autor;

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
/*
    public Long getAutor() {
        return autor;
    }

    public void setAutor(Long autor) {
        this.autor = autor;
    }
     */
     
     public boolean compareTo(Object o)
     {
         if(o==null) return false;
         if(!(o instanceof NoticiaId)) return false;
         NoticiaId n2=(NoticiaId) o;
         if(!titulo.equals(n2.titulo)) return false;
         //if(autor==null || n2.autor==null) return false;
         //if(!autor.equals(n2.autor)) return false;
        
         return true;
     }
     @Override
     public int hashCode()
     {
         return super.hashCode();
     }
}
