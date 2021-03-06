/* Generated by JTB 1.4.11 */
package langx041.syntaxtree;

import langx041.visitor.*;

public class IfExpression implements INode {

  public NodeToken f0;

  public RelationalExpression f1;

  public NodeToken f2;

  public NodeListOptional f3;

  public NodeToken f4;

  private static final long serialVersionUID = 1411L;

  public IfExpression(final NodeToken n0, final RelationalExpression n1, final NodeToken n2, final NodeListOptional n3, final NodeToken n4) {
    f0 = n0;
    f1 = n1;
    f2 = n2;
    f3 = n3;
    f4 = n4;
  }

  @Override
  public <R, A> R accept(final IRetArguVisitor<R, A> vis, final A argu) {
    return vis.visit(this, argu);
  }

  @Override
  public <R> R accept(final IRetVisitor<R> vis) {
    return vis.visit(this);
  }

  @Override
  public <A> void accept(final IVoidArguVisitor<A> vis, final A argu) {
    vis.visit(this, argu);
  }

  @Override
  public void accept(final IVoidVisitor vis) {
    vis.visit(this);
  }

}
