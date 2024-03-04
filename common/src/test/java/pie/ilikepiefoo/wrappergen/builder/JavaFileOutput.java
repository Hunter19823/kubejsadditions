package pie.ilikepiefoo.wrappergen.builder;

public interface JavaFileOutput {
    /**
     * The string used to represent indentation rules in the output.
     */
    String INDENTATION_STRING = "    ";

    /**
     * This method is used to convert the current object into a Java file format.
     * The output is a string representation of the object, formatted as it would appear in a
     * Java file.
     *
     * @param indentLevel The level of indentation to be used in the output. This is used to
     *                    ensure proper formatting and readability of the output.
     * @return A string representation of the object, formatted as it would appear in a Java file.
     */
    String toJavaFile(int indentLevel);
}
