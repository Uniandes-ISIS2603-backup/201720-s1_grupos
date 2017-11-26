/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;


import java.util.Date;
import java.util.Objects;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.jboss.arquillian.container.test.api.Deployment;

import org.jboss.shrinkwrap.api.ShrinkWrap;

import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 * Prueba sobre la persistencia de calificación
 * @author s.guzmanm
 */
@RunWith(Arquillian.class)
public class MultimediaEntityTest {
    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Multimedia, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(MultimediaEntity.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    

    /**
     * Test of qualification methods, of class MultimediaEntity.
     */
    @Test
    public void testNombre()  {
        PodamFactory factory= new PodamFactoryImpl();

        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setNombre("A");

        Assert.assertEquals("A", newEntity.getNombre());

    }
    
    /**
     * Test of description methods, of class MultimediaEntity.
     */
    @Test
    public void testDescripcion()  {
        PodamFactory factory= new PodamFactoryImpl();

        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setDescripcion("HOLA");

        Assert.assertEquals("HOLA", newEntity.getDescripcion());

    }
    
    /**
     * Test of link  methods, of class MultimediaEntity.
     */
    @Test
    public void testLink()  {
        PodamFactory factory= new PodamFactoryImpl();

        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setLink("HOLA");

        Assert.assertEquals("HOLA", newEntity.getLink());

    }
    
    /**
     * Test of route methods, of class MultimediaPersistence.
     */
    @Test
    public void testRuta()  {
        PodamFactory factory= new PodamFactoryImpl();

        MultimediaEntity newEntity = factory.manufacturePojo(MultimediaEntity.class);
        newEntity.setRuta("HOLA");

        Assert.assertEquals("HOLA", newEntity.getRuta());

    }
    
     /**
     * Test del método equals
     */
    @Test
    public void testEquals()
    {
        PodamFactory factory= new PodamFactoryImpl();

        MultimediaEntity e=factory.manufacturePojo(MultimediaEntity.class);
        MultimediaEntity e2=factory.manufacturePojo(MultimediaEntity.class);
        e2.setLink(e.getLink());
        Assert.assertFalse(e.equals(new UsuarioEntity()));
        Assert.assertTrue(e.equals(e2));
    }
    /**
     *
     * Test of hashcode
     */
    @Test
    public void testHash()
    {
        PodamFactory factory= new PodamFactoryImpl();

        MultimediaEntity e=factory.manufacturePojo(MultimediaEntity.class);
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(e.getLink());
        Assert.assertEquals(hash,e.hashCode());
    }
    

    
}
