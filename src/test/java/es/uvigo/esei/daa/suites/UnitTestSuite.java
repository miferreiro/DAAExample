package es.uvigo.esei.daa.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.uvigo.esei.daa.dao.PeopleDAOUnitTest;
import es.uvigo.esei.daa.dao.PetsDAOUnitTest;
import es.uvigo.esei.daa.entities.PersonUnitTest;
import es.uvigo.esei.daa.entities.PetUnitTest;
import es.uvigo.esei.daa.entities.UserUnitTest;
import es.uvigo.esei.daa.rest.PeopleResourceUnitTest;
import es.uvigo.esei.daa.rest.PetsResourceUnitTest;

@SuiteClasses({
	
	PersonUnitTest.class,
	PetUnitTest.class,
	UserUnitTest.class,
	
	PeopleDAOUnitTest.class,
	PetsDAOUnitTest.class,
	
	PetsResourceUnitTest.class,
	PeopleResourceUnitTest.class
})
@RunWith(Suite.class)
public class UnitTestSuite {
}
