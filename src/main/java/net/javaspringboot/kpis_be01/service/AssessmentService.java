package net.javaspringboot.kpis_be01.service;

import net.javaspringboot.kpis_be01.entity.*;
import net.javaspringboot.kpis_be01.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssessmentService {


    //*******************FOR*********************
    //*****************MANAGER*********************

    @Autowired
   private ManagerAssessRepository managerAssessRepository;

    @Autowired
    private MemberAssessManagerRepository memberAssessManagerRepository;

    @Autowired
    private MemberAssessRepository memberAssessRepository;

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private SelfAssessManagerRepository selfAssessManagerRepository;
    @Autowired
    private  SeflAssessStaffRepository selfAccessStaffRepository;

    public List<ManagerAssessMember> getAllResultManagerAssesMemberByRoom_Date(String room_name,String date){
        return  managerAssessRepository.getAllResultManagerAssesMemberByRoom_Date(room_name,date);
    }




    public  List<MemberAssessManager> getAllResultMemberAssessManager(String room_name, String date){
        return  memberAssessManagerRepository.getAllResultMemberAssessManagerByRoomDate(room_name,date);
    }


    public Optional<Staffs> getStaffByUserName(String username){
        return  staffRepository.getStaffsByUsername(username);
    }

    public  Optional<Staffs> getStaffByStaffCode(String rank_code){
        return  staffRepository.getStaffsByStaff_code(rank_code);
    }


    public List<MemberAssessment> getAllResultMemberAssessmentByRomeDate(String room_name, String code,String date){
        return memberAssessRepository.getAllResultMemberAssessByRoomDate(room_name,code,date);
    }

    public List<MemberAssessment> getAllResultMemberAssessmentByNameDate(String unique_username, String date){
        return  memberAssessRepository.getAllResultMemberAssessByNameDate(unique_username,date);
    }

    public  List<MemberAssessment> getAllMemberAssess(){
        return  memberAssessRepository.getAllMemberAssess();
    }

    public List<MemberAssessment> getListMemberAssessByRoomAndMonthYear(String room_name, String date){
        return memberAssessRepository.getListMarkOfMemberByMonthYear(room_name, date);
    }

    public List<SelfAssessManager> getAllSelfAssessManager(String room_name, String date){
        return selfAssessManagerRepository.getAllByRoom_nameDate(room_name,date);
    }

    public List<SelfAssessManager> getAllSelfAssessManagerByDate(String date){return selfAssessManagerRepository.getAllByDate(date);}

    public List<ManagerAssessMember> getAllRessultManagerAssessYourSelfByStaffCodeRoom(String staff_code, String date){
        return  managerAssessRepository.getAllResultManagerAssessMemberByStaffCodeRoom(staff_code,date);
    }

    public List<SelfAssessStaff> getSeflAssessStaffByStaffCode(String staff_code){
        return  selfAccessStaffRepository.getSeflAssessStaffByStaffCode(staff_code);
    }
}
