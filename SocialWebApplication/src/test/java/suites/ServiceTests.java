package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import service.MixedServiceTest;
import service.ShorteningLinksTest;

@RunWith(Suite.class)
@SuiteClasses({MixedServiceTest.class, ShorteningLinksTest.class })
public class ServiceTests {

}
