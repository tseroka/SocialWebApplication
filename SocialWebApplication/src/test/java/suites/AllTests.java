package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import controller.ControllersTest;
import controller.RegistrationTest;
import service.MixedServiceTest;
import service.ShorteningLinksTest;

@RunWith(Suite.class)
@SuiteClasses({MixedServiceTest.class, ShorteningLinksTest.class, ControllersTest.class, RegistrationTest.class })
public class AllTests {

}
