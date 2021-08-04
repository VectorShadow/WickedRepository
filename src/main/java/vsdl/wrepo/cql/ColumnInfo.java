package vsdl.wrepo.cql;

public class ColumnInfo {
    private final String NAME;
    private final String DATA_TYPE;

    public ColumnInfo(String name, String dataType) {
        NAME = name;
        DATA_TYPE = dataType;
    }

    public String getName() {
        return NAME;
    }
    public String getDataType() {
        return DATA_TYPE;
    }
}
