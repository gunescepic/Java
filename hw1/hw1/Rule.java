package hw1;

public interface Rule {
    void clear();
    boolean apply(MathExpression me);
    MathExpression getPremise();
    MathExpression getEntails();
    MathExpression entails(MathExpression me);
}
