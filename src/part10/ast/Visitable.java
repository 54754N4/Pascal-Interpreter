package part10.ast;

public interface Visitable<V> {
	V accept(Visitor<V> visitor);
}
