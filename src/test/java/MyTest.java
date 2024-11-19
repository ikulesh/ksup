import org.example.ksup.tests.EachCatClientTest;
import org.example.ksup.tests.EachClientTest;
import org.example.ksup.tests.FirstClientTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.runners.Parameterized.*;

@RunWith(Parameterized.class)
public class MyTest {

    private String testMethod;

    public MyTest(String testMethod) {
        this.testMethod = testMethod;
    }

    @Parameters
    public static Collection<String> data() {
        return Arrays.asList("eachCatClientTest", "eachClientTest", "firstClientTest");
    }

    @Test
    public void runSelectedTest() throws Exception {
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
                throw new IllegalArgumentException("Test method not found");
        }
    }
}

