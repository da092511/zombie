package zombie;

abstract public class Unit{
	public final int MAX_POS = 20;
	public final int MAX_HP;
	public final int MAX_POWER;
	
	public final int CODE;
	
	private String name;
	private int hp;
	private int pos;
	
	
	private boolean isDead;
	
	public Unit(String name, int hp, int pos, int maxPower, int code){
		this.name = name;
		this.hp = hp;
		this.pos = pos;
		
		MAX_HP = hp;
		MAX_POWER = maxPower;
		CODE = code;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getHp() {
		return this.hp;
	}
	
	public int getPos() {
		return this.pos;
	}
	
	public void setPosForward() {
		this.pos++;
	}
	
	public void setPosBackward() {
		this.pos--;
	}
	
	public void setHpPlus() {
		if(this.hp == MAX_HP)
			return ;
		
		this.hp++;
	}
	
	public void setHpMinus() {
		if(this.hp == 0)
			return;
		this.hp--;
	}
	
	abstract public void attack(Unit unit);
	
	public void attacked(int attack) {
		if(this.hp == 0)
			return;
 		
		System.err.printf("%s -%d\n",this.name, attack);
		
		while(attack > 0 && this.hp > 0) {
			this.setHpMinus();
			attack--;
		}
	}
	
	abstract public void move(int dir);
	
	@Override
	public String toString() {
		return String.format("%s :[%d/%d]\n", this.name, this.hp, MAX_HP);
	}
}
