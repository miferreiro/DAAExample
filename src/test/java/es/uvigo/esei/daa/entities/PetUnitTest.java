package es.uvigo.esei.daa.entities;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class PetUnitTest {
	@Test
	public void testPetIntStringStringInt() {
		final int id = 1;
		final String name = "Rex";
		final String specie = "Dog";
		final int idOwner = 1;
		
		final Pet pet = new Pet(id, name, specie, idOwner);
		
		assertThat(pet.getId(), is(equalTo(id)));
		assertThat(pet.getName(), is(equalTo(name)));
		assertThat(pet.getSpecie(), is(equalTo(specie)));
		assertThat(pet.getIdOwner(), is(equalTo(idOwner)));
	}
	
	@Test(expected = NullPointerException.class)
	public void testPetIntStringStringIntNullName() {
		new Pet(1, null, "Dog", 1);
	}
	
	@Test(expected = NullPointerException.class)
	public void testPetIntStringStringIntNullSpecie() {
		new Pet(1, "Rex", null, 1);
	}
	
//	@Test(expected = Error.class)
//	public void testPetIntStringStringIntNullidOwner() {
//		new Pet(1, "Rex", "Dog", null);
//	}
	
	@Test
	public void testSetName() {
		final int id = 1;
		final String specie = "Dog";
		final int idOwner = 1;
		
		final Pet pet = new Pet(id, "Snoopy", specie, idOwner);
		pet.setName("Rex");
		
		assertThat(pet.getId(), is(equalTo(id)));
		assertThat(pet.getName(), is(equalTo("Rex")));
		assertThat(pet.getSpecie(), is(equalTo(specie)));
		assertThat(pet.getIdOwner(), is(equalTo(idOwner)));
	}
	
	@Test(expected = NullPointerException.class)
	public void testSetNullName() {
		final Pet pet = new Pet(1, "Rex", "Dog", 1);
		
		pet.setName(null);
	}
	
	@Test
	public void testSetSpecie() {
		final int id = 1;
		final String name = "Snoopy";
		final int idOwner = 1;
		
		final Pet pet = new Pet(id, name, "Cat", idOwner);
		pet.setSpecie("Dog");
		
		assertThat(pet.getId(), is(equalTo(id)));
		assertThat(pet.getName(), is(equalTo(name)));
		assertThat(pet.getSpecie(), is(equalTo("Dog")));
		assertThat(pet.getIdOwner(), is(equalTo(idOwner)));
	}
	
	@Test(expected = NullPointerException.class)
	public void testSetNullSpecie() {
		final Pet pet = new Pet(1, "Rex", "Dog", 1);
		
		pet.setSpecie(null);
	}
	
	@Test
	public void testSetIdOwner() {
		final int id = 1;
		final String name = "Snoopy";
		final String specie = "Cat";
		
		final Pet pet = new Pet(id, name, specie, 2);
		pet.setIdOwner(1);
		
		assertThat(pet.getId(), is(equalTo(id)));
		assertThat(pet.getName(), is(equalTo(name)));
		assertThat(pet.getSpecie(), is(equalTo(specie)));
		assertThat(pet.getIdOwner(), is(equalTo(1)));
	}
	
//	@Test(expected = Error.class)
//	public void testSetNullIdOwner() {
//		final Pet pet = new Pet(1, "Rex", "Dog", null);
//		
//		pet.setSpecie(null);
//	}	
	
	
	@Test
	public void testEqualsObject() {		
		final Pet petA = new Pet(1, "Name A", "Specie A", 1);
		final Pet petB = new Pet(1, "Name B", "Specie B", 1);
		
		assertTrue(petA.equals(petB));
	}

	@Test
	public void testEqualsHashcode() {
		EqualsVerifier.forClass(Pet.class)
			.withIgnoredFields("name", "specie", "idOwner")
			.suppress(Warning.STRICT_INHERITANCE)
			.suppress(Warning.NONFINAL_FIELDS)
		.verify();
	}
	
}
