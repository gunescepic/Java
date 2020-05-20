package hw1;

public class Sym implements MathExpression{
    private String value;

    public Sym(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public <T> T accept(MathVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public boolean match(MathExpression me) {
        if(me instanceof Sym && (((Sym) me).getValue().equals(this.getValue()) )){
            return true;
        }
        else
            return false;
    }
}
