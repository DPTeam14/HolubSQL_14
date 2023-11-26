package com.holub.test;

import java.io.*;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.holub.database.Table;
import com.holub.database.TableFactory;
import com.holub.database.XMLExporter;

public class XMLExporterTest {

    @Test
    public void XMLExporterTest() {

        // 정답 준비
        String answer =
        "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"+
        "<XMLTestTable>\n"+
        "  <row>\n"+
        "    <addrId>1</addrId>\n"+
        "    <street>123 MyStreet</street>\n"+
        "    <city>Berkeley</city>\n"+
        "    <state>CA</state>\n"+
        "    <zip>99999</zip>\n"+
        "  </row>\n"+
        "  <row>\n"+
        "    <addrId>2</addrId>\n"+
        "    <street>34 Quarry</street>\n"+
        "    <city>Ln.Bedrock</city>\n"+
        "    <state>AZ</state>\n"+
        "    <zip>12345</zip>\n"+
        "  </row>\n"+
        "  <row>\n"+
        "    <addrId>3</addrId>\n"+
        "    <street>34 Quarry</street>\n"+
        "    <city>Busan</city>\n"+
        "    <state>BB</state>\n"+
        "    <zip>12321</zip>\n"+
        "  </row>\n"+
        "</XMLTestTable>\n";
        
        try {
            // 테스트 테이블 생성
            Table XMLTestTable = TableFactory.create("XMLTestTable", new String[] { "addrId", "street", "city", "state", "zip" });
            XMLTestTable.insert(new Object[] { "1", "123 MyStreet", "Berkeley", "CA", "99999" });
            XMLTestTable.insert(new Object[] { "2", "34 Quarry", "Ln.Bedrock", "AZ", "12345" });
            XMLTestTable.insert(new Object[] { "3", "34 Quarry", "Busan", "BB", "12321" });
            
            // xml파일형식으로 export
            Writer out = new FileWriter("c:/dp2023/XMLTestTable.xml");
			XMLTestTable.export(new XMLExporter(out));
			out.close();
            
            // 생성된 파일을 읽어오는 과정
            StringBuffer stringBuffer = new StringBuffer();

            File file = new File("c:/dp2023/XMLTestTable.xml");
            FileReader fileReader = new FileReader(file);
            int index = 0;
            while ((index = fileReader.read()) != -1) {
                stringBuffer.append((char) index);
            }

            // 정답과 테스트결과 비교
            assertEquals(answer, stringBuffer.toString());

        } catch (Exception e) {
            System.err.println("XML exporter Failed on test");
            System.exit(1);
        }
    }   
    
}
