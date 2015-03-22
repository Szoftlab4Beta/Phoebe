package szoftlab4Szkeleton;

import java.util.HashMap;
import java.util.Map;

public class Logger {

	static Map<Object, String> IDs = new HashMap<Object, String>();
	static String indent = new String();
	static boolean disabled=false;
	
	public static void logCreate(Object object, String name){
		logCreate(object, name, "");
	}
	
	public static void logCreate(Object object, String name, String params){
		int i;
		for(i = 0; i < 10; i++){
			if(!IDs.containsValue(name + "_" + i)){
				IDs.put(object, name + "_" + i);
				break;
			}
		}
		if(i == 10)
			throw new IndexOutOfBoundsException("Can't have more than 10 of any instance");
		
		if(disabled)
			return;
		
		System.out.println(indent + "-><<create>>" + name + "_" + i + params);
	}
	
	public static void logCall(Object object, String msg){
		if(disabled)
			return;
		
		if(!IDs.containsKey(object))
			throw new IllegalArgumentException("Logger doesn't contain this object");
		System.out.println(indent + "->" + IDs.get(object) + "." + msg);
		indent = indent + "\t";
	}
	
	public static void logReturn(Object object, String msg){
		if(disabled)
			return;
		
		if(!IDs.containsKey(object))
			throw new IllegalArgumentException("Logger doesn't contain this object");
		indent = indent.substring(0, indent.length() - 1);
		System.out.println(indent + "<-" + IDs.get(object) + "." + msg);
	}
	
	public static String getIDOf(Object object){
		if(!IDs.containsKey(object))
			throw new IllegalArgumentException("Logger doesn't contain this object");
		return IDs.get(object);
	}
	
	public static void logMSG(String msg){
		if(disabled)
			return;
		
		System.out.print(indent + msg);
	}
	
	public static void enable(){
		disabled=false;
	}
	
	public static void disable(){
		disabled=true;
	}
	
	public static boolean isDisabled(){
		return disabled;
	}
}
