package zombie;

import java.util.Random;

public class Hero extends Unit implements Potion{
	private Random random = new Random();
	
	private int potionCnt;
	
	public Hero() {
		super("HERO", 100, 0, 30, 0);
	}
	
	public int getPotionCount() {
		return this.potionCnt;
	}
	
	public void setPotionCount(int potionCount) {
		this.potionCnt = potionCount;
	}
	
	private void usePotion() {
		this.potionCnt --;
	}
	
	@Override
	public boolean potion() {
		int hp = super.getHp();
		 
		if(hp == MAX_HP) {
			return false;
		}
		
		usePotion();
		
		int getEnergy = 20;
		
		while(getEnergy > 0 && super.getHp() < super.MAX_HP) {
			setHpPlus();
			getEnergy--;
			System.out.println("회복중...");
		}
		
		return true;
	}
	
	@Override
	public void move(int dir) {
		if(dir == 1) {
			if(super.getPos() == super.MAX_POS)
				return;
			
			super.setPosForward();
		}
		else if(dir == 2) {
			if(super.getPos() == 2)
				return;
			
			super.setPosBackward();
		}
	}
	
	@Override
	public void attack(Unit enemy) {
		int attack = random.nextInt(MAX_POWER-10)+10; 
		System.out.printf("%s의 공격 !! %d\n",this.getName(), attack);
		
		if (enemy instanceof Boss) {
			Boss boss = (Boss) enemy;
			if(boss.getShield() > 0) {
				int hp = boss.getShield() - attack;
				
				if(hp >= 0) {
					boss.setShield(hp);
				}else {
					boss.setShield(0);
					boss.attacked(-hp);;
				}
			}else
				boss.attacked(attack);
			
			return;
		}
		
		enemy.attacked(attack);
	}
	
}
