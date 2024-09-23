package net.javaspringboot.kpis_be01.controller;

import lombok.extern.slf4j.Slf4j;
import net.javaspringboot.kpis_be01.dto.response.ApiResponse;
import net.javaspringboot.kpis_be01.entity.ManagerAssessMember;
import net.javaspringboot.kpis_be01.service.AssessmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/manager")
@Slf4j
public class ManagerController {

    @Autowired
   private AssessmentService assessmentService;

    @GetMapping("/getResultManagerAssessMember")
     ApiResponse<List<ManagerAssessMember>> getResultManagerAssessMember(){
        var authentication= SecurityContextHolder.getContext().getAuthentication();
        log.info("Username:{}",authentication.getName());
        var result=  assessmentService.getAllResultManagerAssesMember("Phòng Hành Chánh Quản Trị");
        return ApiResponse.<List<ManagerAssessMember>>builder()
                .result(result)
                .message("SUCCESS")
                .build();
    }


}
