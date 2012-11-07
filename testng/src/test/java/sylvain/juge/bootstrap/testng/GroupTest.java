package sylvain.juge.bootstrap.testng;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

@Test(enabled=false)
public class GroupTest {

    // TODO : things to investigate
    // 
    // annotations fields
    // - dependsOnGroups : if any of tests within this list fails, skip this test
    // - dependsOnMethods : 
    // 

    private void msg(String msg){
        System.out.println(String.format("%s %s",getClass().getName(),msg));
    }

    @BeforeMethod(groups = { "group1" })
    public void beforeMethodGroup1(){
        msg("before method group1");
    }

    // must be run at the end of group1 tests
    @AfterClass
    public void afterClassGroup1(){
        msg("after class group1");
    }

    @Test(groups = { "group1" })
    public void test1(){
        msg("test1 group1");
    }

    @Test(groups = { "group1" })
    public void test2(){
        msg("test2 group1");
    }

    @Test(groups = { "group2" })
    public void test3(){
        msg("test3 group2");
    }

    @Test(groups = { "group2" })
    public void test4(){
        msg("test4 group2");
    }

    // test group membership of methods and @before/@after methods
    //

}
