package es.uvigo.esei.daa.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import es.uvigo.esei.daa.dao.PeopleDAOTest;
import es.uvigo.esei.daa.dao.PetsDAOTest;
import es.uvigo.esei.daa.rest.PeopleResourceTest;
import es.uvigo.esei.daa.rest.PetsResourceTest;
import es.uvigo.esei.daa.rest.UsersResourceTest;

@SuiteClasses({ 
	
	PeopleDAOTest.class,
	PetsDAOTest.class,
	
	PeopleResourceTest.class,
	PetsResourceTest.class,
	UsersResourceTest.class
})
@RunWith(Suite.class)
public class IntegrationTestSuite {
}
