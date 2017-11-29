/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;

import java.util.ArrayList;
import java.util.List;
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
 *
 * @author se.cardenas
 */
@RunWith(Arquillian.class)
public class BlogEntityTest {
    
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
     * Test de la lista de usuarios que tienen como favorito un blog
     */
    @Test
    public void testUsuarios() {
        PodamFactory factory= new PodamFactoryImpl();

        BlogEntity newEntity = factory.manufacturePojo(BlogEntity.class);
        
        List<UsuarioEntity> list = new ArrayList<>();
        UsuarioEntity u1 = factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity u2 = factory.manufacturePojo(UsuarioEntity.class);
        UsuarioEntity u3 = factory.manufacturePojo(UsuarioEntity.class);
        list.add(u1);
        list.add(u2);
        list.add(u3);
        
        newEntity.setUsuarios(list);
        list = newEntity.getUsuarios();

        Assert.assertEquals(u1, list.get(0));
        Assert.assertEquals(u2, list.get(1));
        Assert.assertEquals(u3, list.get(2));
    }
    
    /***
     * test del método equals
     */
    @Test
    public void testEquals() {
        PodamFactory factory= new PodamFactoryImpl();

        BlogEntity newEntity = factory.manufacturePojo(BlogEntity.class);
        
        Assert.assertTrue(newEntity.equals(newEntity));
        Assert.assertFalse(newEntity.equals(null));
        Assert.assertFalse(newEntity.equals(factory.manufacturePojo(UsuarioEntity.class)));
        
        BlogEntity newEntity2 = factory.manufacturePojo(BlogEntity.class);
        newEntity2.setId(newEntity.getId());
        Assert.assertTrue(newEntity.equals(newEntity2));
    }
}
