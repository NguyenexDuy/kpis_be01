package net.javaspringboot.kpis_be01.controller;


import lombok.extern.slf4j.Slf4j;
import net.javaspringboot.kpis_be01.dto.request.KPIRoomListRequest;
import net.javaspringboot.kpis_be01.dto.request.NameListKPIRequest;
import net.javaspringboot.kpis_be01.dto.response.ApiResponse;
import net.javaspringboot.kpis_be01.dto.response.RankStaffResponse;
import net.javaspringboot.kpis_be01.dto.response.RoomTypeResponse;
import net.javaspringboot.kpis_be01.entity.KpiRoomData;
import net.javaspringboot.kpis_be01.entity.NameListKPI;
import net.javaspringboot.kpis_be01.entity.RoomType;
import net.javaspringboot.kpis_be01.entity.Staffs;
import net.javaspringboot.kpis_be01.service.AssessmentService;
import net.javaspringboot.kpis_be01.service.KPIRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static net.javaspringboot.kpis_be01.configuration.checkRoleAccount.hasRole;

@Slf4j
@RestController
@RequestMapping("/room")
public class KpiRoomController {

    @Autowired
    private AssessmentService assessmentService;
    @Autowired
    private KPIRoomService kpiRoomService;



    //xem chỉ số KPI các khoa/Phòng
    @GetMapping("/getAllKpiName")
    public ApiResponse<List<NameListKPI>> getAllKpiName(){
        List<NameListKPI> listKPIS=kpiRoomService.getALlKpiName();
            return ApiResponse.<List<NameListKPI>>builder()
                    .message("SUCCESS")
                    .result(listKPIS)
                    .code(1000)
                    .build();
    }
    // lấy phòng cho thêm các chỉ số kpi
    @GetMapping("/getAllRoomForAddKPI")
    public ApiResponse<List<RoomType>> getAllRoomForAddKPI(){
        List<RoomType> list=assessmentService.getAllRoomType();
        for(RoomType roomType:list){
            roomType.setRoom_name(roomType.getRoom_name()+" ("+roomType.getRoom_symbol()+")");
        }

        return  ApiResponse.<List<RoomType>>builder()
                .message("SUCCESS")
                .code(1000)
                .result(list)
                .build();
    }

    //Thêm các chỉ số kpi các khoa/phòng
    @PostMapping("/saveKpiName")
    public ApiResponse<String> saveKpiName(@RequestBody NameListKPIRequest request){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        Staffs staffs= assessmentService.getStaffByUserName(authentication.getName()).get();
        log.warn("dang thuc hien save");

        log.info("Username:{}",authentication.getName());
        log.warn("dang thuc hien save1");
        log.warn("roomResponsibleSymbol of request"+request.getRoom_responsible_symbol());
        String roomResponsibleSymbol=assessmentService.getRoomTypeBySymbol(request.getRoom_responsible_symbol()).getRoom_symbol();
        log.warn("roomResponsibleSymbol: "+roomResponsibleSymbol);

        String roomReportSymbol=assessmentService.getRoomTypeBySymbol(request.getRoom_report_symbol()).getRoom_symbol();
        log.warn("roomReportSymbol: "+roomReportSymbol);

        String room_name=assessmentService.getRoomTypeBySymbol(request.getRoom_responsible_symbol()).getRoom_name();
        log.warn("room_name: "+room_name);

        String roomReport_name=assessmentService.getRoomTypeBySymbol(request.getRoom_report_symbol()).getRoom_name();
        log.warn("roomReport_name: "+roomReport_name);

        log.warn("dang thuc hien save2");


        NameListKPI nameListKPI=new NameListKPI();
        nameListKPI.setKpi_name(request.getKpi_name().trim()+" ["+roomResponsibleSymbol+"]");
        nameListKPI.setKpi_type(request.getKpi_type());
        nameListKPI.setCompare_type(request.getCompare_type());
        nameListKPI.setRoom_name(room_name);
        nameListKPI.setRoom_responsible_symbol(roomResponsibleSymbol);
        nameListKPI.setRoom_report(roomReport_name);
        nameListKPI.setRoom_report_symbol(roomReportSymbol);
        nameListKPI.setNote(request.getNote());
        nameListKPI.setCreated_at(String.valueOf(LocalDate.now()));
        nameListKPI.setCreated_by(staffs.getUsername().getFullname());

            List<NameListKPI> nameListKPIList=kpiRoomService.getKpiNameByRoomResponsibleSymbol(roomResponsibleSymbol);
            if(nameListKPIList.size()>0){
                for(NameListKPI n:nameListKPIList){
                    if (nameListKPI.getKpi_name().equalsIgnoreCase(n.getKpi_name())){
                            return  ApiResponse.<String>builder()
                                    .code(1100)
                                    .result("Chỉ số cho khoa/phòng này đã có trong danh mục")
                                    .message("UNSUCCESS")
                                    .build();
                    }
                }
            }
        kpiRoomService.InsertOrUpdateNameKPI(nameListKPI);
        return ApiResponse.<String>builder()
                .code(1000)
                .message("SUCCESS")
                .build();
    }
    //nhập số liệu KPI cho Khoa/Phòng theo tháng

