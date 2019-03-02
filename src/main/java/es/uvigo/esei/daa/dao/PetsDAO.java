package es.uvigo.esei.daa.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import es.uvigo.esei.daa.entities.Person;
import es.uvigo.esei.daa.entities.Pet;

/**
 * DAO class for managing the pets of the system.
 * 
 * @author Miguel Ferreiro Diaz
 */
public class PetsDAO extends DAO {
	private final static Logger LOG = Logger.getLogger(PetsDAO.class.getName());

	/**
	 * Returns a person stored persisted in the system.
	 * 
	 * @param id identifier of the pet.
	 * @return a pet with the provided identifier.
	 * @throws DAOException if an error happens while retrieving the pet.
	 * @throws IllegalArgumentException if the provided id does not corresponds
	 * with any persisted pet.
	 */
	public Pet get(int id)
	throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM pets WHERE id=?";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, id);
				
				try (final ResultSet result = statement.executeQuery()) {
					if (result.next()) {
						return rowToEntity(result);
					} else {
						throw new IllegalArgumentException("Invalid id");
					}
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error getting a pet", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Returns a list with all the pets persisted in the system.
	 * 
	 * @return a list with all the pets persisted in the system.
	 * @throws DAOException if an error happens while retrieving the pets.
	 */
	public List<Pet> list() throws DAOException {
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT * FROM pets";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				try (final ResultSet result = statement.executeQuery()) {
					final List<Pet> pets = new LinkedList<>();
					
					while (result.next()) {
						pets.add(rowToEntity(result));
					}
					
					return pets;
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error listing pets", e);
			throw new DAOException(e);
		}
	}

	/**
	 * Persists a new pet in the system. An identifier will be assigned
	 * automatically to the new pet.
	 * 
	 * @param name name of the new pet. Can't be {@code null}.
	 * @param specie specie of the new pet. Can't be {@code null}.
	 * @param idOwner idOwner of the new pet. Can't be {@code null}.
	 * @return a {@link Pet} entity representing the persisted pet.
	 * @throws DAOException if an error happens while persisting the new pet.
	 * @throws IllegalArgumentException if the name, specie and idOwner are {@code null}.
	 */
	public Pet add(String name, String specie, int idOwner)
	throws DAOException, IllegalArgumentException {
		if (name == null) {
			throw new IllegalArgumentException("name can't be null " + name);
		}
		
		if ( specie == null) {
			throw new IllegalArgumentException("specie can't be null " + specie);
		}
		
		if(idOwner < 0) {
			throw new IllegalArgumentException("idOwner can't be negative " + specie);
		}
				
		try (Connection conn = this.getConnection()) {
			final String query = "INSERT INTO pets VALUES(null, ?, ?,?)";
			
			try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
				statement.setString(1, name);
				statement.setString(2, specie);
				statement.setInt(3, idOwner);
				
				if (statement.executeUpdate() == 1) {
					try (ResultSet resultKeys = statement.getGeneratedKeys()) {
						if (resultKeys.next()) {
							return new Pet(resultKeys.getInt(1), name, specie, idOwner);
						} else {
							LOG.log(Level.SEVERE, "Error retrieving inserted id");
							throw new SQLException("Error retrieving inserted id");
						}
					}
				} else {
					LOG.log(Level.SEVERE, "Error inserting value");
					throw new SQLException("Error inserting value");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error adding a pet", e);
			throw new DAOException(e);
		}
	}


	/**
	 * Modifies a pet previously persisted in the system. The pet will be
	 * retrieved by the provided id and its current name, specie and idOwner will be
	 * replaced with the provided.
	 * 
	 * @param pet a {@link Pet} entity with the new data.
	 * @throws DAOException if an error happens while modifying the new pet.
	 * @throws IllegalArgumentException if the pet is {@code null}.
	 */
	public void modify(Pet pet)
	throws DAOException, IllegalArgumentException {
		if (pet == null) {
			throw new IllegalArgumentException("pet can't be null");
		}
		
		try (Connection conn = this.getConnection()) {
			final String query = "UPDATE pets SET name=?, specie=?, idOwner=? WHERE id=?";
			
			try (PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setString(1, pet.getName());
				statement.setString(2, pet.getSpecie());
				statement.setInt(3, pet.getIdOwner());
				statement.setInt(4, pet.getId());
				
				if (statement.executeUpdate() != 1) {
					throw new IllegalArgumentException("name, specie and idOwner can't be null");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error modifying a pet", e);
			throw new DAOException();
		}
	}	
	
	/**
	 * Removes a persisted pet from the system.
	 * 
	 * @param id identifier of the pet to be deleted.
	 * @throws DAOException if an error happens while deleting the pet.
	 * @throws IllegalArgumentException if the provided id does not corresponds
	 * with any persisted pet.
	 */
	public void delete(int id)
	throws DAOException, IllegalArgumentException {
		try (final Connection conn = this.getConnection()) {
			final String query = "DELETE FROM pets WHERE id=?";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				statement.setInt(1, id);
				
				if (statement.executeUpdate() != 1) {
					throw new IllegalArgumentException("Invalid id");
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error deleting a pet", e);
			throw new DAOException(e);
		}
	}
	
	/**
	 * Returns the pets of a person stored persisted in the system.
	 * 
	 * @param id identifier of the person.
	 * @return pets of the person with the provided identifier.
	 * @throws DAOException if an error happens while retrieving the person.
	 * @throws IllegalArgumentException if the provided id does not corresponds
	 * with any persisted person.
	 */
	public List<Pet> getPets(int idOwner)
	throws DAOException, IllegalArgumentException {
		
		if ( idOwner < 0) {
			throw new IllegalArgumentException("idOwner can't be negative");
		}
		
		try (final Connection conn = this.getConnection()) {
			final String query = "SELECT T.id, T.name, T.specie, T.idOwner "
							   + "FROM people P, pets T "
							   + "WHERE P.id = T.idOwner AND P.id = ?";
			
			try (final PreparedStatement statement = conn.prepareStatement(query)) {
				
				statement.setInt(1, idOwner);
							
				try (final ResultSet result = statement.executeQuery()) {
					
					final List<Pet> pets = new LinkedList<>();

					while (result.next()) {
						pets.add(rowToEntity(result));
					}
					
					return pets;
				}
			}
		} catch (SQLException e) {
			LOG.log(Level.SEVERE, "Error getting pets of a person ", e);
			throw new DAOException(e);
		}
	}	
	
	
	private Pet rowToEntity(ResultSet row) throws SQLException {
		return new Pet(
			row.getInt("id"),
			row.getString("name"),
			row.getString("specie"),
			row.getInt("idOwner")
		);
	}
}
