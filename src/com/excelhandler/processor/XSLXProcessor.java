package com.excelhandler.processor;


import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.excelhandler.introspector.ConvertableFiller;

import com.excelhandler.model.Convertable;

public class XSLXProcessor {

	/**
	 * 
	 * @param fis
	 * @param classz
	 * @return
	 * @throws IOException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 */
	public List<Convertable> readXSLX(FileInputStream fis, Class<? extends Convertable> classz) throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException  {
		
	
		ConvertableFiller filler = new ConvertableFiller();

		// Finds the workbook instance for XLSX file
		XSSFWorkbook myWorkBook = new XSSFWorkbook(fis);
		// Return first sheet from the XLSX workbook
		XSSFSheet mySheet = myWorkBook.getSheetAt(0);
		
		List<Convertable> convertables = filler.retrieveBeansFromSheet(mySheet, classz);
		
		myWorkBook.close();		
		fis.close();
	
		return convertables;
		
	}

}