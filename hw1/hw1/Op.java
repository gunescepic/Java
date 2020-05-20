package hw1;

public class Op implements MathExpression{

    private String operand;
    private MathExpression first;
    private MathExpression second;

    public Op(String operand, MathExpression first, MathExpression second) {
        this.operand = operand;
        this.first = first;
        this.second = second;
    }

    public String getOperand() {
        return operand;
    }

    public MathExpression getFirst() {
        return first;
    }

    public MathExpression getSecond() {
        return second;
    }



    @Override
    public <T> T accept(MathVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean match(MathExpression me) {
        if (me instanceof Op && (this.getFirst().match(((Op) me).getFirst()) && this.getSecond().match(((Op) me).getSecond()))){
            return true;
        }
        else if(me instanceof Var){
            return true;
        }
        else{
            return false;
        }
    }
}
