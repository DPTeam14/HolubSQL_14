package app;

public interface Manageable {
	void read(String str);

	void print();

	boolean matches(String kwd);

	boolean contained(String kwd);
}
