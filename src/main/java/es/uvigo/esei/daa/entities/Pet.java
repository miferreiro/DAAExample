package es.uvigo.esei.daa.entities;

import static java.util.Objects.requireNonNull;

/**
 * An entity that represents a pet.
 * 
 * @author Miguel Ferreiro Diaz
 */
public class Pet {
	private int id;
	private String name;
	private String breed;
	private int idOwner;
	
	// Constructor needed for the JSON conversion
	Pet() {}
	
	/**
	 * Constructs a new instance of {@link Person}.
	 *
	 * @param id identifier of the pet.
	 * @param name name of the pet.
	 * @param breed breed of the pet.
	 * @param idOwner owner of the pet
	 */
	public Pet(int id, String name, String breed, int idOwner) {
		this.id = id;
		this.setName(name);
		this.setBreed(breed);
		this.setIdOwner(idOwner);
	}

	/**
	 * Returns the identifier of the pet.
	 * 
	 * @return the identifier of the pet.
	 */
	public int getId() {
		return id;
	}
	/**
	 * Returns the name of the pet.
	 * 
	 * @return the name of the pet.
	 */
	public String getName() {
		return name;
	}
	/**
	 * Set the name of this pet.
	 * 
	 * @param name the new name of the pet.
	 * @throws NullPointerException if the {@code name} is {@code null}.
	 */
	public void setName(String name) {
		this.name = requireNonNull(name, "Name can't be null");
	}
	/**
	 * Returns the breed of the pet.
	 * 
	 * @return the breed of the pet.
	 */
	public String getBreed() {
		return breed;
	}
	/**
	 * Set the breed of this pet.
	 * 
	 * @param breed the new breed of the person.
	 * @throws NullPointerException if the {@code breed} is {@code null}.
	 */
	public void setBreed(String breed) {
		this.breed = requireNonNull(breed, "Name can't be null");
	}

	/**
	 * Returns the owner of the pet
	 * 
	 * @return the owner of the pet
	 */
	public int getIdOwner() {
		return idOwner;
	}
	/**
	 * Set the owner of the pet
	 * 
	 * @param idOwner the new owner of the pet
	 * @throws NullPointerException if the {@code idOwner} is {@code null}.
	 */
	public void setIdOwner(int idOwner) {
		this.idOwner = requireNonNull(idOwner, "Name can't be null");
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((breed == null) ? 0 : breed.hashCode());
		result = prime * result + id;
		result = prime * result + idOwner;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Pet other = (Pet) obj;
		if (breed == null) {
			if (other.breed != null)
				return false;
		} else if (!breed.equals(other.breed))
			return false;
		if (id != other.id)
			return false;
		if (idOwner != other.idOwner)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
}
