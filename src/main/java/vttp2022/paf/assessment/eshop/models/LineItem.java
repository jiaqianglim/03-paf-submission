package vttp2022.paf.assessment.eshop.models;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

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

	public static JsonObjectBuilder toJsonOBldrFromLineItem(LineItem i) {
		return Json.createObjectBuilder().add("item", i.getItem()).add("quantity", i.getQuantity());
	}
}
