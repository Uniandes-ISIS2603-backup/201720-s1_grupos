/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;

/**
 * DTO de Noticia
 * @author jc161
 */
public class NoticiaDTO {
    /**
     * Id de la noticia
     */
    protected Long id;
    /**
     * Título de la noticia.
     */
    protected String titulo;
    /**
     * Información de la noticia.
     */
    protected String informacion;
    /**
     * DTO vacío
     */
    public NoticiaDTO()
    {
        
    }
    /**
     * Crea un nuevo DTO con la entidad pasada por parámetro.<br>
     * @param e Entidad a convertir
     */
    public NoticiaDTO(NoticiaEntity e)
    {
        titulo=e.getTitulo();
        informacion=e.getInformacion();
        id=e.getId();
    }
/**
     *  Obtiene el título de la noticia.<br>
     * @return titulo
     */
    public String getTitulo() {
        return titulo;
    }
    /**
     * Modifica el título al valor dado por parámetro.<br>
     * @param titulo 
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    /**
     * Obtiene la identificación de la noticia.<br>
     * @return id
     */
   public Long getId()
   {
       return id;
   }
   /**
    * Modifica el valor de la id al dado por parámetro.<br>
    * @param n Valor dado por parámetro.
    */
   public void setId(Long n)
   {
       id=n;
   }
   /**
    * Obtiene la información de la noticia.<br>
    * @return informacion
    */
    public String getInformacion() {
        return informacion;
    }
    /**
     * Modifica la información al valor dado por parámetro.<br>
     * @param informacion 
     */
    public void setInformacion(String informacion) {
        this.informacion = informacion;
    }
    /**
     * Genera una entidad a partir del DTO.<br>
     * @return Entidad formada.
     */
    public NoticiaEntity toEntity()
    {
        NoticiaEntity entity= new NoticiaEntity();
        entity.setId(id);
        entity.setInformacion(informacion);
        entity.setTitulo(titulo);
        return entity;
    }

}
