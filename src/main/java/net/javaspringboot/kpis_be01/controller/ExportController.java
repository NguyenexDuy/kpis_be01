package net.javaspringboot.kpis_be01.controller;

import jakarta.servlet.http.HttpServletResponse;
import net.javaspringboot.kpis_be01.service.AssessmentService;
import net.javaspringboot.kpis_be01.service.ExcelExportService;
import net.javaspringboot.kpis_be01.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/export")
public class ExportController {


    @Autowired
    private ExcelExportService excelExportService;
    @Autowired
    private UserSevice userSevice;
    @Autowired
    private AssessmentService assessmentService;

    //Xuất file Excel(User)
    @GetMapping("/exportUserList")
    public void exportUserList(HttpServletResponse response){
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachement; filename=users.xlsx";
        response.setHeader(headerKey,headerValue);//set header to fix error header multi strange symbol 1!&^#@L:D:L
        try {
            excelExportService.userExportToExcel(userSevice.getAllUser(),response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //Xuất file Excel(Staff)
    @GetMapping("/exportStaffList")
    public void exportToExcel(HttpServletResponse response) {
        response.setContentType("application/octet-stream");
        String headerKey = "Content-Disposition";
        String headerValue = "attachement; filename=staffs.xlsx";

        response.setHeader(headerKey,headerValue);//set header to fix error header multi strange symbol 1!&^#@L:D:L
        try {
            excelExportService.staffExportToExcel(assessmentService.getAllStaff(),response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
