package at.saith.twasi.rank.lol.summoner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import at.saith.twasi.rank.json.JSONBuilder;

public class SummonerRankedStats {

	private String rank;
	private String tier;
	private long leaguePoints;
	private String miniSeriesProgress;
	private QueueType queueType;
	private long wins;
	private long losses;
	public SummonerRankedStats() {}
	public SummonerRankedStats(String json) throws ParseException {
		this((JSONObject) new JSONParser().parse(json));
	}
	public SummonerRankedStats(JSONObject jsonObject) {
		this.rank = ((String)jsonObject.get("rank"));
		this.tier = ((String)jsonObject.get("tier"));
		this.leaguePoints = ((long)jsonObject.get("leaguePoints"));
		JSONObject miniSeries = (JSONObject) jsonObject.get("miniSeries");
		if(miniSeries == null) {
			this.miniSeriesProgress = "";
		}else {
			this.miniSeriesProgress	=	(String) miniSeries.get("progress");
		}
		
		String queueTypeString = ((String)jsonObject.get("queueType"));
		this.queueType = QueueType.byName(queueTypeString);
		this.wins =		((long)jsonObject.get("wins"));
		this.losses = ((long)jsonObject.get("losses"));
	}
	public SummonerRankedStats(String rank , String tier, 
								long lp , String mini, 
								String queueType , long wins, long losses) {
		
		this.rank = rank;
		this.tier = tier;
		this.leaguePoints = lp;
		this.miniSeriesProgress = mini;
		this.queueType = QueueType.byName(queueType);
		this.wins = wins;
		this.losses = losses;
	}
	public String getRank() {
		return rank;
	}
	public String getTier() {
		return tier;
	}
	public long getLeaguePoints() {
		return leaguePoints;
	}
	public String getMiniSeriesProgress() {
		return miniSeriesProgress;
	}
	public QueueType getQueueType() {
		return queueType;
	}
	public long getWins() {
		return wins;
	}
	public long getLosses() {
		return losses;
	}
	public boolean hasMiniSeries() {
		return !miniSeriesProgress.equals("");
	}
	public String toString() {
		JSONBuilder jsonBuilder = new JSONBuilder();
		jsonBuilder.
		add("rank", rank).
		add("tier", tier).
		add("leaguePoints", leaguePoints).
		add("wins",wins).
		add("losses", losses);
		if(hasMiniSeries()) {
			jsonBuilder.add("miniSeries", miniSeriesProgress,true);
		}
		return jsonBuilder.toString();
	}
}
