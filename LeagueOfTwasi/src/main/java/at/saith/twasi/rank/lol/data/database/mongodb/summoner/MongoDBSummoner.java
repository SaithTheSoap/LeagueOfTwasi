package at.saith.twasi.rank.lol.data.database.mongodb.summoner;

import java.util.Arrays;
import java.util.List;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

public class MongoDBSummoner{

	    private Properties properties;
	    private List<RankedStats> rankedstats;

	    public MongoDBSummoner() {}


	    public MongoDBSummoner(Properties properties, RankedStats... stats) {
	    	this.properties = properties;
	    	this.rankedstats = Arrays.asList(stats);
	    }

	  

	   public Properties getProperties() {
		   return properties;
	   }
	   public List<RankedStats> getRankedStats() {
		   return rankedstats;
	   }
	   @Entity(value = "summonerProperties", noClassnameStored = true)
	public
	    static class Properties{
		   @Id
		    private ObjectId _id;
		   private long profileIconId;
			private String name;
			private long summonerLevel;
			private long revisionDate;
			private long id;
			private long accountId;
			private long lastUpdate;
		   
			  public ObjectId getObjId() {
			        return _id;
			    }
		   public void setProfileIconId(long profileIconId) {
			this.profileIconId = profileIconId;
		}
		public Properties setName(String name) {
			this.name = name;
			return this;
		}
		public Properties setSummonerLevel(long summonerLevel) {
			this.summonerLevel = summonerLevel;
			return this;
		}
		public Properties setRevisionDate(long revisionDate) {
			this.revisionDate = revisionDate;
			return this;
		}
		public Properties setId(long id) {
			this.id = id;
			return this;
		}
		public Properties setAccountId(long accountId) {
			this.accountId = accountId;
			return this;
		}
		public Properties setLastUpdate(long lastUpdate) {
			this.lastUpdate = lastUpdate;
			return this;
		}
		public long getProfileIconId() {
			return profileIconId;
		}
		public String getName() {
			return name;
		}
		public long getSummonerLevel() {
			return summonerLevel;
		}
		public long getRevisionDate() {
			return revisionDate;
		}
		public long getId() {
			return id;
		}
		public long getAccountId() {
			return accountId;
		}
		public long getLastUpdate() {
			return lastUpdate;
		}
		
	    }
	   @Entity(value = "summonerRankedStats", noClassnameStored = true)
	   public
	   static class RankedStats{
		   @Id
		    private ObjectId _id;
		   private String name;
			private String rank;
			private String tier;
			private long leaguePoints;
			private String miniSeries;//progress
			private String queueType;
			private long wins;
			private long losses;
		   public String getRank() {
			return rank;
		}
		   public ObjectId getObjId() {
		        return _id;
		    }
		public RankedStats setRank(String rank) {
			this.rank = rank;
			return this;
		}
		public String getTier() {
			return tier;
		}
		public RankedStats setTier(String tier) {
			this.tier = tier;
			return this;
		}
		public long getLeaguePoints() {
			return leaguePoints;
		}
		public RankedStats setLeaguePoints(long leaguePoints) {
			this.leaguePoints = leaguePoints;
			return this;
		}
		public String getMiniSeries() {
			return miniSeries;
		}
		public RankedStats setMiniSeries(String miniSeries) {
			this.miniSeries = miniSeries;
			return this;
		}
		public String getQueueType() {
			return queueType;
		}
		public RankedStats setQueueType(String queueType) {
			this.queueType = queueType;
			return this;
		}
		public long getWins() {
			return wins;
		}
		public RankedStats setWins(long wins) {
			this.wins = wins;
			return this;
		}
		public long getLosses() {
			return losses;
		}
		public RankedStats setLosses(long losses) {
			this.losses = losses;
			return this;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	
	    }

}
