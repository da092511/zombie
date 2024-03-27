package zombie;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
	private Scanner scanner = new Scanner(System.in);
	private Random random = new Random();
	
	private final int MAX_POS = 20;
	
	private final int HERO = 0;
	private final int ZOMBIE = 1;
	private final int WOLF = 2;
	private final int ORC = 3;
	private final int BOSS = 4;

	private final int MOVE_FORWARD = 1;
	private final int MOVE_BACKWARD =2;
	private final int ATTACK = 3;
	private final int DRINK_POTION = 4;
	private final int QUIT = 0;
	
	private boolean isRun;
	private boolean isBoss;
	
	private int win;
	
	private ArrayList<Unit> map;
	
	private Game() {
		map = new ArrayList<>();
		setMap();
		
		this.isRun = true;
	}

	private static Game instance = new Game();
	public static Game getInstance() {
		return instance;
	}
	
	private void setMap() {
		Hero hero = new Hero();
		
		int count = inputNumber("포션 갯4수(1개당 +20HP)");
		hero.setPotionCount(count);
		
		this.map.add(hero);
		
		Unit unit = addEnemy();
		
		if(unit != null)
			this.map.add(unit);
	}
	
	private Unit addEnemy() {
		int creative = random.nextInt(2);
		
		if(creative != 1)
			return null;
		
		int enemy = random.nextInt(3)+1;
		
		if(enemy == ZOMBIE) {
			Zombie zombie = new Zombie();
			return zombie;
		}
		else if(enemy == ORC) {
			Orc orc = new Orc();
			return orc;
		}
		else if(enemy == WOLF) {
			Wolf wolf = new Wolf();
			return wolf;
		}
		
		return null;
	}
	
	private void showMap() {
		System.out.println(map);
		
		for(int i=0;i<=MAX_POS;i++) {
			boolean isCheck = false;
			
			for(Unit unit : map) {
				int code = unit.CODE;
				
				if(unit.getPos()==i && code==HERO) {
					System.out.print("[HERO]");
					isCheck = true;
					break;
				}
				else if(i == MAX_POS) {
					System.out.print("★");
					isCheck = true;
					break;
				}
				else if(unit.getPos()==i && code==BOSS) {
					System.out.print("[BOSS]");
					isCheck = true;
					break;
				}
				else if(unit.getPos()==i && code==WOLF) {
					System.out.print("[wolf]");
					isCheck = true;
					break;
				}
				else if(unit.getPos()==i && code==ORC) {
					System.out.print("[Orc]");
					isCheck = true;
					break;
				}
				else if(unit.getPos()==i && code==ZOMBIE) {
					System.out.print("[Zombie]");
					isCheck = true;
					break;
				}
			}
			
			if(!isCheck)
				System.out.print("_");
		}
		System.out.println();
	}
	
	public void runGame(int select) {
		switch(select) {
		case(MOVE_FORWARD):
			moveForward();
			break;
		case(MOVE_BACKWARD):
			moveBackWard();
			break;
		case(ATTACK):
			attack();
			break;
		case(DRINK_POTION):
			drinkPotion();
			break;
		case(QUIT):
			this.isRun = false;
			break;
		}
	}
	
	private void moveForward() {
		Hero hero = (Hero) map.get(HERO);
		int pos = hero.getPos()+1;
		
		if(checkMove((Unit)hero, pos))
			hero.move(MOVE_FORWARD);
		else
			System.err.println("장애물이 있습니다.");
	}
	
	private boolean checkMove(Unit target, int pos) {
		if(pos < 0 || pos > target.MAX_POS)
			return false; 
		
		for(Unit unit: map) {
			if(unit == target)
				continue;
			
			if(unit.getPos() == pos) {
				return false;
			}
		}
		
		return true;
	}
	
	private void moveBackWard() {
		Unit hero = map.get(HERO);
		int pos = hero.getPos() -1;
		
		if(checkMove(hero, pos))
			hero.move(MOVE_BACKWARD);
		else
			System.err.println("장애물이 있습니다.");
	}
	
	private void attack() {
		Hero hero = (Hero) map.get(HERO);
		int pos = hero.getPos();
		
		for(int i=1;i<map.size();i++) {
			Unit unit = map.get(i);
			int targetPos = unit.getPos();
			if(targetPos == pos -1 || targetPos == pos+1)
				hero.attack(unit);
			
			if(unit.getHp() == 0) {
				System.err.printf("%s를 처치하였습니다.",unit.getName());
				map.remove(unit);
			}
		}
	}
	
	private void drinkPotion() {
		Hero hero = (Hero) map.get(HERO);
		
		if(!hero.potion())
			System.err.println("아직 아프지 않습니다.");
		
		System.out.println(hero);
	}
	
	private void attacked() {
		Hero hero = (Hero) map.get(HERO);
		int heroPos = hero.getPos();
		
		for(int i=1;i<map.size();i++) {
			Unit unit = map.get(i);
			int enemyPos = unit.getPos();
			if(enemyPos == heroPos-1 || enemyPos == heroPos +1) {
				unit.attack(hero);
			}
		}
	}
	
	private void putEnemy() {
		Unit unit = addEnemy();
		
		if(unit == null)
			return ;
		
		int unitPos = unit.getPos();
		int heroPos = map.get(HERO).getPos();
		
		if(unitPos >heroPos+3 || unitPos < +3)
			this.map.add(unit);
	}
	
	private void moveEnemy() {
		Unit hero = map.get(HERO);
		int heroPos = hero.getPos();
		
		for(int i=1;i<map.size();i++) {
			Unit target = map.get(i);
			int targetPos = target.getPos();
			
			int dir = 2;
			if(targetPos <= heroPos)
				dir = 1;
			
			int pos = dir == 1 ? 1 : -1;
			pos += targetPos ;
			
			if(pos == heroPos) 
				continue;
			
			if(!checkMove(target,pos)) 
				continue;
			
			target.move(dir);
		}
	}
	
	private void setBoss() {
		Unit boss = (Unit) new Boss();
		map.add(boss);
	}
	
	private boolean checkHero() {
		Hero hero = (Hero) map.get(HERO);
		
		if(hero.getPos() == hero.MAX_POS) {
			this.win = 1;
			return false;
		}
		else if(hero.getHp() == 0) {
			return false;
		}else if(!isBoss && hero.getPos()== 18) {
			setBoss();
			isBoss = true;
		}
		
		return true;
	}
	
	private void showResult() {
		if(this.win == 1)
			System.out.println("VICTORY");
		else
			System.err.println("사망하였습니다.");
	}
	
	public void run() {
		//게임 진행
		while(true) {
			if(!checkHero())
				break;
			
			showMap();
			
			int select = inputNumber("move (앞으로(1) 뒤로(2) 공격(3) 포션먹기(4) 종료(0)");
			runGame(select);
			
			if(!this.isRun)
				break;
			
			attacked();
			
			moveEnemy();
			putEnemy();
		}
		showMap();
		showResult();
	}
	
	private int inputNumber(String message) {
		int number = -1;
		System.out.printf("%s : ",message);
		
		try {
			String input = scanner.next();
			number = Integer.parseInt(input);
		}catch(Exception e) {
			System.err.println("숫자만 입력");
		}
		
		return number;
	}

}
