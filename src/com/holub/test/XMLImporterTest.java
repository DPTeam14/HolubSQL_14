package com.holub.test;

import java.io.*;
import java.util.*;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

import com.holub.database.Table;
import com.holub.database.TableFactory;
import com.holub.database.XMLExporter;
import com.holub.database.XMLImporter;

public class XMLImporterTest {

    @Test
    public void XMLImporterTest() {

        // 정답 준비
        String answer =
        "XMLTestTable2\n"+
        "addrId	street	city	state	zip	\n"+
        "----------------------------------------\n"+
        "1	123 MyStreet	Berkeley	CA	99999	\n"+
        "2	34 Quarry	Ln.Bedrock	AZ	12345	\n"+
        "3	34 Quarry	Busan	BB	12321	\n";
        
        try {
            /* 
             * XMLExporter의 코드 실행과 별개로 테스트가 가능하게 하기 위해
             * 테스트 테이블을 생성하고 내보내는 코드를 포함시켰습니다.
             */
            Table XMLImportTable = TableFactory.create("XMLTestTable2", new String[] { "addrId", "street", "city", "state", "zip" });
            XMLImportTable.insert(new Object[] { "1", "123 MyStreet", "Berkeley", "CA", "99999" });
            XMLImportTable.insert(new Object[] { "2", "34 Quarry", "Ln.Bedrock", "AZ", "12345" });
            XMLImportTable.insert(new Object[] { "3", "34 Quarry", "Busan", "BB", "12321" });

            // xml파일 형식으로 내보내기
            Writer out = new FileWriter("c:/dp2023/XMLTestTable2.xml");
			XMLImportTable.export(new XMLExporter(out));
			out.close();

            // 내보낸 xml파일을 데이터 형식으로 변환하여 읽어오기
            Reader in = new FileReader("c:/dp2023/XMLTestTable2.xml");
			Table.Importer XMLImporterTest = new XMLImporter(in);

            // Import한 데이터를 기반으로 테이블을 형성하는 부분은 코드를 대부분 참고함 (ConcreteTable.java:107)
            XMLImporterTest.startTable();

            // 테이블 제목 체크
            String tableName = XMLImporterTest.loadTableName();
            assertEquals("XMLTestTable2", tableName);

            int width = XMLImporterTest.loadWidth();
            Iterator columns = XMLImporterTest.loadColumnNames();
    
            String[] columnNames = new String[width];
            for (int i = 0; columns.hasNext();)
                columnNames[i++] = (String) columns.next();

            // 테이블 생성
            Table XMLTestTable = TableFactory.create("XMLTestTable2", columnNames);
            while ((columns = XMLImporterTest.loadRow()) != null) {
                Object[] current = new Object[width];
                for (int i = 0; columns.hasNext();)
                    current[i++] = columns.next();
                XMLTestTable.insert(current);
            }
            XMLImporterTest.endTable();
			in.close();

            // 정답과 테스트결과 비교
            assertEquals(answer, XMLTestTable.toString());

        } catch (Exception e) {
            System.err.println("XML importer Failed on test");
            System.exit(1);
        }
        
    }   
    
}
