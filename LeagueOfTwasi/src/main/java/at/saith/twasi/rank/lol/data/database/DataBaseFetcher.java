package at.saith.twasi.rank.lol.data.database;

import java.util.ArrayList;

import at.saith.twasi.rank.lol.data.SummonerFetcher;
import at.saith.twasi.rank.lol.summoner.Summoner;

public abstract class DataBaseFetcher extends SummonerFetcher{

	protected ArrayList<Summoner> SUMMONER_CACHE = new ArrayList<>();
	public DataBaseFetcher(String definition) {
		super(definition);
	}

}
