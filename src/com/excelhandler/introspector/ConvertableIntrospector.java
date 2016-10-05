package com.excelhandler.introspector;

import java.util.Map;

public class ConvertableIntrospector {
	
	private Map<String, String> convertAttributes; 
	
	public void storeClassConvertAttributes(Object object){
		
		if(convertAttributes == null)
			convertAttributes = BeanIntrospector.retrieveConvertableFields(object);
		
	}

	public String getConvertAttribute(String title) {
		return convertAttributes.get(title);
	}
	
	

}
