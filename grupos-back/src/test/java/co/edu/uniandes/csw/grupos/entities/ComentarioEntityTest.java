/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;

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
public class ComentarioEntityTest {
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
    
    /***
     * test del método equals
     */
    @Test
    public void testEquals() {
        PodamFactory factory= new PodamFactoryImpl();

        ComentarioEntity newEntity = factory.manufacturePojo(ComentarioEntity.class);
        
        Assert.assertTrue(newEntity.equals(newEntity));
        Assert.assertFalse(newEntity.equals(null));
        Assert.assertFalse(newEntity.equals(factory.manufacturePojo(UsuarioEntity.class)));
        
        ComentarioEntity newEntity2 = factory.manufacturePojo(ComentarioEntity.class);
        newEntity2.setId(newEntity.getId());
        Assert.assertTrue(newEntity.equals(newEntity2));
    }
}
