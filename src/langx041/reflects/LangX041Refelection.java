package langx041.reflects;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;

import langx041.core.LangX041Value;

public class LangX041Refelection {
	private static LinkedList<String> packages  = new LinkedList<>();
	
	public static void pushPackage(String packageName) {
		packages.add(packageName);
	}
	
	public static String fullIdentifier(String className) {
		// get list of used packages
		for(String pkg:packages) {
			try {
				Class.forName(pkg+"."+className);
				return pkg+"."+className;
			} catch (ClassNotFoundException e) {
				//e.printStackTrace();
			}
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static Class makeObject(String className) {
		try {
			return Class.forName(className);
		} catch (ClassNotFoundException e) {
			//e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("rawtypes")
	public static boolean existsField(Object classInstance, String fieldName) {
			Class cls = classInstance instanceof Class?(Class) classInstance : classInstance.getClass();
			try {
				cls.getField(fieldName);
				return true;
			} catch (NoSuchFieldException | SecurityException e) {
				//e.printStackTrace();
				return false;
			}
	}
	
	@SuppressWarnings("rawtypes")
	public static Object getFieldObject(Object classInstance, String fieldName) {
		try {
			Field field = classInstance instanceof Class ? ((Class)classInstance).getField(fieldName) : classInstance.getClass().getField(fieldName);
			return field.get(classInstance);
		} catch (NoSuchFieldException | SecurityException e) {
			//e.printStackTrace();
		} catch (IllegalArgumentException e) {
			//e.printStackTrace();
		} catch (IllegalAccessException e) {
			//e.printStackTrace();
		}
		
		return null;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static boolean existsSubroutine(Object classInstance, String methodName, LangX041Value ... args) {
		Class cls = classInstance instanceof Class ? (Class)classInstance : classInstance.getClass();
		
		try {
			if(args != null) {
				LinkedList<Class> classes = new LinkedList<>();
				for(LangX041Value value:args) {
					classes.add(value.getType());
				}
				cls.getMethod(methodName, classes.toArray(new Class[] {}));
	
			}else {
				cls.getMethod(methodName, new Class[] {});
			}
		} catch (NoSuchMethodException | SecurityException e) {
			//e.printStackTrace();
		}
		return false;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Object invokeStaticSubroutine(Object classInstance, String methodName, LangX041Value ... args) {
		Class cls = classInstance instanceof Class ? (Class)classInstance : classInstance.getClass();
		
		try {
			if(args != null) {
				LinkedList<Class> params = new LinkedList<>();
				for(LangX041Value value:args) {
					params.add(value.getType());
				}
				
				Method method = cls.getMethod(methodName, params.toArray(new Class[] {}));
				
				LinkedList<Object> values = new LinkedList<>();
				for(LangX041Value value:args) {
					values.add(value.getValue());
				}
				
				return method.invoke(classInstance, values.toArray(new Object[] {}));
	
			}else {
				Method method = cls.getMethod(methodName, new Class[] {});
				return method.invoke(classInstance, new Object[] {});
			}
		} catch (NoSuchMethodException | SecurityException e) {
			//e.printStackTrace();
		} catch (IllegalAccessException e) {
			//e.printStackTrace();
		} catch (IllegalArgumentException e) {
			//e.printStackTrace();
		} catch (InvocationTargetException e) {
			//e.printStackTrace();
		}
		
		return null;
	}
}
