package hw1;
public class XDotZeroIsZeroRule implements Rule{
    private MathExpression premise;
    private MathExpression entails;
    private Var x;

    public XDotZeroIsZeroRule(Var x) {
        this.x = x;
        MathExpression zero = new Num(0);
        MathExpression premise = new Op("*", x, zero);
        this.premise = premise;
        MathExpression entail = new Num(0);
        this.entails = entail;
    }

    public Var getX() {
        return x;
    }

    @Override
    public void clear() {
        x.accept(new ClearVarsVisitor());
        this.getPremise().accept(new ClearVarsVisitor());
        this.getEntails().accept(new ClearVarsVisitor());
    }

    @Override
    public boolean apply(MathExpression me) {
        clear();
        if(me.match(premise)){
            x.setPreviousMatch(((Op) me).getFirst());
            MathExpression zero = new Num(0);
            MathExpression premise_new = new Op("*", x, zero);
            this.premise = premise_new;
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
