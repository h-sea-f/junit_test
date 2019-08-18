package sales;

import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(JMockit.class)
public class SalesAppTest {

	@Test
	public void should_return_true_when_call_isDateValid_given_sale() throws ParseException {
		SalesApp salesApp = spy(new SalesApp());
		Sales sales = mock(Sales.class);
		when(sales.getEffectiveTo()).thenReturn(new SimpleDateFormat("YYYY-MM-DD hh:mm:ss").parse("2020-01-01 10:10:10"));
		when(sales.getEffectiveFrom()).thenReturn(new SimpleDateFormat("YYYY-MM-DD hh:mm:ss").parse("2010-01-01 10:10:10"));
		assertEquals(true,salesApp.isDateValid(sales));
	}

	@Test
	public void should_return_Arrays_when_call_getHeaders(){
		SalesApp salesApp = spy(new SalesApp());
		List<String> headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
		assertEquals(salesApp.getHeaders(true),headers);
	}

	@Test
	public void test_generateSalesActivityReport() throws ParseException {
		SalesApp salesApp = spy(new SalesApp());
		Sales sales = mock(Sales.class);
		SalesDao salesDao = mock(SalesDao.class);
		SalesReportDao salesReportDao = mock(SalesReportDao.class);
		SalesActivityReport salesActivityReport = mock(SalesActivityReport.class);
		new MockUp<SalesApp>() {
			@Mock
			protected SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
				// TODO Auto-generated method stub
				return salesActivityReport;
			}
		};
		EcmService ecmService = mock(EcmService.class);
		when(sales.getEffectiveTo()).thenReturn(new SimpleDateFormat("YYYY-MM-DD hh:mm:ss").parse("2020-01-01 10:10:10"));
		when(sales.getEffectiveFrom()).thenReturn(new SimpleDateFormat("YYYY-MM-DD hh:mm:ss").parse("2010-01-01 10:10:10"));
		when(salesDao.getSalesBySalesId(any())).thenReturn(sales);
		when(salesReportDao.getReportData(sales)).thenReturn(new ArrayList<>());
		when(salesActivityReport.toXml()).thenReturn("");
		doReturn(salesDao).when(salesApp).getSalesDao();
		doReturn(salesReportDao).when(salesApp).getSalesReportDao();
		doReturn(ecmService).when(salesApp).getEcmService();
		salesApp.generateSalesActivityReport("1", 0, true, true);
		verify(salesApp, times(1)).getSales("1");
		verify(salesApp, times(1)).getReportDataList(sales, true);
		verify(salesApp, times(1)).uploadDocument(salesActivityReport);
	}
}
