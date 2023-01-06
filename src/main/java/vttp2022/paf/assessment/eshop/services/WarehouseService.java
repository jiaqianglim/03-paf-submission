package vttp2022.paf.assessment.eshop.services;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import jakarta.json.Json;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;

@Service
public class WarehouseService {

	// You cannot change the method's signature
	// You may add one or more checked exceptions
	public OrderStatus dispatch(Order order) {

		JsonArrayBuilder lineItems = Json.createArrayBuilder();
		for (LineItem i : order.getLineItems()) {
			lineItems.add(LineItem.toJsonOBldrFromLineItem(i));
		}
		JsonObject requestPayload = Json.createObjectBuilder().add("orderId", order.getOrderId())
				.add("name", order.getName()).add("address", order.getCustomer().getAddress())
				.add("email", order.getEmail()).add("lineItems", lineItems)
				.add("createdBy", "Lim Jia Qiang").build();
		RequestEntity<String> req = RequestEntity.post("http://paf.chuklee/dispatch")
				.contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)
				.body(requestPayload.toString());
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> resp = rt.exchange(req, String.class);
		OrderStatus os = new OrderStatus();
		os.setOrderId(order.getOrderId());
		JsonObject body;
		try (InputStream is = new ByteArrayInputStream(resp.getBody().getBytes())) {
			JsonReader jr = Json.createReader(is);
			body = jr.readObject();
			os.setDeliveryId(body.getString("deliveryId"));
			os.setStatus("dispatched");
			return os;
		} catch (Exception e) {
			os.setStatus("pending");
			return os;
		}

		// TODO: Task 4

	}
}
