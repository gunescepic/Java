package hw1;
public class Num implements MathExpression{
    private int value;


    public Num(int value) {
        this.value = value;
    }


    public int getValue() {
        return value;
    }

    @Override
    public <T> T accept(MathVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean match(MathExpression me) {
        if(me instanceof Num && (((Num) me).getValue() == this.getValue())){
            return true;
        }
        else
            return false;
    }
}
