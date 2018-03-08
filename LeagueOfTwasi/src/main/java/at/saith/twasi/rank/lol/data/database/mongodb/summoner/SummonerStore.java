package at.saith.twasi.rank.lol.data.database.mongodb.summoner;

import java.util.ArrayList;
import java.util.List;

import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.utils.Assert.AssertionFailedException;

import at.saith.twasi.rank.lol.data.database.mongodb.summoner.MongoDBSummoner.Properties;
import at.saith.twasi.rank.lol.data.database.mongodb.summoner.MongoDBSummoner.RankedStats;
import at.saith.twasi.rank.lol.summoner.SummonerProperties;
import at.saith.twasi.rank.lol.summoner.SummonerRankedStats;
import net.twasi.core.database.Database;

public class SummonerStore {
	 /**
     * Gets a command by a user and command name
     * @param user the user to search the command for (twitch channel)
     * @param name the name of the command
     * @return the command if found, null otherwise
     */
	public static Properties getSummonerProperties(String summonerName) {
		Query<Properties> q = Database.getStore().createQuery(Properties.class);

		List<Properties> summonerProperties = null;
		try {
			summonerProperties = q.field("name").equalIgnoreCase(summonerName).asList();
		}catch(AssertionFailedException e) {
			e.printStackTrace();
			return null;
		}
		
		if(summonerProperties.size() == 0) {
			return null;
		}
		return summonerProperties.get(0);
	}

	public static List<RankedStats> getSummonerRankedStats(String summonerName){
		Query<RankedStats> q = Database.getStore().createQuery(RankedStats.class);
		List<RankedStats> summonerRankedStats = null;
		try {
			summonerRankedStats = q.field("name").equalIgnoreCase(summonerName).asList();
			if(summonerRankedStats == null) {
			summonerRankedStats = new ArrayList<>();
			RankedStats stat = new RankedStats();
			stat.setTier("Unranked");
			summonerRankedStats.add(stat);
			}
		}catch(AssertionFailedException e) {
			e.printStackTrace();
			return null;
		}
		
		return summonerRankedStats;
	}
	public static RankedStats getSummonerRankedStats(String summonerName, String queueType) {
		Query<RankedStats> q = Database.getStore().createQuery(RankedStats.class);
		List<RankedStats> summonerRankedStats = null;
		try {
			summonerRankedStats = q.field("name").equalIgnoreCase(summonerName).field("queueType").equalIgnoreCase(queueType).asList();
		}catch(AssertionFailedException e) {
			e.printStackTrace();
			return null;
		}
		if(summonerRankedStats.size() == 0) {
			return null;
		}
		return summonerRankedStats.get(0);
	}

	public static boolean createSummonerProperties(SummonerProperties properties) {
		return createSummonerProperties(properties.getName(), properties.getAccountId(), properties.getId(), System.currentTimeMillis(), properties.getSummonerLevel()
				, properties.getProfileIconId(), properties.getRevisionDate());
	}
    public static boolean createSummonerProperties(String summonerName, long accountId, long id,long lastUpdate, long summonerLevel, long profileIconId, long revisionDate) {
        if (getSummonerProperties(summonerName) == null) {
            Properties properties = new Properties();
            properties.
            setAccountId(accountId).
            setId(id).
            setLastUpdate(lastUpdate).
            setName(summonerName).
            setSummonerLevel(summonerLevel).
            setRevisionDate(revisionDate).
            setProfileIconId(profileIconId);
            
            Database.getStore().save(properties);
            return true;
        }
        return false;
    }
    public static boolean createSummonerRankedStats(String summonerName,ArrayList<SummonerRankedStats> rankedStats) {
		for(SummonerRankedStats stats:rankedStats) {
			if(!createSummonerRankedStat(
					summonerName, 
					stats.getTier(), 
					stats.getRank(), 
					stats.getLosses(), 
					stats.getWins(), 
					stats.getLeaguePoints(), 
					stats.getQueueType().name(), 
					stats.getMiniSeriesProgress())) {
				return false;
			}
		}
		return true;
	}
    public static boolean createSummonerRankedStat(String summonerName, String tier, String rank, long losses, long wins, long lp, String queueType, String miniSeries) {
    	if(getSummonerRankedStats(summonerName,queueType) == null) {
    		RankedStats rankedstats = new RankedStats();
    		rankedstats.
    		setTier(tier).
    		setRank(rank).
    		setWins(wins).
    		setLosses(losses).
    		setLeaguePoints(lp).
    		setQueueType(queueType).
    		setMiniSeries(miniSeries).
    		setName(summonerName);
    		Database.getStore().save(rankedstats);
    		return true;
    	}
    	return false;
    }
 
    public static boolean updateSummonerRankedStat(String summonerName,SummonerRankedStats rankedstats) {
        RankedStats stats =  getSummonerRankedStats(summonerName,rankedstats.getQueueType().toString());

        if (stats == null) {
            return false;
        }
        stats.
        setLeaguePoints(rankedstats.getLeaguePoints()).
        setLosses(rankedstats.getLosses()).
        setMiniSeries(rankedstats.getMiniSeriesProgress()).
        setRank(rankedstats.getRank()).
        setTier(rankedstats.getTier()).
        setWins(rankedstats.getWins());
        
        Database.getStore().save(stats);

        return true;
    }


	
}
