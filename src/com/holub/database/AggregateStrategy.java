package com.holub.database;

import java.util.List;

// 전략 패턴을 적용한 집계함수 interface
interface AggregateStrategy {
	public int apply(ConcreteTable input, String columnName);
}

class AggCount implements AggregateStrategy {
	public int apply(ConcreteTable input, String columnName) {
		Cursor cursor = input.rows();
		int count = 0;
		String value;
		while (cursor.advance()) {
			if ((value = cursor.column(columnName).toString()) != null)
				count++;
		}
		return count;
	}
}

class AggAverage implements AggregateStrategy {
	public int apply(ConcreteTable input, String columnName) {
		Cursor cursor = input.rows();
		int sum = 0;
		int count = 0;
		String value;
		while (cursor.advance()) {
			if ((value = cursor.column(columnName).toString()) != null) {
				sum += Integer.parseInt(value);
				count++;
			}
		}
		return sum/count;
	}
}

class AggSum implements AggregateStrategy {
	public int apply(ConcreteTable input, String columnName) {
		Cursor cursor = input.rows();
		int sum = 0;
		String value;
		while (cursor.advance()) {
			if ((value = cursor.column(columnName).toString()) != null)
				sum += Integer.parseInt(value);
		}
		return sum;
	}
}

class AggMin implements AggregateStrategy {
	public int apply(ConcreteTable input, String columnName) {
		Cursor cursor = input.rows();
		int min = Integer.MAX_VALUE;
		String value;
		while (cursor.advance()) {
			if (((value = cursor.column(columnName).toString()) != null)&&(min > Integer.parseInt(value)))
				min = Integer.parseInt(value);
		}
		return min;
	}
}

class AggMax implements AggregateStrategy {
	public int apply(ConcreteTable input, String columnName) {
		Cursor cursor = input.rows();
		int max = Integer.MIN_VALUE;
		String value;
		while (cursor.advance()) {
			if (((value = cursor.column(columnName).toString()) != null)&&(max < Integer.parseInt(value)))
				max = Integer.parseInt(value);
		}
		return max;
	}
}