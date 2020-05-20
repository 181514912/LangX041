package langx041.core;

import java.util.Enumeration;
import java.util.LinkedList;

import langx041.interpreter.InterpretFace;
import langx041.reflects.LangX041Refelection;
import langx041.syntaxtree.AdditiveExpression;
import langx041.syntaxtree.IfExpression;
import langx041.syntaxtree.JavaStaticMethods;
import langx041.syntaxtree.MathExpression;
import langx041.syntaxtree.MultiplicativeExpression;
import langx041.syntaxtree.NodeChoice;
import langx041.syntaxtree.NodeSequence;
import langx041.syntaxtree.NodeToken;
import langx041.syntaxtree.RelationalEqualityExpression;
import langx041.syntaxtree.RelationalExpression;
import langx041.syntaxtree.RelationalGreaterExpression;
import langx041.syntaxtree.RelationalLessExpression;
import langx041.syntaxtree.Require;
import langx041.syntaxtree.Start;
import langx041.syntaxtree.StatementExpression;
import langx041.syntaxtree.UnaryExpression;
import langx041.syntaxtree.UnaryRelational;
import langx041.syntaxtree.VariableAssign;
import langx041.syntaxtree.VariableDeclaration;
import langx041.syntaxtree.VariableName;
import langx041.syntaxtree.WhileExpression;

@SuppressWarnings("rawtypes")
public class Interpreter implements InterpretFace {

	@Override
	public Object visit(Start node) throws Exception {
		Enumeration importedPackagesEnum = (Enumeration) node.f0.elements();
		while(importedPackagesEnum.hasMoreElements()) {
			// adding required packages
			NodeSequence ns = (NodeSequence) importedPackagesEnum.nextElement();
			LangX041Refelection.pushPackage(this.visit((Require)ns.elementAt(0),null).toString());
		}
		if(node.f1.size()>0) {
			LangX041Scope parent = new LangX041Scope(null);
			Enumeration statement = (Enumeration) node.f1.elements();
			while(statement.hasMoreElements()) {
				this.visit((StatementExpression)statement.nextElement(), parent);
			}
		}
		return null;
	}

	@Override
	public Object visit(Require node, LangX041Scope scope, Object... objects) {
		StringBuilder builder = new StringBuilder();
		Enumeration element = (Enumeration) node.f1.elements();
		while(element.hasMoreElements()) {
			builder.append(element.nextElement());
			if(element.hasMoreElements()) {
				builder.append(".");
			}
		}
		return builder;
	}

	@Override
	public Object visit(MathExpression node, LangX041Scope scope, Object... objects) throws Exception {
		return this.visit(node.f0,scope,objects);
	}

	@Override
	public Object visit(AdditiveExpression node, LangX041Scope scope, Object... objects) throws Exception {
		
		LangX041Value value = (LangX041Value) this.visit(node.f0,scope,objects);
		
		Enumeration itr = (Enumeration) node.f1.elements();
		while(itr.hasMoreElements()) {
			NodeSequence ns = (NodeSequence) itr.nextElement();
			NodeChoice nc = (NodeChoice) ns.elementAt(0);
			
			LangX041Value temp = (LangX041Value) this.visit((MultiplicativeExpression) ns.elementAt(1),scope,objects);
			
			if(nc.choice.toString().equals("+")) {
				temp.setValue(value.getValue()+temp.getValue());
				return temp;
			}else {
				temp.setValue(value.getValue() - temp.getValue());
				return temp;
			}
		}
		return value;
	}

	@Override
	public Object visit(MultiplicativeExpression node, LangX041Scope scope, Object... objects) throws Exception {
		
		LangX041Value value = (LangX041Value) this.visit(node.f0,scope,objects);
		
		Enumeration itr = (Enumeration) node.f1.elements();
		while(itr.hasMoreElements()) {
			NodeSequence ns = (NodeSequence) itr.nextElement();
			NodeChoice nc = (NodeChoice) ns.elementAt(0);
			
			LangX041Value temp = (LangX041Value) this.visit((UnaryExpression) ns.elementAt(1),scope,objects);
			
			if(nc.choice.toString().equals("*")) {
				temp.setValue(value.getValue()*temp.getValue());
				return temp;
			}else if(nc.choice.toString().equals("/")) {
				temp.setValue(value.getValue() / temp.getValue());
				return temp;
			}else if(nc.choice.toString().equals("%")) {
				temp.setValue(value.getValue() % temp.getValue());
				return temp;
			}
		}
		return value;
	}

