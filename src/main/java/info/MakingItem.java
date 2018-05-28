package info;

public class MakingItem {
	
	private int moonstone;
	private int soulstone;
	private int soulbead;
	private int whitebead;
	private int gold;
	private String name;
	private int count;

	public MakingItem(String name, int soulstone, int moonstone, int soulbead, int whitebead, int gold, int count) {
		this.name = name;
		this.moonstone = moonstone;
		this.soulstone = soulstone;
		this.soulbead = soulbead;
		this.whitebead = whitebead;
		this.gold = gold;
		this.count = count;

	}
	
	public String getName() {
		return name;
	}

	public int getMoonstone() {
		return moonstone;
	}

	public int getSoulstone() {
		return soulstone;
	}

	public int getSoulbead() {
		return soulbead;
	}

	public int getGold() {
		return gold;
	}

	public int getCount() {
		return count;
	}

	public int getWhitebead() {
		return whitebead;
	}
	
	
	
	
	

}
