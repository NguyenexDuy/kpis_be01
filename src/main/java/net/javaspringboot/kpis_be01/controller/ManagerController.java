package net.javaspringboot.kpis_be01.controller;
import lombok.extern.slf4j.Slf4j;
import net.javaspringboot.kpis_be01.dto.request.ManagerAssesListRequest;
import net.javaspringboot.kpis_be01.dto.response.ApiResponse;
import net.javaspringboot.kpis_be01.entity.*;
import net.javaspringboot.kpis_be01.service.AssessmentService;
import net.javaspringboot.kpis_be01.service.MethodService;
import net.javaspringboot.kpis_be01.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.*;

import static net.javaspringboot.kpis_be01.configuration.checkRoleAccount.hasRole;

@RestController
@RequestMapping("/manager")
@Slf4j
public class ManagerController {

    @Autowired
   private AssessmentService assessmentService;
    @Autowired
    private UserSevice userSevice;
    @Autowired
    private MethodService methodService;

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
        Staffs staffs1=assessmentService.getStaffByUserName(staffs.getUsername().getRoom_type().getUser().getUsername()).get();
        leadersList.add(staffs1);

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

//        for(ManagerAssessLeader item: managerAssessList.getManagerAssessLeaderList()){
//            item.getUsername().getRoom_type().setUnique_username();
//        }
        return ApiResponse.<ManagerAssesListRequest>builder()
                .message("SUCCESS").code(1000)
                .result(managerAssessList)
                .build();
    }

    //tự đánh giá bản thân(Manager)
    @PostMapping("/saveSelfAssessManager")
    public  ApiResponse<String> saveSelfAssessManager(@RequestBody SelfAssessManager request){
        log.info("dang thuc hien tu danh gia");
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        Staffs staffs=assessmentService.getStaffByUserName(authentication.getName()).get();
        List<SelfAssessManager> selfAssessManagerList=assessmentService.findAllSelfAssessManagerByUserDate(staffs.getUsername().getUsername(),request.getMonth()+"/"+request.getYear());
        for(SelfAssessManager s:selfAssessManagerList){
            if(request.getId()==null){
                if(s.getMonth()==request.getMonth()&&s.getYear()==request.getYear()){
                    return ApiResponse.<String>builder()
                            .message("UNSUCCESS")
                            .result("Bạn đã thực hiện đánh giá tháng này rồi")
                            .code(1100)
                            .build();
                }
            }
        }
        User user;
        if(request.getId()==null){
            user=userSevice.getUserByUsername(staffs.getUsername().getUsername());
        }
        else {
            user=userSevice.getUserByUsername(request.getCreated_by());
        }
        request.setCreated_by(user.getUsername());
        request.setCreated_at(request.getMonth()+"/"+request.getYear());
        request.setRoom_name(user.getRoom_type().getRoom_name());
        request.setRoom_symbol(user.getRoom_type().getRoom_symbol());
        request.setRank(user.getRank_code().getRank_code());
        request.setGroup_rank(user.getRank_code().getGroup_rank_staff().getGroup_name());
        request.setTime_submit(LocalDate.now().toString());
        assessmentService.SaveOrUpdateSelfAssessManager(request);
        return  ApiResponse.<String>builder()
                .message("SUCCESS")
                .result("Đánh giá thành công")
                .code(1000)
                .build();

    }
    //đánh giá Phó khoa/phòng hoặc ĐDT/KTYT/HST
    @GetMapping("/managerCaptainAssessment")
    public ApiResponse<ManagerAssesListRequest> managerCaptainAssessment(@RequestParam(value = "month") int month,@RequestParam(value = "year") int year){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        String date=month+"/"+year;
        Staffs staffs= assessmentService.getStaffByUserName(authentication.getName()).get();

        List<Staffs> staffsList=assessmentService.getStaffListByRoom(staffs.getUsername().getRoom_type().getRoom_name());
        Set<Staffs> memberList=new HashSet<>();

        for (Staffs s:staffsList){
            if(s.getRank_code()!=null){
                if(hasRole("Manager")&&(s.getRank_code().equals("VDE")||s.getRank_code().equals("VMG"))){
                    memberList.add(s);
                }
                if(captain_rank_list.contains(s.getRank_code())){
                    memberList.add(s);
                }
            }
        }
        Iterator<Staffs> iterator=memberList.iterator();
        while (iterator.hasNext()){
            Staffs s=iterator.next();
            ManagerAssessMember managerCheckObj=assessmentService.getObjManagerAssessMemberByCodeRoomSymbolDate(s.getStaff_code(),s.getUsername().getRoom_type().getRoom_symbol(),date);
            if(managerCheckObj!=null){
                iterator.remove();
            }
            if (hasRole("Manager")&&(s.getUsername().getUsername().equalsIgnoreCase(staffs.getUsername().getUsername()))){
                iterator.remove();
            }
        }
        ManagerAssesListRequest managerAssessList=new ManagerAssesListRequest();
        for(Staffs staffs1:memberList){
            managerAssessList.getManagerAssessMemberList().add(new ManagerAssessMember(
                    staffs1.getStaff_code(),staffs1.getFullname(),staffs1.getUsername()));
            log.warn("username cua staff"+staffs1.getUsername().getUsername());
        }
        return  ApiResponse.<ManagerAssesListRequest>builder()
                .code(1000)
                .message("SUCCESS")
                .result(managerAssessList)
                .build();
    }
     //Trưởng nhóm đánh giá các trưởng nhóm( nếu có)

    @GetMapping("/captainAssessment")
    public  ApiResponse<ManagerAssesListRequest> captainAssessment(@RequestParam(value = "month") int month,@RequestParam(value = "year") int year){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        String date=month+"/"+year;
        Staffs staffs= assessmentService.getStaffByUserName(authentication.getName()).get();

        List<Staffs> staffsList=assessmentService.getStaffListByRoom(staffs.getUsername().getRoom_type().getRoom_name());
        Set<Staffs> memberList=new HashSet<>();

        for (Staffs s:staffsList){
            if(s.getRank_code()!=null){

                if(group_rank_list.contains(s.getRank_code())||s.getUsername().getRole_name().getRolename().equals("Group_Leader")){
                    memberList.add(s);
                }
            }
        }
        Iterator<Staffs> iterator=memberList.iterator();
        while (iterator.hasNext()){
            Staffs s=iterator.next();
            ManagerAssessMember managerCheckObj=assessmentService.getObjManagerAssessMemberByCodeRoomSymbolDate(s.getStaff_code(),s.getUsername().getRoom_type().getRoom_symbol(),date);
            if(managerCheckObj!=null){
                iterator.remove();
            }
        }
        ManagerAssesListRequest managerAssessList = new ManagerAssesListRequest();
        for (Staffs staff : memberList) {
            managerAssessList.getManagerAssessMemberList().add(new ManagerAssessMember(
                    staff.getStaff_code(), staff.getFullname(), staff.getUsername()));
        }
        return  ApiResponse.<ManagerAssesListRequest>builder()
                .code(1000)
                .message("SUCCESS")
                .result(managerAssessList)
                .build();
    }
    //Trưởng/phó khoa, trưởng phòng đánh giá BS/NVVP

    //Điều dưỡng/KTY/Hộ sinh trưởng khoa đánh giá các nhân viên không có trưởng nhóm

    @GetMapping("/groupAssessment")
    public  ApiResponse<ManagerAssesListRequest> groupAssessment(@RequestParam(value = "month") int month,@RequestParam(value = "year") int year){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        String date=month+"/"+year;
        Staffs staffs= assessmentService.getStaffByUserName(authentication.getName()).get();
        Set<Staffs> memeberList=new HashSet<>(methodService.getMemberListByRoomGroup(staffs.getUsername(),staffs));
        Iterator<Staffs> iterator=memeberList.iterator();
        while (iterator.hasNext()){
            Staffs s=iterator.next();
            ManagerAssessMember managerCheckObj=assessmentService.getObjManagerAssessMemberByCodeRoomSymbolDate(s.getStaff_code(),s.getUsername().getRoom_type().getRoom_symbol(),date);
            if (managerCheckObj != null){
                iterator.remove();
            }
        }
        ManagerAssesListRequest managerAssessList=new ManagerAssesListRequest();
        for(Staffs staffs1:memeberList){
            if(!staffs1.getRank_code().equals("VDE")&&!staffs1.getRank_code().equals("VMG")){
                managerAssessList.getManagerAssessMemberList().add(new ManagerAssessMember(staffs1.getStaff_code(),staffs1.getFullname(),staffs1.getUsername()));
            }
        }
        return  ApiResponse.<ManagerAssesListRequest>builder()
                .code(1000)
                .message("SUCCESS")
                .result(managerAssessList)
                .build();
    }

}
