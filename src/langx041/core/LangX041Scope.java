package langx041.core;

import java.util.Hashtable;

public class LangX041Scope extends LangX041Object {
	
	private Hashtable<String, LangX041Variable> childs = new Hashtable<>();

	public LangX041Scope(LangX041Object parent) {
		super(parent);
	}

	public Hashtable<String, LangX041Variable> getChilds() {
		return childs;
	}

	public boolean pushChilds(String name, LangX041Variable child) {
		return this.childs.put(name, child)!=null;
	}
	
	public LangX041Variable child(String name) {
		return child(name,this);
	}

	private LangX041Variable child(String name, LangX041Scope scope) {
		if(scope.getChilds().containsKey(name)) {
			return scope.getChilds().get(name);
		}else if(scope.getParent()!=null && scope.getParent() instanceof LangX041Scope) {
			return child(name, (LangX041Scope)scope.getParent());
		}
		return null;
	}
	
	public boolean existsChild(String name) {
		return existsChild(name,this);
	}

	private boolean existsChild(String name, LangX041Scope scope) {
		if(scope.getChilds().containsKey(name)) {
			return true;
		}else if(scope.getParent()!=null && scope.getParent() instanceof LangX041Scope) {
			return existsChild(name, (LangX041Scope)scope.getParent());
		}
		return false;
	}
}
