package models;

public class Item {
public String name;
public int drawableId;

	public Item(String name, int drawableId) {
		this.name = name;
		this.drawableId = drawableId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the drawableId
	 */
	public int getDrawableId() {
		return drawableId;
	}
}