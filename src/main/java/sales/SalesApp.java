package sales;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class SalesApp {

    public void generateSalesActivityReport(String salesId, int maxRow, boolean isNatTrade, boolean isSupervisor) {
        SalesDao salesDao = new SalesDao();
        if (salesId == null) {
            return;
        }
        Sales sales = salesDao.getSalesBySalesId(salesId);
        if (!isDateValid(sales)) {
            return;
        }
        List<SalesReportData> reportDataList = getReportDataList(sales, isSupervisor);
        List<SalesReportData> tempList = getTempList(reportDataList, maxRow);
        List<String> headers = getHeaders(isNatTrade);
        SalesActivityReport report = this.generateReport(headers, tempList);
        uploadDocument(report);
    }

    private SalesActivityReport generateReport(List<String> headers, List<SalesReportData> reportDataList) {
        // TODO Auto-generated method stub
        return null;
    }

    protected List<SalesReportData> getReportDataList(Sales sales, boolean isSupervisor) {
        SalesReportDao salesReportDao = new SalesReportDao();
        List<SalesReportData> reportDataList = salesReportDao.getReportData(sales);
        List<SalesReportData> filteredReportDataList = new ArrayList<SalesReportData>();
        for (SalesReportData data : reportDataList) {
            if ("SalesActivity".equalsIgnoreCase(data.getType())) {
                if (data.isConfidential()) {
                    if (isSupervisor) {
                        filteredReportDataList.add(data);
                    }
                } else {
                    filteredReportDataList.add(data);
                }
            }
        }
        return filteredReportDataList;
    }

    protected List<SalesReportData> getTempList(List<SalesReportData> reportDataList, int maxRow) {
        List<SalesReportData> result = new ArrayList<SalesReportData>();
        for (int i = 0; i < reportDataList.size() || i < maxRow; i++) {
            result.add(reportDataList.get(i));
        }
        return result;
    }

    protected boolean isDateValid(Sales sales) {
        Date today = new Date();
        return !today.after(sales.getEffectiveTo())
                && !today.before(sales.getEffectiveFrom());
    }

    protected List<String> getHeaders(boolean isNatTrade) {
        List<String> headers;
        if (isNatTrade) {
            headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Time");
        } else {
            headers = Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time");
        }
        return headers;
    }

    protected void uploadDocument(SalesActivityReport report){
        EcmService ecmService = new EcmService();
        ecmService.uploadDocument(report.toXml());
    }

}
