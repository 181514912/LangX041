package langx041.core;

public class LangX041Object {
	
	private LangX041Object parent = null;

	public LangX041Object() {
		
	}

	public LangX041Object(LangX041Object parent) {
		super();
		this.parent = parent;
	}

	public LangX041Object getParent() {
		return parent;
	}
	
}
