package config;

import common.ParseXML;

/**
 * @author cylong
 * @version 2014年11月28日 下午12:38:53
 */
public class RMI {

	public static String URL;
	public static String OBJ_NAME;
	public static int PORT; 

	static {
		ParseXML parse = new ParseXML("config/RMI.xml", "RMI");
		URL = parse.getValue("url");
		OBJ_NAME = parse.getValue("obj_name");
		PORT = Integer.parseInt(parse.getValue("port"));
	}
}