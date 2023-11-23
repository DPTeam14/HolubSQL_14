package com.holub.database;

public abstract class Visitor {
	public abstract Table visit(Table input);
}

class AscVisitor extends Visitor {
	public Table visit (Table input) {
		System.out.println("ASC");
		Cursor cur = input.rows();
		ConcreteTable resultTable = new ConcreteTable(null, cur.columnNames());
		
		return input;		
	}
}

class DescVisitor extends Visitor {
	public Table visit (Table input) {
		System.out.println("DESC");
		return input;
	}
}
