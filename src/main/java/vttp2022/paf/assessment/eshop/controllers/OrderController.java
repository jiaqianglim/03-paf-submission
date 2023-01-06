package vttp2022.paf.assessment.eshop.controllers;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.websocket.server.PathParam;
import vttp2022.paf.assessment.eshop.models.Customer;
import vttp2022.paf.assessment.eshop.respositories.CustomerRepository;
import vttp2022.paf.assessment.eshop.respositories.autowired;

@RestController
@RequestMapping("/api/order")
public class OrderController {

	@autowired
	private CustomerRepository crepo;
	//TODO: Task 3
	public ResponseEntity<String> getCustomerByName(String name){
		Optional<Customer> c = crepo.findCustomerByName(name);
		if(c.isEmpty())
			returnError(name);
		Customer cus = c.get();
		return 
	}

	private ResponseEntity<String> returnError(String name){
		JsonObject o = Json.createObjectBuilder().add("error", String.format("Customer %s not found", name)).build();
		return ResponseEntity.status(404).body(o.toString());
	}

	@GetMapping("/{name}/status")
	public ResponseEntity<String> getOrdersByCustomer(@PathVariable String name){

	}
}
