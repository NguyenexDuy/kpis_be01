package net.javaspringboot.kpis_be01.controller;
import lombok.extern.slf4j.Slf4j;
import net.javaspringboot.kpis_be01.dto.response.ApiResponse;
import net.javaspringboot.kpis_be01.entity.MemberAssessment;
import net.javaspringboot.kpis_be01.entity.Staffs;
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
@RequestMapping("/staff")
@Slf4j
public class StaffController {

    @Autowired
  private   AssessmentService assessmentService;


    @GetMapping("/getAllMemberAssess")
    ApiResponse<List<MemberAssessment>> getAllMemberAssess(){
        return  ApiResponse.<List<MemberAssessment>>builder()
                .result(assessmentService.getAllMemberAssess())
                .message("SUCCESS")
                .build();
    }


    //kết quả đánh giá cấp nhân viên
    @GetMapping("/getResultMemberAssess")
    ApiResponse<List<MemberAssessment>> getResultMemberAssess(@RequestParam(value = "month") int month, @RequestParam(value = "year") int year){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        Staffs staffs= assessmentService.getStaffByUserName(authentication.getName()).get();
        log.info("Username:{}",authentication.getName());
        List<MemberAssessment> membersList = new ArrayList<>();

        if(hasRole("User")){
            membersList=assessmentService.getAllResultMemberAssessmentByNameDate(staffs.getUsername().getUsername(),month+"/"+year);
        }
        else {
            membersList=assessmentService.getAllResultMemberAssessmentByRomeDate(staffs.getRoom_name(),staffs.getStaff_code(),month+"/"+year);
            if (hasRole("Group_Leader")){//nếu role group leader thì chỉ show các thành viên chung nhóm với nhau
                Iterator<MemberAssessment> iterator = membersList.iterator();
                while (iterator.hasNext()){
                    MemberAssessment m = iterator.next();
                    String group_work_member = assessmentService.getStaffByStaffCode(m.getStaff_code()).get().getGroup_work();
                    //nếu không cùng group work thì ẩn đi
                    if (!staffs.getGroup_work().equals(group_work_member)){
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
}