    @GetMapping("kpiInputment")
    public ApiResponse<KPIRoomListRequest>kpiInputment(@RequestParam(value = "month") int month,@RequestParam(value = "year") int year){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        Staffs staffs= assessmentService.getStaffByUserName(authentication.getName()).get();
        log.info("Username:{}",authentication.getName());
        List<NameListKPI> nameListKPIList=new ArrayList<>();
        if(hasRole("Admin")||hasRole("Input")){
            nameListKPIList=kpiRoomService.getALlKpiName();
        }else {
            nameListKPIList=kpiRoomService.getKpiNameByRoomReportSymbol(staffs.getUsername().getRoom_type().getRoom_symbol());
        }

        Iterator<NameListKPI> kpiNameIterator = nameListKPIList.iterator();
        while (kpiNameIterator.hasNext()){
            NameListKPI n = kpiNameIterator.next();
            KpiRoomData kCheck = kpiRoomService.getKpiRoomDataByNameDate(n.getKpi_name(),month+"/"+year);
            if (kCheck != null){
                kpiNameIterator.remove();
            }
        }
        KPIRoomListRequest kpiRoomAssessListRequest = new KPIRoomListRequest();
        for (NameListKPI kpi_name : nameListKPIList){
            kpiRoomAssessListRequest.getKpiRoomInputList().add(new KpiRoomData(kpi_name.getId(),
                    kpi_name.getKpi_name(),
                    kpi_name.getKpi_type(),
                    kpi_name.getRoom_report(),
                    kpi_name.getRoom_report_symbol(),
                    kpi_name.getRoom_name(),
                    kpi_name.getRoom_responsible_symbol(),
                    kpi_name.getNote(),//mô tả chỉ tiêu (goal)
                    kpi_name.getCompare_type(),
                    month,
                    year));
        }
        return  ApiResponse.<KPIRoomListRequest>builder()
                .result(kpiRoomAssessListRequest)
                .code(1000)
                .message("SUCCESS")
                .build();
    }

    //kết quả KPI khoa/phòng phụ trách
    @GetMapping("/getAllKPIRoomData")
    public ApiResponse<List<KpiRoomData>> getAllKPIRoomData(@RequestParam(value = "month") int month,@RequestParam(value = "year") int year){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        Staffs staffs= assessmentService.getStaffByUserName(authentication.getName()).get();
        log.info("Username:{}",authentication.getName());
        List<KpiRoomData> kpiRoomDataList=new ArrayList<>();
        if(hasRole("Admin") || hasRole("Director") || hasRole("Input")){
            kpiRoomDataList=kpiRoomService.showAllKpiRoomByDate(month+"/"+year);
        }
        else {
            kpiRoomDataList=kpiRoomService.getAllKpiRoomDataByRoomRepOrResByDate(staffs.getUsername().getRoom_type().getRoom_symbol(),month+"/"+year);

        }
        return  ApiResponse.<List<KpiRoomData>>builder()
                .result(kpiRoomDataList)
                .code(1000)
                .message("SUCCESS")
                .build();
    }
}
