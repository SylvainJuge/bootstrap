
package sylvain.juge.bootstrap.testng;

import org.testng.annotations.Test;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;

//import static org.testng.AssertJunit.*;

@Test
public class DependenciesTest {

    private String lastMethod = null;

    @AfterClass
    public void checkLastMethod(){
        // the dependee should run last
        assertEquals(lastMethod,"dependsOnAnotherMethod");
    }

    @AfterMethod
    public void afterMethod(){
        assertNotNull(lastMethod);
        msg("after method, lastMethod = "+ lastMethod );
    }

    @Test(dependsOnMethods="theMethodSomeTestsDependsOn")
    public void dependsOnAnotherMethod(){
        assertEquals("theMethodSomeTestsDependsOn",lastMethod);
        lastMethod = "dependsOnAnotherMethod";
    }

    @Test
    public void theMethodSomeTestsDependsOn(){
        assertEquals(null,lastMethod);
        lastMethod = "theMethodSomeTestsDependsOn";
    }

    private void msg(String msg){
        System.out.println(String.format("%s %s",getClass().getName(),msg));
    }

    private static void assertNotNull(Object o){
        if( null == o ){
            throw new RuntimeException(String.format("expected null value, got %s",o.toString()));
        }
    }

    private static void assertEquals(String expected, String actual){
        if((null == expected && null!=actual) || (null != expected && !expected.equals(actual))){
            throw new RuntimeException(String.format("expected %s, got %s",expected,actual));
        }
    }
}