	@Override
	public Object visit(UnaryExpression node, LangX041Scope scope, Object... objects) throws Exception {
		
		if(node.f0.choice instanceof NodeToken) {
			LangX041Value value = new LangX041Value();
			value.setValue(Long.parseLong(node.f0.choice.toString()));
			value.setType(long.class);
			return value;
		} else if(node.f0.choice instanceof VariableName) {
			String var = this.visit((VariableName)node.f0.choice,scope,objects).toString();
			if(scope.existsChild(var)) {
				return scope.child(var).getVariableValue();
			}else {
				throw new Exception("Sorry, but the variable "+var+" not exists !");
			}
		} else if(node.f0.choice instanceof NodeSequence) {
			NodeSequence ns = (NodeSequence) node.f0.choice;
			return this.visit((MathExpression)ns.elementAt(1),scope,objects);
		}
		return null;
	}

	@Override
	public Object visit(RelationalExpression node, LangX041Scope scope, Object... objects) throws Exception {
		return this.visit(node.f0,scope,objects);
	}

	@Override
	public Object visit(RelationalEqualityExpression node, LangX041Scope scope, Object... objects) throws Exception {
		
		Object obj = this.visit(node.f0,scope,objects);
		if(node.f1 != null && obj instanceof Long) {
			// modified code
			Object temp = this.visit((RelationalGreaterExpression)node.f1.elementAt(1),scope,objects);
			
			if(temp instanceof Long) {
				NodeChoice nc = (NodeChoice) node.f1.elementAt(0);
				if(nc.choice.toString().equals("==")) {
					obj = Long.parseLong(obj.toString()) == Long.parseLong(temp.toString());
				}else if(nc.choice.toString().equals("!=")) {
					obj = Long.parseLong(obj.toString()) != Long.parseLong(temp.toString());
				}
			}
		}
		return obj;
	}

	@Override
	public Object visit(RelationalGreaterExpression node, LangX041Scope scope, Object... objects) throws Exception {
		
		Object obj = this.visit(node.f0,scope,objects);
		if(node.f1 != null && obj instanceof Long) {
			// modified code
			Object temp = this.visit((RelationalLessExpression)node.f1.elementAt(1),scope,objects);
			
			if(temp instanceof Long) {
				NodeChoice nc = (NodeChoice) node.f1.elementAt(0);
				if(nc.choice.toString().equals(">")) {
					obj = Long.parseLong(obj.toString()) > Long.parseLong(temp.toString());
				}else if(nc.choice.toString().equals(">=")) {
					obj = Long.parseLong(obj.toString()) >= Long.parseLong(temp.toString());
				}
			}
		}
		return obj;
	}

	@Override
	public Object visit(RelationalLessExpression node, LangX041Scope scope, Object... objects) throws Exception {
		
		Object obj = this.visit(node.f0,scope,objects);
		if(node.f1 != null && obj instanceof Long) {
			// modified code
			Object temp = this.visit((UnaryExpression)node.f1.elementAt(1),scope,objects);
			
			if(temp instanceof Long) {
				NodeChoice nc = (NodeChoice) node.f1.elementAt(0);
				if(nc.choice.toString().equals("<")) {
					obj = Long.parseLong(obj.toString()) < Long.parseLong(temp.toString());
				}else if(nc.choice.toString().equals("<=")) {
					obj = Long.parseLong(obj.toString()) <= Long.parseLong(temp.toString());
				}
			}
		}
		return obj;
	}

	@Override
	public Object visit(UnaryRelational node, LangX041Scope scope, Object... objects) throws Exception {
		return ((LangX041Value)this.visit((MathExpression)node.f0.choice,scope,objects)).getValue();
	}

