package at.saith.twasi.rank.variable;

import at.saith.twasi.rank.lol.SummonerUtil;
import at.saith.twasi.rank.lol.summoner.QueueType;
import at.saith.twasi.rank.lol.summoner.Summoner;
import at.saith.twasi.rank.lol.summoner.SummonerProperties;
import at.saith.twasi.rank.lol.summoner.SummonerRankedStats;


public class RankVariableProcessor {

	

	public static String process(String[] params) {
		
		
		String variable = "";		
			if(params == null) 
				return variable;
			
			
				if(params.length >= 1 &&params.length <=3) {
						String summonerName = params[0];
						
						String varName = "rankedstats";
						
						String region = "euw1";
						
						
						if(params.length >= 3) {
							varName = params[2];
						}
						if(params.length == 4) {
							region = params[2];
							varName = params[3];
						}
						
						
						Summoner summoner = SummonerUtil.getSummoner(summonerName,region);
						
						SummonerRankedStats stats = summoner.getRankedStats(QueueType.RANKED_SOLO_5X5);
						
						if(varName.equalsIgnoreCase("rankedstats")) {
							
							variable = toVariableString(stats);
							
						}else {
							SummonerProperties prop = summoner.getProperties();
							if(prop == null) {
								return variable;
							}
							variable = getVariable(varName, prop, stats);
							
						}
				}
				return variable;
			}
			
	private static String toVariableString(SummonerRankedStats stats) {
		String variable = "";
		if(stats == null) {
			variable = "UNRANKED";	
			}else {
			variable = stats.getTier() +" "+stats.getRank()+" "+stats.getLeaguePoints()+"LP";
			if(stats.hasMiniSeries()) {
				variable+=" "+stats.getMiniSeriesProgress();
			}
			}
		return variable;
	}
	private static String getVariable(String varName, SummonerProperties prop, SummonerRankedStats stats) {
		String variable = "";
		switch(varName.toLowerCase()) {
		//PROPERTIES
		case "name":
			variable = prop.getName();
			break;
		case "accountid":
			variable += prop.getAccountId();
			break;
		
		case "summonerid"://Fall Through
		case "id":
			variable += prop.getId();
			break;
		case "profileiconid":
			variable += prop.getProfileIconId();
			break;
		case "summonerlevel":
		case "level":
			variable += prop.getSummonerLevel();
			break;
			
			
		//RANKEDSTATS
		case "rank":
			variable = stats.getRank();
			break;
		case "tier":
			variable = stats.getTier();
			break;
		case "lp":
			variable += stats.getLeaguePoints();
			break;
		case "losses":
			variable += stats.getLosses();
			break;
		case "wins":
			variable += stats.getWins();
			break;
		case "series":
			variable+=stats.getMiniSeriesProgress();
			break;
		}
		return variable;
	}
}
