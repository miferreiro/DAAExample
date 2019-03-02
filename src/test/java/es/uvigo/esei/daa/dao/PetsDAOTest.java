package es.uvigo.esei.daa.dao;

import static es.uvigo.esei.daa.dataset.PetsDataset.existentId;
import static es.uvigo.esei.daa.dataset.PetsDataset.existentIdOwner;
import static es.uvigo.esei.daa.dataset.PetsDataset.existentPet;
import static es.uvigo.esei.daa.dataset.PetsDataset.newName;
import static es.uvigo.esei.daa.dataset.PetsDataset.newPet;
import static es.uvigo.esei.daa.dataset.PetsDataset.newSpecie;
import static es.uvigo.esei.daa.dataset.PetsDataset.newIdOwner;
import static es.uvigo.esei.daa.dataset.PetsDataset.nonExistentId;
import static es.uvigo.esei.daa.dataset.PetsDataset.nonExistentIdOwner;
import static es.uvigo.esei.daa.dataset.PetsDataset.negativeIdOwner;
import static es.uvigo.esei.daa.dataset.PetsDataset.nonExistentPet;
import static es.uvigo.esei.daa.dataset.PetsDataset.pets;
import static es.uvigo.esei.daa.dataset.PetsDataset.petsOwner;
import static es.uvigo.esei.daa.dataset.PetsDataset.petsWithout;
import static es.uvigo.esei.daa.matchers.IsEqualToPet.containsPetsInAnyOrder;
import static es.uvigo.esei.daa.matchers.IsEqualToPet.equalsToPet;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import com.github.springtestdbunit.annotation.ExpectedDatabase;

import es.uvigo.esei.daa.entities.Pet;
import es.uvigo.esei.daa.listeners.ApplicationContextBinding;
import es.uvigo.esei.daa.listeners.ApplicationContextJndiBindingTestExecutionListener;
import es.uvigo.esei.daa.listeners.DbManagement;
import es.uvigo.esei.daa.listeners.DbManagementTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:contexts/mem-context.xml")
@TestExecutionListeners({
	DbUnitTestExecutionListener.class,
	DbManagementTestExecutionListener.class,
	ApplicationContextJndiBindingTestExecutionListener.class
})
@ApplicationContextBinding(
	jndiUrl = "java:/comp/env/jdbc/daaexample",
	type = DataSource.class
)
@DbManagement(
	create = "classpath:db/hsqldb.sql",
	drop = "classpath:db/hsqldb-drop.sql"
)
@DatabaseSetup("/datasets/dataset.xml")
@ExpectedDatabase("/datasets/dataset.xml")
public class PetsDAOTest {
	private PetsDAO dao;

	@Before
	public void setUp() throws Exception {
		this.dao = new PetsDAO();
	}

	@Test
	public void testList() throws DAOException {
		assertThat(this.dao.list(), containsPetsInAnyOrder(pets()));
	}
	
	@Test
	public void testGetPets() throws DAOException {
		assertThat(this.dao.getPets(existentIdOwner()), containsPetsInAnyOrder(petsOwner(existentIdOwner())));
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testGetPetsNegativeIdOwner() throws DAOException {
		this.dao.getPets(negativeIdOwner());
	}
	
	@Test
	public void testGet() throws DAOException {
		final Pet pet = this.dao.get(existentId());
		
		assertThat(pet, is(equalsToPet(existentPet())));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testGetNonExistentId() throws DAOException {
		this.dao.get(nonExistentId());
	}
	
	@Test
	@ExpectedDatabase("/datasets/dataset-delete-pets.xml")
	public void testDelete() throws DAOException {
		this.dao.delete(existentId());

		assertThat(this.dao.list(), containsPetsInAnyOrder(petsWithout(existentId())));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDeleteNonExistentId() throws DAOException {
		this.dao.delete(nonExistentId());
	}

	@Test
	@ExpectedDatabase("/datasets/dataset-modify-pets.xml")
	public void testModify() throws DAOException {
		final Pet pet = existentPet();
		pet.setName(newName());
		pet.setSpecie(newSpecie());
		pet.setIdOwner(newIdOwner());
		
		this.dao.modify(pet);
		
		final Pet persistentPet = this.dao.get(pet.getId());
		
		assertThat(persistentPet, is(equalsToPet(pet)));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testModifyNonExistentId() throws DAOException {
		this.dao.modify(nonExistentPet());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testModifyNullPerson() throws DAOException {
		this.dao.modify(null);
	}

	@Test
	@ExpectedDatabase("/datasets/dataset-add-pets.xml")
	public void testAdd() throws DAOException {
		final Pet pet = this.dao.add(newName(), newSpecie(), newIdOwner());
		
		assertThat(pet, is(equalsToPet(newPet())));
		
		final Pet persistentPet = this.dao.get(pet.getId());

		assertThat(persistentPet, is(equalsToPet(newPet())));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNullName() throws DAOException {
		this.dao.add(null, newSpecie(), newIdOwner());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testAddNullSpecie() throws DAOException {
		this.dao.add(newName(), null, newIdOwner());
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void testAddNegativeIdOwner() throws DAOException {
		this.dao.add(newName(), newSpecie(),negativeIdOwner());
	}
}
