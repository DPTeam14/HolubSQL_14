package com.holub.database;

import java.io.*;
import java.util.*;

import com.holub.tools.ArrayIterator;

public class XMLImporter implements Table.Importer {
    private BufferedReader      in;
    private String              tableName;
    private List<String>        columnNames = new ArrayList<String>();
    private List<List<String>>  rows = new ArrayList<>();
    private int                 currentRow = 0;
    private List<String>        currentRowData = null;

    public XMLImporter(Reader in) throws IOException {
        this.in = in instanceof BufferedReader
                    ? (BufferedReader)in
                    : new BufferedReader(in);
    }

    // 주어진 스트링에서 태그이름을 반환합니다.
    private String getTagName(String line) {
        String tagName = line.split("<")[1].split(">")[0].split(" ")[0];

        if (tagName.startsWith("/")) {
            tagName = tagName.substring(1);
        }

        return tagName;
    }

    // 태그로 감싸진 데이터를 반환합니다.
    private String getTagValue(String line) {
        String tagValue = line.split("<")[1].split(">")[1];

        return tagValue;
    }

    // 각줄에 대해서 태그이름과 데이터를 반환하여 각각 저장합니다.
    private void processLine(String line) {
        String tagName = getTagName(line);
        String tagValue = getTagValue(line);

        if (!columnNames.contains(tagName)) {
            columnNames.add(tagName);
        }
        if (!tagValue.isEmpty()) {
            currentRowData.add(tagValue);
        }
    }

    // 테이블 정보를 저장하는 메소드
    public void startTable() throws IOException{ 
        BufferedReader reader = new BufferedReader(in);
        String line;

        while ((line = reader.readLine()) != null) {
            if (line.contains("<row>")) {
                currentRowData = new ArrayList<>();
            } else if (line.contains("</row>")) {
                if (currentRowData != null) {
                    rows.add(currentRowData);
                }
                currentRowData = null;
            } else if (currentRowData != null) {
                processLine(line);
            }else if (!line.contains("xml")){
                tableName = getTagName(line);
            }
        }

        if (currentRowData != null) {
            rows.add(currentRowData);
        }
    }

    public String loadTableName() {
        return tableName;
    }

    public int loadWidth() {
        if (rows.isEmpty()) {
            return 0;
        }
        return rows.get(0).size();
    }

    public Iterator loadColumnNames() {
        if (rows.isEmpty()) {
            return null;
        }
        return new ArrayIterator(columnNames.toArray()); 
    }

    public Iterator loadRow() {
        if (currentRow < rows.size()) {
            List<String> rowData = rows.get(currentRow);
            currentRow++;
            return rowData.iterator();
        } else {
            return null;
        }
    }

    public void endTable() { }
}
