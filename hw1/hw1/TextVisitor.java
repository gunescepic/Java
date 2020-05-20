package hw1;
public interface TextVisitor <T> {

    T visit(Paragraph paragraph);
    T visit(EquationText equationText);
    T visit(Document doc);

}
