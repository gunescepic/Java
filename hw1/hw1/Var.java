package hw1;
public class Var implements MathExpression{
    private int id;
    private MathExpression previous_match;

    public Var(int id) {
        this.id = id;
        previous_match = null;
    }

    public int getId() {
        return id;
    }

    public MathExpression getPreviousMatch() {
        return previous_match;
    }

    public void setPreviousMatch(MathExpression me) {
        previous_match = me;
    }

    @Override
    public <T> T accept(MathVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean match(MathExpression me) {
        if(this.getPreviousMatch() != null){
            return me.match(this.getPreviousMatch());
        }
        else{
            this.setPreviousMatch(me);
            return true;
        }
    }
}
