package hw1;

public class XDotYDivXIsYRule implements Rule {
    private MathExpression premise;
    private MathExpression entails;
    private Var x;
    private Var y;

    public XDotYDivXIsYRule(Var x, Var y) {
        this.x = x;
        this.y = y;
        MathExpression fst = new Op("*", x,y);
        MathExpression premise = new Op("/", fst, x);
        this.premise =  premise;
        this.entails = y;
    }

    public Var getX() {
        return x;
    }

    public Var getY() {
        return y;
    }

    @Override
    public void clear() {
        this.getPremise().accept(new ClearVarsVisitor());
        this.getEntails().accept(new ClearVarsVisitor());
        x.accept(new ClearVarsVisitor());
        y.accept(new ClearVarsVisitor());

    }

    @Override
    public boolean apply(MathExpression me) {
        clear();
        if(me.match(this.getPremise())){
            x.setPreviousMatch(((Op) me).getSecond());
            y.setPreviousMatch(((Op)(((Op) me).getFirst())).getSecond());
            MathExpression fst = new Op("*", x,y);
            MathExpression new_premise = new Op("/", fst, x);
            this.premise =  new_premise;
            this.entails = y;
            return true;
        }
        else{
            return false;
        }
    }

    @Override
    public MathExpression getPremise() {
        return premise;
    }

    @Override
    public MathExpression getEntails() {
        return entails;
    }

    @Override
    public MathExpression entails(MathExpression me) {
        apply(me);

        return getEntails();
    }
}
