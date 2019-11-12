package db;
import java.util.ArrayList;

public class Column <T>{
    private String type;
    private String header;
    private ArrayList<T> column;

    Column (String type, String header) {
        this.type = type;
        this.header = header;
        this.column = new ArrayList<T>();

    }

    void addElements(T[] elements) {
        for(T item : elements) {
            column.add(item);
        }
    }
    void addElement(T element) {
        column.add(element);
    }

    T get(int index) {

        try {
            return column.get(index);
        } catch(Exception e) {
            System.out.print(e.getMessage());
        }
        return null;
    }
    Column getCopy() {
        Column copy = new Column(this.type, this.header);
        for (T elem : column) {
            copy.addElement(elem);
        }

        return copy;
    }
    String getHeader() {
        return this.header;
    }
    String getType() {
        return this.type;
    }
    ArrayList<T> getRows(int[] index) {
        ArrayList<T> elements = new ArrayList<>();
        for (int i : index) {
            elements.add(column.get(i));
        }

        return elements;
    }

    int size() {
       return column.size();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Column<T> column1 = (Column<T>) o;

        if (type != null ? !type.equals(column1.type) : column1.type != null) return false;
        if (header != null ? !header.equals(column1.header) : column1.header != null) return false;
        if (this.size() != column1.size()) {
            return false;
        } else {
            for(int i = 0; i < this.size(); i++) {
                if (!column.get(i).equals(column1.get(i))) {
                    return false;
                }
            }
            return true;
        }
    }


     ArrayList<Pair> indicesConnector(Column column2, ArrayList<Pair> sameElems) {
        ArrayList<Pair> indices = new ArrayList<>();

        if(sameElems == null || sameElems.size() != 0) {
            for (Pair pair : sameElems) {
                if(this.column.get(pair.first()).equals(column2.get(pair.second()))) {
                    indices.add(pair);
                }
            }
        } else {
            for (int i= 0; i < this.column.size(); i++) {
                for (int j= 0; j < this.column.size(); j++) {
                    if(column.get(i).equals(column2.get(j))) {
                        indices.add(new Pair(i,j));
                    }
                }
            }
        }
        return indices;
    }
}


