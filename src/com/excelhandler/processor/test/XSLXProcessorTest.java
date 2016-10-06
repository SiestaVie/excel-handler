package com.excelhandler.processor.test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
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
	public void writeConvertableIntoXLSTest() throws IOException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException, InstantiationException{
		
		
		List<Convertable> convertables = new ArrayList<>();
		
		ConvertBeanSample sample1 = new ConvertBeanSample();
		sample1.setName("So----");
		
		ConvertBeanSample sample2 = new ConvertBeanSample();
		sample2.setName("Then-----");
		
		convertables.add(sample1);
		convertables.add(sample2);
		
		File file = processor.writeConvertableIntoXLS(convertables);
		
		FileInputStream fis = null;
		fis = new FileInputStream(file);
		
		List<Convertable> convertables2 = processor.readXLS(fis, ConvertBeanSample.class);
		
		for(Convertable conv : convertables2){
			System.out.println(((ConvertBeanSample) conv).getName());
		}
		
		}

}