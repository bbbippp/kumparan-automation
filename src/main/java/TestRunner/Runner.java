package TestRunner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

/**
 * @author Ian Gumilang
 */
@RunWith(Cucumber.class)
@CucumberOptions(features="src/main/java/features",glue={"steps"})
public class Runner
{

}
