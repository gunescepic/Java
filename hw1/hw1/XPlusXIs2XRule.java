package hw1;

public class XPlusXIs2XRule implements Rule{

    private Var x;
    private MathExpression premise;
    private MathExpression entails;

    public XPlusXIs2XRule(Var x) {
        this.x = x;
        MathExpression premise = new Op("+", x,x);
        this.premise = premise;
        MathExpression two = new Num(2);
        MathExpression entail = new Op("*",two,x);
        this.entails = entail;
    }

    public Var getX() {
        return x;
    }

    @Override
    public void clear() {
        this.getPremise().accept(new ClearVarsVisitor());
        this.getEntails().accept(new ClearVarsVisitor());
        x.accept(new ClearVarsVisitor());

    }

    @Override
    public boolean apply(MathExpression me) {
        clear();
        if(me.match(premise) == true){
            x.setPreviousMatch(((Op) me).getFirst());
            MathExpression new_premise = new Op("+", x,x);
            this.premise = new_premise;
            MathExpression two = new Num(2);
            MathExpression new_entail = new Op("*",two,x);
            this.entails = new_entail;
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
    public MathExpression entails(MathExpression me){
        apply(me);

        return getEntails();
    }
}
