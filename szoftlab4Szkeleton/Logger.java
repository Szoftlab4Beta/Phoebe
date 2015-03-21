package szoftlab4Szkeleton;

import java.util.HashMap;
import java.util.Map;

public class Logger {

	static Map<Object, String> IDs = new HashMap<Object, String>();
	static String indent = new String();
	
	public static void logCreate(Object object, String name){
		int i;
		for(i = 0; i < 10; i++){
			if(!IDs.containsValue(name + "_" + i)){
				IDs.put(object, name + "_" + i);
				break;
			}
		}
		if(i == 10)
			throw new IndexOutOfBoundsException("Can't have more than 10 of any instance");
		System.out.println(indent + "-><<create>>" + name + "_" + i);
	}
	
	public static void logCall(Object object, String msg){
		if(!IDs.containsKey(object))
			throw new IllegalArgumentException("Logger doesn't contain this object");
		System.out.println(indent + "->" + IDs.get(object) + "." + msg);
		indent = indent + "\t";
	}
	
	public static void logReturn(Object object, String msg){
		if(!IDs.containsKey(object))
			throw new IllegalArgumentException("Logger doesn't contain this object");
		System.out.println(indent + "<-" + IDs.get(object) + "." + msg);
		indent = indent.substring(0, indent.length() - 2);
	}
	
}
