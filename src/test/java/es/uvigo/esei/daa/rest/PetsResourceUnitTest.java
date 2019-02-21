package es.uvigo.esei.daa.rest;

import static es.uvigo.esei.daa.dataset.PetsDataset.existentId;
import static es.uvigo.esei.daa.dataset.PetsDataset.existentIdOwner;
import static es.uvigo.esei.daa.dataset.PetsDataset.existentPet;
import static es.uvigo.esei.daa.dataset.PetsDataset.newName;
import static es.uvigo.esei.daa.dataset.PetsDataset.newPet;
import static es.uvigo.esei.daa.dataset.PetsDataset.newSpecie;
import static es.uvigo.esei.daa.dataset.PetsDataset.newIdOwner;
import static es.uvigo.esei.daa.dataset.PetsDataset.pets;
import static es.uvigo.esei.daa.dataset.PetsDataset.petsOwner;
import static es.uvigo.esei.daa.matchers.HasHttpStatus.hasBadRequestStatus;
import static es.uvigo.esei.daa.matchers.HasHttpStatus.hasInternalServerErrorStatus;
import static es.uvigo.esei.daa.matchers.HasHttpStatus.hasOkStatus;
import static es.uvigo.esei.daa.matchers.IsEqualToPet.containsPetsInAnyOrder;
import static es.uvigo.esei.daa.matchers.IsEqualToPet.equalsToPet;
import static java.util.Arrays.asList;
import static org.easymock.EasyMock.anyInt;
import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.anyString;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.hamcrest.CoreMatchers.is;

import java.util.List;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import es.uvigo.esei.daa.dao.DAOException;
import es.uvigo.esei.daa.dao.PetsDAO;
import es.uvigo.esei.daa.entities.Pet;

public class PetsResourceUnitTest {
	private PetsDAO daoMock;
	private PetsResource resource;
	
	@Before
	public void setUp() throws Exception {
		daoMock = createMock(PetsDAO.class);
		resource = new PetsResource(daoMock);
	}

	@After
	public void tearDown() throws Exception {
		try {
			verify(daoMock);
		} finally {
			daoMock = null;
			resource = null;
		}
	}
	
	@Test
	@SuppressWarnings("unchecked")
	public void testList() throws Exception {
		final List<Pet> pets = asList(pets());
		
		expect(daoMock.list()).andReturn(pets);
		
		replay(daoMock);
		
		final Response response = resource.list(null);
		
		assertThat(response, hasOkStatus());
		assertThat((List<Pet>) response.getEntity(), containsPetsInAnyOrder(pets()));
	}

	@Test
	public void testListDAOException() throws Exception {
		expect(daoMock.list()).andThrow(new DAOException());
		
		replay(daoMock);
		
		final Response response = resource.list(null);
		
		assertThat(response, hasInternalServerErrorStatus());
	}

	@Test
	@SuppressWarnings("unchecked")
	public void testListOwner() throws Exception {
		final List<Pet> pets = asList(petsOwner(existentIdOwner()));
		
		expect(daoMock.getPets(existentIdOwner())).andReturn(pets);
		
		replay(daoMock);
		
		final Response response = resource.list(existentIdOwner());
		
		assertThat(response, hasOkStatus());
		assertThat((List<Pet>) response.getEntity(), containsPetsInAnyOrder((petsOwner(existentIdOwner()))));
	}	
	
	@Test
	public void testListOwnerDAOException() throws Exception {
		expect(daoMock.getPets(existentIdOwner())).andThrow(new DAOException());
		
		replay(daoMock);
		
		final Response response = resource.list(existentIdOwner());
		
		assertThat(response, hasInternalServerErrorStatus());
	}
	
	@Test
	public void testGet() throws Exception {
		final Pet pet = existentPet();
		
		expect(daoMock.get(pet.getId())).andReturn(pet);
		
		replay(daoMock);
		
		final Response response = resource.get(pet.getId());
		
		assertThat(response, hasOkStatus());
		assertThat((Pet) response.getEntity(), is(equalsToPet(pet)));
	}

