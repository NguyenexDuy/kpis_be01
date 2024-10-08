package net.javaspringboot.kpis_be01.controller;
import lombok.extern.slf4j.Slf4j;
import net.javaspringboot.kpis_be01.dto.request.ManagerAssesListRequest;
import net.javaspringboot.kpis_be01.dto.response.ApiResponse;
import net.javaspringboot.kpis_be01.entity.*;
import net.javaspringboot.kpis_be01.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static net.javaspringboot.kpis_be01.configuration.checkRoleAccount.hasRole;

@RestController
@RequestMapping("/manager")
@Slf4j
public class ManagerController {

    @Autowired
   private AssessmentService assessmentService;

    //added VDR (vice director) to memberlist for view leader and manager of HRD
    private static final List<String> leader_rank_list = List.of("VDR");

    //added CEF to memberlist for giám đốc bộ phận
    private static final List<String> cef_rank_list = List.of("CEF");

    //added DEA, MNG to memberlist for manager
    private static final List<String> manager_rank_list = List.of("DEA","MNG");

    //added DEA, MNG to memberlist for manager, vice manager
    private static final List<String> vice_rank_list = List.of("VDE","VMG");

    //added VDE, DDT, KTT, HSH, VMG to memberlist for manager assess captain
    private static final List<String> captain_rank_list = List.of("DDT","KTT","HSH");

    //added DDG, KTG, HSG, SCG, SPG to memberlist for captain assess group leader
    private static final List<String> group_rank_list = List.of("DDG","KTG","HSG","SCG","SPG");
    //added member to memberlist for captain assess group leader
    private static final List<String> member_rank_list = List.of("HCS","SCS","SPS");


    //kết quả quản lý đánh giá nhân viên
    @GetMapping("/getResultManagerAssessMember")
     ApiResponse<List<ManagerAssessMember>> getResultManagerAssessMember(@RequestParam(value = "month") int month, @RequestParam(value = "year") int year){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        Staffs staffs= assessmentService.getStaffByUserName(authentication.getName()).get();
        log.info("Username:{}",authentication.getName());
        List<ManagerAssessMember> result=  assessmentService.getAllResultManagerAssesMemberByRoom_Date(staffs.getRoom_name(),month+"/"+year);

        Iterator<ManagerAssessMember> iterator = result.iterator();

        while (iterator.hasNext()){
            ManagerAssessMember m=iterator.next();
            //rank nhân viên đc đánh giá
            String rank = assessmentService.getStaffByStaffCode(m.getStaff_code()).get().getRank_code();
            if (hasRole("Manager")){
                if (manager_rank_list.contains(rank)){
                    iterator.remove();
                }
            } else if (hasRole("Vice_Manager")) {
                if (manager_rank_list.contains(rank) || vice_rank_list.contains(rank)){
                    iterator.remove();
                }
            } else if (hasRole("Captain")) {
                if (manager_rank_list.contains(rank) || vice_rank_list.contains(rank) ||
                        captain_rank_list.contains(rank)){
                    iterator.remove();
                }
            } else if (hasRole("Group_Leader")) {
                if (manager_rank_list.contains(rank) || vice_rank_list.contains(rank) ||
                        captain_rank_list.contains(rank) ||  group_rank_list.contains(rank)){
                    iterator.remove();
                }
            }
        }
        return ApiResponse.<List<ManagerAssessMember>>builder()
                .result(result)
                .code(1000)
                .message("SUCCESS")
                .build();
    }

    //kết quả đánh giá cấp quản lý

    // loop qua cái mảng -> lấy từng positon -> query table rank_staff để lấy ra tên rank_name== position
    // gán cho postiton== rank_name xong trả ra
    @GetMapping("/getResultMemberAssessManager")
    ApiResponse<List<MemberAssessManager>> getResultMemberAssessManager(@RequestParam(value = "month") int month,@RequestParam(value = "year") int year){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        Staffs staffs= assessmentService.getStaffByUserName(authentication.getName()).get();
        log.info("Username:{}",authentication.getName());
        var result=assessmentService.getAllResultMemberAssessManager(staffs.getRoom_name(),month+"/"+year);
        Iterator<MemberAssessManager> iterator = result.iterator();

        while (iterator.hasNext()){
            MemberAssessManager m=iterator.next();
            //rank nhân viên đc đánh giá
            String rank = assessmentService.getStaffByStaffCode(m.getStaff_code()).get().getRank_code();
            if (hasRole("Manager")){
                if (manager_rank_list.contains(rank)){
                    iterator.remove();
                }
            } else if (hasRole("Vice_Manager")) {
                if (manager_rank_list.contains(rank) || vice_rank_list.contains(rank)){
                    iterator.remove();
                }
            } else if (hasRole("Captain")) {
                if (manager_rank_list.contains(rank) || vice_rank_list.contains(rank) ||
                        captain_rank_list.contains(rank)){
                    iterator.remove();
                }
            } else if (hasRole("Group_Leader")) {
                if (manager_rank_list.contains(rank) || vice_rank_list.contains(rank) ||
                        captain_rank_list.contains(rank) ||  group_rank_list.contains(rank)){
                    iterator.remove();
                }
            }
        }
        if(hasRole("User")){
            result=assessmentService.getListMembersAssessManagerByUsernameRoom(staffs.getUsername().getUsername(),staffs.getRoom_name());

        }

        for(MemberAssessManager selfMana:result){
            Staffs staffs1=assessmentService.getStaffByStaffCode(selfMana.getStaff_code()).get();
            selfMana.setRank_manager(staffs1.getUsername().getRank_code().getRank_name());
            Staffs staffs2=assessmentService.getStaffByUserName(selfMana.getUnique_username()).get();
            selfMana.setPosition(staffs2.getUsername().getRank_code().getRank_name());
        }

        return  ApiResponse.<List<MemberAssessManager>>builder()
                .result(result)
                .message("SUCCESS")
                .build();
    }


