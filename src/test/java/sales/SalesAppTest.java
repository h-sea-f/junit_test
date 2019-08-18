package sales;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class SalesAppTest {

//	@Test
//	public void testGenerateReport() {
//
//		SalesApp salesApp = new SalesApp();
//		salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);
//
//	}
	@Test
	public void should_return_false_when_call_isDateValid_given_sale(){
		SalesApp salesApp = spy(new SalesApp());
		Sales sales = mock(Sales.class);
		when(sales.getEffectiveTo()).thenReturn(new Date());
		assertEquals(false,salesApp.isDateValid(sales));
	}
}
