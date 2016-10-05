package com.excelhandler.model;

public class ConvertBeanSample implements Convertable{
	
	@Convert(title = "nome")
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}		

}
