package net.javaspringboot.kpis_be01.service;

import net.javaspringboot.kpis_be01.entity.ManagerAssessMember;
import net.javaspringboot.kpis_be01.entity.SelfAssessStaff;
import net.javaspringboot.kpis_be01.repository.ManagerAssessRepository;
import net.javaspringboot.kpis_be01.repository.SelfAccessStaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssessmentService {


    //*******************FOR*********************
    //*****************MANAGER*********************

    @Autowired
   private ManagerAssessRepository managerAssessRepository;
    @Autowired
    private SelfAccessStaffRepository selfAccessStaffRepository;

    public List<ManagerAssessMember> getAllResultManagerAssesMember(String room_name){
        return  managerAssessRepository.getAllResultManagerAssesMember(room_name);
    }

    public List<SelfAssessStaff> getAllResultSelfAssessStaff(String room_name){
        return  selfAccessStaffRepository.getAllResultSelfAssessStaff(room_name);
    }


}
