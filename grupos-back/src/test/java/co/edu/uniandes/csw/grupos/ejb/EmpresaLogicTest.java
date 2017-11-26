/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.ejb;

import co.edu.uniandes.csw.grupos.persistence.*;
import co.edu.uniandes.csw.grupos.entities.EmpresaEntity;
import co.edu.uniandes.csw.grupos.exceptions.BusinessException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author af.lopezf
 */
@RunWith(Arquillian.class)
public class EmpresaLogicTest {
    
    @Deployment
    public static JavaArchive createDeployment() {
    return ShrinkWrap.create(JavaArchive.class)
                .addPackage(EmpresaEntity.class.getPackage())
                .addPackage(EmpresaPersistence.class.getPackage())
                .addPackage(EmpresaLogic.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
     /**
     * Logger de la lógica
     */
    private static final Logger LOGGER = Logger.getLogger(EmpresaLogicTest.class.getName());
    //inyección de la lógica
    @Inject
    private EmpresaLogic logic;

    /**
     * Contexto de Persistencia que se va a utilizar para acceder a la Base de
     * datos por fuera de los métodos que se están probando.
     */
    @PersistenceContext
    private EntityManager em;

    /**
     * Variable para martcar las transacciones del em anterior cuando se
     * crean/borran datos para las pruebas.
     */
    @Inject
    UserTransaction utx;

     /**
     *
     */
    private List<EmpresaEntity> data = new ArrayList<>();

    
    public EmpresaLogicTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
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
    
    
    private void clearData() {
        em.createQuery("delete from TarjetaEntity").executeUpdate();
    }


    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            EmpresaEntity entity = factory.manufacturePojo(EmpresaEntity.class);

            em.persist(entity);
            data.add(entity);
        }
    }
    
    
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class EmpresaPersistence.
     */
    @Test
    public void testCreate() {
        boolean estaBien=true;
        try
        {
            logic.createEmpresa(data.get(0));
        }
        catch(BusinessException e)
        {
            LOGGER.info(e.getMessage());
            estaBien=false;
        }
        if(estaBien)
        {
            Assert.fail();
        }
        estaBien=true;
        try
        {
            PodamFactory factory = new PodamFactoryImpl();
            EmpresaEntity newEntity = factory.manufacturePojo(EmpresaEntity.class);

            EmpresaEntity result = logic.createEmpresa(newEntity);

            Assert.assertNotNull(result);
            EmpresaEntity entity = em.find(EmpresaEntity.class, result.getNit());
            Assert.assertNotNull(entity);
            Assert.assertEquals(newEntity.getNit(), entity.getNit());
        }
        catch(BusinessException e)
        {
            estaBien=false;
        }
        if(!estaBien)
        {
            Assert.fail();
        }
        

    }

    /**
     * Test of findAll method, of class EmpresaPersistence.
     */
    @Test
    public void testFindAll() {
        List<EmpresaEntity> list = logic.getEmpresas();
        Assert.assertEquals(data.size(), list.size());
        for (EmpresaEntity ent : list) {
            boolean found = false;
            for (EmpresaEntity entity : data) {
                if (ent.getNit() == entity.getNit()) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }


        
    }

    /**
     * Test of findByNit method, of class EmpresaPersistence.
     */
    @Test
    public void testFindByNit() {
        EmpresaEntity entity = data.get(0);
        EmpresaEntity newEntity = logic.getEmpresaByNit(entity.getNit());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getNit(), newEntity.getNit());
        
    }

    /**
     * Test of update method, of class EmpresaPersistence.
     */
    @Test
    public void testUpdate() {
        
        EmpresaEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        EmpresaEntity newEntity = factory.manufacturePojo(EmpresaEntity.class);

        newEntity.setNit(entity.getNit());

        logic.update(newEntity);

        EmpresaEntity resp = em.find(EmpresaEntity.class, entity.getNit());

        Assert.assertEquals(newEntity.getNit(), resp.getNit());


    }

    /**
     * Test of delete method, of class EmpresaPersistence.
     */
    @Test
    public void testDelete() {
        EmpresaEntity entity = data.get(0);
        logic.deleteEmpresa(entity.getNit());
        EmpresaEntity deleted = em.find(EmpresaEntity.class, entity.getNit());
        Assert.assertNull(deleted);
    }
    
}
