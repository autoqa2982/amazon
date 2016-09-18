package framework.util;

import core.framework.DriverScript;

/**
 * BuildXML class is used to create custom TestNG XML that gets generated from the runorder sheet. 
 * Pre-Req: Runorder sheets need to be updated with the test cases that needs to be executed
 * Output: The Scripts build a custom TestNG XML and is saved in the RUNXML fodler in the project 
 */
public class BuildXML 
{
    public static void main( String[] args ) throws Exception
    {    	
		try {
			@SuppressWarnings("unused")
			DriverScript driver = new DriverScript();
			
		} catch (Exception e) {			
			e.printStackTrace();
		}
    	        
    }
        
}
