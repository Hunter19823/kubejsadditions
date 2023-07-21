package dev.kostromdan.mods.netjs.results;

import java.util.Map;

public class NetJSResultExeption extends NetJSResult {
	public Exception exception;

	public NetJSResultExeption(String id, Exception exeption, Map<String, Object> result) {
		this.id = id;
		this.exception = exeption;
		this.result = result;
		this.success = false;
	}

	public NetJSResultExeption(String id, Exception exception) {
		this(id, exception, null);
	}
}
