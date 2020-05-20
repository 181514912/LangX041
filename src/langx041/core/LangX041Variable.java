package langx041.core;

public class LangX041Variable extends LangX041Object {
	
	private String variableName = null;
	private LangX041Value variableValue = null;
	
	public String getVariableName() {
		return variableName;
	}
	
	public void setVariableName(String variableName) {
		this.variableName = variableName;
	}
	
	public LangX041Value getVariableValue() {
		return variableValue;
	}
	
	public void setVariableValue(LangX041Value variableValue) {
		this.variableValue = variableValue;
	}
	
	
}
