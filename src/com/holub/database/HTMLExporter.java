package com.holub.database;

import java.io.*;
import java.util.*;

public class HTMLExporter implements Table.Exporter{
    private final Writer out;
    private final Stack<String> tagStack = new Stack<>();

    public HTMLExporter(Writer out){
        this.out = out;
    }

    // 스택을 이용하여 태그앞에 탭을 입력. 계층구조를 나타냅니다.
    private void writeTab() throws IOException {
        for(int i = 0; i < tagStack.size()-2; i++){
            out.write("  ");
        }
    }

    // 태그를 파일에 입력할때 쓰는 메소드
    private void writeTag(String tag) throws IOException{
        String[] tableTag = {"title", "h1", "th", "td"};
        boolean isTableTag = Arrays.asList(tableTag).contains(tag);

        // 여는 태그일때
        if(!tagStack.contains(tag)){
            tagStack.push(tag);
            writeTab();
            if(isTableTag)
                out.write("<"+tag+">");
            else
                out.write("<"+tag+">\n");
        }
        // 닫는 태그일때
        else{
            if(!Arrays.asList(tableTag).contains(tag)) writeTab();
            tagStack.pop();
            out.write("</" + tag + ">\n");
        }
    }

    // 테이블의 메타정보
    public void startTable() throws IOException  {
        writeTag("!DOCTYPE html");
        writeTag("html");
    }

    // 테이블의 타이틀과 컬럼 정보
	public void storeMetadata( String tableName,
							   int width,
							   int height,
							   Iterator columnNames ) throws IOException {	
        if(tableName != null) {
            writeTag("head");
            writeTag("title");
            out.write(tableName);
            writeTag("title");
            writeTag("head");
        }

        writeTag("body");
        if(tableName != null){
            writeTag("h1");
            out.write(tableName);
            writeTag("h1");
        }

        writeTag("table border=\"1\"");
        writeTag("tr");
        while(columnNames.hasNext()){
            writeTag("th");
            out.write(columnNames.next().toString());
            writeTag("th");
        }
        writeTag("tr");
	}
    
    // 테이블의 데이터 정보
    public void storeRow( Iterator data ) throws IOException
    {	
        writeTag("tr");
        while (data.hasNext()) {
            Object datum = data.next();
            if(datum != null){
                writeTag("td");
                out.write(datum.toString());
                writeTag("td");
            }
        }
        writeTag("tr");
    }

    public void endTable()   throws IOException {
        writeTag("table");
        writeTag("body");
        writeTag("html");
    }
}
