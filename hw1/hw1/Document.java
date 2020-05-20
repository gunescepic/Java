package hw1;

import java.util.ArrayList;

public class Document implements DocElement{

    private String title;
    private ArrayList<DocElement> elements;

    public Document(String title) {
        this.title = title;
        elements = null;
    }
    public ArrayList<DocElement> getElements(){
        return elements;
    }; //a getter of all the elements, in the order they were
                                                //added.

    public void setElements(ArrayList<DocElement> arr){
        if(arr == null){
            elements = null;
        }
        elements = null;
        ArrayList<DocElement> elements = new ArrayList<DocElement>(arr.size());

    }

    public void add(DocElement me){
        if(elements == null){
            elements = new ArrayList<DocElement>(1);
            elements.add(me);
            return;
        }
        elements.add(me);

    }



    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public <T> T accept(TextVisitor<T> visitor) {
        return visitor.visit(this);
    }
}
