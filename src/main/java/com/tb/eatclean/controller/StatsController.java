package com.tb.eatclean.controller;

import com.google.gson.Gson;
import com.tb.eatclean.entity.ResponseDTO;
import com.tb.eatclean.entity.bill.Bill;
import com.tb.eatclean.entity.bill.BillStatus;
import com.tb.eatclean.entity.carts.Cart;
import com.tb.eatclean.entity.user.User;
import com.tb.eatclean.repo.BillRepo;
import com.tb.eatclean.repo.CategoryRepo;
import com.tb.eatclean.service.user.UserService;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

@AllArgsConstructor
@Data
class StatsCategory implements Serializable {
    String label;
    long revenue;
}

@Data
@AllArgsConstructor
class StatsCategoryWeek {
    String label;
    long revenue;
    LocalDate date;
}

@Data
@AllArgsConstructor
class ResponseExcel {
    String username;
    String food;
    long foodId;
    String category;
    int quantity;
}

@Data
@AllArgsConstructor
class StatsRevenueWeek {
    long revenue;
    LocalDate date;
}

@RestController
@CrossOrigin("*")
@RequestMapping("/api/v1/stats")
public class StatsController {
    @Autowired
    private BillRepo billRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryRepo categoryRepo;

    @GetMapping("revenue")
    public ResponseEntity<ResponseDTO<long []>> revenues (){
        List<Object[]> list = billRepo.statsRevenue(LocalDate.now().getYear());

        long[] monthData = new long[12];

        for (int i = 0; i < 12; i++) {
            monthData[i] = 0;
            for (Object[] m: list) {
                if (Objects.equals(m[0], i + 1)) {
                    monthData[i] = Long.parseLong(m[1].toString());
                    break;
                }
            }
        }
        return ResponseEntity.ok(new ResponseDTO<>(monthData, "200", "Success", true));
    }

    @GetMapping("category/year")
    public ResponseEntity<ResponseDTO<?>> categoryYear (){
        List<Object[]> list = this.billRepo.statsCategory(LocalDate.now().getYear());

        List<Object[]> categories = this.categoryRepo.getCategorySelect();

        List<StatsCategory> statsCategories = new ArrayList<>();

        for(Object[] category: categories) {
            StatsCategory statsCategory = new StatsCategory(category[1].toString(), 0);


            for (Object[] c: list) {
                if(c[0] == category[0]) {
                    statsCategory.setRevenue((long) Double.parseDouble(c[1].toString()));
                }
            }

            statsCategories.add(statsCategory);
        }

        return ResponseEntity.ok(new ResponseDTO<>(statsCategories, "200", "Success", true));
    }

    @GetMapping("category/week")
    public ResponseEntity<ResponseDTO<List<StatsCategoryWeek>>> categoryWeek (){

        List<Object[]> categories = this.categoryRepo.getCategorySelect();

        List<StatsCategoryWeek> statsCategories = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        List<LocalDate> dateList = new ArrayList<>();
        for (int i = 0; i< 7; i++) {
            dateList.add(currentDate.minusDays(i));
        }

        for (LocalDate date: dateList) {
            int day = date.getDayOfMonth();
            int month = date.getMonthValue();
            int year = date.getYear();

            List<Object[]> list = this.billRepo.statsCategory(year, month, day);

            for(Object[] category: categories) {
                StatsCategoryWeek statsCategory = new StatsCategoryWeek(category[1].toString(), 0, date);

                for (Object[] c: list) {
                    if(c[0] == category[0]) {
                        statsCategory.revenue = Long.parseLong(c[1].toString());
                    }
                }

                statsCategories.add(statsCategory);
            }
        }

        return ResponseEntity.ok(new ResponseDTO<>(statsCategories, "200", "Success", true));
    }

