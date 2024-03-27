package zombie;

import java.util.Random;

public class Boss extends Unit{
	private Random random = new Random();
	
	private int shield;
	
	public Boss() {
		super("BOSS", 100, 19, 20 , 4);
		
		this.shield = 50;
	}
	
	public int getShield(){
		return this.shield;
	}
	
	public void setShield(int shield) {
		this.shield = shield;
	}

	@Override
	public void move(int dir) {
	}
	
	@Override
	public void attack(Unit hero) {

		int a = random.nextInt(4) + 1;
		if (a == 1) {
			System.out.println("보스의 필살기 발동 2배의 공격력");
			int power = 2 * (random.nextInt(MAX_POWER) + 1);
			hero.attacked(power);
			System.err.println("보스가 " + power + "의 공격력으로 공격 :" + " 현재 Hero hp : " + hero.getHp());
		} else {
			System.out.println("보스의 일반공격 ");
			int power = random.nextInt(MAX_POWER) + 1;
			hero.attacked(power);
			System.err.println("보스가 " + power + "의 공격력으로 공격 :" + " 현재 Hero hp : " + hero.getHp());
		}
	}
}
