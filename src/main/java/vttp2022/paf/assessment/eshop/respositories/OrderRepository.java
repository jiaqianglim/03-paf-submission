package vttp2022.paf.assessment.eshop.respositories;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.transaction.annotation.Transactional;

import vttp2022.paf.assessment.eshop.models.LineItem;
import vttp2022.paf.assessment.eshop.models.Order;

public class OrderRepository {
	// TODO: Task 3 save order to the database

	@Autowired
	private JdbcTemplate temp;

	@Transactional
	public boolean add(Order o) throws Exception{
		int addedOrder = temp.update("insert into eshop.order(order_id, deliveryid, name, order_date) values(?,?,?,?)", o.getOrderId(),o.getDeliveryId(),o.getName(),o.getOrderDate());

		return true;	
	}

	public Optional<Map<String,Integer>> getStatusByName(String name){
		Map<String, Integer> result = new LinkedHashMap<>();
		SqlRowSet rs = temp.queryForRowSet("select status, count(status) as _count from order_status as os join order as o on os.delivery_id = o.delivery_id_where o.name = '?'group by status;", name);
		if(rs.next()){
			result.put(rs.getString("status"), rs.getInt("_count"));
		}
		if(result.size()>2)
			return Optional.of(result);
		return Optional.empty();
	}

}
