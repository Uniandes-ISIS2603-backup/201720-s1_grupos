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
public class PatrocinioEntityTest {
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
                .addPackage(PatrocinioEntity.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    

    /**
     * Test of payment methods, of class PatrocinioEntity.
     */
    @Test
    public void testPago()  {
        PodamFactory factory= new PodamFactoryImpl();

        PatrocinioEntity newEntity = factory.manufacturePojo(PatrocinioEntity.class);
        newEntity.setPago(4.0);

        Assert.assertEquals(new Double(4.0), new Double(newEntity.getPago()));

    }
    
    /**
     * Test of user methods, of class PatrocinioEntity.
     */
    @Test
    public void testUsuario()  {
        PodamFactory factory= new PodamFactoryImpl();

        PatrocinioEntity newEntity = factory.manufacturePojo(PatrocinioEntity.class);
        UsuarioEntity usuario=factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setUsuario(usuario);

        Assert.assertEquals(usuario, newEntity.getUsuario());

    }
    
    /**
     * Test of event  methods, of class PatrocinioEntity.
     */
    @Test
    public void testEvento()  {
        PodamFactory factory= new PodamFactoryImpl();

        PatrocinioEntity newEntity = factory.manufacturePojo(PatrocinioEntity.class);
        EventoEntity evento=factory.manufacturePojo(EventoEntity.class);
        newEntity.setEvento(evento);

        
        Assert.assertEquals(evento, newEntity.getEvento());

    }
   
    
    /**
     * Test of id methods, of class PatrocinioEntity.
     */
    @Test
    public void testId()  {
        PodamFactory factory= new PodamFactoryImpl();

        PatrocinioEntity newEntity = factory.manufacturePojo(PatrocinioEntity.class);
        newEntity.setId(4l);

        Assert.assertEquals(new Long(4l), newEntity.getId());

    }
    
     /**
     * Test del método equals
     */
    @Test
    public void testEquals()
    {
        PodamFactory factory= new PodamFactoryImpl();

        PatrocinioEntity e=factory.manufacturePojo(PatrocinioEntity.class);
        PatrocinioEntity e2=factory.manufacturePojo(PatrocinioEntity.class);
        e2.setId(e.getId());
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

        PatrocinioEntity e=factory.manufacturePojo(PatrocinioEntity.class);
        int hash = 7;
        hash = 29 * hash + Objects.hashCode(e.getId());
        Assert.assertEquals(hash,e.hashCode());
    }

    
    
}
