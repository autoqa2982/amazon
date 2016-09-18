package pageobjects.home;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class home {
	
	@FindBy(css = "input[id *= 'mpwrPlanBox']")
	private WebElement txtPlanNumber;
	@FindBy(css = "span[class *= 'growl-title']")
	private WebElement errorMsgBox;
	
	public void appLogin(){
		
	}
	
	public boolean ifLoginisSuccssfull(){
		
		return false;
	}
	
}