	@Override
	public Object visit(IfExpression node, LangX041Scope scope, Object... objects) throws Exception {
		
		LangX041Scope ifScope = new LangX041Scope(scope);
		if(new Boolean(this.visit(node.f1,scope,objects).toString())) {
			Enumeration itr = (Enumeration) node.f3.elements();
			while(itr.hasMoreElements()) {
				this.visit((StatementExpression)itr.nextElement(),ifScope,objects);
			}
		}
		return null;
	}

	@Override
	public Object visit(WhileExpression node, LangX041Scope scope, Object... objects) throws Exception {
		
		LangX041Scope whileScope = new LangX041Scope(scope);
		if(new Boolean(this.visit(node.f1,scope,objects).toString())) {
			Enumeration itr = (Enumeration) node.f3.elements();
			while(itr.hasMoreElements()) {
				this.visit((StatementExpression)itr.nextElement(),whileScope,objects);
			}
		}
		return null;
	}

	@Override
	public Object visit(VariableDeclaration node, LangX041Scope scope, Object... objects) throws Exception {
		
		LangX041Variable var = new LangX041Variable();
		var.setVariableName(this.visit(node.f1,scope,objects).toString());
		var.setVariableValue((LangX041Value)this.visit(node.f3,scope,objects));
		
		scope.pushChilds(var.getVariableName(), var);
		return null;
	}

	@Override
	public Object visit(VariableAssign node, LangX041Scope scope, Object... objects) throws Exception {
		
		String name = this.visit(node.f0,scope,objects).toString();
		if(scope.existsChild(name)) {
			LangX041Variable var = scope.child(name);
			var.setVariableValue((LangX041Value) this.visit(node.f2,scope,objects));
		}
		return null;
	}

	@Override
	public Object visit(VariableName node, LangX041Scope scope, Object... objects) throws Exception {
		return node.f0.tokenImage;
	}

	@Override
	public Object visit(JavaStaticMethods node, LangX041Scope scope, Object... objects) throws Exception {
		String id = LangX041Refelection.fullIdentifier(node.f0.tokenImage);
		if(id != null) {
			// making a class object
			Object currObj = LangX041Refelection.makeObject(id);
			if(currObj != null) {
				Enumeration itr = (Enumeration) node.f1.elements();
				while(itr.hasMoreElements()) {
					NodeSequence ns = (NodeSequence) itr.nextElement();
					if(LangX041Refelection.existsField(currObj, ns.elementAt(1).toString())) {
						currObj = LangX041Refelection.getFieldObject(currObj, ns.elementAt(1).toString());
					}else {
						LinkedList<LangX041Value> params = new LinkedList<>();
						params.add((LangX041Value) this.visit(node.f3,scope,objects));
						
						Enumeration eval = (Enumeration) node.f4.elements();
						while(eval.hasMoreElements()) {
							NodeSequence nsVal = (NodeSequence) eval.nextElement();
							params.add((LangX041Value) this.visit((MathExpression)nsVal.elementAt(1),scope,objects));
						}
						
						if(LangX041Refelection.existsSubroutine(currObj, ns.elementAt(1).toString(), params.toArray(new LangX041Value[] {}))) {
							return LangX041Refelection.invokeStaticSubroutine(currObj, ns.elementAt(1).toString(), params.toArray(new LangX041Value[] {}));
						}
						break;
					}
				}
			}
		}
		return null;
	}

	@Override
	public Object visit(StatementExpression node, LangX041Scope scope, Object... objects) throws Exception {
		
		if(node.f0.choice instanceof VariableDeclaration) {
			return this.visit((VariableDeclaration)node.f0.choice,scope,objects);
		}else if(node.f0.choice instanceof VariableAssign) {
			return this.visit((VariableAssign)node.f0.choice,scope,objects);
		}else if(node.f0.choice instanceof JavaStaticMethods) {
			return this.visit((JavaStaticMethods)node.f0.choice,scope,objects);
		}else if(node.f0.choice instanceof IfExpression) {
			return this.visit((IfExpression)node.f0.choice,scope,objects);
		}else if(node.f0.choice instanceof WhileExpression) {
			return this.visit((WhileExpression)node.f0.choice,scope,objects);
		}
		return null;
	}

}
