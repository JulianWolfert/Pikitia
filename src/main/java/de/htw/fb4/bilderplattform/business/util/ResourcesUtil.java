package de.htw.fb4.bilderplattform.business.util;

import java.text.MessageFormat;

import de.htw.fb4.bilderplattform.spring.SpringPropertiesUtil;

/**
 * 
 * @author Wojciech Konitzer
 * 
 *         22.12.2012
 * 
 */
public class ResourcesUtil {

	public static String loadPropertyWithWildcardValues(String property, String ...wildcards) {
		String propertyText = SpringPropertiesUtil.getProperty(property);	
		propertyText = MessageFormat.format(propertyText, (Object[])wildcards);	
		return propertyText;
	}
}
