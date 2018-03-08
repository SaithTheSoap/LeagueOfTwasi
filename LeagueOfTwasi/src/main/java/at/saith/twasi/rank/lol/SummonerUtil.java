package at.saith.twasi.rank.lol;

import java.util.ArrayList;

import at.saith.twasi.rank.lol.data.SummonerFetcher;
import at.saith.twasi.rank.lol.summoner.Summoner;

public class SummonerUtil {

	private static SummonerFetcher datafetcher;


	public static void setup(SummonerFetcher fetcher) {
		datafetcher = fetcher;
	}
	
	public static Summoner getSummoner(String summonerName) {
		return getSummoner(summonerName,"euw1");
	}
	
	public static Summoner getSummoner(String summonerName, String region) {
		return datafetcher.getSummoner(summonerName,region);
	}
	
	public static ArrayList<Summoner> getMultiSummoner(String...summonerNames){
		ArrayList<Summoner> summoners = new ArrayList<>();
		for(String summonerName:summonerNames) {
			summoners.add(getSummoner(summonerName));
		}
		return summoners;
	}

	
}
