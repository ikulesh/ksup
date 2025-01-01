import org.example.ksup.tests.EachCatClientTest;
import org.example.ksup.tests.EachClientTest;
import org.example.ksup.tests.FirstClientTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class MyTest {

    @ParameterizedTest
    @ValueSource(strings = {"firstClientTest", "eachCatClientTest", "eachClientTest"})
    public void runSelectedTest(String testMethod) throws Exception {
        switch (testMethod) {
            case "eachCatClientTest":
                System.out.println("RUNNING eachCatClientTest");
                EachCatClientTest.eachCatClientTest();
                break;
            case "eachClientTest":
                System.out.println("RUNNING eachClientTest");
                EachClientTest.eachClientTest();
                break;
            case "firstClientTest":
                System.out.println("RUNNING firstClientTest");
                FirstClientTest.firstClientTest();
                break;
            default:
                throw new IllegalArgumentException("Test method not found: " + testMethod);
        }
    }
}