    // Ý kiến nhân viên đánh giá lẫn nhau
    @GetMapping("/getResultMemberOpinion")
    public ApiResponse<List<MemberAssessment>> getResultMemberOpinion(@RequestParam(value = "month") int month,@RequestParam(value = "year") int year)
    {
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        Staffs staffs= assessmentService.getStaffByUserName(authentication.getName()).get();
        log.info("Username:{}",authentication.getName());
        List<MemberAssessment> membersList = new ArrayList<>();

        if(hasRole("Manager")){
        membersList=assessmentService.getListMemberAssessByRoomAndMonthYear(staffs.getRoom_name(),month+"/"+year);
            Iterator<MemberAssessment> iterator = membersList.iterator();
            while (iterator.hasNext()){
                MemberAssessment m = iterator.next();
                if (m.getNote_desc().isBlank()){
                    iterator.remove();
                }
            }
        }
        for(MemberAssessment mem:membersList){
            Staffs staffs1=assessmentService.getStaffByStaffCode(mem.getStaff_code()).get();
            mem.setPosition(staffs1.getUsername().getRank_code().getRank_name());

        }
        return ApiResponse.<List<MemberAssessment>>builder()
                .message("SUCCESS")
                .result(membersList)
                .code(1000)
                .build();
    }

    //kết quả tự đánh giá
    @GetMapping("/getResultSelfAsscessManger")
    public ApiResponse<List<SelfAssessManager>> getAllSelfAssessManager(@RequestParam(value = "month") int month,@RequestParam(value = "year") int year){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        String date=month+"/"+year;
        Staffs staffs= assessmentService.getStaffByUserName(authentication.getName()).get();
        log.info("Username:{}",authentication.getName());
        List<SelfAssessManager> selfAssessManagersList=assessmentService.getAllSelfAssessManager(staffs.getRoom_name(),date);
        if (hasRole("Manager") || hasRole("Admin") || hasRole("Director")){
            if(staffs.getUsername().getRoom_type().getRoom_symbol().equalsIgnoreCase("hrd")){
                selfAssessManagersList=assessmentService.getAllSelfAssessManagerByDate(date);
                Iterator<SelfAssessManager> iterator_manager = selfAssessManagersList.iterator();
                while (iterator_manager.hasNext()){
                    SelfAssessManager self = iterator_manager.next();
                    Staffs s = assessmentService.getStaffByStaffCode(self.getStaff_code()).get();
                    //loại ra các user ko có role manager
                    if (!s.getUsername().getRole_name().getRolename().equalsIgnoreCase("manager")){
                        iterator_manager.remove();
                    }
                }
            }
        }
        for(SelfAssessManager selfMana:selfAssessManagersList){
            Staffs staffs1=assessmentService.getStaffByStaffCode(selfMana.getStaff_code()).get();
            selfMana.setRank(staffs1.getUsername().getRank_code().getRank_name());
        }
        return ApiResponse.<List<SelfAssessManager>>builder()
                .code(1000)
                .message("SUCCESS")
                .result(selfAssessManagersList)
                .build();
    }


    //kết quả đánh giá lãnh đạo
    //xem BGĐ đánh giá
    @GetMapping("/getResultLeaderAssessManager")
    public ApiResponse<List<LeaderAssessManager>> getResultLeaderAssessManager(){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        Staffs staffs= assessmentService.getStaffByUserName(authentication.getName()).get();
        log.info("Username:{}",authentication.getName());

        List<LeaderAssessManager> mlist=assessmentService.getListLeaderAssessManagerByRoomDate(staffs.getRoom_name());

        return  ApiResponse.<List<LeaderAssessManager>>builder()
                .result(mlist)
                .code(1000)
                .message("SUCCESS")
                .build();
    }

    //đánh giá lãnh đạo quản lý trực tiếp
    @GetMapping("/getResultmanagerLeaderAssessment")
    public ApiResponse<ManagerAssesListRequest> getResultmanagerLeaderAssessment(@RequestParam(value = "month") int month,@RequestParam(value = "year") int year){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        String date=month+"/"+year;
        Staffs staffs= assessmentService.getStaffByUserName(authentication.getName()).get();
        log.info("Username:{}",authentication.getName());
        List<Staffs> leadersList = new ArrayList<>();
        //thêm leader phụ trách khoa/phòng của user đó vào phần đánh giá
        leadersList.add(staffs);

        //loại đi lãnh đạo đã đc quản lý đánh giá trong tháng này rồi
        Iterator<Staffs> iterator = leadersList.iterator();

        while (iterator.hasNext()){
            Staffs s = iterator.next();
            ManagerAssessLeader managerAssessLeader=assessmentService.getObjManagerAssessLeaderByUserNameCodeRoomDate(staffs.getUsername().getUsername(),s.getStaff_code(),s.getRoom_name(),date);
            if (managerAssessLeader != null){
                iterator.remove();
            }
        }
        ManagerAssesListRequest managerAssessList = new ManagerAssesListRequest();
        for (Staffs staffss : leadersList) {
            managerAssessList.getManagerAssessLeaderList().add(new ManagerAssessLeader(
                    staffss.getStaff_code(), staffss.getFullname(), staffss.getUsername()));
        }
        return ApiResponse.<ManagerAssesListRequest>builder()
                .message("SUCCESS").code(1000)
                .result(managerAssessList)
                .build();
    }



}
