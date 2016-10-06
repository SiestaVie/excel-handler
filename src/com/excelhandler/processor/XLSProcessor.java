package com.excelhandler.processor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.excelhandler.introspector.ConvertableFiller;
import com.excelhandler.model.Convertable;

public class XLSProcessor {

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
	public List<Convertable> readXLS(InputStream fis, Class<? extends Convertable> classz) throws IOException,
			InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

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

	public File writeConvertableIntoXLS(List<Convertable> convertables) throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {

		ConvertableFiller filler = new ConvertableFiller();
		Date data = new Date();

		FileOutputStream fileOut = new FileOutputStream(data.getTime() + ".xls");
		File file = new File(data.getTime() + ".xls");

		HSSFWorkbook book = filler.generateSheetFromBeans(convertables);

		book.write(fileOut); 

		fileOut.close(); 
		book.close();

		return file;

	}

}