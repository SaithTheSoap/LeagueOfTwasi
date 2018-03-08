package at.saith.twasi.rank.variable;

import java.util.Arrays;
import java.util.List;

import net.twasi.core.interfaces.api.TwasiInterface;
import net.twasi.core.models.Message.TwasiMessage;
import net.twasi.core.plugin.api.TwasiUserPlugin;
import net.twasi.core.plugin.api.TwasiVariable;

public class RankVariable extends TwasiVariable{

	public RankVariable(TwasiUserPlugin owner) {
		super(owner);
	}
	@Override
	public List<String> getNames() {
		// TODO Auto-generated method stub
		return Arrays.asList("rank","summrank","summonerrank");
	}

	@Override
	public String process(String name, TwasiInterface inf, String[] params, TwasiMessage message) {
		// TODO Auto-generated method stub
		return RankVariableProcessor.process(params);
	}
}
