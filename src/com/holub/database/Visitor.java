package com.holub.database;

import java.util.List;
import java.util.regex.Pattern;

public abstract class Visitor {
	public abstract Table visit(Table input, List order_by);
	private Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

	protected boolean isNumeric(String strNum) {
	    if (strNum == null)
	        return false;
	    return pattern.matcher(strNum).matches();
	}
}

class AscVisitor extends Visitor {
	public Table visit (Table input, List order_by) {
		Cursor cur_org = input.rows();
		Cursor cur_res;
		String col = order_by.get(0).toString();
		ConcreteTable resultTable = new ConcreteTable(null, cur_org.columnNames());
		
		while (cur_org.advance()) {
			cur_res = resultTable.rows();
			Object[] row = cur_org.getCloneRow();
			
			if (!cur_res.hasNext()) {
				resultTable.insert(row);
				continue;
			}
			
			int idx = -1;
			boolean idx_flag = true;
			while (cur_res.advance()) {
				idx++;
				String str_org = cur_org.column(col).toString();
				String str_res = cur_res.column(col).toString();
				if (isNumeric(str_res)) {
					if (Integer.parseInt(str_org) < Integer.parseInt(str_res)) {
						idx_flag = false;
						break;
					}
				}
				else {
					if (str_org.compareTo(str_res) < 0) {
						idx_flag = false;
						break;
					}
				}
			}
			if (idx_flag)
				resultTable.insert(row);
			else
				resultTable.insertByIndex(idx, row);
		}
		return resultTable;		
	}
}

class DescVisitor extends Visitor {
	public Table visit (Table input, List order_by) {
		Cursor cur_org = input.rows();
		Cursor cur_res;
		String col = order_by.get(0).toString();
		ConcreteTable resultTable = new ConcreteTable(null, cur_org.columnNames());

		while (cur_org.advance()) {
			cur_res = resultTable.rows();
			Object[] row = cur_org.getCloneRow();
			
			if (!cur_res.hasNext()) {
				resultTable.insert(row);
				continue;
			}
			
			int idx = -1;
			boolean idx_flag = true;
			while (cur_res.advance()) {
				idx++;
				String str_org = cur_org.column(col).toString();
				String str_res = cur_res.column(col).toString();
				if (isNumeric(str_res)) {
					if (Integer.parseInt(str_org) > Integer.parseInt(str_res)) {
						idx_flag = false;
						break;
					}
				}
				else {
					if (str_org.compareTo(str_res) > 0) {
						idx_flag = false;
						break;
					}
				}
			}
			if (idx_flag)
				resultTable.insert(row);
			else
				resultTable.insertByIndex(idx, row);
		}
		return resultTable;		
	}
}
