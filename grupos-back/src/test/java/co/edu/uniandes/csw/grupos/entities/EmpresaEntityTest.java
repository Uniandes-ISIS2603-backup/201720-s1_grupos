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
public class EmpresaEntityTest {
    /**
     *
     * @return Devuelve el jar que Arquillian va a desplegar en el Glassfish
     * embebido. El jar contiene las clases de Empresa, el descriptor de la
     * base de datos y el archivo beans.xml para resolver la inyección de
     * dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EmpresaEntity.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    

    /**
     * Test of nit methods, of class EmpresaEntity.
     */
    @Test
    public void testNit()  {
        PodamFactory factory= new PodamFactoryImpl();

        EmpresaEntity newEntity = factory.manufacturePojo(EmpresaEntity.class);
        newEntity.setNit(4);

        Assert.assertEquals(4, newEntity.getNit());

    }
    
    /**
     * Test of blog methods, of class EmpresaEntity.
     */
    @Test
    public void testRuta()  {
        PodamFactory factory= new PodamFactoryImpl();

        EmpresaEntity newEntity = factory.manufacturePojo(EmpresaEntity.class);
        newEntity.setLogo("RUTA");

        Assert.assertEquals("RUTA", newEntity.getLogo());

    }
    
    /**
     * Test of createEntity method, of class EmpresaPersistence.
     */
    @Test
    public void testNombre()  {
        PodamFactory factory= new PodamFactoryImpl();

        EmpresaEntity newEntity = factory.manufacturePojo(EmpresaEntity.class);
        newEntity.setNombre("A");
        Assert.assertEquals("A", newEntity.getNombre());

    }
     /**
     * Test del método equals
     */
    @Test
    public void testEquals()
    {
        PodamFactory factory= new PodamFactoryImpl();

        EmpresaEntity e=factory.manufacturePojo(EmpresaEntity.class);
        EmpresaEntity e2=factory.manufacturePojo(EmpresaEntity.class);
        e2.setNit(e.getNit());
        Assert.assertFalse(e.equals(new UsuarioEntity()));
        Assert.assertTrue(e.equals(e2));
        while(e2.equals(e))
        {
            e2.setNit((int)(Math.random()*1000));
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

        EmpresaEntity e=factory.manufacturePojo(EmpresaEntity.class);
        int hash = 3;
        hash = 53 * hash + e.getNit();
        Assert.assertEquals(hash,e.hashCode());
    }

    
    
}
