package logicaEstados;
import logicaJogo.*;

public class AwaitArchersAttack extends StateAdapter {
	
	public AwaitArchersAttack(GameData gameData) {
		super(gameData);
	}
	
	@Override
	public IStates ladder() {
		gameData.setChoice(1);
		gameData.setAction(gameData.getActionsList().get(0));	
		gameData.getAction().action(gameData);
		return new AwaitPlayerActions(gameData);
	}
	
	@Override
	public IStates ram() {
		gameData.setChoice(2);
		gameData.setAction(gameData.getActionsList().get(0));	
		gameData.getAction().action(gameData);
		return new AwaitPlayerActions(gameData);
	}
	
	@Override 
	public IStates tower() {
		gameData.setChoice(3);
		gameData.setAction(gameData.getActionsList().get(0));	
		gameData.getAction().action(gameData);
		return new AwaitPlayerActions(gameData);
	}
	
	@Override 
	public IStates cancel() {
		return new AwaitPlayerActions(gameData);
	}
}
