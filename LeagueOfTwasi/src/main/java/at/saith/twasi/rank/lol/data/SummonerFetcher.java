package at.saith.twasi.rank.lol.data;

import at.saith.twasi.rank.lol.summoner.Summoner;

public abstract class SummonerFetcher {

	private String description;
	public SummonerFetcher(String description) {
		this.description = description;
	}

	public abstract Summoner getSummoner(String summonerName, String region);

	
	
	public String getDescription() {
		return description;
	}
	public String toString() {
		return description;
	}

}
