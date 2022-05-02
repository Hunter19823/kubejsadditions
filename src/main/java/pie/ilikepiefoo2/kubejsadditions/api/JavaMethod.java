package pie.ilikepiefoo2.kubejsadditions.api;

public class JavaMethod {
	public String name;
	public String returnType;

	public String access;
	public String[] parameters;
	public String[] parameterTypes;
	public String[] exceptions;
	public JavaField javaField;

	public JavaMethod(String name, String returnType, String access, String[] parameters, String[] parameterTypes,String[] exceptions, JavaField javaField) {
		this.name = name;
		this.returnType = returnType;
		this.access = access;
		this.parameters = parameters;
		this.parameterTypes = parameterTypes;
		this.exceptions = exceptions;
		this.javaField = javaField;
	}

	public String getName() {
		return name;
	}

	public String getReturnType() {
		return returnType;
	}

	public String getAccess() {
		return access;
	}

	public String[] getParameters() {
		return parameters;
	}

	public String[] getExceptions() {
		return exceptions;
	}

	public JavaField getJavaField() {
		return javaField;
	}

	public String toString() {
		// Build the method signature
		StringBuilder sb = new StringBuilder();
		String spacing = "    ";
		sb.append(spacing).append(access).append(" ").append(returnType).append(" ").append(name).append("(");
		for (int i = 0; i < parameters.length; i++) {
			sb.append(parameters[i]);
			if (i < parameters.length - 1) {
				sb.append(", ");
			}
		}
		sb.append(")");
		// Add the exceptions
		sb.append(" throws ");
		for (int i = 0; i < exceptions.length; i++) {
			sb.append(exceptions[i]);
			if (i < exceptions.length - 1) {
				sb.append(", ");
			}
		}
		sb.append("; {\n");
		// Add the field if it is not null;
		sb.append(spacing).append(spacing);
		sb.append("if (").append(javaField.getName()).append(" != null)");
		sb.append("\n").append(spacing).append(spacing).append(spacing);
		sb.append("return ").append(javaField.getName()).append(".process(this,");
		for (int i = 0; i < parameters.length; i++) {
			sb.append(parameters[i]);
			if (i < parameters.length - 1) {
				sb.append(", ");
			}
		}
		sb.append(");");
		// Return super if the field is null
		sb.append("\n").append(spacing).append(spacing);
		sb.append("return super.").append(name).append("(");
		for (int i = 0; i < parameters.length; i++) {
			sb.append(parameters[i]);
			if (i < parameters.length - 1) {
				sb.append(", ");
			}
		}
		sb.append(");");
		sb.append("\n}");
		return sb.toString();
	}
}
