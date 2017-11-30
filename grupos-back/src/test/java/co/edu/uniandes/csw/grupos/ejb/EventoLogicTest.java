/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.entities.EventoEntity;
import co.edu.uniandes.csw.grupos.entities.LugarEntity;
import co.edu.uniandes.csw.grupos.entities.PatrocinioEntity;
import co.edu.uniandes.csw.grupos.entities.UsuarioEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import co.edu.uniandes.csw.grupos.persistence.EventoPersistence;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
public class EventoLogicTest {
    @Inject
    private EventoLogic logic;
    
    @Inject
    UserTransaction utx;
    
    /**
     * Manejador de entidades
     */
    @PersistenceContext
    private EntityManager em;
    
    private List<EventoEntity> data = new ArrayList<>();
    
    private List<UsuarioEntity> dataU = new ArrayList<>();
    
    private List<PatrocinioEntity> dataP = new ArrayList<>();
    
    private LugarEntity l;
    
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
                .addPackage(EventoEntity.class.getPackage())
                .addPackage(EventoPersistence.class.getPackage())
                .addPackage(EventoLogic.class.getPackage())
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
        em.createQuery("delete from EventoEntity").executeUpdate();
        em.createQuery("delete from UsuarioEntity").executeUpdate();
        em.createQuery("delete from PatrocinioEntity").executeUpdate();
        em.createQuery("delete from LugarEntity").executeUpdate();
    }
    
    /**
     * Inserta datos en el sistema para hacer las pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        l = factory.manufacturePojo(LugarEntity.class);
        em.persist(l);
        
        for(int i = 0; i<3; i++) {
            UsuarioEntity usuario = factory.manufacturePojo(UsuarioEntity.class);
            em.persist(usuario);
            dataU.add(usuario);
        }
        
        for(int i = 0; i<3; i++) {
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            List<UsuarioEntity> list = new ArrayList<>();
            for(int j = 0; j<3-i; j++) {
                list.add(dataU.get(j));
            }
            evento.setUsuarios(list);
            em.persist(evento);
            data.add(evento);
        }
        EventoEntity ev = data.get(1);
        ev.setPatrocinios(new ArrayList<>());
        for(int i = 0; i<3; i++) {
            PatrocinioEntity patrocinio = factory.manufacturePojo(PatrocinioEntity.class);
            ev.getPatrocinios().add(patrocinio);
        }
        data.set(1, em.merge(ev));
    }
    
    @Test
    public void testGetEntity() {
        try {
            EventoEntity evento = logic.getEntity(data.get(0).getId());
            Assert.assertEquals(data.get(0), evento);
            Assert.assertEquals(data.get(0).getNombre(), evento.getNombre());
        }
        catch (BusinessException e) {
            Assert.fail();
        }
        try {
            logic.getEntity(null);
            Assert.fail();
        }
        catch (BusinessException e) {
            //no pasa nada
        }
        try {
            logic.getEntity(darIdNoUsado());
            Assert.fail();
        }
        catch (NotFoundException | EJBException e1) {
            //no pasa nada
        }
        catch (BusinessException e) {
            Assert.fail();
        }
    }
    
    @Test
    public void testGetAll() {
        compararListas(data, logic.getAll());
    }
    
    @Test
    public void testCreateUpdateDeleteEntity() throws ParseException{
        try {
            logic.createEntity(null);
            Assert.fail();
        }
        catch (BusinessException e) {
            //no pasa nada
        }
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        PodamFactory factory = new PodamFactoryImpl();
        try {
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            evento.setFechaInicio(df.parse("01/12/2017"));
            evento.setFechaFin(df.parse("01/01/2017"));
            logic.createEntity(evento);
            Assert.fail();
        }
        catch (BusinessException e) {
            //no pasa nada
        }
        EventoEntity entity = null;
        try {
            EventoEntity evento = factory.manufacturePojo(EventoEntity.class);
            evento.setFechaFin(df.parse("01/12/2017"));
            evento.setFechaInicio(df.parse("01/01/2017"));
            entity = logic.createEntity(evento);
        }
        catch (BusinessException e) {
            Assert.fail();
        }
        
        
        EventoEntity newEntity = factory.manufacturePojo(EventoEntity.class);
        newEntity.setFechaFin(df.parse("01/12/2017"));
        newEntity.setFechaInicio(df.parse("01/01/2017"));
        newEntity.setId(darIdNoUsado());
        while(Long.compare(newEntity.getId(), entity.getId()) == 0) {
            newEntity.setId(darIdNoUsado());
        }
        try {
            logic.updateEntity(newEntity);
            Assert.fail();
        }
        catch(NotFoundException | EJBException e) {
            //no pasa nada
        }
        
        newEntity.setId(entity.getId());
        try {
            logic.updateEntity(newEntity);
        }
        catch(NotFoundException | EJBException e) {
            Assert.fail();
        }
        
        
        Long id = darIdNoUsado();
        while(Long.compare(id, entity.getId()) == 0) {
            id = darIdNoUsado();
        }
        newEntity.setId(id);
        try {
            logic.deleteEntity(newEntity);
            Assert.fail();
        }
        catch (NotFoundException | EJBException e) {
            //no pasa nada.
        }
        newEntity.setId(entity.getId());
        try {
            logic.deleteEntity(newEntity);
        }
        catch (NotFoundException | EJBException e) {
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
        EventoEntity entity = new EventoEntity();
        entity.setId((long)0);
        while(data.indexOf(entity)>=0) {
            entity.setId((long)((Math.random())*100));
        }
        return entity.getId();
    }
}
