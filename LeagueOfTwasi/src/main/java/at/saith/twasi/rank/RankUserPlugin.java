package at.saith.twasi.rank;

import java.util.ArrayList;
import java.util.List;

import at.saith.twasi.rank.variable.RankVariable;
import net.twasi.core.database.models.User;
import net.twasi.core.models.Message.TwasiCommand;
import net.twasi.core.plugin.api.TwasiUserPlugin;
import net.twasi.core.plugin.api.TwasiVariable;
import net.twasi.core.plugin.api.events.TwasiCommandEvent;
import net.twasi.core.plugin.api.events.TwasiEnableEvent;
import net.twasi.core.plugin.api.events.TwasiInstallEvent;

public class RankUserPlugin extends TwasiUserPlugin{
	
	
	
	    @Override
	    public void onInstall(TwasiInstallEvent e) {
	    	 e.getDefaultGroup().addKey("rank.use.command");
	         e.getModeratorsGroup().addKey("rank.use.*");
	    }

	    @Override
	    public void onUninstall(TwasiInstallEvent e) {
	    	 e.getDefaultGroup().removeKey("rank.use.command");
	         e.getModeratorsGroup().removeKey("rank.use.*");
	    }
	    
	    @Override
	    public void onEnable(TwasiEnableEvent e) {

	    }
	    
	    @Override
	    public void onCommand(TwasiCommandEvent e) {
	        TwasiCommand cmd = e.getCommand();
	        User user = getTwasiInterface().getStreamer().getUser();
	        if (cmd.getCommandName().equalsIgnoreCase("rank")||cmd.getCommandName().equalsIgnoreCase("elo")) {
	            if (user.hasPermission(cmd.getSender(), "rank.use.command")) {
	            	String reply = getTranslations().getTranslation(user, "rank.msg");
	            	
	                cmd.reply(reply);
	            }
	        }
	    }
	  @Override
	public List<TwasiVariable> getVariables() {
	ArrayList<TwasiVariable> list = new ArrayList<>();
	list.add(new RankVariable(this));
	return list;
	}

}