    @GetMapping("revenue/week")
    public ResponseEntity<ResponseDTO<List<StatsRevenueWeek>>> revenueWeek (){
        List<StatsRevenueWeek> statsRevenue = new ArrayList<>();

        LocalDate currentDate = LocalDate.now();
        List<LocalDate> dateList = new ArrayList<>();
        for (int i = 0; i< 7; i++) {
            dateList.add(currentDate.minusDays(i));
        }

        for (LocalDate date: dateList) {
            int day = date.getDayOfMonth();
            int month = date.getMonthValue();
            int year = date.getYear();

            long revenue = 0;

            Long stats = this.billRepo.statsRevenue(year, month, day);

            if(stats != null) revenue = stats;
           statsRevenue.add(new StatsRevenueWeek(revenue, date));
        }

        return ResponseEntity.ok(new ResponseDTO<>(statsRevenue, "200", "Success", true));
    }


    @PostMapping("/export/report")
    public void export(@RequestBody List<LocalDate> range, HttpServletResponse response) {
        List<Bill> rs =billRepo.findByBillStatusAndUpdateAtIsBetween(BillStatus.COMPLETED, range.get(0).atStartOfDay(), range.get(1).atStartOfDay());
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Báo cáo");
        CellStyle cellStyle = createCellStyle(sheet, workbook, false, false);
        Row rowTime = sheet.createRow( 0);
        Cell cell = rowTime.createCell(0);
        cell.setCellValue("Ngày lập: " + LocalDateTime.now().toString());
        cell.setCellStyle(createCellStyle(sheet, workbook, true, false));

        rowTime = sheet.createRow(2);
        cell = rowTime.createCell(0);
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 15);
        CellStyle cellStyle1 = sheet.getWorkbook().createCellStyle();
        cellStyle1.setFont(font);
        cellStyle1.setBorderBottom(BorderStyle.THIN);
        cellStyle1.setBorderLeft(BorderStyle.THIN);
        cellStyle1.setBorderRight(BorderStyle.THIN);
        cellStyle1.setBorderTop(BorderStyle.THIN);
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);
        cell.setCellValue("Từ ngày: " + range.get(0) + " đến " + range.get(1) );
        cell.setCellStyle(cellStyle1);
        writeHeaderForRevenue(sheet, 1, "Báo cáo doanh thu theo thời gian");

        int sum = 0;
        int count = 1;

        // Thêm dữ liệu
        for(int i = 0; i < rs.size() ; i++) {
            Bill bill = rs.get(i);
            for (int j = 0; j < bill.getCarts().size(); j++) {
                Cart cart = bill.getCarts().get(j);
                Row dataRow = sheet.createRow(count + 4);
                cell = dataRow.createCell(0);
                cell.setCellValue(count);
                cell.setCellStyle(cellStyle);
                cell = dataRow.createCell(1);
                cell.setCellValue(bill.getUsername());
                cell.setCellStyle(cellStyle);

                cell = dataRow.createCell(2);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(cart.getFoods().getSlug());

                cell = dataRow.createCell(3);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(cart.getFoods().getName());

                cell = dataRow.createCell(4);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(cart.getQuantity());

                cell = dataRow.createCell(5);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(cart.getFoods().getPrice());

                cell = dataRow.createCell(6);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(bill.getUpdateAt().toLocalDate().toString());

                cell = dataRow.createCell(7);
                cell.setCellStyle(cellStyle);
                cell.setCellValue(cart.getFoods().getPrice() * cart.getQuantity());

                count ++;
            }
        }

        Row row = sheet.createRow(count + 5);
        cell = row.createCell(6);
        cell.setCellStyle(createCellStyle(sheet, workbook, true, true));
        cell.setCellValue("Người lập báo cáo");
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        System.out.println(principal);
        User user = null;
        if (principal instanceof String && !((String) principal).isEmpty()) {
            user = userService.findByEmail((String) principal);
        }

        row = sheet.createRow(count + 6);
        cell = row.createCell(6);

        cell.setCellStyle(createCellStyle(sheet, workbook, true, false));
        if (user != null) {
            cell.setCellValue(user.getName());
        } else {
            cell.setCellValue("Nguyễn Thị Tuyết");
        }

        for (int columnIndex = 0; columnIndex < 20; columnIndex++) {
            if (columnIndex == 0) {
                sheet.setColumnWidth(columnIndex, 2000);
            } else {
                sheet.autoSizeColumn(columnIndex);
                sheet.setColumnWidth(columnIndex, sheet.getColumnWidth(columnIndex) + 2000);
            }
            sheet.setDefaultRowHeight((short) 450);
        }



        sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 7));
        sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 7));

        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setHeader("Content-Disposition", "attachment; filename=revenue-service.xlsx");



        try {
            workbook.write(response.getOutputStream());
            workbook.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
    private static void writeHeaderForRevenue(Sheet sheet, int rowIndex, String header) {
        // create CellStyle
        CellStyle cellStyle = createStyleForHeader(sheet);
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 15); // font size

        CellStyle cellStyle1 = sheet.getWorkbook().createCellStyle();
        cellStyle1.setFont(font);

        Row row1 = sheet.createRow(rowIndex);
        row1.setHeight((short) 550);
        Cell cell2 = row1.createCell(0);
        cell2.setCellValue(header.toUpperCase());
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);
        cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);
        cell2.setCellStyle(cellStyle1);
        cellStyle1.setBorderBottom(BorderStyle.THIN);
        cellStyle1.setBorderLeft(BorderStyle.THIN);
        cellStyle1.setBorderRight(BorderStyle.THIN);
        cellStyle1.setBorderTop(BorderStyle.THIN);
        Row row = sheet.createRow(rowIndex + 3);

        Cell cell = row.createCell(0);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("STT ");

        cell = row.createCell(1);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Tên khách hàng");

        cell = row.createCell(2);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Mã sản phẩm");

        cell = row.createCell(3);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Tên sản phẩm");

        cell = row.createCell(4);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Số lượng");

        cell = row.createCell(5);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Đơn giá");

        cell = row.createCell(6);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Ngày mua");

        cell = row.createCell(7);
        cell.setCellStyle(cellStyle);
        cell.setCellValue("Thành tiền");

    }


    private static CellStyle createStyleForHeader(Sheet sheet) {
        Font font = sheet.getWorkbook().createFont();
        font.setFontName("Times New Roman");
        font.setBold(true);
        font.setFontHeightInPoints((short) 14); // font size
        font.setColor(IndexedColors.WHITE.getIndex());

        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setFont(font);
        cellStyle.setFillForegroundColor(IndexedColors.GREEN.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        return cellStyle;
    }


    private static void autosizeColumn(Sheet sheet, int sizeMerge) {
        for (int columnIndex = 0; columnIndex < 20; columnIndex++) {
            if (columnIndex != 11) {
                sheet.setColumnWidth(columnIndex, 4000);
            } else {
                sheet.autoSizeColumn(columnIndex);
            }

            sheet.setDefaultRowHeight((short) 450);
        }


        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, sizeMerge));

    }

    private CellStyle createCellStyle(Sheet sheet, Workbook workbook, boolean withoutBorder, boolean isBold) {
        short format = (short)BuiltinFormats.getBuiltinFormat("#,##0");

        Font font = sheet.getWorkbook().createFont();
        font.setBold(isBold);
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 14); // font size

        //Create CellStyle
        CellStyle cellStyleFormatNumber = workbook.createCellStyle();
        cellStyleFormatNumber.setDataFormat(format);
        cellStyleFormatNumber.setFont(font);
        if (!withoutBorder) {
            cellStyleFormatNumber.setBorderBottom(BorderStyle.THIN);
            cellStyleFormatNumber.setBorderLeft(BorderStyle.THIN);
            cellStyleFormatNumber.setBorderRight(BorderStyle.THIN);
            cellStyleFormatNumber.setBorderTop(BorderStyle.THIN);
        }

        return cellStyleFormatNumber;
    }
}
