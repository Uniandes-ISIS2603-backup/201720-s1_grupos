/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.dtos;

import java.io.File;

/**
 * Representación minimum de archivo.<br>
 * @author se.cardenas
 */
public class ArchivoDTO {
   /**
    * Id del dto
    */
    private String ruta;
   
    /**
     * Construye un ArchivoDTO vacío
     */
    public ArchivoDTO() {
        
    }
    
    /**
     * Construye un ArchivDTO dada una ruta
     * @param entity BlogEntity
     */
    public ArchivoDTO(String ruta) {
        this.ruta = ruta;
    }
      
}
