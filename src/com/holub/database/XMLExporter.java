package com.holub.database;

import java.io.*;
import java.util.*;

public class XMLExporter implements Table.Exporter {	
    private final Writer out;
    private 	  int	 width;
    private String tableName;
    private List columnNameList = new ArrayList();

    public XMLExporter( Writer out ) {	
        this.out = out;
    }

    public void storeMetadata( String tableName,
                               int width,
                               int height,
                               Iterator columnNames ) throws IOException {
        this.width = width;
        this.tableName = tableName;

        while(columnNames.hasNext()){
            columnNameList.add(columnNames.next().toString());
        }
        out.write("<" + tableName +">\n");
    }

    public void storeRow( Iterator data ) throws IOException {
        int currentColumnIndex = width;
        out.write("  <row>\n");

        while (data.hasNext()) {
            Object datum = data.next();
            String currentColumn = columnNameList.get(width - currentColumnIndex).toString();
            if (datum != null) {
                writeTag(currentColumn, datum.toString());
                currentColumnIndex = currentColumnIndex - 1;
            }
        }
        out.write("  </row>\n");
    }

    @Override
    public void startTable() throws IOException {
        out.write("<?xml version=\"1.0\" encoding=\"utf-8\"?>\n");
    }
    @Override
    public void endTable() throws IOException {
        out.write("</"+ tableName +">\n");
    }

    private void writeTag(String tagName, String value) throws IOException {
        out.write("    <" + tagName + ">");
        out.write(value);
        out.write("</" + tagName + ">\n");
    }
}