	@Test
	public void testGetDAOException() throws Exception {
		expect(daoMock.get(anyInt())).andThrow(new DAOException());
		
		replay(daoMock);
		
		final Response response = resource.get(existentId());
		
		assertThat(response, hasInternalServerErrorStatus());
	}

	@Test
	public void testGetIllegalArgumentException() throws Exception {
		expect(daoMock.get(anyInt())).andThrow(new IllegalArgumentException());
		
		replay(daoMock);
		
		final Response response = resource.get(existentId());
		
		assertThat(response, hasBadRequestStatus());
	}
	
	@Test
	public void testDelete() throws Exception {
		daoMock.delete(anyInt());
		
		replay(daoMock);
		
		final Response response = resource.delete(1);
		
		assertThat(response, hasOkStatus());
	}

	@Test
	public void testDeleteDAOException() throws Exception {
		daoMock.delete(anyInt());
		expectLastCall().andThrow(new DAOException());
		
		replay(daoMock);
		
		final Response response = resource.delete(1);
		
		assertThat(response, hasInternalServerErrorStatus());
	}

	@Test
	public void testDeleteIllegalArgumentException() throws Exception {
		daoMock.delete(anyInt());
		expectLastCall().andThrow(new IllegalArgumentException());
		replay(daoMock);
		
		final Response response = resource.delete(1);
		
		assertThat(response, hasBadRequestStatus());
	}

	@Test
	public void testModify() throws Exception {
		final Pet pet = existentPet();
		pet.setName(newName());
		pet.setSpecie(newSpecie());
		pet.setIdOwner(newIdOwner());
		daoMock.modify(pet);
		
		replay(daoMock);

		final Response response = resource.modify(
				pet.getId(), pet.getName(), pet.getSpecie(), pet.getIdOwner());
		
		assertThat(response, hasOkStatus());
		assertEquals(pet, response.getEntity());
	}

	@Test
	public void testModifyDAOException() throws Exception {
		daoMock.modify(anyObject());
		expectLastCall().andThrow(new DAOException());
		
		replay(daoMock);

		final Response response = resource.modify(existentId(), newName(), newSpecie(), newIdOwner());
		
		assertThat(response, hasInternalServerErrorStatus());
	}

	@Test
	public void testModifyIllegalArgumentException() throws Exception {
		daoMock.modify(anyObject());
		expectLastCall().andThrow(new IllegalArgumentException());
		
		replay(daoMock);

		final Response response = resource.modify(existentId(), newName(), newSpecie(), newIdOwner());
		
		assertThat(response, hasBadRequestStatus());
	}

	@Test
	public void testModifyNullPointerException() throws Exception {
		daoMock.modify(anyObject());
		expectLastCall().andThrow(new NullPointerException());
		
		replay(daoMock);

		final Response response = resource.modify(existentId(), newName(), newSpecie(), newIdOwner());
		
		assertThat(response, hasBadRequestStatus());
	}

	@Test
	public void testAdd() throws Exception {
		expect(daoMock.add(newName(), newSpecie(), newIdOwner()))
			.andReturn(newPet());
		replay(daoMock);
		

		final Response response = resource.add(newName(), newSpecie(), newIdOwner());
		
		assertThat(response, hasOkStatus());
		assertThat((Pet) response.getEntity(), is(equalsToPet(newPet())));
	}

	@Test
	public void testAddDAOException() throws Exception {
		expect(daoMock.add(anyString(), anyString(), anyInt()))
			.andThrow(new DAOException());
		replay(daoMock);

		final Response response = resource.add(newName(), newSpecie(), newIdOwner());
		
		assertThat(response, hasInternalServerErrorStatus());
	}

	@Test
	public void testAddIllegalArgumentException() throws Exception {
		expect(daoMock.add(anyString(), anyString(), anyInt()))
			.andThrow(new IllegalArgumentException());
		replay(daoMock);
		
		final Response response = resource.add(newName(), newSpecie(), newIdOwner());
		
		assertThat(response, hasBadRequestStatus());
	}	
	
}
