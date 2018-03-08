package at.saith.twasi.rank.lol.summoner;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import at.saith.twasi.rank.json.JSONBuilder;

public class SummonerProperties {

	private long profileIconId;
	private String name;
	private long summonerLevel;
	private long revisionDate;
	private long id;
	private long accountId;
	private long lastUpdate;
	
	public SummonerProperties() {}
	public SummonerProperties(String json) throws ParseException {
		this((JSONObject) new JSONParser().parse(json));
		
	}
	public SummonerProperties(JSONObject jsonObject) {
		if(jsonObject == null) {
		return;	
		}
		this.profileIconId = (long)jsonObject.get("profileIconId");
		this.name = ((String)jsonObject.get("name"));
		this.summonerLevel = ((long)jsonObject.get("summonerLevel"));
		this.revisionDate = ((long)jsonObject.get("revisionDate"));
		this.id = ((long)getSummonerId(jsonObject));
		this.accountId = ((long)jsonObject.get("accountId"));
		this.lastUpdate = ((long)getLastUpdate(jsonObject));
	}
	public SummonerProperties(long profileIconId, String name,
							  long summonerLevel,
							  long revisionDate, long id, long accountId,long lastUpdate) {
		this.profileIconId = profileIconId;
		this.name = name;
		this.summonerLevel = summonerLevel;
		this.id = id;
		this.revisionDate = revisionDate;
		this.accountId = accountId;
		this.lastUpdate = lastUpdate;
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
	public long lastUpdate() {
		return lastUpdate;
	}
	public String toString() {
		JSONBuilder jsonBuilder = new JSONBuilder();
			jsonBuilder.
			add("profileIconId", profileIconId).
			add("name", name).
			add("summonerLevel", summonerLevel).
			add("revisionDate", revisionDate).
			add("id", id).
			add("accountId", accountId).
			add("lastUpdate", lastUpdate);
		return jsonBuilder.toString();
	}
	private static Object getSummonerId(JSONObject jsonObject) {
		Object summonerId = jsonObject.get("summonerId");
		if(summonerId == null) {
			return jsonObject.get("id");
		}
		return summonerId;
	}
	private static Object getLastUpdate(JSONObject jsonObject) {
		Object lastUpdate =jsonObject.get("lastUpdate") ;
		if(lastUpdate == null) {
			return System.currentTimeMillis();
		}
		return lastUpdate;
	}
}
