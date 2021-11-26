package part18;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class ActivationRecord {
	public final String name;
	public final ARType type;
	public final int nestingLevel;
	public final Map<String, Double> members;
	
	public ActivationRecord(String name, ARType type, int nestingLevel) {
		this.name = name;
		this.type = type;
		this.nestingLevel = nestingLevel;
		members = new HashMap<>();
	}
	
	public Double get(String key) {
		return members.get(key);
	}
	
	public void set(String key, Double data) {
		members.put(key, data);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%d: %s %s%n", nestingLevel, type, name));
		for (Entry<String, Double> entry : members.entrySet())
			sb.append(String.format("\t%s: %s%n", entry.getKey(), entry.getValue()));
		return sb.toString();
	}

}
