package hw1;
public class PrintMathMLVisitor implements MathVisitor<String> {
    @Override
    public String visit(Op op) {
        if(op.getOperand().equals("/")){
            return "<mrow><mfrac>" + op.getFirst().accept(this)+ op.getSecond().accept(this)+
                    "</mfrac></mrow>";
        }
        if(op.getOperand().equals("+")){
            return "<mrow><mo>(</mo>" + op.getFirst().accept(this) + "<mo> +"  +
                    "</mo> "+op.getSecond().accept(this)+" <mo>)</mo></mrow>";
        }
        else if(op.getOperand().equals("*")){
            return "<mrow><mo>(</mo>" + op.getFirst().accept(this) + "<mo>&times;</mo> " +op.getSecond().accept(this)+" <mo>)</mo></mrow>";
        }
        else if(op.getOperand().equals("|-")){
            return "<mrow><mo>(</mo>" + op.getFirst().accept(this) + "<mo>&vdash;</mo> "+op.getSecond().accept(this)+" <mo>)</mo></mrow>";
        }
        return "<mrow><mo>(</mo>" + op.getFirst().accept(this) + "<mo>+</mo> "+op.getSecond().accept(this)+" <mo>)</mo></mrow>";
    }

    @Override
    public String visit(Num num) {
        return "<mrow><mn>" +num.getValue()+ "</mn></mrow>";
    }

    @Override
    public String visit(Sym sym) {
        return "<mrow><mi>" +sym.getValue()+ " </mi></mrow>";
    }

    @Override
    public String visit(Var var) {
        if(var.getPreviousMatch() != null){
            return "<mrow><msub><mi>V</mi><mn>" + var.getId() +
                    "</mn></msub><mo>[</mo>"+ var.getPreviousMatch().accept(this) +
                    "<mo>]</mo></mrow>";
        }
        else{
            return "<mrow><msub><mi>V</mi><mn> "+ var.getId() + "</mn></msub></mrow>";
        }

    }
}
