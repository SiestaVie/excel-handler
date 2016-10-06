package com.excelhandler.processor.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.excelhandler.model.ConvertBeanSample;
import com.excelhandler.model.Convertable;
import com.excelhandler.processor.XLSProcessor;

public class XSLXProcessorTest {
	
	XLSProcessor processor = new XLSProcessor();

	@Before
	public void init(){
		processor = new XLSProcessor();		
	}

	@Test
	public void writeConvertableIntoXLSTest() throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException{
		
		
		List<Convertable> convertables = new ArrayList<>();
		
		ConvertBeanSample sample1 = new ConvertBeanSample();
		sample1.setName("Nome-----");
		
		convertables.add(sample1);
		
		File file = processor.writeConvertableIntoXLS(convertables);
		
		BufferedReader br = null;

		try {

			String sCurrentLine;

			br = new BufferedReader(new FileReader(file));

			while ((sCurrentLine = br.readLine()) != null) {
				System.out.println(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
	}


}