package com.excelhandler.processor;


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
	public List<Convertable> readXSLX(InputStream fis, Class<? extends Convertable> classz) throws IOException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException  {
		
	
		ConvertableFiller filler = new ConvertableFiller();

		// Finds the workbook instance for XLSX file
		HSSFWorkbook myWorkBook = new HSSFWorkbook(fis);
		// Return first sheet from the XLSX workbook
		HSSFSheet sheet = myWorkBook.getSheetAt(0);
		
		List<Convertable> convertables = filler.retrieveBeansFromSheet(sheet, classz);
		
		myWorkBook.close();		
		fis.close();
	
		return convertables;
		
	}

}