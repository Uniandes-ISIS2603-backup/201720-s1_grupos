/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;

import java.util.Objects;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author se.cardenas
 */
public class CategoriaEntityTest {
    
    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Calificacion, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * test del getter y setter de descripcion
     */
    @Test
    public void testDescripcion() {
        PodamFactory factory= new PodamFactoryImpl();

        CategoriaEntity newEntity = factory.manufacturePojo(CategoriaEntity.class);
        newEntity.setDescripcion("pipo muere");
        
        Assert.assertEquals("pipo muere", newEntity.getDescripcion());
    }
    
    /**
     * test del getter y setter de ruta
     */
    @Test
    public void testRuta() {
        PodamFactory factory= new PodamFactoryImpl();

        CategoriaEntity newEntity = factory.manufacturePojo(CategoriaEntity.class);
        newEntity.setRutaIcono("ruta x");
        
        Assert.assertEquals("ruta x", newEntity.getRutaIcono());
    }
    
    /**
     * test del hashcode
     */
    @Test
    public void testHashCode()
    {
        PodamFactory factory= new PodamFactoryImpl();

        CategoriaEntity e=factory.manufacturePojo(CategoriaEntity.class);
        int hash = 5;
        hash = 67 * hash + Objects.hashCode(e.getId());
        Assert.assertEquals(hash,e.hashCode());
    }
    
    /***
     * test del método equals
     */
    @Test
    public void testEquals() {
        PodamFactory factory= new PodamFactoryImpl();

        CategoriaEntity newEntity = factory.manufacturePojo(CategoriaEntity.class);
        
        Assert.assertTrue(newEntity.equals(newEntity));
        Assert.assertFalse(newEntity.equals(null));
        Assert.assertFalse(newEntity.equals(factory.manufacturePojo(UsuarioEntity.class)));
        
        CategoriaEntity newEntity2 = factory.manufacturePojo(CategoriaEntity.class);
        newEntity2.setId(newEntity.getId());
        Assert.assertTrue(newEntity.equals(newEntity2));
    }
}
