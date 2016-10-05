package com.excelhandler.introspector;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;

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
	public List<Convertable> retrieveBeansFromSheet(XSSFSheet mySheet, Class<? extends Convertable> classz) throws InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {

		
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
	
	
}