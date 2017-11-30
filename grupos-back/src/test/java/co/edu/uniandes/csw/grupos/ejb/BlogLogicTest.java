/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.entities.CalificacionEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.entities.MultimediaEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.BlogPersistence;
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
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author se.cardenas
 */
@RunWith(Arquillian.class)
public class BlogLogicTest {
    @Inject
    private BlogLogic logic;
    
    @Inject
    UserTransaction utx;
    
    /**
     * Manejador de entidades
     */
    @PersistenceContext
    private EntityManager em;
    
    private List<BlogEntity> data = new ArrayList<>();
    
    private List<CalificacionEntity> dataC = new ArrayList<>();
    
    private List<MultimediaEntity> dataM = new ArrayList<>();
    
    private GrupoEntity g;
    
    private MultimediaEntity m;
    
    private MultimediaEntity m2;
    
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
                .addPackage(BlogEntity.class.getPackage())
                .addPackage(BlogPersistence.class.getPackage())
                .addPackage(BlogLogic.class.getPackage())
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
        em.createQuery("delete from BlogEntity").executeUpdate();
        em.createQuery("delete from MultimediaEntity").executeUpdate();
        em.createQuery("delete from GrupoEntity").executeUpdate();
    }
    
    /**
     * Inserta datos en el sistema para hacer las pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        g = factory.manufacturePojo(GrupoEntity.class);
        em.persist(g);
        for(int i = 0; i<3; i++) {
            BlogEntity blog = factory.manufacturePojo(BlogEntity.class);
            blog.setGrupo(g);
            List<MultimediaEntity> multimediaList = new ArrayList<>();
            if(i==0) {
                for(int j = 0; j<2; j++) {
                    MultimediaEntity multi = factory.manufacturePojo(MultimediaEntity.class);
                    multi.setLink(darLinkNoUsado());
                    em.persist(multi);
                    dataM.add(multi);
                    multimediaList.add(multi);
            }
            MultimediaEntity multi = factory.manufacturePojo(MultimediaEntity.class);
            multi.setLink(darLinkNoUsado());
            em.persist(multi);
            m = multi;
            multi = factory.manufacturePojo(MultimediaEntity.class);
            multi.setLink(darLinkNoUsado());
            while(multi.equals(m)) {
                multi.setLink(darLinkNoUsado());
            }
            em.persist(multi);
            m2 = multi;
            
            blog.setMultimedia(multimediaList);
            em.persist(blog);
            data.add(blog);
            }
        }
        
        for(int i = 0; i<3; i++) {
            CalificacionEntity calif = factory.manufacturePojo(CalificacionEntity.class);
            calif.setBlog(data.get(0));
            em.persist(calif);
            dataC.add(calif);
        }
    }
    
    @Test
    public void testGetBlogs() {
        compararListas(data, logic.getBlogs());
        compararListas(data, logic.getBlogs(g.getId()));
    }
    
    @Test
    public void testGetBlog() {
        try {
            logic.getBlog(darIdNoUsado());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        
        try {
            BlogEntity blog = logic.getBlog(data.get(0).getId());
            Assert.assertEquals(data.get(0), blog);
        }
        catch(NotFoundException | EJBException e) {
            Assert.fail();
        }
        
        try {
            logic.getBlog(g.getId()+1, data.get(0).getId());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        
        try {
            BlogEntity blog = logic.getBlog(g.getId(), data.get(0).getId());
            Assert.assertEquals(data.get(0), blog);
        }
        catch(NotFoundException | EJBException e) {
            Assert.fail();
        }
    }
    
    
    
    @Test
    public void testCreateUpdateDeleteBlog() {
        try {
            logic.createBlog(null, g.getId());
            Assert.fail();
        }
        catch(BusinessException e) {
            //no pasa nada
        }
        
        PodamFactory factory = new PodamFactoryImpl();
        BlogEntity blog = factory.manufacturePojo(BlogEntity.class);
        blog.setTitulo(null);
        try {
            logic.createBlog(blog, g.getId());
            Assert.fail();
        }
        catch(BusinessException e) {
            //no pasa nada
        }
        
        blog = factory.manufacturePojo(BlogEntity.class);
        try {
            blog = logic.createBlog(blog, g.getId());
        }
        catch(BusinessException e) {
            Assert.fail();
        }
        
        
        BlogEntity newBlog = factory.manufacturePojo(BlogEntity.class);
        newBlog.setId(darIdNoUsado());
        while(newBlog.equals(blog)) {
            newBlog.setId(darIdNoUsado());
        }
        
        try {
            logic.updateBlog(newBlog, g.getId());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        newBlog.setId(blog.getId());
        logic.updateBlog(newBlog, g.getId());
        
        newBlog = factory.manufacturePojo(BlogEntity.class);
        newBlog.setId(darIdNoUsado());
        while(newBlog.equals(blog)) {
            newBlog.setId(darIdNoUsado());
        }
        try {
            logic.deleteBlog(g.getId(), newBlog.getId());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        try {
            logic.deleteBlog(g.getId()+1, blog.getId());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        logic.deleteBlog(g.getId(), blog.getId());
    }
    
    @Test
    public void testGetMultimedia() {
        try {
            compararListas(dataM, logic.getMultimedia(data.get(0).getId()));
        }
        catch(BusinessException e) {
            Assert.fail();
        }
        
        try {
            logic.getMultimedia(data.get(0).getId(), darLinkNoUsado());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        try {
            Assert.assertEquals(dataM.get(0), logic.getMultimedia(data.get(0).getId(), dataM.get(0).getLink()));
        }
        catch(NotFoundException | EJBException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testAddUpdateDeleteMultimedia() {
        logic.addMultimedia(data.get(0).getId(), m.getLink());
        
        
        String link = darLinkNoUsado();
        while(link.equals(m.getLink()) || link.equals(m2.getLink())) {
            link = darLinkNoUsado();
        }
        PodamFactory factory = new PodamFactoryImpl();
        MultimediaEntity multi = factory.manufacturePojo(MultimediaEntity.class);
        try {
            logic.updateMultimedia(g.getId(), data.get(0).getId(), multi, darLinkNoUsado());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        catch(BusinessException e1) {
            Assert.fail();
        }
        try {
            logic.updateMultimedia(g.getId(), data.get(0).getId(), multi, m2.getLink());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        catch(BusinessException e1) {
            Assert.fail();
        }
        try {
            logic.updateMultimedia(g.getId(), data.get(0).getId(), multi, m.getLink());
        }
        catch(NotFoundException | EJBException |BusinessException e) {
            Assert.fail();
        }
        
        
        link = darLinkNoUsado();
        while(link.equals(m.getLink()) || link.equals(m2.getLink())) {
            link = darLinkNoUsado();
        }
        try {
            logic.deleteMultimedia(data.get(0).getId(), link);
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        catch(BusinessException e1) {
            Assert.fail();
        }
        try {
            logic.deleteMultimedia(data.get(0).getId(), m2.getLink());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        catch(BusinessException e1) {
            Assert.fail();
        }
        try {
            logic.deleteMultimedia(data.get(0).getId(), m.getLink());
        }
        catch(NotFoundException | EJBException | BusinessException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testGetCalificacion() {
        compararListas(dataC, logic.getCalificaciones(data.get(0).getId()));
        
        try {
            logic.getCalificacion(data.get(0).getId(), darIdCalifNoUsado());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        logic.getCalificacion(data.get(0).getId(), dataC.get(0).getId());
    }
    
    @Test
    public void testUpdateCalificacion() {
        PodamFactory factory = new PodamFactoryImpl();
        CalificacionEntity calif = factory.manufacturePojo(CalificacionEntity.class);
        try {
            logic.updateCalificacion(g.getId(), data.get(0).getId(), darIdCalifNoUsado(), calif);
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        catch(BusinessException e1) {
            Assert.fail();
        }
    }
    
    @Test
    public void testRemoveCalificacion() {
        try {
            logic.removeCalificacino(g.getId(), data.get(0).getId(), darIdCalifNoUsado());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        catch(BusinessException e1) {
            Assert.fail();
        }
        
        try {
            logic.removeCalificacino(g.getId(), data.get(0).getId(), dataC.get(dataC.size()-1).getId());
            dataC.remove(dataC.size()-1);
        }
        catch(NotFoundException | EJBException | BusinessException e) {
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
        BlogEntity entity = new BlogEntity();
        entity.setId((long)0);
        while(data.indexOf(entity)>=0) {
            entity.setId((long)((Math.random())*100));
        }
        return entity.getId();
    }
    
    private Long darIdCalifNoUsado() {
        CalificacionEntity entity = new CalificacionEntity();
        entity.setId((long)0);
        while(dataC.indexOf(entity)>=0) {
            entity.setId((long)((Math.random())*100));
        }
        return entity.getId();
    }
    
    private String darLinkNoUsado() {
        PodamFactory factory = new PodamFactoryImpl();
        MultimediaEntity entity = factory.manufacturePojo(MultimediaEntity.class);
        while(dataM.contains(entity)) {
            entity = factory.manufacturePojo(MultimediaEntity.class);
        }
        return entity.getLink();
    }
}
