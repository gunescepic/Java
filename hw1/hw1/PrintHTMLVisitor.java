package hw1;

public class PrintHTMLVisitor implements TextVisitor<String> {
    @Override
    public String visit(Paragraph paragraph) {
        return "<p>"+paragraph.getText()+" </p>";
    }

    @Override
    public String visit(EquationText equationText) {
        return "<math> " + equationText.getInnerMath().accept(new PrintMathMLVisitor()) +" </math>";
    }

    @Override
    public String visit(Document document) {
        String ret = "<html><head><title>" + document.getTitle()+
                "</title></head><body>";
        for(int i = 0; i < document.getElements().size(); i ++){
                    ret = ret + document.getElements().get(i).accept(this);
                }
        ret = ret + "</body></html>";
        return  ret;
    }
}
