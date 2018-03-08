package at.saith.twasi.rank.lol.summoner;

import java.util.ArrayList;

public enum QueueType {
RANKED_SOLO_5X5("RANKED_SOLO_5X5","solo"),
RANKED_FLEX_SR("RANKED_FLEX_SR","team","flex"),
RANKED_SOLO_3X3("RANKED_SOLO_3X3","solo_3x3");
	private String queueType;
	private ArrayList<String> aliases = new ArrayList<>();
	QueueType(String queueType,String... aliases) {
		this.queueType = queueType;
		if(aliases != null) {
			for(String alias: aliases) {
				this.aliases.add(alias.toLowerCase());
			}
		}
	}
	public String getName() {
		return queueType;
	}
	public String toString() {
		return getName();
	}
	public static QueueType byName(String queue) {
		for(QueueType type: values()) {
			if(type.isEqual(queue)) {
				return type;
			}
		}
		return null;
	}
	public boolean isEqual(Object obj) {
		if(obj == null) {
			return false;
		}
		String objString = obj.toString();
		if(aliases.contains(objString.toLowerCase())) {
			return true;
		}
		
		return queueType.equalsIgnoreCase(objString);
	}
}
