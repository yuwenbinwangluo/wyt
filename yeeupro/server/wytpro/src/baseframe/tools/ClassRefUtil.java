package baseframe.tools;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/*
 * 通过Java反射机制，动态给对象属性赋值，并获取属性值
 */
public class ClassRefUtil {

	/**
	 * 通过反射填充对象【注意：set get方法】
	 * @param datas
	 * @param objclass
	 * @return
	 */
	public static <T> T inflateMap(Class<T> objclass,Map<String,Object> datas)
	{
		T bean=null;
		try {
			bean=objclass.newInstance();
			Field[] fields=objclass.getDeclaredFields();
			Method[] methods=objclass.getDeclaredMethods();
			Field field;
			for(int i=0;i<fields.length;i++)
			{
				//获得set方法
				field=fields[i];
				String fieldSetName=parSetName(field.getName());
				if(!checkSetMet(methods, fieldSetName))
				{
					continue;
				}
			    Method fieldSetMet = objclass.getMethod(fieldSetName,  field.getType());  
				Object value=datas.get(field.getName());
				if(value!=null)
				{
					fieldSetMet.invoke(bean, value);
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}catch (InvocationTargetException e) {
			e.printStackTrace();
		}catch (NoSuchMethodException e) {
			e.printStackTrace();
		}catch (SecurityException e) {
			e.printStackTrace();
		}
		return bean;
	}
	/**
	 * 通过反射填充对象到Map【注意：set get方法】
	 * @param datas
	 * @param objclass
	 * @return
	 */
	public static Map<String,Object> inflateObjectToMap(Object obj,Map<String,Object> datas)
	{
		Class<?> cls=obj.getClass();
		try {
			Field[] fields=cls.getDeclaredFields();
			Method[] methods=cls.getDeclaredMethods();
			Field field;
			for(int i=0;i<fields.length;i++)
			{
				//获得set方法
				field=fields[i];
				String fieldGetName=parGetName(field.getName());
				if(!checkGetMet(methods, fieldGetName))
				{
					continue;
				}
			    Method fieldGetMet= cls.getMethod(fieldGetName);
				Object value=fieldGetMet.invoke(obj);
				datas.put(field.getName(), value);
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}catch (InvocationTargetException e) {
			e.printStackTrace();
		}catch (NoSuchMethodException e) {
			e.printStackTrace();
		}catch (SecurityException e) {
			e.printStackTrace();
		}
		return datas;
	}
	/**
	 * 通过反射填充对象【注意：public 的属性】
	 * @param datas
	 * @param objclass
	 * @return
	 */
	public static <T> T inflateMapProperty(Class<T> objclass,Map<String,Object> datas)
	{
		T result=null;
		try {
			result=objclass.newInstance();
			Field[] fields=objclass.getDeclaredFields();
			Field field;
			for(int i=0;i<fields.length;i++)
			{
				field=fields[i];
				Object objvalue=datas.get(field.getName());
				if(objvalue!=null)
				{
					field.set(result, objvalue);
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return result;
	}
   
    /** 
     * 判断是否存在某属性的 set方法 
     * @param methods 
     * @param fieldSetMet 
     * @return boolean 
     */ 
    private static boolean checkSetMet(Method[] methods, String fieldSetMet) {  
        for (Method met : methods) {  
            if (fieldSetMet.equals(met.getName())) {  
                return true;  
            }  
        }  
        return false;  
    }  
    /** 
     * 判断是否存在某属性的 get方法 
     * @param methods 
     * @param fieldGetMet 
     * @return boolean 
     */ 
    private static boolean checkGetMet(Method[] methods, String fieldGetMet) {  
        for (Method met : methods) {  
            if (fieldGetMet.equals(met.getName())) {  
                return true;  
            }  
        }  
        return false;  
    }  
    /** 
     * 拼接某属性的 get方法 
     * @param fieldName 
     * @return String 
     */ 
    private static String parGetName(String fieldName) {  
        if (null == fieldName || "".equals(fieldName)) {  
            return null;  
        }  
        return "get" + fieldName.substring(0, 1).toUpperCase()  
                + fieldName.substring(1);  
    }  
    /** 
     * 拼接在某属性的 set方法 
     * @param fieldName 
     * @return String 
     */ 
    private static String parSetName(String fieldName) {  
        if (null == fieldName || "".equals(fieldName)) {  
            return null;  
        }  
        return "set" + fieldName.substring(0, 1).toUpperCase()  
                + fieldName.substring(1);  
    } 
}
