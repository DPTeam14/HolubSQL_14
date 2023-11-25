package com.holub.database;

import java.io.*;
import java.util.*;

public class HTMLExporter implements Table.Exporter{

    private final Writer out;
    private final Stack<String> tagStack = new Stack<>();

    private void tab() throws IOException {
        for(int i = 0; i < tagStack.size()-2; i++){
            out.write("  ");
        }
    }

    public HTMLExporter(Writer out){
        this.out = out;
    }

    public void startTable() throws IOException  {
        startTag("DOCTYPE");
        startTag("html");
    }

	public void storeMetadata( String tableName,
							   int width,
							   int height,
							   Iterator columnNames ) throws IOException {	
        if(tableName != null) {
            startTag("head");
            startTag("title");
            out.write(tableName);
            endTag("title");
            endTag("head");
            out.write("\n");
        }

        startTag("body");
        if(tableName != null){
            startTag("h1");
            out.write(tableName);
            endTag("h1");
        }

        startTag("table");
        startTag("tr");
        while(columnNames.hasNext()){
            startTag("th");
            out.write(columnNames.next().toString());
            endTag("th");
        }
        endTag("tr");
	}
    
    public void storeRow( Iterator data ) throws IOException
    {	
        startTag("tr");
        while (data.hasNext()) {
            Object datum = data.next();
            if(datum != null){
                startTag("td");
                out.write(datum.toString());
                endTag("td");
            }
        }
        endTag("tr");
    }

    public void endTable()   throws IOException {
        endTag("table");
        endTag("body");
        endTag("html");
    }

    private void startTag(String tag) throws IOException{
        tagStack.push(tag);
        tab();

        if(tag=="table")
            out.write("<"+tag+" border=\"1\">\n");
        else if (tag=="DOCTYPE")
            out.write("<!"+tag+" html>\n\n");
        else if (tag=="title" || tag=="h1" || tag=="th" || tag=="td")
            out.write("<"+tag+">");
        else
            out.write("<"+tag+">\n");
    }

    private void endTag(String tag) throws IOException{
        if (tag!="title" && tag!="h1" && tag!="th" && tag!="td") {
            tab();  
        }
        tagStack.pop();
    
        out.write("</"+tag+">\n");
    }
}
