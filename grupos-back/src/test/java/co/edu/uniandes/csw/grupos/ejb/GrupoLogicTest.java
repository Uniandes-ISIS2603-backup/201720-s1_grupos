/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.BlogEntity;
import co.edu.uniandes.csw.grupos.entities.CategoriaEntity;
import co.edu.uniandes.csw.grupos.entities.EventoEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.entities.NoticiaEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.GrupoPersistence;
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
public class GrupoLogicTest {
    @Inject
    private GrupoLogic logic;
    
    @Inject
    UserTransaction utx;
    
    /**
     * Manejador de entidades
     */
    @PersistenceContext
    private EntityManager em;
    
    private List<CategoriaEntity> dataC = new ArrayList<>();
    
    private List<GrupoEntity> data = new ArrayList<>();
    
    private List<UsuarioEntity> dataU = new ArrayList<>();
    
    private List<BlogEntity> dataB = new ArrayList<>();
    
    private List<NoticiaEntity> dataN = new ArrayList<>();
    
    private List<EventoEntity> dataE = new ArrayList<>();
    
    private NoticiaEntity n;
    
    private UsuarioEntity u;
    
    private EventoEntity e;
    
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
                .addPackage(GrupoEntity.class.getPackage())
                .addPackage(GrupoPersistence.class.getPackage())
                .addPackage(GrupoLogic.class.getPackage())
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
        em.createQuery("delete from NoticiaEntity").executeUpdate();
        em.createQuery("delete from BlogEntity").executeUpdate();
        em.createQuery("delete from EventoEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
        em.createQuery("delete from GrupoEntity").executeUpdate();
        em.createQuery("delete from CategoriaEntity").executeUpdate();
    }
    
