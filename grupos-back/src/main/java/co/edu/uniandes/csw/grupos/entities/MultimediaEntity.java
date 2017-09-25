/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;


import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
 *Entidad de multimedia
 * @author s.guzmanm
 */
@Entity
public class MultimediaEntity implements Serializable {
    /**
     * Nombre de la multimedia.
     */
    private String nombre;
    /**
     * Descripción de la multimedia
     */
    private String descripcion;
    /**
     * Link de la multimedia
     */
    @Id
    private String link;
    /**
     * Obtiene el nombre de la multimedia.
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Modifica el nombre por el valor dado por parámetro.<br>
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Obtiene la descripción de la multimedia.<br>
     * @return descripcion
     */
    public String getDescripcion() {
        return descripcion;
    }
    /**
     * Modifica la descripción de la multimedia por el valor dado por parámetro.<br>
     * @param descripcion 
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    /**
     * Obtiene el link de la multimedia.<br>
     * @return link
     */
    public String getLink() {
        return link;
    }
    /**
     * Modifica el link por el valor dado por parámetro.<br>
     * @param link 
     */
    public void setLink(String link) {
        this.link = link;
    }
    /**
     * Override del equals <br>
     * @param o Objeto a comparar.<br>
     * @return Mira si hay dos multiemdias iguales.
     */
    @Override
    public boolean equals(Object o)
    {
        if(!(o instanceof MultimediaEntity)) return false;
        MultimediaEntity m = (MultimediaEntity)o;
        return link.equals(m.getLink());
    }
    /**
     * Override del hashcode.<br>
     * @return Hash code del link.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.link);
        return hash;
    }
    
}
