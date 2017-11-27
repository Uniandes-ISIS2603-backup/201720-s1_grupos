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
public class TarjetaEntityTest {
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
     * Test of max capacity methods, of class TarjetaEntity.
     */
    @Test
    public void testMaxCupo()  {
        PodamFactory factory= new PodamFactoryImpl();

        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setMaxCupo(4.0);

        Assert.assertEquals(new Double(4.0), new Double(newEntity.getMaxCupo()));

    }
    
    /**
     * Test of available money methods, of class TarjetaEntity.
     */
    @Test
    public void testDineroDisponible()  {
        PodamFactory factory= new PodamFactoryImpl();

        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setDineroDisponible(4.0);

        Assert.assertEquals(new Double(4.0), new Double(newEntity.getDineroDisponible()));

    }
    
    /**
     * Test of bank  methods, of class TarjetaEntity.
     */
    @Test
    public void testBanco()  {
        PodamFactory factory= new PodamFactoryImpl();

        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setBanco("A");

        
        Assert.assertEquals("A", newEntity.getBanco());

    }
    
  
    
    /**
     * Test of number methods, of class CalificacionPersistence.
     */
    @Test
    public void testNumero()  {
        PodamFactory factory= new PodamFactoryImpl();

        TarjetaEntity newEntity = factory.manufacturePojo(TarjetaEntity.class);
        newEntity.setNumero(4);

        Assert.assertEquals(new Integer(4), new Integer(newEntity.getNumero()));

    }
    
     /**
     * Test del método equals
     */
    @Test
    public void testEquals()
    {
        PodamFactory factory= new PodamFactoryImpl();

        TarjetaEntity e=factory.manufacturePojo(TarjetaEntity.class);
        TarjetaEntity e2=factory.manufacturePojo(TarjetaEntity.class);
        e2.setNumero(e.getNumero());
        Assert.assertFalse(e.equals(new UsuarioEntity()));
        Assert.assertTrue(e.equals(e2));
        while(e2.equals(e))
        {
            e2.setNumero((int)(Math.random()*1000));
        }
        Assert.assertFalse(e.equals(e2));
    }
    /**
     *
     * Test of hashcode
     */
    @Test
    public void testHash()
    {
        PodamFactory factory= new PodamFactoryImpl();

        TarjetaEntity e=factory.manufacturePojo(TarjetaEntity.class);
        int hash = 7;
        hash = 79 * hash + e.getNumero();
        Assert.assertEquals(hash,e.hashCode());
    }

    
    
}
