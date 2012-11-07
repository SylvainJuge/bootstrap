package sylvain.juge.bootstrap.testng;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

// Notes
//
// @AfterClass and @BeforeClass : like with junit, does not require method to be static
// @BeforeMethod and @AfterMethod : just what you can expect from their names
//
//
// Annotations common fields :
// - alwaysRun : bypass group selection constraints
//  -> see how it behaves when you use test suites
// - dependsOnGroups
// - dependsOnMethods
// - enabled : true|false to enable|disable class/method
// - groups : which groups this class/method belongs to
// - inheritGroups : when true, method inherit groups this class belongs to
//
// TODO :
// - @BeforeGroups and @AfterGroups ??
// - @DataProvider to inject test data
// - find usages for @BeforeSuite and @AfterSuite 
//   -> to handle init/destroy lifecycle ?
//   -> to check that executed tests leave a clean state
// - Suite : set of tests classes that is defined through an xml
//   -> how to define and run a suite, which usages ?
//
@Test(enabled=false)
public class SimpleTest {

    private void msg(String msg){
        System.out.println(String.format("%s %s",getClass().getName(),msg));
    }

    @BeforeSuite
    public void beforeSuite(){
        msg("before suite");
    }

    @AfterSuite
    public void afterSuite(){
        msg("after suite");
    }

    @BeforeClass
    public void beforeClass(){
        msg("before class");
    }

    @AfterClass
    public void afterClass(){
        msg("after class");
    }

    @BeforeMethod
    public void beforeMethod(){
        msg("before method");
    }

    @AfterMethod
    public void afterMethod(){
        msg("after method");
    }

    @Test
    public void test1(){
        msg("test1");
    }

    @Test
    public void test2(){
        msg("test2");
    }

}
