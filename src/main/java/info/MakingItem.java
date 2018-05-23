package info;

public class MakingItem {
	
	int moonstone;
	int soulstone;
	int soulbead;
	int whitebead;
	int gold;
	String name;
	int count;

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
	
	
	

}
