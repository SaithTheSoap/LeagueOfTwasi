package at.saith.twasi.rank;

import at.saith.twasi.rank.config.Config;
import at.saith.twasi.rank.lol.SummonerUtil;
import at.saith.twasi.rank.lol.data.DefaultSummonerFetcher;
import at.saith.twasi.rank.lol.data.SummonerFetcher;
import at.saith.twasi.rank.lol.data.database.mongodb.MongoDBFetcher;
import at.saith.twasi.rank.lol.data.exception.InvalidAPIKeyException;
import net.twasi.core.logger.TwasiLogger;
import net.twasi.core.plugin.TwasiPlugin;
import net.twasi.core.plugin.api.TwasiUserPlugin;

public class RankPlugin extends TwasiPlugin{
	private static RankPlugin INSTANCE;
	private static String prefix = "[RANK] ";
	
    @Override
    public void onActivate() {
    	INSTANCE = this;
    	setupSummonerUtil();
    	TwasiLogger.log.info(prefix+"Plugin enabled.");
    }


    @Override
    public void onDeactivate() {
    	TwasiLogger.log.info(prefix+"Plugin disabled.");
    }
    
    @Override
    public Class<? extends TwasiUserPlugin> getUserPluginClass() {
        return RankUserPlugin.class;
    }
    
    private void setupSummonerUtil() {
    	SummonerFetcher dataFetcher = new DefaultSummonerFetcher();
    	String type = (String) Config.get("datafetcher/type");
    	if(type != null) {
    		Object apikey = Config.get("apikey");
    		if(apikey != null) {
    			try {
    		if(type.equalsIgnoreCase("Mongodb")) {
					dataFetcher = new MongoDBFetcher((String) apikey);
				
    		}
    			} catch (InvalidAPIKeyException e) {
					TwasiLogger.log.error(prefix+e.getMessage());
				}
    		}
    	}
    	
    	TwasiLogger.log.debug(prefix+"Using "+dataFetcher.getDescription()+" as DataFetcher!");
    	
    	SummonerUtil.setup(dataFetcher);
    	
	}
    
    public static RankPlugin getInstance() {
    	return INSTANCE;
    }
    public static String getPrefix() {
    	return prefix;
    }
}
