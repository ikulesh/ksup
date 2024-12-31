import org.example.ksup.tests.EachCatClientTest;
import org.example.ksup.tests.EachClientTest;
import org.example.ksup.tests.FirstClientTest;
import org.junit.Test;

public class MyTest {

    @Test
    public void runSelectedTest() throws Exception {
        // Get the test method name from system properties
        String testMethod = System.getProperty("testMethod");

        if (testMethod == null || testMethod.isEmpty()) {
            throw new IllegalArgumentException("Test method name is not specified. Use -DtestMethod=<methodName>.");
        }

        switch (testMethod) {
            case "eachCatClientTest":
                EachCatClientTest.eachCatClientTest();
                break;
            case "eachClientTest":
                EachClientTest.eachClientTest();
                break;
            case "firstClientTest":
                FirstClientTest.firstClientTest();
                break;
            default:
                throw new IllegalArgumentException("Test method not found: " + testMethod);
        }
    }
}
