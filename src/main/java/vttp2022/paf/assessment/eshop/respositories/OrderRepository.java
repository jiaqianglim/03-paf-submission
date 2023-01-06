package vttp2022.paf.assessment.eshop.respositories;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseStatus;

import vttp2022.paf.assessment.eshop.models.Order;
import vttp2022.paf.assessment.eshop.models.OrderStatus;

@Repository
public class OrderRepository {
	// TODO: Task 3 save order to the database

	@Autowired
	private JdbcTemplate temp;

	@Transactional
	public boolean add(Order o) throws DataAccessException {
		List<Object[]> params = o.getLineItems().stream()
				.map(li -> new Object[] { li.getItem(), li.getQuantity(), o.getOrderId() })
				.collect(Collectors.toList());
		int addedOrder = temp.update("insert into eshop.order(order_id, deliveryid, name, order_date) values(?,?,?,?)",
				o.getOrderId(), o.getDeliveryId(), o.getName(), o.getOrderDate());
		int addedLineItems[] = temp.batchUpdate("insert into eshop.lineitem(item, quantity, order_id) values (?,?,?)",
				params);
		if (addedOrder == 0 || Arrays.stream(addedLineItems).anyMatch(num -> num == 0))
			return false;
		return true;
	}

	public Optional<Map<String, Integer>> getStatusByName(String name) {
		Map<String, Integer> result = new LinkedHashMap<>();
		SqlRowSet rs = temp.queryForRowSet(
				"select status, count(status) as _count from order_status as os join order as o on os.order_id = o.order_id_where o.name = '?' group by status;",
				name);
		if (rs.next()) {
			result.put(rs.getString("status"), rs.getInt("_count"));
		}
		if (result.size() > 0)
			return Optional.of(result);
		return Optional.empty();
	}

	public boolean add(OrderStatus os) {
		int addedOrderStatus = 0;
		if (os.getStatus().equals("dispatched")) {
			addedOrderStatus = temp.update(
					"insert into eshop.order_status(order_id, delivery_id, status) values(?,?,?)",
					os.getOrderId(), os.getDeliveryId(), "dispatched");
		}
		if (os.getStatus().equals("pending")) {
			addedOrderStatus = temp.update("insert into eshop.order_status(order_id, status) values(?,?)",
					os.getOrderId(), "pending");
		}
		return addedOrderStatus > 0;
	}

}
