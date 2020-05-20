package hw1;
public class CountAtomicsVisitor implements MathVisitor<Integer>{

    @Override
    public Integer visit(Op op) {
        //recursively sum both first and second parts content
        return op.getFirst().accept(this) + op.getSecond().accept(this);
    }

    @Override
    public Integer visit(Num num) {
        return 1;
    }

    @Override
    public Integer visit(Sym sym) {
        return 1;
    }

    @Override
    public Integer visit(Var var) {
        return 1 ;
    }

}
