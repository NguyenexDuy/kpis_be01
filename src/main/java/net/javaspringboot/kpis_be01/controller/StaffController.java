package net.javaspringboot.kpis_be01.controller;
import net.javaspringboot.kpis_be01.entity.SelfAssessStaff;
import net.javaspringboot.kpis_be01.service.AssessmentService;
import net.javaspringboot.kpis_be01.service.UserSevice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    AssessmentService assessmentService;
    @Autowired
    UserSevice userSevice;
//    @GetMapping("/")
//    List<SelfAssessStaff> getAllResultManagerAssesMember(){
//        var user=userSevice.get
//    }
}
