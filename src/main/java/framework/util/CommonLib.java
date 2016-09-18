package framework.util;

import java.util.List;
import java.util.Map;
import java.util.Properties;

import lib.Log;
import lib.Log.Level;
import lib.Reporter;
import lib.Reporter.Status;
import lib.Stock;
import lib.Web;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class CommonLib {
	static Properties testConfig = null;
	
	public static void HighlightElement(WebElement ele, WebDriver driver) {
		for (int i = 0; i < 3; i++) {
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);", ele,
					"color: yellow; border: 2px solid yellow;");
			js.executeScript(
					"arguments[0].setAttribute('style', arguments[1]);", ele,
					"");
		}
	}
	
	public static boolean waitForHiddenEle(WebElement ele) throws InterruptedException{
		int timeout = Integer.parseInt(Stock.getConfigParam("objectSyncTimeout"));
		for(int iTimeOut=0;iTimeOut<=timeout;iTimeOut++){
			if(ele.getCssValue("display").equals("none")){
				return true;
			}					
			Thread.sleep(1000);
		}
		return false;
	}
	
	public static void enterData(WebElement ele, String value) {
		String tagName = "";
		try {
			tagName = ele.getTagName();
			if (tagName.equals("input")) {
				if (ele.getAttribute("type").equals("text")) {
					Web.setTextToTextBox(ele, value);
				} else if (ele.getAttribute("type").equals("radio")) {
					Web.clickOnElement(ele);
				}
			}
			if (tagName.equals("select")) {
				Select select = new Select(ele);
				for (WebElement opt : select.getOptions()) {
					if (Web.VerifyPartialText(value, opt.getText(), true)) {
						opt.click();
						break;
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void fillForm(WebElement parentNode, String... coLNames) {
		String tagName = "";
		try {
			for (String colNm : coLNames) {
				tagName = parentNode.findElement(By.id(colNm)).getTagName();
				if (tagName.equals("input")) {
					if (parentNode.findElement(By.id(colNm))
							.getAttribute("type").equals("text")) {
						Thread.sleep(500);
						Web.setTextToTextBox(
								parentNode.findElement(By.id(colNm)),
								Stock.globalTestdata.get(colNm));
					} else if (parentNode.findElement(By.id(colNm))
							.getAttribute("type").equals("radio")) {
						Thread.sleep(500);
						Web.clickOnElement(parentNode.findElement(By.id(colNm)));
					}
				}
				if (tagName.equals("select")) {
					Select select = new Select(parentNode.findElement(By
							.id(colNm)));
					for (WebElement opt : select.getOptions()) {
						if (Web.VerifyPartialText(
								Stock.globalTestdata.get(colNm), opt.getText(),
								true)) {
							opt.click();
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean VerifyText(String inExpectedText,
			String inActualText, boolean... ignoreCase) {
		boolean isMatching = false;

		if (ignoreCase.length > 0) {
			if (ignoreCase[0]) {
				if (inExpectedText.trim().equalsIgnoreCase(inActualText.trim())) {
					isMatching = true;
				}
			} else {
				if (inExpectedText.trim().equals(inActualText.trim())) {
					isMatching = true;
				}
			}
		} else {
			if (inExpectedText.trim().equals(inActualText.trim())) {
				isMatching = true;
			}
		}
		return isMatching;
	}
	
	public static String getIterationDataAsString(Map<String, String> testdata){
		String result = "";
		for(Map.Entry<String,String> td : testdata.entrySet()){
			result = result+(td.getKey()+" : "+td.getValue())+" | ";
		}
		return result.substring(0,result.lastIndexOf("|"));
	}
	
	public static void FrameSwitchONandOFF(boolean switchFrame,WebElement... frm){
		if(Web.isWebElementDisplayed(frm[0],false) && switchFrame){
		   Web.webdriver.switchTo().frame(frm[0]);
		}
		
		if(!switchFrame){
		   Web.webdriver.switchTo().defaultContent();
		}		
	}
	
	public static boolean VerifyBreadCrum(List<WebElement> eleBreadCrumb,String pageName,String dataColName) {
		int breadcrumcnt=0;
		try{
			//verify bread crumb    // HANDLE OTHER PARTNERS	
			if(Web.isWebElementDisplayed(eleBreadCrumb.get(1),true)){
				//checking from index 1 as the first breadcrum link is hidden
				for(int iLoop=1;iLoop<=Stock.GetParameterValue(dataColName).split(",").length;iLoop++){
			        if(eleBreadCrumb.get(iLoop).getText().trim()
			          .equalsIgnoreCase(Stock.GetParameterValue(dataColName).split(",")[iLoop-1].trim())){
			          breadcrumcnt++;
			        }	
				}
				if(breadcrumcnt==Stock.GetParameterValue(dataColName).split(",").length){
					Log.Report(Level.INFO, "Verified bread crumb is in " + pageName.toUpperCase() +" page");			
					return true;
				}			
			}
		}catch(Exception e){
			Reporter.logEvent(Status.FAIL,"Failed to verify breadcrum for app  "
		    +Stock.getConfigParam("TEST_ENV").split("_")[0],"Failed to verify breadcrum for page "
			+pageName+" Error : "+e.getMessage(),true);
			Log.Report(Level.INFO,"Bread crumb verification failed in " +  pageName.toUpperCase() +" page");
		}
		return false;
	}

	public static void SetTestEnv(String string) {
		if(testConfig == null) testConfig = new Properties();
		
	}

}
