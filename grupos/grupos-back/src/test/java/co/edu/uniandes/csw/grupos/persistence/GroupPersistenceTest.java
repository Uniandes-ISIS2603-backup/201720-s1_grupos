<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.GroupEntity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author cm.sarmiento10
 */
@RunWith(Arquillian.class)
public class GroupPersistenceTest {
    
    /**
     * Inyección de la dependencia a la clase GroupPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private GroupPersistence persistence;

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
    private List<GroupEntity> data = new ArrayList<GroupEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(GroupEntity.class.getPackage())
                .addPackage(GroupPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    public GroupPersistenceTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    private void clearData() {
        em.createQuery("delete from GroupEntity").executeUpdate();
    }


 private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            GroupEntity entity = factory.manufacturePojo(GroupEntity.class);

            em.persist(entity);
            data.add(entity);
        }
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
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class GroupPersistence.
     */
    @Test
    public void testCreate() throws Exception {
         PodamFactory factory = new PodamFactoryImpl();
    GroupEntity newEntity = factory.manufacturePojo(GroupEntity.class);
    GroupEntity result = persistence.create(newEntity);

    Assert.assertNotNull(result);
    GroupEntity entity = em.find(GroupEntity.class, result.getId());
    Assert.assertNotNull(entity);
    Assert.assertEquals(newEntity.getId(), entity.getId());
    }

    /**
     * Test of update method, of class GroupPersistence.
     */
    @Test
    public void testUpdate() throws Exception {
     GroupEntity entity = data.get(0);
    PodamFactory factory = new PodamFactoryImpl();
    GroupEntity newEntity = factory.manufacturePojo(GroupEntity.class);

    newEntity.setId(entity.getId());

    persistence.update(newEntity);

    GroupEntity resp = em.find(GroupEntity.class, entity.getId());

    Assert.assertEquals(newEntity.getId(), resp.getId());
}

    /**
     * Test of delete method, of class GroupPersistence.
     */
    @Test
    public void testDelete() throws Exception {
    GroupEntity entity = data.get(0);
    persistence.delete(entity.getId());
    GroupEntity deleted = em.find(GroupEntity.class, entity.getId());
    Assert.assertNull(deleted);
}

    /**
     * Test of find method, of class GroupPersistence.
     */
    @Test
    public void testFind() throws Exception {
        
     GroupEntity entity = data.get(0);
    GroupEntity newEntity = persistence.find(entity.getId());
    Assert.assertNotNull(newEntity);
    Assert.assertEquals(entity.getId(), newEntity.getId());
}

    /**
     * Test of findAll method, of class GroupPersistence.
     */
    @Test
    public void testFindAll() throws Exception {
         List<GroupEntity> list = persistence.findAll();
    Assert.assertEquals(data.size(), list.size());
    for (GroupEntity ent : list) {
        boolean found = false;
        for (GroupEntity entity : data) {
            if (ent.getId().equals(entity.getId())) {
                found = true;
            }
        }
        Assert.assertTrue(found);
    }
    }

    /**
     * Test of findByAddress method, of class GroupPersistence.
     */
    @Test
    public void testFindByNombre() throws Exception {
    GroupEntity entity = data.get(0);
    GroupEntity newEntity = persistence.findByNombre(entity.getNombre());
    Assert.assertNotNull(newEntity);
    Assert.assertEquals(entity.getId(), newEntity.getId());
}
    
}
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.grupos.persistence;

import co.edu.uniandes.csw.grupos.entities.GroupEntity;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author cm.sarmiento10
 */
@RunWith(Arquillian.class)
public class GroupPersistenceTest {
    
    /**
     * Inyección de la dependencia a la clase GroupPersistence cuyos métodos
     * se van a probar.
     */
    @Inject
    private GroupPersistence persistence;

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
    private List<GroupEntity> data = new ArrayList<GroupEntity>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(GroupEntity.class.getPackage())
                .addPackage(GroupPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    public GroupPersistenceTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    private void clearData() {
        em.createQuery("delete from GroupEntity").executeUpdate();
    }


 private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            GroupEntity entity = factory.manufacturePojo(GroupEntity.class);

            em.persist(entity);
            data.add(entity);
        }
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
    
    @After
    public void tearDown() {
    }

    /**
     * Test of create method, of class GroupPersistence.
     */
    @Test
    public void testCreate() throws Exception {
         PodamFactory factory = new PodamFactoryImpl();
    GroupEntity newEntity = factory.manufacturePojo(GroupEntity.class);
    GroupEntity result = persistence.create(newEntity);

    Assert.assertNotNull(result);
    GroupEntity entity = em.find(GroupEntity.class, result.getId());
    Assert.assertNotNull(entity);
    Assert.assertEquals(newEntity.getId(), entity.getId());
    }

    /**
     * Test of update method, of class GroupPersistence.
     */
    @Test
    public void testUpdate() throws Exception {
     GroupEntity entity = data.get(0);
    PodamFactory factory = new PodamFactoryImpl();
    GroupEntity newEntity = factory.manufacturePojo(GroupEntity.class);

    newEntity.setId(entity.getId());

    persistence.update(newEntity);

    GroupEntity resp = em.find(GroupEntity.class, entity.getId());

    Assert.assertEquals(newEntity.getId(), resp.getId());
}

    /**
     * Test of delete method, of class GroupPersistence.
     */
    @Test
    public void testDelete() throws Exception {
    GroupEntity entity = data.get(0);
    persistence.delete(entity.getId());
    GroupEntity deleted = em.find(GroupEntity.class, entity.getId());
    Assert.assertNull(deleted);
}

    /**
     * Test of find method, of class GroupPersistence.
     */
    @Test
    public void testFind() throws Exception {
        
     GroupEntity entity = data.get(0);
    GroupEntity newEntity = persistence.find(entity.getId());
    Assert.assertNotNull(newEntity);
    Assert.assertEquals(entity.getId(), newEntity.getId());
}

    /**
     * Test of findAll method, of class GroupPersistence.
     */
    @Test
    public void testFindAll() throws Exception {
         List<GroupEntity> list = persistence.findAll();
    Assert.assertEquals(data.size(), list.size());
    for (GroupEntity ent : list) {
        boolean found = false;
        for (GroupEntity entity : data) {
            if (ent.getId().equals(entity.getId())) {
                found = true;
            }
        }
        Assert.assertTrue(found);
    }
    }

    /**
     * Test of findByAddress method, of class GroupPersistence.
     */
    @Test
    public void testFindByNombre() throws Exception {
    GroupEntity entity = data.get(0);
    GroupEntity newEntity = persistence.findByNombre(entity.getNombre());
    Assert.assertNotNull(newEntity);
    Assert.assertEquals(entity.getId(), newEntity.getId());
}
    
}
>>>>>>> origin/Completa
