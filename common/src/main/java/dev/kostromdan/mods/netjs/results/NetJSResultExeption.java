package dev.kostromdan.mods.netjs.results;

public class NetJSResultExeption extends NetJSResult {
	public Exception exception;

	public NetJSResultExeption(String id, Exception exeption, String raw) {
		this.id = id;
		this.exception = exeption;
		this.raw = raw;
		this.success = false;
	}

	public NetJSResultExeption(String id, Exception exception) {
		this(id, exception, null);
	}
}
