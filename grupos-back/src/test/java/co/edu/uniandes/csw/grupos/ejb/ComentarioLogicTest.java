/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.entities.ComentarioEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.ComentarioPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJBException;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import javax.ws.rs.NotFoundException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author se.cardenas
 */
@RunWith(Arquillian.class)
public class ComentarioLogicTest {
    
    @Inject
    private ComentarioLogic logic;
    
    @Inject
    UserTransaction utx;
    
    /**
     * Manejador de entidades
     */
    @PersistenceContext
    private EntityManager em;
    
    private List<ComentarioEntity> data = new ArrayList<>();
    
    private NoticiaEntity n;
    
    private BlogEntity b;
    
    private GrupoEntity g;
    
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
                .addPackage(ComentarioEntity.class.getPackage())
                .addPackage(ComentarioPersistence.class.getPackage())
                .addPackage(ComentarioLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    @Before
    public void setUp() {
         try {
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    /**
     * Borra toda la información del sistema.
     */
    private void clearData() {
        em.createQuery("delete from BlogEntity").executeUpdate();
        em.createQuery("delete from NoticiaEntity").executeUpdate();
        em.createQuery("delete from GrupoEntity").executeUpdate();
        em.createQuery("delete from ComentarioEntity").executeUpdate();
    }
    
    /**
     * Inserta datos en el sistema para hacer las pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        g = factory.manufacturePojo(GrupoEntity.class);
        n = factory.manufacturePojo(NoticiaEntity.class);
        b = factory.manufacturePojo(BlogEntity.class);
        em.persist(g);
        em.persist(n);
        b.setGrupo(g);
        
        for(int i = 0; i<3; i++) {
            ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
            em.persist(comentario);
            data.add(comentario);
        }
        List<ComentarioEntity> list = new ArrayList<>();
        for(int i = 0; i<2; i++) {
            list.add(data.get(i));
        }
        b.setComentarios(list);
        b = em.merge(b);
        
        list = new ArrayList<>();
        list.add(data.get(2));
        n.setComentarios(list);
        n = em.merge(n);
    }
    
    @Test
    public void testCreateDeleteComentario() {
        try {
            logic.createComentario(null);
            Assert.fail();
        }
        catch (BusinessException e) {
            //no pasa nada
        }
        ComentarioEntity comentario = new ComentarioEntity();
        try {
            logic.createComentario(comentario);
            Assert.fail();
        }
        catch (BusinessException e) {
            //no pasa nada
        }
        PodamFactory factory = new PodamFactoryImpl();
        comentario = factory.manufacturePojo(ComentarioEntity.class);
        try {
            comentario = logic.createComentario(comentario);
        }
        catch (BusinessException e) {
            Assert.fail();
        }
        
        Long id = comentario.getId();
        while(data.indexOf(comentario)>=0 || Long.compare(id, comentario.getId()) == 0) {
            comentario.setId((long)((Math.random())*100));
        }
        try {
            logic.deleteComentario(comentario.getId());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        try {
            logic.deleteComentario(id);
        }
        catch(NotFoundException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testCreateDeleteComentarioBlog() {
        PodamFactory factory = new PodamFactoryImpl();
        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        try {
            comentario = logic.createComentarioBlog(g.getId(), b.getId(), comentario);
        }
        catch (BusinessException e) {
            Assert.fail();
        }
        
        Long id = comentario.getId();
        while(data.indexOf(comentario)>=0 || Long.compare(id, comentario.getId()) == 0) {
            comentario.setId((long)((Math.random())*100));
        }
        
        try {
            logic.deleteComentarioBlog(g.getId(), b.getId(), comentario.getId());
            Assert.fail();
        }
        catch (NotFoundException | EJBException e) {
            //no pasa nada
        }
        
        try {
            logic.deleteComentarioBlog(g.getId(), b.getId(), id);
        }
        catch (NotFoundException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testCreateDeleteComentarioNoticia() {
        PodamFactory factory = new PodamFactoryImpl();
        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        try {
            comentario = logic.createComentarioNoticia(n.getId(), comentario);
        }
        catch (BusinessException e) {
            Assert.fail();
        }
        
        Long id = comentario.getId();
        while(data.indexOf(comentario)>=0 || Long.compare(id, comentario.getId()) == 0) {
            comentario.setId((long)((Math.random())*100));
        }
        
        try {
            logic.deleteComentarioNoticia(n.getId(), comentario.getId());
            Assert.fail();
        }
        catch (NotFoundException | EJBException e) {
            //no pasa nada
        }
        catch (BusinessException e1) {
            Assert.fail();
        }
        
        try {
            logic.deleteComentarioNoticia(n.getId(), id);
        }
        catch (NotFoundException | BusinessException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testGetComentarios() {
        compararListas(data, logic.getComentarios());
    }
    
    @Test
    public void testGetComentariosBlog() {
        List<ComentarioEntity> list = new ArrayList<>();
        list.add(data.get(0));
        list.add(data.get(1));
        compararListas(logic.getComentariosBlog(g.getId(), b.getId()), list);
        
        
        try {
            logic.getComentarioBlog(g.getId(), b.getId(), darIdNoUsado());
            Assert.fail();
        }
        catch (NotFoundException | EJBException e) {
            //no pasa nada
        }
        try {
            ComentarioEntity entity = logic.getComentarioBlog(g.getId(), b.getId(), data.get(0).getId());
            Assert.assertEquals(entity, data.get(0));
        }
        catch (NotFoundException | EJBException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testGetComentariosNoticia() {
        try {
            logic.getComentariosNoticia(n.getId()+1);
            Assert.fail();
        }
        catch(NotFoundException | EJBException e1) {
            //no pasa nada
        }
        catch(BusinessException e2) {
            Assert.fail();
        }
        List<ComentarioEntity> list = new ArrayList<>();
        list.add(data.get(2));
        try {
            compararListas(logic.getComentariosNoticia(n.getId()), list);
        }
        catch (BusinessException e) {
            Assert.fail();
        }
        
        
        try {
            logic.getComentarioNoticia(n.getId(), darIdNoUsado());
            Assert.fail();
        }
        catch (NotFoundException | EJBException e) {
            //no pasa nada
        }
        catch (BusinessException e1) {
            Assert.fail();
        }
        try {
            ComentarioEntity entity = logic.getComentarioNoticia(n.getId(), data.get(2).getId());
            Assert.assertEquals(entity, data.get(2));
        }
        catch (NotFoundException | EJBException | BusinessException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testUpdateComentario() {
        PodamFactory factory = new PodamFactoryImpl();
        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        comentario.setId(darIdNoUsado());
        try {
            logic.updateComentario(comentario);
            Assert.fail();
        }
        catch (NotFoundException | EJBException e) {
            //no pasa nada
        }
        comentario.setId(data.get(0).getId());
        ComentarioEntity newComentario = logic.updateComentario(comentario);
        Assert.assertEquals(comentario, newComentario);
        Assert.assertEquals(comentario.getComentario(), newComentario.getComentario());
        Assert.assertEquals(comentario.getAutor(), newComentario.getAutor());
        logic.updateComentario(data.get(0));
    }
    
    @Test
    public void testUpdateComentarioBlog() {
        PodamFactory factory = new PodamFactoryImpl();
        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        comentario.setId(data.get(0).getId());
        ComentarioEntity newComentario = logic.updateComentarioBlog(g.getId(), b.getId(), comentario);
        Assert.assertEquals(comentario, newComentario);
        Assert.assertEquals(comentario.getComentario(), newComentario.getComentario());
        Assert.assertEquals(comentario.getAutor(), newComentario.getAutor());
        logic.updateComentario(data.get(0));
    }
    
    @Test
    public void testUpdateComentarioNoticia() {
        PodamFactory factory = new PodamFactoryImpl();
        ComentarioEntity comentario = factory.manufacturePojo(ComentarioEntity.class);
        comentario.setId(data.get(2).getId());
        try {
            ComentarioEntity newComentario = logic.updateComentarioNoticia(n.getId(), comentario);
            Assert.assertEquals(comentario, newComentario);
            Assert.assertEquals(comentario.getComentario(), newComentario.getComentario());
            Assert.assertEquals(comentario.getAutor(), newComentario.getAutor());
            logic.updateComentario(data.get(2));
        }
        catch (BusinessException e) {
            Assert.fail();
        }
    }
    
    private void compararListas(List list1, List list2) {
        Assert.assertEquals(list1.size(), list2.size());
        for(int i = 0; i<list1.size(); i++) {
            Assert.assertTrue(list2.indexOf(list1.get(i))>=0);
        }
        
        for(int i = 0; i<list1.size(); i++) {
            Assert.assertTrue(list1.indexOf(list2.get(i))>=0);
        }
    }
    
    private Long darIdNoUsado() {
        ComentarioEntity entity = new ComentarioEntity();
        entity.setId((long)0);
        while(data.indexOf(entity)>=0) {
            entity.setId((long)((Math.random())*100));
        }
        return entity.getId();
    }
}
