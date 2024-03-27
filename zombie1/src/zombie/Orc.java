package zombie;

import java.util.Random;

public class Orc extends Unit{
	private Random random = new Random();
	
	public Orc() {
		super("ORC",30,10,5,3);
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
