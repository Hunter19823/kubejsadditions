package pie.ilikepiefoo2.kubejsadditions.api;

public class JavaField {
	public String name;
	public String type;
	public String value;
	public String comment;
	public String visibility;
	public String modifiers;
	public String annotations;

	public JavaField(String name, String type, String value, String comment, String visibility, String modifiers, String annotations) {
		this.name = name;
		this.type = type;
		this.value = value;
		this.comment = comment;
		this.visibility = visibility;
		this.modifiers = modifiers;
		this.annotations = annotations;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public String getComment() {
		return comment;
	}

	public String getVisibility() {
		return visibility;
	}

	public String getModifiers() {
		return modifiers;
	}

	public String getAnnotations() {
		return annotations;
	}

	public String toString() {
		return String.format("%s %s %s %s %s %s %s", visibility, modifiers, type, name, value, annotations, comment);
	}
}
