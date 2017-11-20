/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.resources;

import co.edu.uniandes.csw.grupos.dtos.ArchivoDTO;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 *Recurso de archivo.<br>
 * @author af.lopezf
 */
@Path("archivos")
@Produces("application/json")
@Stateless
public class ArchivoResource {
    
    
    public final static  String RUTA="src"+File.separator+"main"+File.separator+"webapp"+File.separator+"data";
     /**
     * GET http://localhost:8080/gurpos-web/Stark/archivos
     * @return Devuelve todos los objetos de tipo Empresa de la aplicación en formato JSON
     * @throws BusinessException
     */
    @GET
    public List<ArchivoDTO> allArchivos() throws BusinessException
    {
        List<ArchivoDTO> archivos= new ArrayList<>();
        File f = new File("BlogDetailDTO.java");
        //
        System.out.println(f.getPath());
        System.out.println(f.getAbsolutePath());
        String s=f.getAbsolutePath().replaceAll(("BlogDetailDTO.java"), "");
        System.out.println(s);
          f = new File(s+RUTA);
        System.out.println(s+RUTA);
        ArchivoDTO.listar();
        if(f.exists()){
            File[] ficheros = f.listFiles();
            for (int x=0;x<ficheros.length;x++){
            archivos.add(new ArchivoDTO(ficheros[x].getName()));
            }
        }  
        return archivos;
    }
    
}
