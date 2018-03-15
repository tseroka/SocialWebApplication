package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import controller.LoginRedirectsTest;
import controller.RegistrationTest;

@RunWith(Suite.class)
@SuiteClasses({LoginRedirectsTest.class, RegistrationTest.class })
public class ControllerTests {

}
