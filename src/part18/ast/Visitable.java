package part18.ast;

public interface Visitable<V> {
	V accept(Visitor<V> visitor);
}
