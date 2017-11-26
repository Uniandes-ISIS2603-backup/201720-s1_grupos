/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.entities;


import java.util.ArrayList;
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
public class NoticiaEntityTest {
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
                .addPackage(NoticiaEntity.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    

    /**
     * Test of title methods, of class NoticiaEntity.
     */
    @Test
    public void testTitulo()  {
        PodamFactory factory= new PodamFactoryImpl();

        NoticiaEntity newEntity = factory.manufacturePojo(NoticiaEntity.class);
        newEntity.setTitulo("A");

        Assert.assertEquals("A", newEntity.getTitulo());

    }
    
    /**
     * Test of information methods, of class NoticiaEntity.
     */
    @Test
    public void testInformacion()  {
        PodamFactory factory= new PodamFactoryImpl();

        NoticiaEntity newEntity = factory.manufacturePojo(NoticiaEntity.class);
        newEntity.setInformacion("A");

        Assert.assertEquals("A", newEntity.getInformacion());

    }
    
    /**
     * Test of author  methods, of class NoticiaEntity.
     */
    @Test
    public void testAutor()  {
        PodamFactory factory= new PodamFactoryImpl();

        NoticiaEntity newEntity = factory.manufacturePojo(NoticiaEntity.class);
        UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
        newEntity.setAutor(usuario);

        
        Assert.assertEquals(usuario, newEntity.getAutor());

    }
    
    /**
     * Test of group methods, of class NoticiaEntity.
     */
    @Test
    public void testGrupo()  {
        PodamFactory factory= new PodamFactoryImpl();
        
        NoticiaEntity newEntity = factory.manufacturePojo(NoticiaEntity.class);
        GrupoEntity grupo = factory.manufacturePojo(GrupoEntity.class);
        newEntity.setGrupo(grupo);

        Assert.assertEquals(grupo, newEntity.getGrupo());

    }
    
    /**
     * Test of id methods, of class NoticiaEntity.
     */
    @Test
    public void testId()  {
        PodamFactory factory= new PodamFactoryImpl();

        NoticiaEntity newEntity = factory.manufacturePojo(NoticiaEntity.class);
        newEntity.setId(4l);

        Assert.assertEquals(new Long(4l), newEntity.getId());

    }
    /**
     * Test of multimedia methods of class NoticiaEntity
     */
    @Test
    public void testMultimedia()
    {
        PodamFactory factory= new PodamFactoryImpl();
        
        NoticiaEntity entity = factory.manufacturePojo(NoticiaEntity.class);
        ArrayList<MultimediaEntity> list = new ArrayList<>();
        for(int i=0;i<4;i++)
        {
            list.add(factory.manufacturePojo(MultimediaEntity.class));
        }
        entity.setMultimedia(list);
        for(int i=0;i<4;i++)
        {
            Assert.assertEquals(list.get(i), entity.getMultimedia().get(i));
        }
        
    }
    
    /**
     * Test of comments methods of class NoticiaEntity
     */
    @Test
    public void testComentario()
    {
        PodamFactory factory= new PodamFactoryImpl();
        
        NoticiaEntity entity = factory.manufacturePojo(NoticiaEntity.class);
        
        ArrayList<ComentarioEntity> list = new ArrayList<>();
        for(int i=0;i<4;i++)
        {
            list.add(factory.manufacturePojo(ComentarioEntity.class));
        }
        entity.setComentarios(list);
        for(int i=0;i<4;i++)
        {
            Assert.assertEquals(list.get(i), entity.getComentarios().get(i));
        }
        
    }
     /**
     * Test del método equals
     */
    @Test
    public void testEquals()
    {
        PodamFactory factory= new PodamFactoryImpl();

        NoticiaEntity e=factory.manufacturePojo(NoticiaEntity.class);
        NoticiaEntity e2=factory.manufacturePojo(NoticiaEntity.class);
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

        NoticiaEntity e=factory.manufacturePojo(NoticiaEntity.class);
        int hash = 3;
        hash = 61 * hash + Objects.hashCode(e.getId());
        Assert.assertEquals(hash,e.hashCode());
    }

    
    
}
