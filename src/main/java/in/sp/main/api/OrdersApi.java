package in.sp.main.api;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;

import in.sp.main.entities.Orders;
import in.sp.main.services.OrdersService;



@RestController
@RequestMapping("/api")
public class OrdersApi {
	
	@Autowired
	private OrdersService oredersService;
	
	@Value("${razorpay.key.id}")
    private String keyId;

    @Value("${razorpay.key.secret}")
    private String keySecret;
	
	
	@PostMapping("/storeOrderDetails")
	public ResponseEntity<String> storeUserOrederDetails(@RequestBody Orders orders) throws RazorpayException {
		
		RazorpayClient razorpayClient = new RazorpayClient(keyId, keySecret);

		

		JSONObject orderRequest = new JSONObject();
		
		orderRequest.put("amount",orders.getCourseAmount());
		orderRequest.put("currency","INR");
		orderRequest.put("receipt","rcpt_id_"+System.currentTimeMillis());
		
		Order order = razorpayClient.orders.create(orderRequest);
		
		System.out.println(order);
		
		String OrderId = order.get("id");
		//orders.setOrederId(OrderId);
		orders.setOrderId(OrderId);
		
		
		oredersService.storeUserOrders(orders);
		return ResponseEntity.ok("Orders Details stored Successfully");
		
	}
	
	
}
