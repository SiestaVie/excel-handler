package com.excelhandler.introspector;

import java.util.Map;

public class ConvertableIntrospector {

	private Map<String, String> convertAttributes; 

	public void storeClassConvertAttributes(Object object){

		if(convertAttributes == null)
			convertAttributes = BeanIntrospector.retrieveConvertableFields(object);

	}

	public Map<String, String> getConvertAttributes(){
		return convertAttributes;
	}

	public String getConvertAttribute(String title) {
		return convertAttributes.get(title);
	}

}