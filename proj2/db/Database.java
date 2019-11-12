package db;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Database {

    private Map<String, Table> tables;

    public Database() {
        this.tables = new HashMap<>();
    }

    public String createTable(String name, String[] colNames, String[] colTypes){
        String result = "";


        return result;
    }
    public String load(String tableName){
        String result = "";


        return result;
    }
    public String store(String tableName){
        String result = "";


        return result;
    }

    public String dropTable(String tableName){
        String result = "";


        return result;
    }
    public String insertInto(String tableName, ArrayList literals){
        String result = "";


        return result;
    }  //wrong

    public String print(String tableName, Table table){

        return tables.get(tableName).print();
    }
    public String select(String[] colExps, String[] tableNames, String[] conditions){
        Table[] newTables = (Table[]) new Object[tableNames.length];
        for(int i = 0; i < tableNames.length; i++){
            newTables[i] = tables.get(tableNames[i]);
        }

        return null;
    }

    public String transact(String query) {
        return "doodool tala";
    }
}
