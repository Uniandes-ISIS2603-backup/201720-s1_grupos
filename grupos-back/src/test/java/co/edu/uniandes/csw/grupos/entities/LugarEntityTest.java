/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;


import java.util.Date;
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
public class LugarEntityTest {
    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Lugar, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(LugarEntity.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    

    /**
     * Test of capacity methods, of class LugarEntity.
     */
    @Test
    public void testCapacidad()  {
        PodamFactory factory= new PodamFactoryImpl();

        LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
        newEntity.setCapacidad(4);

        Assert.assertEquals(new Integer(4), newEntity.getCapacidad());

    }
    
    /**
     * Test of direction methods, of class LugarEntity.
     */
    @Test
    public void testDireccion()  {
        PodamFactory factory= new PodamFactoryImpl();

        LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
        newEntity.setDireccion("HOLA");

        Assert.assertEquals("HOLA", newEntity.getDireccion());

    }
    
    /**
     * Test of name  methods, of class LugarEntity.
     */
    @Test
    public void testNombre()  {
         PodamFactory factory= new PodamFactoryImpl();

        LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
        newEntity.setNombre("HOLA");

        Assert.assertEquals("HOLA", newEntity.getNombre());

    }
    
    /**
     * Test of id methods, of class LugarEntity.
     */
    @Test
    public void testId()  {
        PodamFactory factory= new PodamFactoryImpl();

        LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
        newEntity.setId(4l);

        Assert.assertEquals(new Long(4l), newEntity.getId());

    }
    /**
     * Test of availability methods, of class LugarEntity.
     */
    @Test
    public void testDisponibilidad()  {
        PodamFactory factory= new PodamFactoryImpl();

        LugarEntity newEntity = factory.manufacturePojo(LugarEntity.class);
        newEntity.setDisponibilidad(true);

        Assert.assertEquals(true, newEntity.isDisponibilidad());

    }

    
    
}
