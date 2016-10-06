package com.excelhandler.introspector;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.excelhandler.model.Convertable;

public class ConvertableFiller {

	/**
	 * Baseado na primeira coluna da tabela faz de-para com o atributo name da anotação 
	 * Export nos Convertables e preenche os atributos de acordo com os nomes
	 * @param mySheet
	 * @return
	 * @throws IllegalAccessException 
	 * @throws InstantiationException 
	 * @throws InvocationTargetException 
	 * @throws NoSuchMethodException 
	 */
	public List<Convertable> retrieveBeansFromSheet(HSSFSheet mySheet, Class<? extends Convertable> classz) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

		
		List<Convertable> convertables = new ArrayList<>();
		Iterator<Row> rowIterator = mySheet.iterator();

		HashMap<Integer, String> collumnTitles = new HashMap<>();
		boolean firstLine = true;
		ConvertableIntrospector convIntrospector = new ConvertableIntrospector();

		while (rowIterator.hasNext()) {

			Row row = rowIterator.next();
			Iterator<Cell> cellIterator = row.cellIterator();			
			Convertable objct = classz.newInstance();

			while (cellIterator.hasNext()) {

				Cell cell = cellIterator.next();

				if(firstLine){
					collumnTitles.put(cell.getColumnIndex(), cell.toString());
					convIntrospector.storeClassConvertAttributes(classz.newInstance());
				}else{
					String attributeName = convIntrospector.getConvertAttribute(collumnTitles.get(cell.getColumnIndex()));
					if(attributeName != null && !attributeName.isEmpty())
					BeanIntrospector.setProperty(objct, attributeName, cell.toString());
				}

			}
			if(!firstLine)
				convertables.add(objct);
			firstLine = false;

		}

		return convertables;
	}


	public HSSFWorkbook generateSheetFromBeans(List<Convertable> convertables) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {
		
		HSSFWorkbook book = new HSSFWorkbook();
		HSSFSheet sheet = book.createSheet();
		ConvertableIntrospector convIntrospector = new ConvertableIntrospector();
		
		boolean first = true;
		int rowNum = 0;
		int cellNum = 0;
		
		for(Convertable convertable : convertables){
			
			if(first){
				Row row = sheet.createRow(rowNum);
				convIntrospector.storeClassConvertAttributes(convertable);				
				
				for(String title : convIntrospector.getConvertAttributes().keySet()){
					Cell cell = row.createCell(cellNum++);
					cell.setCellValue(title);					
				}
				first = false;
				cellNum = 0;
			}
			
			Row row = sheet.createRow(++rowNum);
			
			for(String title : convIntrospector.getConvertAttributes().keySet()){
				Cell cell = row.createCell(cellNum++);
				cell.setCellValue( BeanIntrospector.getProperty(convertable, convIntrospector.getConvertAttribute(title)).toString() );			
			}
			cellNum = 0;
			
			
		}
					
		return book;
	}
	
	
}