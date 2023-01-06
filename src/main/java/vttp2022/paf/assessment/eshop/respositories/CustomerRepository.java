package vttp2022.paf.assessment.eshop.respositories;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import vttp2022.paf.assessment.eshop.models.Customer;

@Repository
public class CustomerRepository {

	@Autowired
	private JdbcTemplate temp;

	// You cannot change the method's signature
	public Optional<Customer> findCustomerByName(String name) {
		// TODO: Task 3
		Customer c = new Customer();
		SqlRowSet rs = temp.queryForRowSet("SELECT * FROM ESHOP.CUSTOMERS WHERE NAME = '?'", name);
		if (rs.next()) {
			c.setName(rs.getString("name"));
			c.setAddress(rs.getString("address"));
			if (!rs.getString("email").isEmpty())
				c.setEmail(rs.getString("email"));
			return Optional.of(c);
		}
		return Optional.empty();
	}
}
