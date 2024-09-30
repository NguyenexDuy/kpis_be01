package net.javaspringboot.kpis_be01.controller;
import lombok.extern.slf4j.Slf4j;
import net.javaspringboot.kpis_be01.dto.response.ApiResponse;
import net.javaspringboot.kpis_be01.entity.*;
import net.javaspringboot.kpis_be01.service.AssessmentService;
import net.javaspringboot.kpis_be01.service.MethodService;
import net.javaspringboot.kpis_be01.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Year;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import static net.javaspringboot.kpis_be01.configuration.checkRoleAccount.hasRole;

@RestController
@RequestMapping("/staff")
@Slf4j
public class StaffController {

    @Autowired
    private AssessmentService assessmentService;
    @Autowired
    private UserSevice userSevice;

    @Autowired
    private MethodService methodService;


    private final int[] mark5 = {100, 95, 80, 50, 30, 0}; //điểm đánh giá theo 5 mức độ
    private final int[] markList = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1, 0};//điểm đánh giá theo thang 10
    private final int[] mark06810 = {10, 8, 6, 0};//điểm đánh giá theo thang 0,6,8,10
    private final int[] months = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
    private int currentYear = Year.now().getValue();
    //bộ chỉ số kpi cá nhân
    private static double cs1 = 0;// chỉ số tính theo điểm Kỷ luật lao động và khen thưởng
    private static double cs2 = 0;// chỉ số tính theo điểm Chất lượng thực hiện chuyên môn
    private static double cs3 = 0; // chỉ số tính theo điểm MĐ học tập và phát triển bản thân
    private static double team_work = 0;// chỉ số tính theo điểm Mức độ phối hợp trong hoạt động chuyên môn của khoa/phòng

    //added VDR (vice director) to memberlist for view leader and manager of HRD
    private static final List<String> leader_rank_list = List.of("VDR");

    //added CEF to memberlist for giám đốc bộ phận
    private static final List<String> cef_rank_list = List.of("CEF");

    //added DEA, MNG to memberlist for manager
    private static final List<String> manager_rank_list = List.of("DEA", "MNG");

    //added DEA, MNG to memberlist for manager, vice manager
    private static final List<String> vice_rank_list = List.of("VDE", "VMG");

    //added VDE, DDT, KTT, HSH, VMG to memberlist for manager assess captain
    private static final List<String> captain_rank_list = List.of("DDT", "KTT", "HSH");

    //added DDG, KTG, HSG, SCG, SPG to memberlist for captain assess group leader
    private static final List<String> group_rank_list = List.of("DDG", "KTG", "HSG", "SCG", "SPG");

    //added member to memberlist for captain assess group leader
    private static final List<String> member_rank_list = List.of("HCS", "SCS", "SPS");


    @GetMapping("/getAllMemberAssess")
    ApiResponse<List<MemberAssessment>> getAllMemberAssess() {
        return ApiResponse.<List<MemberAssessment>>builder()
                .result(assessmentService.getAllMemberAssess())
                .message("SUCCESS")
                .build();
    }


    //kết quả đánh giá cấp nhân viên
    @GetMapping("/getResultMemberAssess")
    ApiResponse<List<MemberAssessment>> getResultMemberAssess(@RequestParam(value = "month") int month, @RequestParam(value = "year") int year) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Staffs staffs = assessmentService.getStaffByUserName(authentication.getName()).get();
        log.info("Username:{}", authentication.getName());
        List<MemberAssessment> membersList = new ArrayList<>();

        if (hasRole("User")) {
            membersList = assessmentService.getAllResultMemberAssessmentByNameDate(staffs.getUsername().getUsername(), month + "/" + year);
        } else {
            membersList = assessmentService.getAllResultMemberAssessmentByRomeDate(staffs.getRoom_name(), staffs.getStaff_code(), month + "/" + year);
            if (hasRole("Group_Leader")) {//nếu role group leader thì chỉ show các thành viên chung nhóm với nhau
                Iterator<MemberAssessment> iterator = membersList.iterator();
                while (iterator.hasNext()) {
                    MemberAssessment m = iterator.next();
                    String group_work_member = assessmentService.getStaffByStaffCode(m.getStaff_code()).get().getGroup_work();
                    //nếu không cùng group work thì ẩn đi
                    if (!staffs.getGroup_work().equals(group_work_member)) {
                        iterator.remove();
                    }
                }
            }

        }
        return ApiResponse.<List<MemberAssessment>>builder()
                .result(membersList)
                .code(1000)
                .message("SUCCESS")
                .build();
    }

    //xem cấp trên đánh giá
    @GetMapping("/getManagerAssessYourSelf")
    ApiResponse<List<ManagerAssessMember>> getManagerAssessYourSelf() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Staffs staffs = assessmentService.getStaffByUserName(authentication.getName()).get();
        log.info("Username:{}", authentication.getName());
        List<ManagerAssessMember> mList = assessmentService.getAllRessultManagerAssessYourSelfByStaffCodeRoom(staffs.getStaff_code(), staffs.getRoom_name());

        return ApiResponse.<List<ManagerAssessMember>>builder()
                .message("SUCESS")
                .result(mList)
                .code(1000)
                .build();
    }

    //kết quả tự đánh giá
    @GetMapping("/getSeflAssessStaff")
    ApiResponse<List<SelfAssessStaff>> getSeflAssessStaffByStaffCode() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Staffs staffs = assessmentService.getStaffByUserName(authentication.getName()).get();
        log.info("Username:{}", authentication.getName());
        List<SelfAssessStaff> mList = assessmentService.getSelfAssessStaffByStaffCode(staffs.getStaff_code());
        return ApiResponse.<List<SelfAssessStaff>>builder()
                .message("SUCCESS")
                .code(1000)
                .result(mList)
                .build();
    }

    //kết quả nhân viên tự đánh giá
    @GetMapping("/getResultSelfAssessStaff")
    public ApiResponse<List<SelfAssessStaff>> getAllSelfAssessStaff(@RequestParam(value = "month") int month, @RequestParam(value = "year") int year) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String date = month + "/" + year;
        Staffs staffs = assessmentService.getStaffByUserName(authentication.getName()).get();
        log.info("Username:{}", authentication.getName());
        List<SelfAssessStaff> mList = new ArrayList<>();
        if (hasRole("Manager") || hasRole("Admin") || hasRole("Director")) {
            mList = assessmentService.getSelfAssessStaffByRoomDate(staffs.getRoom_name(), date);

        } else {
            mList = assessmentService.getSeflAssessStaffByUserDate(staffs.getUsername().getUsername(), date);

        }
        for(SelfAssessStaff self:mList){
            Staffs staffs1 = assessmentService.getStaffByStaffCode(self.getStaff_code()).get();
            self.setRank(staffs1.getUsername().getRank_code().getRank_name());
        }
        return ApiResponse.<List<SelfAssessStaff>>builder()
                .message("SUCCESS")
                .result(mList)
                .code(1000)
                .build();
    }

    //kết quả KPI cá nhân
    @GetMapping("/getResultPersonalKPI")
    public ApiResponse<List<ResultPersonalKPI>> getAllResultPersonalKPI(@RequestParam(value = "month") int month, @RequestParam(value = "year") int year) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        String date = month + "/" + year;
        Staffs staffs = assessmentService.getStaffByUserName(authentication.getName()).get();
        String name_rank = staffs.getUsername().getRank_code().getRank_name();
        String room_symbol = staffs.getUsername().getRoom_type().getRoom_symbol();
        List<ResultPersonalKPI> mlist = assessmentService.getListResultPersonalKPIByRoomSymbolDate(room_symbol, date);
        if (hasRole("Manager") && room_symbol.equalsIgnoreCase("hrd")) {
            mlist = assessmentService.getListResultPersonalKPIByDate(date);
        }
        Iterator<ResultPersonalKPI> iteratorR = mlist.iterator();
        while (iteratorR.hasNext()) {
            ResultPersonalKPI r = iteratorR.next();
            Staffs s = assessmentService.getStaffByStaffCode(r.getStaff_code()).get();
            //DONT show result Personal KPI for account was disabled or roles were specified AND GĐBP, MANAGER, VICE DIRECTOR ROLE
            /*remove result kpi cef, manager and leader to avoid error null obj*/
            if (manager_rank_list.contains(s.getRank_code()) || leader_rank_list.contains(s.getRank_code()) || cef_rank_list.contains(s.getRank_code())
                    /*loại đi record có role manager nhưng rank là cấp phó (áp cho 1 số khoa thay trưởng khoa kiêm nhiệm gđ) to avoid error null obj*/
                    || (vice_rank_list.contains(s.getRank_code()) && s.getUsername().getRole_name().getRolename().equalsIgnoreCase("manager"))
                    /*============================================*/
                    || s.getUsername().getRole_name().getRolename().equalsIgnoreCase("admin")
                    || s.getUsername().getRole_name().getRolename().equalsIgnoreCase("authorizer")
                    || s.getUsername().getRole_name().getRolename().equalsIgnoreCase("input")
                    || s.getUsername().isStatus() == false) {
                iteratorR.remove();

            }

        }
        for (ResultPersonalKPI result : mlist) {
            Staffs staffs1 = assessmentService.getStaffByStaffCode(result.getStaff_code()).get();
            User user = userSevice.getUserByUsername(staffs1.getUsername().getUsername());
            ManagerAssessMember member = assessmentService.getObjManagerAssessMemberByCodeRoomSymbolDate(result.getStaff_code(),
                    result.getRoom_symbol(), result.getDate());
            if (member_rank_list.contains(result.getRank_code())
                    || user.getRole_name().getRolename().equalsIgnoreCase("user")) {
                team_work = methodService.getMarkMemberAssessMemberAverageByStaffcodeRoomDate(result.getStaff_code(), result.getRoom_name(), result.getDate());
            } else {//nếu cấp quản lý thì lấy điểm bên bảng quản lý
                team_work = methodService.getMarkMemberAssessManagerAverageByStaffcodeRoomDate(result.getStaff_code(), result.getRoom_name(), result.getDate());
            }

            result.setCs1(((float) member.getKy_luat_khen_thuong_member()) / 100 * 100);
            result.setCs2((float) ((member.getChat_luong_chuyen_mon_member() + team_work) / 2) / 10 * 100);
            result.setCs3((float) (member.getBang_chung_hoc_tap_pt_member()) / 10 * 100);
            //làm tròn đến 2 chữ số
            result.setCs1(Math.round(result.getCs1() * 100) / 100d);
            result.setCs2(Math.round(result.getCs2() * 100) / 100d);
            result.setCs3(Math.round(result.getCs3() * 100) / 100d);
            cs1 = result.getCs1() * user.getRank_code().getGroup_rank_staff().getTs1();
            cs2 = result.getCs2() * user.getRank_code().getGroup_rank_staff().getTs2();
            cs3 = result.getCs3() * user.getRank_code().getGroup_rank_staff().getTs3();
            double resultKPI = Math.round(((cs1 + cs2 + cs3) / 100) * 100) / 100d;//làm tròn đến 2 chữ số
            result.setTeam_mark_assess(team_work);
            result.setResultKPI(resultKPI);
//            result.setRank_code(staffs1.getRank_code());
            result.setRank_code(staffs1.getUsername().getRank_code().getRank_name());
            assessmentService.SaveOrUpdateResultSelfKPI(result);
        }

        return ApiResponse.<List<ResultPersonalKPI>>builder()
                .result(mlist)
                .code(1000)
                .message("SUCCESS")
                .build();

    }
}
