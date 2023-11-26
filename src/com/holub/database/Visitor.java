package com.holub.database;

import java.util.LinkedList;
import java.util.List;

public abstract class Visitor {
	public abstract Table visit(Table input, List order_by);
}

class AscVisitor extends Visitor {
	public Table visit (Table input, List order_by) {
		System.out.println("ASC");
		Cursor cur_org = input.rows();
		Cursor cur_res;
		String col = order_by.get(0).toString();
		ConcreteTable resultTable = new ConcreteTable(null, cur_org.columnNames());
		
		while (cur_org.advance() /* && this.rowSet.size()>0 */) {
			cur_res = resultTable.rows();
			Object[] row = cur_org.getCloneRow();
			
			if (!cur_res.advance()) {
				resultTable.insert(row);
				continue;
			}
			
			String str_org = cur_org.column(col).toString();
			String str_res = cur_res.column(col).toString();
			
			while (cur_res.advance() /* && this.rowSet.size()>0 */) {
				System.out.println(str_org + " compare to " + str_res);
				if (str_org.compareTo(str_res) < 0) {
					System.out.println("insertFirst");
					resultTable.insertFirst(row);
				}
				else {
					resultTable.insert(row);
				}
			}
			System.out.println();
		}
		
		return resultTable;		
	}
}

class DescVisitor extends Visitor {
	public Table visit (Table input, List order_by) {
		System.out.println("DESC");
		return input;
	}
}
