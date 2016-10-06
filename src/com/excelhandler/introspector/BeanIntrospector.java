package com.excelhandler.introspector;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;

import com.excelhandler.model.Convert;



public class BeanIntrospector {

	/**
    *
    * @param bean
    * @param name
    * @return
    * @throws IllegalAccessException
    * @throws InvocationTargetException
    * @throws NoSuchMethodException
    * @throws NoSuchFieldException
    */
   public static Object getProperty(Object bean, String name) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, NoSuchFieldException {

       Method method;
       if (bean == null) {
    	  return null;           
       }
       if (name == null) {
    	 return null;
       }
       method = bean.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1));
       return method.invoke(bean, (Object[]) null);

   }

   /**
    * 
    * @param bean
    * @param name
    * @param value
    * @throws NoSuchMethodException
    * @throws IllegalAccessException
    * @throws InvocationTargetException 
    */
   public static void setProperty(Object bean, String name, String value) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

	   if (null == bean) {
		   return;
       }
       if (null == name) {
    	   return;
       }

       @SuppressWarnings("rawtypes")
       Class[] parameterTypes = new Class[]{String.class};
       Object[] argumentsList = new Object[]{value};
       Method setterMethod = bean.getClass().getMethod("set" + name.substring(0, 1).toUpperCase() + name.substring(1), parameterTypes);
       setterMethod.invoke(bean, argumentsList);
   }

   public static HashMap<String, Object> retrieveFieldsAndValues(Object object) {

	   HashMap<String, Object> fieldsAndValues = new HashMap<String, Object>();
	   Field[] fields = object.getClass().getDeclaredFields();

		  for (Field field : fields) {
			  Object property = null;
			  
			try {
				property = BeanIntrospector.getProperty(object, field.getName());
			} catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException | NoSuchFieldException e) {
				e.printStackTrace();
			}
		      
			  fieldsAndValues.put(field.getName(), property);
		  }

		  return fieldsAndValues;
   }
   
   public static HashMap<String, String> retrieveConvertableFields(Object object) {

	   HashMap<String, String> fieldsAndValues = new HashMap<String, String>();
	   Field[] fields = object.getClass().getDeclaredFields();

		  for (Field field : fields) {
			  if(field.isAnnotationPresent(Convert.class)){
				  Convert annotation = field.getAnnotation(Convert.class);
				  fieldsAndValues.put(annotation.title(), field.getName());
			  }

		  }

		  return fieldsAndValues;
   }

}