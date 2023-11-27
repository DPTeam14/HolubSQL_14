package com.holub.database;

import java.io.*;
import java.util.*;

public class XMLExporter implements Table.Exporter {	
    private final Writer out;
    private 	  int	 width;
    private String tableName;
    private List columnNameList = new ArrayList();
    private Stack<String> tagStack = new Stack<>();
    
    public XMLExporter( Writer out ) {	
        this.out = out;
    }

    // 스택을 이용한 탭 입력
    private void writeTab() throws IOException {
        for (int i = 0; i < tagStack.size() - 2; i++) {
            out.write("  ");
        }
    }

    // 태그 입력 메소드
    private void writeTag(String tag) throws IOException {
        boolean isMetaTag = tableName == tag || "row".equals(tag) || tag.contains("xml");

        if(!tagStack.contains(tag)){
            tagStack.push(tag);
            writeTab();
            out.write("<" + tag + ">" + (isMetaTag ? "\n" : "" ));
        }
        else{
            if(isMetaTag) writeTab();
            tagStack.pop();
            out.write("</" + tag + ">\n");
        }
    }

    // 컬럼정보를 저장
    public void storeMetadata( String tableName,
                               int width,
                               int height,
                               Iterator columnNames ) throws IOException {
        this.width = width;
        this.tableName = tableName;

        while(columnNames.hasNext()){
            columnNameList.add(columnNames.next().toString());
        }
        writeTag(tableName);
    }

    // 각 데이터를 저장
    public void storeRow( Iterator data ) throws IOException {
        int currentColumnIndex = width;
        writeTag("row");


        while (data.hasNext()) {
            Object datum = data.next();
            String currentColumn = columnNameList.get(width - currentColumnIndex).toString();
            if (datum != null) {
                writeTag(currentColumn);
                out.write(datum.toString());
                writeTag(currentColumn);
                currentColumnIndex = currentColumnIndex - 1;
            }
        }
        writeTag("row");
    }

    @Override
    public void startTable() throws IOException {
        writeTag("?xml version=\"1.0\" encoding=\"utf-8\"?");
    }
    @Override
    public void endTable() throws IOException {
        writeTag(tableName);
    }
}