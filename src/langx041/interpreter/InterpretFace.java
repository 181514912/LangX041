package langx041.interpreter;

import langx041.core.LangX041Scope;
import langx041.syntaxtree.AdditiveExpression;
import langx041.syntaxtree.IfExpression;
import langx041.syntaxtree.JavaStaticMethods;
import langx041.syntaxtree.MathExpression;
import langx041.syntaxtree.MultiplicativeExpression;
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

public interface InterpretFace {

	public Object visit(Start node) throws Exception;

	public Object visit(Require node, LangX041Scope scope, Object... objects);

	public Object visit(MathExpression node, LangX041Scope scope, Object... objects) throws Exception;

	public Object visit(AdditiveExpression node, LangX041Scope scope, Object... objects) throws Exception;

	public Object visit(MultiplicativeExpression node, LangX041Scope scope, Object... objects) throws Exception;

	public Object visit(UnaryExpression node, LangX041Scope scope, Object... objects) throws Exception;

	public Object visit(RelationalExpression node, LangX041Scope scope, Object... objects) throws Exception;

	public Object visit(RelationalEqualityExpression node, LangX041Scope scope, Object... objects) throws Exception;

	public Object visit(RelationalGreaterExpression node, LangX041Scope scope, Object... objects) throws Exception;

	public Object visit(RelationalLessExpression node, LangX041Scope scope, Object... objects) throws Exception;

	public Object visit(UnaryRelational node, LangX041Scope scope, Object... objects) throws Exception;

	public Object visit(IfExpression node, LangX041Scope scope, Object... objects) throws Exception;

	public Object visit(WhileExpression node, LangX041Scope scope, Object... objects) throws Exception;

	public Object visit(VariableDeclaration node, LangX041Scope scope, Object... objects) throws Exception;

	public Object visit(VariableAssign node, LangX041Scope scope, Object... objects) throws Exception;

	public Object visit(VariableName node, LangX041Scope scope, Object... objects) throws Exception;

	public Object visit(JavaStaticMethods node, LangX041Scope scope, Object... objects) throws Exception;

	public Object visit(StatementExpression node, LangX041Scope scope, Object... objects) throws Exception;

}
