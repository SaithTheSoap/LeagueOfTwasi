package at.saith.twasi.rank.lol.data.database.mongodb;

import java.util.ArrayList;
import java.util.List;

import at.saith.twasi.rank.lol.data.RiotUtil;
import at.saith.twasi.rank.lol.data.database.DataBaseFetcher;
import at.saith.twasi.rank.lol.data.database.mongodb.summoner.MongoDBSummoner.Properties;
import at.saith.twasi.rank.lol.data.database.mongodb.summoner.MongoDBSummoner.RankedStats;
import at.saith.twasi.rank.lol.data.database.mongodb.summoner.SummonerStore;
import at.saith.twasi.rank.lol.data.exception.InvalidAPIKeyException;
import at.saith.twasi.rank.lol.summoner.Region;
import at.saith.twasi.rank.lol.summoner.Summoner;
import at.saith.twasi.rank.lol.summoner.SummonerProperties;
import at.saith.twasi.rank.lol.summoner.SummonerRankedStats;
import net.twasi.core.database.Database;

public class MongoDBFetcher extends DataBaseFetcher{
	public MongoDBFetcher(String apikey) throws InvalidAPIKeyException {
		super("MongoDB with RiotRest-API");
		RiotUtil.setup(apikey);
  	  Database.getMorphia().mapPackageFromClass(Properties.class);
  	  Database.getMorphia().mapPackageFromClass(RankedStats.class);
	}
public Summoner getSummoner(String summonerName) {
		return getSummoner(summonerName, "euw1");
	}



	@Override
	public Summoner getSummoner(String summonerName,String region){
		Region reg = Region.byName(region);
		Summoner toReturn = loadFromCache(summonerName, region);
		if(toReturn != null) {
			return toReturn;
		}
		
		 toReturn = getSummonerFromDataBase(summonerName,region);
		if(RiotUtil.validSummoner(toReturn)) {
			SUMMONER_CACHE.add(toReturn);
			return toReturn;
		}else {
		SummonerProperties properties = RiotUtil.getSummonerPropertiesByName(summonerName,region);
		ArrayList<SummonerRankedStats> rankedStats = RiotUtil.getSummonerRankedStatsBySummonerId(properties.getId());
		
			toReturn = new Summoner(properties,rankedStats,reg);
			SUMMONER_CACHE.add(toReturn);
			updateSummoner(summonerName, region,properties,rankedStats);
		}
		return toReturn;
	}
	
	private static boolean createSummonerIfNotExists(String summonerName, String region) {
		SummonerProperties properties = RiotUtil.getSummonerPropertiesByName(summonerName,region);
		ArrayList<SummonerRankedStats> rankedStats = RiotUtil.getSummonerRankedStatsBySummonerId(properties.getId(), region);
		return SummonerStore.createSummonerProperties(properties)
				&&
				SummonerStore.createSummonerRankedStats(summonerName,rankedStats);
		
	}

	private Summoner loadFromCache(String summonerName,String region) {
		for(Summoner summoner:SUMMONER_CACHE) {
			if(summoner.getProperties().getName().equalsIgnoreCase(summonerName)&&summoner.getRegion().equals(Region.byName(region))) {
				return summoner;
			}
		}
		return null;
	}
	private void updateSummoner(String summonerName,String region, SummonerProperties properties, ArrayList<SummonerRankedStats> rankedstats) {
		for(SummonerRankedStats stats:rankedstats) {
		SummonerStore.updateSummonerRankedStat(summonerName, stats);
		}
	}

	private static Summoner getSummonerFromDataBase(String summonerName,String region) {
		Properties  properties = SummonerStore.getSummonerProperties(summonerName);
		List<RankedStats> rankedstats = SummonerStore.getSummonerRankedStats(summonerName);
		if(properties == null) {
			createSummonerIfNotExists(summonerName, region);
			return getSummonerFromDataBase(summonerName, region);
		}
		Summoner summoner = new Summoner(properties, rankedstats, region);
		return summoner;
	}
}
