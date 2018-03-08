package at.saith.twasi.rank.lol.data;

import at.saith.twasi.rank.lol.summoner.Summoner;

public class DefaultSummonerFetcher extends SummonerFetcher{

	public DefaultSummonerFetcher() {
		super("None");
	}

	@Override
	public Summoner getSummoner(String summonerName, String region) {
		return new Summoner();
	}

}
