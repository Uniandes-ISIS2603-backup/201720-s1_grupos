/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.CategoriaEntity;
import co.edu.uniandes.csw.grupos.entities.GrupoEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.CategoriaPersistence;
import java.util.ArrayList;
import java.util.List;
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
public class CategoriaLogicTest {
    
    @Inject
    private CategoriaLogic logic;
    
    @Inject
    UserTransaction utx;
    
    /**
     * Manejador de entidades
     */
    @PersistenceContext
    private EntityManager em;
    
    private List<CategoriaEntity> data = new ArrayList<>();
    
    private List<GrupoEntity> dataG = new ArrayList<>();
    
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
                .addPackage(CategoriaEntity.class.getPackage())
                .addPackage(CategoriaPersistence.class.getPackage())
                .addPackage(CategoriaLogic.class.getPackage())
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
        em.createQuery("delete from GrupoEntity").executeUpdate();
        em.createQuery("delete from CategoriaEntity").executeUpdate();
    }
    
    /**
     * Inserta datos en el sistema para hacer las pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i<3; i++) {
            CategoriaEntity categoria = factory.manufacturePojo(CategoriaEntity.class);
            em.persist(categoria);
            data.add(categoria);
        }
        for (int i = 0; i < 3; i++) {
            GrupoEntity grupo = factory.manufacturePojo(GrupoEntity.class);
            List<CategoriaEntity> list = new ArrayList<>();
            for(int j = 0; j < 3-i; j++) {
                list.add(data.get(j));
            }
            grupo.setCategorias(list);
            em.persist(grupo);
            dataG.add(grupo);
        }
    }
    
    @Test
    public void testGetCategorias()  {
        List<CategoriaEntity> list = logic.getCategorias();
        compararListas(list, data);
    }
    
    @Test
    public void testCreateDeleteCategoria() {
        PodamFactory factory = new PodamFactoryImpl();
        CategoriaEntity categoria = factory.manufacturePojo(CategoriaEntity.class);
        categoria.setTipo(data.get(0).getTipo());
        try {
            logic.createCategoria(categoria);
            Assert.fail();
        }
        catch(BusinessException e) {
            //No se hace nada
        }
        categoria.setTipo(darNombreMasLargo()+"a");
        try {
            categoria = logic.createCategoria(categoria);
        }
        catch(BusinessException e) {
            Assert.fail();
        }
        Long id = categoria.getId();
        Long newId = (long)0;
        categoria.setId(newId);
        while(data.indexOf(categoria)>=0 || Long.compare(id, categoria.getId()) == 0) {
            newId = (long)((Math.random())*100);
            categoria.setId(newId);
        }
        try {
            logic.deleteCategoria(newId);
            Assert.fail();
        }
        catch(NotFoundException e) {
            //no pasa nada
        }
        try {
            logic.deleteCategoria(id);
        }
        catch(NotFoundException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testGetCategoria() {
        PodamFactory factory = new PodamFactoryImpl();
        CategoriaEntity categoria = factory.manufacturePojo(CategoriaEntity.class);
        while(data.indexOf(categoria)>=0) {
            categoria.setId((long)((Math.random())*100));
        }
        try {
            logic.getCategoria(categoria.getId());
            Assert.fail();
        }
        catch (NotFoundException e) {
            //no pasa nada
        }
        try {
            CategoriaEntity oldCategoria = data.get(0);
            categoria = logic.getCategoria(oldCategoria.getId());
            Assert.assertEquals(oldCategoria, categoria);
            Assert.assertEquals(oldCategoria.getDescripcion(), categoria.getDescripcion());
            Assert.assertEquals(oldCategoria.getTipo(), categoria.getTipo());
            Assert.assertEquals(oldCategoria.getRutaIcono(), categoria.getRutaIcono());
        }
        catch (NotFoundException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testUpdateCategoria() {
        PodamFactory factory = new PodamFactoryImpl();
        CategoriaEntity categoria = factory.manufacturePojo(CategoriaEntity.class);
        while(data.indexOf(categoria)>=0) {
            categoria.setId((long)((Math.random())*100));
        }
        try {
            logic.updateCategoria(categoria);
            Assert.fail();
        }
        catch (NotFoundException e) {
            //no pasa nada
        }
        categoria.setId(data.get(0).getId());
        try {
            CategoriaEntity newCategoria = categoria = logic.updateCategoria(categoria);
            Assert.assertEquals(newCategoria, categoria);
            Assert.assertEquals(newCategoria.getDescripcion(), categoria.getDescripcion());
            Assert.assertEquals(newCategoria.getTipo(), categoria.getTipo());
            Assert.assertEquals(newCategoria.getRutaIcono(), categoria.getRutaIcono());
        }
        catch (NotFoundException e) {
            Assert.fail();
        }
        
    }
    
    @Test
    public void testListGrupos() {
        List<GrupoEntity> list = logic.listGrupos(data.get(0).getId());
        compararListas(list, dataG);
    }
    
    @Test
    public void testGetGrupo() {
        Assert.assertNull(logic.getGrupo(dataG.get(1).getId(), data.get(2).getId()));
        Assert.assertNotNull(logic.getGrupo(dataG.get(0).getId(), data.get(0).getId()));
    }
    
    private void compararListas(List list1, List list2) {
        Assert.assertEquals(list1.size(), list2.size());
        for(int i = 0; i<3; i++) {
            Assert.assertTrue(list2.indexOf(list1.get(i))>=0);
        }
        
        for(int i = 0; i<3; i++) {
            Assert.assertTrue(list1.indexOf(list2.get(i))>=0);
        }
    }
    
    private String darNombreMasLargo() {
        String masLargo = "";
        for(int i = 0; i<3; i++) {
            String nombre = data.get(i).getTipo();
            if(nombre.length()>masLargo.length()) {
                masLargo=nombre;
            }
        }
        return masLargo;
    }
}
