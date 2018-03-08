package at.saith.twasi.rank.lol.data;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import at.saith.twasi.rank.json.JSONURLParser;
import at.saith.twasi.rank.lol.data.exception.InvalidAPIKeyException;
import at.saith.twasi.rank.lol.summoner.Summoner;
import at.saith.twasi.rank.lol.summoner.SummonerProperties;
import at.saith.twasi.rank.lol.summoner.SummonerRankedStats;
import net.twasi.core.logger.TwasiLogger;

public class RiotUtil {
	private static String API_KEY = "";
	private static String BASE_URL = "https://%SERVER%.api.riotgames.com/";

	public static void setup(String apikey) throws InvalidAPIKeyException {
		if(API_KEY.equals("")) {
			if(!isvalidKey(apikey)) {
				throw new InvalidAPIKeyException(apikey);
			}
			API_KEY = apikey;
		}
	}
	public static SummonerProperties getSummonerPropertiesByName(String summonerName) {
		return getSummonerPropertiesByName(summonerName, "euw1");
	}
	public static SummonerProperties getSummonerPropertiesByName(String summonerName,String region) {
		String summonerPropertiesMethod = "lol/summoner/v3/summoners/by-name/";
		URL summonerPropertiesUrl = getRiotUrl(summonerPropertiesMethod,region, summonerName);
		JSONObject json = (JSONObject) parseUrl(summonerPropertiesUrl);
		return new SummonerProperties(json);
	}
	public static ArrayList<SummonerRankedStats> getSummonerRankedStatsBySummonerId(long summonerId) {
		return getSummonerRankedStatsBySummonerId(summonerId, "euw1");
	}
	public static ArrayList<SummonerRankedStats> getSummonerRankedStatsBySummonerId(long summonerId,String region) {
		String summonerRankedStatsMethod = "lol/league/v3/positions/by-summoner/";
		URL summonerRankedStatsUrl = getRiotUrl(summonerRankedStatsMethod,region, summonerId+"");
		ArrayList<SummonerRankedStats> stats = new ArrayList<>();
		JSONArray array = (JSONArray) parseUrl(summonerRankedStatsUrl);
		if(array.size() == 0) {
			
		}
		for(Object obj :array) {
			if(obj instanceof JSONObject) {
				stats.add(new SummonerRankedStats((JSONObject) obj));
			}
		}
		return stats;
	}

	private static boolean upToDateSummoner(long lastUpdate) {
		long currentTime = System.currentTimeMillis();
		long difference = currentTime - lastUpdate;
		if(difference >= 10000) {
			return false;
		}
		return true;
	}
	private static Object parseUrl(URL url){
		
		Object json = null;
		try {
			JSONURLParser urlparser = new JSONURLParser();
			String jsonString = urlparser.parseUrl(url);
			json = new JSONParser().parse(jsonString);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch(IOException e) {
			
			}
		return json;
	}
	private static URL getRiotUrl(String method,String region,String param) {
			
		try {
			return new URL(BASE_URL.replace("%SERVER%", region)+method+param+"?api_key="+API_KEY);
		} catch (IOException e) {
			
		}
		return null;
	}
	private static boolean isvalidKey(String apikey) {
		String method = "lol/status/v3/shard-data";
		URL url = null;
		try {
			url = new URL(BASE_URL.replace("%SERVER%", "euw1")+method+"?api_key="+apikey);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		JSONObject object =  (JSONObject)parseUrl(url);
		if(object == null) {
			return false;
		}
		JSONObject status = (JSONObject) object.get("status");
		if(status == null) {
			return true;
		}
		
		if(status != null) {
		TwasiLogger.log.error(status.get("message"));	
		}
		return false;
	}
	public static boolean validSummoner(Summoner summoner) {
		return 
				upToDateSummoner(summoner.getProperties().lastUpdate());
	}
}
