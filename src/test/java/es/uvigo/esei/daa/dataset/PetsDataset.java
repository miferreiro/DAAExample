package es.uvigo.esei.daa.dataset;

import static java.util.Arrays.binarySearch;
import static java.util.Arrays.stream;

import java.util.Arrays;
import java.util.function.Predicate;

import es.uvigo.esei.daa.entities.Pet;

public class PetsDataset {
	private PetsDataset() {}

	
	public static Pet[] pets() {
		return new Pet[] {
			new Pet(1,"Rex","Perro",1),
			new Pet(2,"Snoopy","Perro",1),
			new Pet(3,"Asno","Asno",2),
			new Pet(4,"Bugs Bunny","Conejo",3),
			new Pet(5,"Scooby Doo","Perro",4),
			new Pet(6,"Jerry","Ratón",1),
			new Pet(7,"Mickey Mouse","Ratón",1),
			new Pet(8,"Garfield","Gato",6),
			new Pet(9,"Piolín","Canario",6),
			new Pet(10,"Nemo","Pez",7)
		};
	}
	
	public static Pet[] petsWithout(int ... ids) {
		Arrays.sort(ids);
		
		final Predicate<Pet> hasValidId = pet ->
			binarySearch(ids, pet.getId()) < 0;
		
		return stream(pets())
			.filter(hasValidId)
		.toArray(Pet[]::new);
	}
	
	public static Pet[] petsOwner(int ... ids) {
		Arrays.sort(ids);
		
		final Predicate<Pet> hasValidId = pet ->
			binarySearch(ids, pet.getIdOwner()) >= 0;
		
		return stream(pets())
			.filter(hasValidId)
		.toArray(Pet[]::new);
	}
	
	public static Pet pet(int id) {
		return stream(pets())
			.filter(pet -> pet.getId() == id)
			.findAny()
		.orElseThrow(IllegalArgumentException::new);
	}
	
	public static int existentId() {
		return 5;
	}
	
	public static int nonExistentId() {
		return 1234;
	}
	
	public static int existentIdOwner() {
		return 1;
	}
	
	public static int nonExistentIdOwner() {
		return 1234;
	}
	
	public static int negativeIdOwner() {
		return -1;
	}
	
	public static Pet existentPet() {
		return pet(existentId());
	}
	
	public static Pet nonExistentPet() {
		return new Pet(nonExistentId(), "Rex", "Perro",2);
	}
	
	public static String newName() {
		return "Dolly";
	}
	
	public static String newSpecie() {
		return "Oveja";
	}
	
	public static int newIdOwner() {
		return 4;
	}
	public static Pet newPet() {
		return new Pet(pets().length + 1, newName(), newSpecie(), newIdOwner());
	}
	
}