    /**
     * Inserta datos en el sistema para hacer las pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        n = factory.manufacturePojo(NoticiaEntity.class);
        em.persist(n);
        u = factory.manufacturePojo(UsuarioEntity.class);
        em.persist(u);
        e = factory.manufacturePojo(EventoEntity.class);
        em.persist(e);
        for(int i = 0; i<2; i++) {
            CategoriaEntity cat = factory.manufacturePojo(CategoriaEntity.class);
            em.persist(cat);
            dataC.add(cat);
        }
        for(int i = 0; i<3; i++) {
            UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(usuario);
            dataU.add(usuario);
        }
        for(int i = 0; i<3; i++) {
            GrupoEntity grupo = factory.manufacturePojo(GrupoEntity.class);
            grupo.setNombre(darNombreNoUsado());
            List<CategoriaEntity> list = new ArrayList<>();
            list.add(dataC.get(0));
            grupo.setCategorias(list);
            if(i==0) {
                grupo.setMiembros(dataU);
                List<UsuarioEntity> admins = new ArrayList<>();
                admins.add(dataU.get(0));
                grupo.setAdministradores(admins);
            }
            em.persist(grupo);
            data.add(grupo);
        }
        for(int i = 0; i<3; i++) {
            BlogEntity blog = factory.manufacturePojo(BlogEntity.class);
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            NoticiaEntity noticia = factory.manufacturePojo(NoticiaEntity.class);
            blog.setGrupo(data.get(0));
            evento.setGrupo(data.get(0));
            noticia.setGrupo(data.get(0));
            em.persist(blog);
            em.persist(evento);
            em.persist(noticia);
            dataB.add(blog);
            dataE.add(evento);
            dataN.add(noticia);
        }
    }
    
    @Test
    public void testGetGrupo() {
        compararListas(data, logic.getGrupos());
        
        try {
            logic.getGrupo(darIdNoUsado());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        try {
            Assert.assertEquals(data.get(0), logic.getGrupo(data.get(0).getId()));
        }
        catch(NotFoundException | EJBException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testGetGrupoNombre() {
        try {
            logic.getGrupo(darNombreNoUsado());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        try {
            GrupoEntity newGrupo = logic.getGrupo(data.get(0).getNombre());
            Assert.assertEquals(data.get(0), newGrupo);
            Assert.assertEquals(data.get(0).getNombre(), newGrupo.getNombre());
        }
        catch(NotFoundException | EJBException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testCreateEntity() {
        PodamFactory factory = new PodamFactoryImpl();
        GrupoEntity grupo = factory.manufacturePojo(GrupoEntity.class);
        grupo.setNombre(data.get(0).getNombre());
        try {
            logic.createGrupo(grupo);
            Assert.fail();
        }
        catch(BusinessException e) {
            //no pasa nada
        }
        grupo.setNombre(darNombreNoUsado());
        try {
            grupo = logic.createGrupo(grupo);
        }
        catch(BusinessException e) {
            Assert.fail();
        }
        
        
        GrupoEntity newGrupo = factory.manufacturePojo(GrupoEntity.class);
        newGrupo.setId(darIdNoUsado());
        while(newGrupo.equals(grupo)) {
            newGrupo.setId(darIdNoUsado());
        }
        try {
            logic.updateGrupo(newGrupo);
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        newGrupo.setId(grupo.getId());
        try {
            logic.updateGrupo(newGrupo);
        }
        catch(NotFoundException | EJBException e) {
            Assert.fail();
        }
        
        
        newGrupo.setId(darIdNoUsado());
        while(newGrupo.equals(grupo)) {
            newGrupo.setId(darIdNoUsado());
        }
        try {
            logic.deleteGrupo(newGrupo.getId());
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        try {
            logic.deleteGrupo(grupo.getId());
        }
        catch(NotFoundException | EJBException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testGetCategoria() {
        List<CategoriaEntity> list = logic.listCategorias(data.get(0).getId());
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(dataC.get(0), list.get(0));
        
        Assert.assertEquals(dataC.get(0), logic.getCategoria(data.get(0).getId(), dataC.get(0).getId()));
        Assert.assertNull(logic.getCategoria(data.get(0).getId(), dataC.get(1).getId()));
    }
    
    @Test
    public void testAddRemoveCategoria() {
        logic.addCategoria(data.get(0).getId(), dataC.get(1).getId());
        compararListas(dataC, logic.listCategorias(data.get(0).getId()));
        
        logic.removeCategoria(data.get(0).getId(), dataC.get(1).getId());
        List<CategoriaEntity> list = logic.listCategorias(data.get(0).getId());
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(dataC.get(0), list.get(0));
    }
    
    @Test
    public void testGetMiembros() {
        compararListas(dataU, logic.listMiembros(data.get(0).getId()));
        
        Assert.assertNull(logic.getMiembro(data.get(0).getId(), darIdMiembroNoUsado()));
        Assert.assertEquals(dataU.get(0), logic.getMiembro(data.get(0).getId(), dataU.get(0).getId()));
    }
    
    @Test
    public void testAddRemoveMiembro() {
        logic.removeMiembro(data.get(0).getId(), dataU.get(2).getId());
        Assert.assertNull(logic.getMiembro(data.get(0).getId(), dataU.get(2).getId()));
        
        try {
            logic.addMiembro(data.get(0).getId(), dataU.get(2).getId());
            Assert.assertEquals(dataU.get(2), logic.getMiembro(data.get(0).getId(), dataU.get(2).getId()));
        }
        catch(BusinessException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testGetAdmins() {
        List<UsuarioEntity> list = logic.listAdmins(data.get(0).getId());
        Assert.assertEquals(1, list.size());
        Assert.assertEquals(dataU.get(0), list.get(0));
        
        Assert.assertNull(logic.getAdmin(data.get(0).getId(), dataU.get(1).getId()));
        Assert.assertEquals(dataU.get(0), logic.getAdmin(data.get(0).getId(), dataU.get(0).getId()));
    }
    
    @Test
    public void testAddRemoveAdmin() {
        try {
            logic.addAdmin(data.get(0).getId(), dataU.get(2).getId());
            Assert.assertEquals(dataU.get(2), logic.getMiembro(data.get(0).getId(), dataU.get(2).getId()));
        }
        catch(BusinessException e) {
            Assert.fail();
        }
        
        logic.removeAdmin(data.get(0).getId(), dataU.get(2).getId());
        Assert.assertNull(logic.getAdmin(data.get(0).getId(), dataU.get(2).getId()));
    }
    
    @Test
    public void testGetBlogs() {
        compararListas(dataB, logic.listBlogs(data.get(0).getId()));
        
        Assert.assertNull(logic.getBlog(data.get(0).getId(), darIdBlogNoUsado()));
        Assert.assertEquals(dataB.get(0), logic.getBlog(data.get(0).getId(), dataB.get(0).getId()));
    }
    
    @Test
    public void testRemoveBlog() {
        logic.removeBlog(data.get(0).getId(), dataB.get(2).getId());
        Assert.assertNull(logic.getBlog(data.get(0).getId(), dataB.get(2).getId()));
        dataB.remove(2);
    }
    
    @Test
    public void testGetNoticias() {
        compararListas(dataN, logic.listNoticias(data.get(0).getId()));
        
        Assert.assertNull(logic.getNoticia(data.get(0).getId(), darIdNoticiaNoUsado()));
        Assert.assertEquals(dataN.get(0), logic.getNoticia(data.get(0).getId(), dataN.get(0).getId()));
    }
    
    @Test
    public void testAddDelete() {
        try {
            logic.addNoticia(data.get(0).getId(), n.getId());
        }
        catch(BusinessException e) {
            Assert.fail();
        }
        
        try {
            logic.deleteNoticia(data.get(0).getId(), n.getId());
            Assert.assertNull(logic.getNoticia(data.get(0).getId(), n.getId()));
        }
        catch(BusinessException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testCreateUpdateDelete() {
        PodamFactory factory = new PodamFactoryImpl();
        NoticiaEntity noticia = factory.manufacturePojo(NoticiaEntity.class);
        noticia.setAutor(u);
        try {
            noticia = logic.createNoticia(data.get(0).getId(), noticia);
            Assert.assertEquals(noticia, logic.getNoticia(data.get(0).getId(), noticia.getId()));
        }
        catch(BusinessException e) {
            Assert.fail();
        }
        
        
        NoticiaEntity newNoticia = factory.manufacturePojo(NoticiaEntity.class);
        newNoticia.setAutor(u);
        try {
            NoticiaEntity not2 = logic.updateNoticia(data.get(0).getId(), noticia.getId(), newNoticia);
            newNoticia.setId(noticia.getId());
            Assert.assertEquals(newNoticia, not2);
        }
        catch(BusinessException e) {
            Assert.fail();
        }
        
        try {
            logic.deleteNoticia(data.get(0).getId(), newNoticia.getId());
            Assert.assertNull(logic.getNoticia(data.get(0).getId(), newNoticia.getId()));
        }
        catch(BusinessException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testGetEventos() {
        compararListas(dataE, logic.listEventos(data.get(0).getId()));
        
        try {
            logic.getEvento(data.get(0).getId(), darIdEventoNoUsado());
            Assert.fail();
        }
        catch(NotFoundException e) {
            //no pasa nada
        }
        Assert.assertEquals(dataE.get(0), logic.getEvento(data.get(0).getId(), dataE.get(0).getId()));
    }
    
    @Test
    public void testAddRemoveEvento() {
        try {
            logic.addEvento(data.get(0).getId(), e.getId());
            Assert.assertEquals(e, logic.getEvento(data.get(0).getId(), e.getId()));
        }
        catch(BusinessException e) {
            Assert.fail();
        }
        
        
        try {
            logic.removeEvento(data.get(0).getId(), darIdEventoNoUsado());
            Assert.fail();
        }
        catch(NotFoundException e) {
            //no pasa nada
        }
        try {
            logic.removeEvento(data.get(0).getId(), e.getId());
            try {
                logic.getEvento(data.get(0).getId(), e.getId());
                Assert.fail();
            }
            catch(NotFoundException e) {
                //no pasa nada
            }
        }
        catch(NotFoundException e) {
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
        GrupoEntity entity = new GrupoEntity();
        entity.setId((long)0);
        while(data.indexOf(entity)>=0) {
            entity.setId((long)((Math.random())*100));
        }
        return entity.getId();
    }
    
    private Long darIdMiembroNoUsado() {
        UsuarioEntity entity = new UsuarioEntity();
        entity.setId((long)0);
        while(dataU.indexOf(entity)>=0) {
            entity.setId((long)((Math.random())*100));
        }
        return entity.getId();
    }
    
    private Long darIdBlogNoUsado() {
        BlogEntity entity = new BlogEntity();
        entity.setId((long)0);
        while(dataB.indexOf(entity)>=0) {
            entity.setId((long)((Math.random())*100));
        }
        return entity.getId();
    }
    
    private Long darIdNoticiaNoUsado() {
        NoticiaEntity entity = new NoticiaEntity();
        entity.setId((long)0);
        while(dataN.indexOf(entity)>=0 || n.equals(entity)) {
            entity.setId((long)((Math.random())*100));
        }
        return entity.getId();
    }
    
    private Long darIdEventoNoUsado() {
        EventoEntity entity = new EventoEntity();
        entity.setId((long)0);
        while(dataE.indexOf(entity)>=0 || e.equals(entity)) {
            entity.setId((long)((Math.random())*100));
        }
        return entity.getId();
    }
    
    private String darNombreNoUsado() {
        String nombre = "nombre";
        boolean band = false;
        while(!band) {
            band = true;
            for(GrupoEntity g : data) {
                if(g.getNombre().equals(nombre)) {
                    band = false;
                    nombre = nombre+"a";
                }
            }
        }
        return nombre;
    }
}
