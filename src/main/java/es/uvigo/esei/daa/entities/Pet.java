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
	private String specie;
	private int idOwner;
	
	// Constructor needed for the JSON conversion
	Pet() {}
	
	/**
	 * Constructs a new instance of {@link Person}.
	 *
	 * @param id identifier of the pet.
	 * @param name name of the pet.
	 * @param specie specie of the pet.
	 * @param idOwner owner of the pet
	 */
	public Pet(int id, String name, String specie, int idOwner) {
		this.id = id;
		this.setName(name);
		this.setSpecie(specie);
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
	 * Returns the specie of the pet.
	 * 
	 * @return the specie of the pet.
	 */
	public String getSpecie() {
		return specie;
	}
	/**
	 * Set the specie of this pet.
	 * 
	 * @param specie the new specie of the pet.
	 * @throws NullPointerException if the {@code specie} is {@code null}.
	 */
	public void setSpecie(String specie) {
		this.specie = requireNonNull(specie, "specie can't be null");
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
		if(idOwner < 1) {
			throw new IllegalArgumentException(idOwner + " can't be less than 0");
		}
		this.idOwner = idOwner;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Pet))
			return false;
		Pet other = (Pet) obj;
		if(id != other.id)
			return false;
		return true;
	}
}
