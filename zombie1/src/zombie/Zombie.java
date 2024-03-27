package zombie;

import java.util.Random;

public class Zombie extends Unit{
	private Random random = new Random();
	
	public Zombie() {
		super("Zombie",10,12,12,1);
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
	public void attack(Unit hero) {
		int attack = random.nextInt(MAX_POWER+1);
		
		if(attack == 0)
			return;
		
		System.out.printf("%s의 공격 !! %d\n",this.getName(), attack);;
		hero.attacked(attack);
	}
	
}
