/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
*/
package co.edu.uniandes.csw.grupos.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import uk.co.jemos.podam.common.PodamExclude;
/**
 *
 * @author cm.sarmiento10
 */
@Entity
public class CategoriaEntity implements Serializable {
    
    /**
     * Id de la categoría, PK en la base de datos
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**
     * id de la categoría
     */
    private Long id;
    
    /**
     * tipo de la categoría
     */
    private String tipo;
    
    /**
     * descripción de la categoría
     */
    private String descripcion;
    
    /**
     * ruta de la imagen de la categoría
     */
    private String rutaIcono;
    
    /**
     * Lista de grupos
     */
    @PodamExclude
    @ManyToMany(mappedBy = "categorias")
    private List<GrupoEntity> grupos= new ArrayList<GrupoEntity>();
    
    /**
     * 
     * @return id del grupo
     */
    public Long getId() {
        return id;
    }
    
    /**
     * 
     * @param id, id a colocar
     */
    public void setId(Long id) {
        this.id = id;
    }
    
    /**
     * 
     * @return tipo de la categoría
     */
    public String getTipo() {
        return tipo;
    }
    
    /**
     * 
     * @param tipo, tipo a colocar
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    /**
     * 
     * @return descripción de la categoría
     */
    public String getDescripcion() {
        return descripcion;
    }
    
    /**
     * 
     * @param descripcion, descripción a colocar
     */
    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    /**
     * 
     * @return ruta de la imagen de la categoría
     */
    public String getRutaIcono() {
        return rutaIcono;
    }
    
    /**
     * 
     * @param rutaIcono, ruta de la categoría
     */
    public void setRutaIcono(String rutaIcono) {
        this.rutaIcono = rutaIcono;
    }
    
    /**
     * 
     * @return Hash de la categoría
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(this.id);
        return hash;
    }
    
    /**
     * 
     * @param obj, objeto a comparar
     * @return true si tienen el mismo id, false de lo contrario
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final CategoriaEntity other = (CategoriaEntity) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }
    
    /**
     * @return lista de grupos
     */
    public List<GrupoEntity> getGrupos() {
        return grupos;
    }

    /**
     * 
     * @param grupos, lista de grupos a colocar
     */
    public void setGrupos(List<GrupoEntity> grupos) {
        this.grupos = grupos;
    }
    
    
    
    
}
