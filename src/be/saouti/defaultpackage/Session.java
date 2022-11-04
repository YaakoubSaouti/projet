package be.saouti.defaultpackage;

import java.util.HashMap;
import java.util.Map;

class Session {
	//Members
	private static Session instance = new Session();
	private Map<String, String> session = new HashMap<String, String>();
	//Constructors
	private Session(){}
	
	//Methods
	public static Session getInstance(){ return instance; }
	
	public void set(String key, String value){
		session.put(key, value);
	}
	public String get(String key) {
		return session.get(key);
	}
	
	public void destroy() { session.clear(); }
}
