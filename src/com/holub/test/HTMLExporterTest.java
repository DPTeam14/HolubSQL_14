package com.holub.test;

import java.io.*;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.holub.database.HTMLExporter;
import com.holub.database.Table;
import com.holub.database.TableFactory;

public class HTMLExporterTest {

    @Test
    public void testHTMLExporter() {

        // 정답 준비
        String answer =
        "<!DOCTYPE html>\n"+
        "<html>\n"+
        "  <head>\n"+
        "    <title>HTMLTestTable</title>\n"+
        "  </head>\n"+
        "  <body>\n"+
        "    <h1>HTMLTestTable</h1>\n"+
        "    <table border=\"1\">\n"+
        "      <tr>\n"+
        "        <th>addrId</th>\n"+
        "        <th>street</th>\n"+
        "        <th>city</th>\n"+
        "        <th>state</th>\n"+
        "        <th>zip</th>\n"+
        "      </tr>\n"+
        "      <tr>\n"+
        "        <td>1</td>\n"+
        "        <td>123 MyStreet</td>\n"+
        "        <td>Berkeley</td>\n"+
        "        <td>CA</td>\n"+
        "        <td>99999</td>\n"+
        "      </tr>\n"+
        "      <tr>\n"+
        "        <td>2</td>\n"+
        "        <td>34 Quarry</td>\n"+
        "        <td>Ln.Bedrock</td>\n"+
        "        <td>AZ</td>\n"+
        "        <td>12345</td>\n"+
        "      </tr>\n"+
        "      <tr>\n"+
        "        <td>3</td>\n"+
        "        <td>34 Quarry</td>\n"+
        "        <td>Busan</td>\n"+
        "        <td>BB</td>\n"+
        "        <td>12321</td>\n"+
        "      </tr>\n"+
        "    </table>\n"+
        "  </body>\n"+
        "</html>\n";
        
        
        try {
            // 테스트 테이블 생성
            Table HTMLTestTable = TableFactory.create("HTMLTestTable", new String[] { "addrId", "street", "city", "state", "zip" });
            HTMLTestTable.insert(new Object[] { "1", "123 MyStreet", "Berkeley", "CA", "99999" });
            HTMLTestTable.insert(new Object[] { "2", "34 Quarry", "Ln.Bedrock", "AZ", "12345" });
            HTMLTestTable.insert(new Object[] { "3", "34 Quarry", "Busan", "BB", "12321" });
            
            // html 형식으로 내보내기
            Writer out = new FileWriter("c:/dp2023/HTMLTestTable.html");
			HTMLTestTable.export(new HTMLExporter(out));
			out.close();
            
            // html 파일 읽어오기
            StringBuffer stringBuffer = new StringBuffer();

            File file = new File("c:/dp2023/HTMLTestTable.html");
            FileReader fileReader = new FileReader(file);
            int index = 0;
            while ((index = fileReader.read()) != -1) {
                stringBuffer.append((char) index);
            }

            // 정답과 테스트 결과 비교
            assertEquals(answer, stringBuffer.toString());
            
        } catch (Exception e) {
            System.err.println("HTML exporter Failed on test");
            System.exit(1);
        }
    }   
    
}
