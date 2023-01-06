package vttp2022.paf.assessment.eshop.models;

import jakarta.json.JsonObject;

// DO NOT CHANGE THIS CLASS
public class LineItem {

	private String item;
	private Integer quantity;

	public String getItem() {
		return this.item;
	}

	public void setItem(String item) {
		this.item = item;
	}

	public Integer getQuantity() {
		return this.quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public static LineItem toLineItemFromJson(JsonObject o) {
		LineItem li = new LineItem();
		li.setItem(o.getString("item"));
		li.setQuantity(o.getInt("quantity"));
		return li;
	}
}
