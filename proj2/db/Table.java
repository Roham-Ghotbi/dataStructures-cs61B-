package db;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

class Table {
    String name;
    private Map<String, Column> table;
    private int numRows = 0;
    private int numColumns = 0;
    private String[] headers;

    Table(){
        this.table = new HashMap<>();
    }
    Table(String[] colNames, String[] colTypes) {
        this.table = new HashMap<>();
        this.headers = new String[colNames.length];

        for (int i = 0; i < colNames.length; i ++) {
            String type = colTypes[i];
            headers[i] = colNames[i];
            numColumns += 1;
            if(type.equals("String")) {
                table.put(colNames[i], new Column<String>(type, colNames[i]));
            } else if(type.equals("int")) {
                table.put(colNames[i], new Column<Integer>(type, colNames[i]));
            } else {
                table.put(colNames[i], new Column<Float>(type, colNames[i]));
            }

        }

        numRows = table.get(colNames[0]).size();

    }

    int getNumRows() {
        return this.numRows;
    }
    int getNumColumns() {
        return this.numColumns;
    }
    void addColumn(String header, Column column) {
        table.put(header, column);
        numColumns += 1;
    }

    private String[] getHeaders() {
        /*int index = 0;
        String [] head = new String[table.keySet().size()];

        for(String key : table.keySet()) {
            head[index] = key;
            index += 1;
        }

        return head;*/

        return this.headers;
    }
    private Column getColumn(String key) {
        return this.table.get(key);
    }
    private String getColumnType(String key) {
        return this.getColumn(key).getType();
    }
    private Table copy() {
        Table copy = new Table();
        for (String key : table.keySet()) {
            copy.addColumn(key, this.getColumn(key).getCopy());
        }

        return copy;
    }
    private static Table concatenate(Table table1, Table table2) {
        Table newTable = new Table();
        newTable = table1.copy();
        newTable.table.putAll(table2.copy().table);

        return newTable;
    }

    private static Pair[] elementChecker(String[] colHeaders, Table table1, Table table2){

        ArrayList<Pair> indices = new ArrayList<>();
        for (String head : colHeaders) {
            indices = table1.getColumn(head).indicesConnector(table2.getColumn(head), indices);
        }

        return indices.toArray(new Pair[indices.size()]);
    }

    static Table join (Table[] tables) {

        Table table1 = tables[0].copy();
        Table newTable = new Table();

        for (int i = 1; i < tables.length; i++) {
            ArrayList<String> sameHeads = new ArrayList<>();
            ArrayList<String> newTableTypes = new ArrayList<>();
            ArrayList<String> newTableHeaders = new ArrayList<>();

            if (table1.equals(tables[i])) {          // check to see if tables are exactly same
                newTable = table1;
            }

            for (String head1 : table1.headers) {
                for (String head2 : tables[i].headers) {   //Finding the same Headers
                    if (head1.equals(head2)) {
                        sameHeads.add(head1);
                        newTableTypes.add(table1.getColumnType(head1));
                        newTableHeaders.add(head1);
                    }
                }
            }

            for (String key : newTableHeaders) {
                if (!sameHeads.contains(key)) {
                    newTableHeaders.add(key);
                    newTableTypes.add(table1.getColumnType(key)); //get the types
                }
            }

            for (String key : tables[i].headers) {   //creating new table headers
                if (!sameHeads.contains(key)) {
                    newTableTypes.add(tables[i].getColumnType(key)); //get the types from second table
                    newTableHeaders.add(key);
                }
            }

            if (sameHeads.size() == 0) {     //cartesian multiplication case
                newTable = concatenate(table1, tables[i]);
            } else {
                String[] colHeaders =
                        newTableHeaders.toArray(new String[newTableHeaders.size()]);
                String[] colTypes = newTableTypes.toArray(new String[newTableTypes.size()]);
                newTable = new Table(colHeaders, colTypes);

                Pair[] sameElements = elementChecker(colHeaders, table1, tables[i]);
                int j = 0;
                int[] first = new int[sameElements.length];
                int[] second = new int[sameElements.length];
                for (Pair pair : sameElements) {        //Attempting to fill the table
                    first[j] = pair.first();       //with correct data
                    second[j] = pair.second();
                    j += 1;
                }

                for (String head : table1.getHeaders()) {
                    for (int index : first) {
                        newTable.getColumn(head).addElement(first[index]);
                    }
                }

                for (String head : tables[i].getHeaders()) {    //BAD Coding its N^3
                    for (String head2 : sameHeads) {
                        if (!head.equals(head2)) {
                            for (int index : second) {
                                newTable.getColumn(head).addElement(second[index]);
                            }
                        }
                    }
                }

            }
            table1 = newTable;
        }

        return newTable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Table table1 = (Table) o;
        String[] head1 = table1.getHeaders();
        String[] head = this.getHeaders();

        if (numRows != table1.numRows) return false;
        if (numColumns != table1.numColumns) return false;
        if(head.length == head1.length) {
            for (int i = 0; i < head.length; i++) {
                if (!this.getColumn(head[i]).equals(table1.getColumn(head1[i]))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }

    }


    String print() {
        String header = "";
        String rest = "";

        for(String key : table.keySet()) {
            header += key + " " + getColumn(key).getType() +"," ;
        }
        header = header.substring(0, header.length() - 1) + "\n";

        for (int i = 0; i < numRows; i++) {
            for (String key : table.keySet()) {
                rest +="\'" + getColumn(key).get(i) + "\',";
            }

            rest = rest.substring(0, rest.length() - 1) + "\n";
        }
        return header + rest;
    }

    @Test
    public void tester() {

    }
}
