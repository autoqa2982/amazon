package app.amazon.hometestcase;

import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import core.framework.Globals;
import framework.util.CommonLib;
import lib.Reporter;
import lib.Stock;
import lib.Reporter.Status;


public class Home {
    
	private LinkedHashMap<Integer, Map<String, String>> testData = null;

	@BeforeClass
	public void InitTest() throws Exception {
		// implement set property partner code > TEST_ENV : append value >		
		//CommonLib.SetTestEnv("AF");		
		Reporter.initializeModule(this.getClass().getName());
	}

	@DataProvider
	public Object[][] setData(Method tc) throws Exception {
		prepTestData(tc);
		return Stock.setDataProvider(this.testData);
	}

	private void prepTestData(Method testCase) throws Exception {
		this.testData = Stock.getTestData(this.getClass().getPackage().getName(), Globals.GC_MANUAL_TC_NAME);	
	}
	
	
	@Test(dataProvider = "setData")
	public void TC001_AF_Verify_Employee_Information(int itr, Map<String, String> testdata) {		
		try {
			Reporter.initializeReportForTC(itr, Globals.GC_MANUAL_TC_NAME);
			Reporter.logEvent(Status.INFO, "Testcase Description",
					 "Verifying American Fund"+" Employee Information", false);
			Reporter.logEvent(Status.INFO,"Test Data used for iteration"+itr,
					 CommonLib.getIterationDataAsString(testdata),false);
			
			
			
			
			
			
			
		}catch (Exception e) {
			e.printStackTrace();
			Globals.exception = e;
			Reporter.logEvent(Status.FAIL, "A run time exception occured.", e.getCause().getMessage(), true);	
		} catch (Error ae) {
			ae.printStackTrace();
			Globals.error = ae;
			String errorMsg = ae.getMessage();
			Reporter.logEvent(Status.FAIL, "Assertion Error Occured",
					errorMsg, true);
		} finally {
			try {
				Reporter.finalizeTCReport();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}
	
	
	
	
	
}
