
package real.Objects;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import real.Enumerations.DataType;

/**
 * Class that is used for storing data relation.
 */
public class Dataset extends AbstractTableModel
{

    private String name;
    private ArrayList<Column> columns;
    private ArrayList<Row> rows;

    public Dataset(String name, String[] columns, ArrayList<Row> rows)
    {
        this(name, evaluateDatatypes(columns, rows), rows);
    }

    public Dataset(String name, ArrayList<Column> columns, ArrayList<Row> rows)
    {
        this.name = name;
        this.columns = columns;
        this.rows = rows;
    }
    
    @Override
    public Dataset clone()
    {
        ArrayList<Column> newColumns = new ArrayList<>();
        ArrayList<Row> newRows = new ArrayList<>();
           
        for(Column column : columns)
        {
            newColumns.add(column.clone());         
        }
        
        for(Row row : rows)
        {
            newRows.add(row.clone());
        }
        
        return new Dataset(this.getName(), newColumns, newRows);             
    }
    
    @Override
    public int getRowCount()
    {
        return this.rows.size();
    }

    @Override
    public int getColumnCount()
    {
        return this.columns.size();
    }

    @Override
    public Object getValueAt(int i, int i1)
    {
        return this.rows.get(i).getValue(this.getColumnName(i1));
    }

    @Override
    public String getColumnName(int col)
    {
        return this.columns.get(col).getName();
    }

    @Override
    public Class getColumnClass(int c)
    {
        return getValueAt(0, c).getClass();
    }

    @Override
    public boolean isCellEditable(int row, int col)
    {
        return true;
    }

    @Override
    public void setValueAt(Object value, int row, int col)
    {
        Column column = this.columns.get(col);
        DataType dataType = Utility.checkType(column.getDataType(), (String) value);
        if (dataType == column.getDataType())
        {
            Row _row = (Row) this.rows.get(row);
            _row.setValue(this.getColumnName(col), value.toString());
            fireTableCellUpdated(row, col);
        }
    }

    public boolean setColumnName(String columnName, String changeName)  
    {     
        for(int i = 0; i < this.columns.size(); ++i)
        {
            Column column = this.columns.get(i);
            
            if(columnName.equals(column.getName()))
            {
                this.columns.set(i, new Column(changeName, column.getDataType()));
                
                for(Row row : this.rows)
                {
                    row.changeMapping(columnName, changeName);
                }
               
                return true;
            }
        }
        
        return false;
    }
    
    public String getName()
    {
        return this.name;
    }

    public ArrayList<Column> getColumns()
    {
        return this.columns;
    }

    public ArrayList<Column> getColumns(String[] columnnames)
    {
        ArrayList<Column> columns = new ArrayList<>();
        
        for (String columnname : columnnames)
        {
            for (Column column : this.columns)
            {
                if (column.getName().equals(columnname))
                {
                    columns.add(column);
                }
            }
        }

        return columns;
    }

    public ArrayList<Row> getRows()
    {
        return this.rows;
    }

    public boolean equalsSchema(Dataset other)
    {
        return this.compare(this, other) && this.compare(other, this);
    }

    private boolean compare(Dataset dataset1, Dataset dataset2)
    {
        if (dataset1.getColumnCount() != dataset2.getColumnCount())
        {
            return false;
        }
        for (Column column : dataset1.columns)
        {
            if (!dataset2.columns.contains(column))
            {
                return false;
            }
        }
        return true;
    }

    private static ArrayList<Column> evaluateDatatypes(String[] columnnames, ArrayList<Row> rows)
    {
        ArrayList<Column> columns = new ArrayList<>();
        if (columnnames != null && rows != null)
        {
            for (String column : columnnames)
            {
                DataType type = DataType.UNKNOWN;

                for (Row row : rows)
                {
                    String value = row.getValue(column);
                    type = Utility.checkType(type, value);
                }

                columns.add(new Column(column, type));
            }
        }
        return columns;
    }
}
