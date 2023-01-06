package vttp2022.paf.assessment.eshop.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.websocket.server.PathParam;
import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.respositories.CustomerRepository;
import vttp2022.paf.assessment.eshop.respositories.OrderRepository;

@RestController
@RequestMapping(path = "/api/order",consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

	@Autowired
	private CustomerRepository crepo;

	@Autowired 
	private OrderRepository orepo;
	//TODO: Task 3


	@PostMapping("/")
	public ResponseEntity<String> postNewOrder(@RequestBody String payload) throws Exception{
		JsonObject pl = null;
		try (InputStream is = new ByteArrayInputStream(payload.getBytes())){
			JsonReader reader = Json.createReader(is);
			pl = reader.readObject();
		}catch(IOException e){
			returnError("Cannot read order");
		}
		String name = pl.getString("name");
		Optional<Customer> c = crepo.findCustomerByName(name);
		if(c.isEmpty())
			returnError(String.format("Customer %s not found", name));
		//clear false valid name
		Customer customer = c.get();
		JsonArray orders = pl.getJsonArray("orders");
		List<LineItem> lineitemlist = orders.stream().map(o ->o.asJsonObject()).map(o -> LineItem.toLineItemFromJson(o)).toList();
		Order newOrder = new Order();
		newOrder.setOrderId(UUID.randomUUID().toString().substring(0,8));
		//generate order id
		newOrder.setDeliveryId(UUID.randomUUID().toString().substring(0,8));
		newOrder.setName(name);
		newOrder.setAddress(customer.getAddress());
		newOrder.setEmail(customer.getEmail());
		newOrder.setStatus("pending");
		newOrder.setLineItems(lineitemlist);
		if(orepo.add(newOrder)) //insert order to database
			return ResponseEntity.ok(null);
		return returnError("Cannot add order to database");

	}



	private ResponseEntity<String> returnError(String errorMsg){
		JsonObject o = Json.createObjectBuilder().add("error", errorMsg).build();
		return ResponseEntity.status(404).body(o.toString());
	}

	@GetMapping("/{name}/status")
	public ResponseEntity<String> getOrdersByCustomer(@PathVariable String name){
		//todo




	}
}
