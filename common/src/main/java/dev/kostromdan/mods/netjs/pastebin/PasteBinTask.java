package dev.kostromdan.mods.netjs.pastebin;

import java.util.LinkedHashMap;

public class PasteBinTask {

	public String id;
	public LinkedHashMap<String, Object> result = null;
	public boolean success;
	public Exception exception;

	PasteBinTask(){
		result=new LinkedHashMap<String,Object>();
	}
}
