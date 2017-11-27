/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;

import java.util.Objects;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Prueba del funcionamiento de GrupoEntity
 * @author se.cardenas
 */
@RunWith(Arquillian.class)
public class GrupoEntityTest {
    
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
                .addPackage(TarjetaEntity.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    /**
     * Test del get y set del nombre
     */
    @Test
    public void testNombre() {
        PodamFactory factory= new PodamFactoryImpl();

        GrupoEntity newEntity = factory.manufacturePojo(GrupoEntity.class);
        
        newEntity.setNombre("pipo muere");
        
        Assert.assertEquals("pipo muere", newEntity.getNombre());
    }
    
    /**
     * test del get y set de la descripción
     */
    @Test
    public void testDescripcion() {
        PodamFactory factory= new PodamFactoryImpl();

        GrupoEntity newEntity = factory.manufacturePojo(GrupoEntity.class);
        
        newEntity.setDescripcion("pipo muere, NOOHH");
        
        Assert.assertEquals("pipo muere, NOOHH", newEntity.getDescripcion());
    }
    
    /**
     * test del equals
     */
    @Test
    public void testEquals() {
        PodamFactory factory= new PodamFactoryImpl();

        GrupoEntity e=factory.manufacturePojo(GrupoEntity.class);
        GrupoEntity e2=factory.manufacturePojo(GrupoEntity.class);
        e2.setId(e.getId());
        Assert.assertFalse(e.equals(new UsuarioEntity()));
        Assert.assertTrue(e.equals(e2));
        e2.setId(e2.getId()+1);
        Assert.assertFalse(e.equals(e2));
        Assert.assertFalse(e.equals(null));
    }
    
    /**
     * test del hashcode
     */
    @Test
    public void testHashCode()
    {
        PodamFactory factory= new PodamFactoryImpl();

        GrupoEntity e=factory.manufacturePojo(GrupoEntity.class);
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(e.getId());
        Assert.assertEquals(hash,e.hashCode());
    }
}
