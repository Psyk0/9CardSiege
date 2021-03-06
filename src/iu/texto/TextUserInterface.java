package iu.texto;

import logicaEstados.*;
import logicaJogo.*;
import java.util.*;

public class TextUserInterface {

	private final Game g;
	Scanner sc;
	
	public TextUserInterface(Game game)
    {
        g = game; 
        sc = new Scanner(System.in);
    }
	
	public void run(){
		int x=1;
		while(x==1) {
			if(g.getState() instanceof AwaitBeginning)
				InputAwaitBeginning();
			if(g.getState() instanceof AwaitEnemyLineCheck)
				InputAwaitEnemyLineCheck();
			if(g.getState() instanceof AwaitCardSelection)
				InputAwaitCardSelection();
			if(g.getState() instanceof AwaitEventPhase)
				InputAwaitEventPhase();
			if(g.getState() instanceof AwaitEnemyMovement)
				InputAwaitEnemyMovement();
			if(g.getState() instanceof AwaitPlayerActions)
				InputAwaitPlayerActions();
			if(g.getState() instanceof AwaitWinOrLossCheck)
				InputAwaitWinOrLossCheck();
			if(g.getState() instanceof AwaitEndOfDay)
				InputAwaitEndOfDay();
		}
	}
	
	public boolean checkLimits(int min,int max,int value)
    {
        return value>min ||  value<max;
    }
	
	public void InputAwaitBeginning() {
		System.out.println(" 1 -> Come�ar Jogo");
		System.out.println(" 2 -> Carregar Jogo");
		
		int escolhainit = sc.nextInt();
		
		System.out.println();
		
		if(checkLimits(1,2,escolhainit)) {
			if(escolhainit == 1) {
				g.ResolveStart();
			}
		}
	}
	
	public void InputAwaitEnemyLineCheck() {
		boolean i = Boolean.FALSE;
		boolean u = Boolean.FALSE;
		g.ResolveLineCheck(i,u);
		if(i == Boolean.TRUE) {
			System.out.println("You lost your soldiers in the Enemy Line");
		}
		else if(u == Boolean.TRUE) {
			System.out.println("Your soldiers on the Enemy Line were undetected");
			}
			else {
				System.out.println("You don't have any soldier on the Enemy Line");
			}
		g.ResolveAdvance();
	}
	
	public void InputAwaitCardSelection() {
		g.ResolveCard();
	}
	
	public void InputAwaitEventPhase() {
		System.out.print("Name: ");
		System.out.println(g.getGameData().getEvAtual().evName);
		System.out.print("Description: ");
		System.out.println(g.getGameData().getEvAtual().event);
		System.out.print("Action points: ");
		System.out.println(g.getGameData().getEvAtual().actionPoints);
		System.out.print("Enemy Advancement Order(s): ");
		for(int c = 0; c < g.getGameData().getEvAtual().enemyAdOrder.length ; c++) {
			if(g.getGameData().getEvAtual().enemyAdOrder[c] == 0)
				System.out.print("None");
			if(g.getGameData().getEvAtual().enemyAdOrder[c] == 1)
				System.out.print("Ladder\t");
			if(g.getGameData().getEvAtual().enemyAdOrder[c] == 2)
				System.out.print("Battering Ram\t");
			if(g.getGameData().getEvAtual().enemyAdOrder[c] == 3)
				System.out.print("Tower Siege\t");
			if(g.getGameData().getEvAtual().enemyAdOrder[c] == 4)
				System.out.print("Ladder\tBattering Ram\tTower Siege");
		}
		System.out.println("");
		
		g.EventPhase();
	}
	
	public void InputAwaitEnemyMovement() {
		g.EnemyMovement();
	}
	
	public void InputAwaitPlayerActions() {
		
		System.out.println("Enemy Track Card:");
		System.out.println("Ladder: " + g.getGameData().getLeft_ladder());
		System.out.println("Battering Ram: " + g.getGameData().getMid_ram());
		System.out.println("Tower Siege: " + g.getGameData().getRight_siege());
		System.out.println("Trebuchets: " + g.getGameData().getTrebuchet());
		
		System.out.println("Status Track Card:");
		System.out.println("Wall: " + g.getGameData().getLeft_wall());
		System.out.println("Morale: " + g.getGameData().getMid_morale());
		System.out.println("Supplies: " + g.getGameData().getRight_supplies());
		System.out.println("Tunnel position: " + g.getGameData().getTunnel());
		System.out.println("Supplies Raided: " + g.getGameData().getSupplies());
		int v=0;
		for( v=0 ; v < g.getGameData().getActionP() ; v++) {
			int linha = 0;
			int escolha = 0;
			if((g.getGameData().getLeft_ladder() == 0 && g.getGameData().getMid_ram() == 0) || (g.getGameData().getLeft_ladder() == 0 && g.getGameData().getRight_siege() == 0) || (g.getGameData().getMid_ram() == 0 && g.getGameData().getRight_siege() == 0)) {
				System.out.println("There are 2 enemies on the Close Combat Space! You have to fight them!");
				System.out.print("Choose the lane you want to attack: ");
				linha = sc.nextInt();
				g.getGameData().setChoice(linha);
				g.PlayerActions(3);
				if(g.getGameData().isChange() == true) {
					System.out.println(g.getGameData().getText());
				}
				g.getGameData().setActionP_minus(1);
				continue;
			}
			System.out.println("Choose the action you want to do:");
			System.out.println("1 - Archers Attack");
			System.out.println("2 - Boiling Water Attack");
			System.out.println("3 - Close Combat Attack");
			System.out.println("4 - Coupure");
			System.out.println("5 - Rally Troops");
			System.out.println("6 - Tunnel Movement");
			System.out.println("7 - Supply Raid");
			System.out.println("8 - Sabotage");
			System.out.println("9 - Additional Player Action Points");
		
			escolha = sc.nextInt();
		
			if(checkLimits(1,9,escolha)) {
				if(escolha == 1 || escolha == 2 || escolha == 3) {
					System.out.print("Choose the lane to attack: ");
					linha = sc.nextInt();
					g.getGameData().setChoice(linha);
					g.PlayerActions(escolha);
					if(g.getGameData().isChange() == true) {
						System.out.println(g.getGameData().getText());
						v--;
						System.out.println("Chose another option");
					}
					g.getGameData().setActionP_minus(1);
				}
				else if(escolha == 5) {
					System.out.println("Do you want to spend a supply point to gain +1 DRM?");
					System.out.println("1 - Yes");
					System.out.println("2 - No");
					linha = sc.nextInt();
					g.getGameData().setChoice(linha);
					g.PlayerActions(escolha);
					if(g.getGameData().isChange() == true) {
						System.out.println(g.getGameData().getText());
						v--;
						System.out.println("Chose another option");
					}
					g.getGameData().setActionP_minus(1);
				}
				else {
					g.PlayerActions(escolha);
					if(g.getGameData().isWrongOption() == true) {
						System.out.println(g.getGameData().getText());
						v--;
					}
					g.getGameData().setActionP_minus(1);
				}
			}
		}
			
		g.NextState();
		
	}
	
	public void InputAwaitWinOrLossCheck(){
		boolean wl = Boolean.FALSE;
		
		g.WinOrLossCheck(wl);
		if(wl==Boolean.TRUE){
			System.out.println("You lost!");
			System.exit(0);
		}	
	}
	
	public void InputAwaitEndOfDay() {
		g.EndOfDay();
		System.out.println("You won!");
		System.exit(0);
	}
}
