package vttp2022.paf.assessment.eshop.respositories;

import java.util.zip.DataFormatException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
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
		if(addedOrder<1||addedLineitem<1)
			throw new Exception();
		return true;	
	}

}
