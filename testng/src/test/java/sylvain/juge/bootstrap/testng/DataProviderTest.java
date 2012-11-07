package sylvain.juge.bootstrap.testng;

import java.lang.reflect.Method;
import java.util.Iterator;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Test(enabled=false) // disabled for faster testing
public class DataProviderTest {

    public void msg(String s){
        System.out.println(s);
    }

    private String methodFullName(Method m){
        return getClass().getName()+"#"+m.getName();
    }

    @DataProvider(name="provider1")
    public Object[][] dataProvider(Method m){
        return new Object[][]{{methodFullName(m)}};
    }

    @Test(dataProvider = "provider1")
    public void testWithDataProvider(String testData){
        msg(testData);
    }

    // we use same name to make sure that we don't get provider above when testing
    // if we do, it means that we have a kind of "take local provider 1st" resolution policy
    @Test( dataProvider = "provider1", dataProviderClass = DataProviders.class )
    public void testWithAnotherClassDataProvider(String testData){
        msg(testData);
    }

    // when provider method is not static, tests associated to it are just not executed
    // and we get an error within reports, but it does not fail the maven build
    public static final class DataProviders {
        @DataProvider(name="provider1")
        public static Object[][] dataProvider(){
            return new Object[][]{{"data from dataProviderFromAnotherClass"}};
        }
    }


    @Test(dataProvider = "lazyProvider")
    public void testWithIteratorDataProvider(String data){
        msg(data);
    }

    @DataProvider(name = "lazyProvider")
    public Iterator<Object[]> lazyProvider(Method m){
        return new LazyIterator(methodFullName(m),5);
    }

    private static class LazyIterator implements Iterator<Object[]> {
        private int left = 0;
        private final String prefix;
        public LazyIterator(String prefix, int count){
            left = count;
            this.prefix = prefix;
        }
        public Object[] next(){
            return new Object[]{prefix+Integer.toString(left--)};
        }
        public boolean hasNext(){
            return left>0;
        }
        public void remove(){
            throw new UnsupportedOperationException("not implemented");
        }
    }

}
