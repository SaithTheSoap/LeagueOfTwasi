package at.saith.twasi.rank.lol.summoner;

public enum Region {
RU,
KR,
EUW1,
BR1,
OC1,
JP1,
EUN1,
TR1,
LA1,
LA2;
	

	
	public static boolean isRegion(String name) {
		Region s = byName(name);
		if(s == null) {
			return false;
		}
		return true;
	}
	public static Region byName(String name) {
		for(Region server : values()) {
			if(server.name().equalsIgnoreCase(name)) {
				return server;
			}
		}
		return null;
	}
}
