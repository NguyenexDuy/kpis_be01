package net.javaspringboot.kpis_be01.service;


import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import net.javaspringboot.kpis_be01.entity.Staffs;
import net.javaspringboot.kpis_be01.entity.User;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class ExcelExportService {

    public void userExportToExcel(List<User> data, HttpServletResponse response) throws IOException{
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet 1");
        int rowNum = 0;
        //create header for column excel
        Row header = sheet.createRow(rowNum++);
        header.createCell(0).setCellValue("STT");
        header.createCell(1).setCellValue("Họ tên");
        header.createCell(2).setCellValue("Username");
        header.createCell(3).setCellValue("Cấp nhân sự");
        header.createCell(4).setCellValue("Roles");
        header.createCell(5).setCellValue("Email (nếu có)");
        header.createCell(6).setCellValue("Khoa/Phòng");
        header.createCell(7).setCellValue("Mã Khoa/Phòng");
        header.createCell(8).setCellValue("Nhóm làm việc");
        header.createCell(9).setCellValue("ID record");
        header.createCell(10).setCellValue("Status");
        for (User obj : data) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            //không xuất user của 3 role này
            if (!obj.getRole_name().getRolename().equalsIgnoreCase("admin") &&
                    !obj.getRole_name().getRolename().equalsIgnoreCase("input") &&
                    !obj.getRole_name().getRolename().equalsIgnoreCase("authorizer")) {
                row.createCell(colNum++).setCellValue(data.indexOf(obj)+1);
                row.createCell(colNum++).setCellValue(obj.getFullname());
                row.createCell(colNum++).setCellValue(obj.getUsername());
                row.createCell(colNum++).setCellValue(obj.getRank_code().getRank_name());
                row.createCell(colNum++).setCellValue(obj.getRole_name().getRolename());
                row.createCell(colNum++).setCellValue(obj.getEmail());
                row.createCell(colNum++).setCellValue(obj.getRoom_type().getRoom_name());
                row.createCell(colNum++).setCellValue(obj.getRoom_type().getRoom_symbol());
                row.createCell(colNum++).setCellValue(obj.getGroup_work());
                row.createCell(colNum++).setCellValue(obj.getId());
                row.createCell(colNum++).setCellValue(obj.isStatus()==true?"Yes":"No");
                // Add more cells as needed for your object's fields
            }
        }
        //dùng ServletOutputStream và HttpServletResponse for export file to excel without need create folder
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }

    //export staffs list
    public void staffExportToExcel(List<Staffs> data, HttpServletResponse response) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Sheet 1");

        int rowNum = 0;
        for (Staffs obj : data) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            row.createCell(colNum++).setCellValue(obj.getStaff_code());
            row.createCell(colNum++).setCellValue(obj.getFullname());
            row.createCell(colNum++).setCellValue(obj.getEmail());
            row.createCell(colNum++).setCellValue(obj.getRoom_name());
            // Add more cells as needed for your object's fields
        }

        //dùng ServletOutputStream và HttpServletResponse for export file to excel without need create folder
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
