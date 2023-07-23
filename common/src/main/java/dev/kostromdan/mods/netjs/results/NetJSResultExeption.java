package dev.kostromdan.mods.netjs.results;

import java.util.LinkedHashMap;

public class NetJSResultExeption extends NetJSResult {
	public Exception exception;

	public NetJSResultExeption(Exception exeption, LinkedHashMap<String, Object> result) {
		this.exception = exeption;
		this.result = result;
		this.success = false;
	}

	public NetJSResultExeption(Exception exception) {
		this(exception, null);
	}
}
