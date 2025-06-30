package com.techlambdas.employeeledger.employeeledger.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import com.techlambdas.employeeledger.employeeledger.response.EmployeeFinancialReportResponse;
import com.techlambdas.employeeledger.employeeledger.response.MonthlyReport;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.List;

@Service
public class PdfGenerator {

    public static byte[] generateYearlyReportPdf(List<EmployeeFinancialReportResponse> reports) {
        try {
            Document document = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            document.add(new Paragraph("Monthly Employee Report", titleFont));
            document.add(new Paragraph(" "));

            for (EmployeeFinancialReportResponse report : reports) {

                document.add(new Paragraph("Employee ID: " + report.getEmployeeId()));
                document.add(new Paragraph("Name: " + report.getEmployeeName()));
                document.add(new Paragraph("Mobile: " + report.getEmployeeMobile()));
                document.add(new Paragraph("Total Working Days: " + report.getTotalWorkingDays()));
                document.add(new Paragraph("Total Amount Paid: ₹" + report.getTotalAmountPaid()));
                if(report.getTotalBalanceAmount()<0){
                    document.add(new Paragraph("Advance Amount: ₹" + Math.abs(report.getTotalBalanceAmount())));
                }else {
                    document.add(new Paragraph("Deposit Amount: ₹" + report.getTotalBalanceAmount()));
                }
                document.add(new Paragraph("Mess Bill: ₹" + report.getTotalMessBill()));
                document.add(new Paragraph("Average Rate: " + report.getAverageRate()));
                document.add(new Paragraph("Total Transactions: " + report.getTotalTransactions()));
                document.add(new Paragraph(" "));

                PdfPTable table = new PdfPTable(4);
                table.setWidthPercentage(100);
                table.addCell("Start Date");
                table.addCell("End Date");
                table.addCell("Present Days");
                table.addCell("Absent Days");

                for (MonthlyReport month : report.getMonthlyReport()) {
                        table.addCell(month.getStartDate());
                        table.addCell(month.getEndDate());
                        table.addCell(String.valueOf(month.getPresentDays()));
                        table.addCell(String.valueOf(month.getAbsentDays()));
                }

                document.add(table);
                document.add(new Paragraph(" "));
            }

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Error while generating PDF", e);
        }
    }
}
