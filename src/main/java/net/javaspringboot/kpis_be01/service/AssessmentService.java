package net.javaspringboot.kpis_be01.service;

import net.javaspringboot.kpis_be01.entity.*;
import net.javaspringboot.kpis_be01.repository.*;
import net.javaspringboot.kpis_be01.repository.LeaderAssessManagerRepository;
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
    private UserRepository userRepository;

    @Autowired
    private StaffRepository staffRepository;
    @Autowired
    private SelfAssessManagerRepository selfAssessManagerRepository;
    @Autowired
    private SelfAssessStaffRepository selfAccessStaffRepository;

    @Autowired
    private LeaderAssessManagerRepository leaderAssessManager;

    @Autowired
    private ManagerAssessDirecRepository managerAssessDirecRepository;

    @Autowired
    private  ResultRepo resultRepo;


    public  Optional<User> getUserInformation(String username){
        return userRepository.findByUsername(username);
    }
    public List<ManagerAssessMember> getAllResultManagerAssesMemberByRoom_Date(String room_name,String date){
        return  managerAssessRepository.getAllResultManagerAssesMemberByRoom_Date(room_name,date);
    }
    public List<Staffs> getStaffListByRoom(String room_name){return staffRepository.findByRoomNameLike(room_name);}

    public List<MemberAssessManager> getListMemberAssessManagerByCodeRoomDate(String code, String room_name, String date){
        return memberAssessManagerRepository.getListMemberAssessManagerByCodeRoomDate(code, room_name, date);
    }
    public ManagerAssessMember getObjManagerAssessMemberByCodeRoomSymbolDate(String code, String room, String date){
        return managerAssessRepository.findObjManagerAssessMemberByCodeRoomSymbolDate(code, room, date);
    }
    public List<MemberAssessment> getMark3MemberAssessByCodeRoomDate(String code, String room_name, String date){
        return memberAssessRepository.getListMarkOfMemberByCodeRoomDate(code, room_name, date);
    }

    public  List<MemberAssessManager> getAllResultMemberAssessManager(String room_name, String date){
        return  memberAssessManagerRepository.getAllResultMemberAssessManagerByRoomDate(room_name,date);
    }
    public void SaveOrUpdateResultSelfKPI(ResultPersonalKPI result){
        resultRepo.save(result);
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

    public List<SelfAssessStaff> getSelfAssessStaffByStaffCode(String staff_code){
        return  selfAccessStaffRepository.getSelfAssessStaffByStaffCode(staff_code);
    }
    public  List<SelfAssessStaff> getSelfAssessStaffByRoomDate(String room_name, String date){
        return  selfAccessStaffRepository.getSelfAssessStaffByRoomDate(room_name,date);
    }

    public  List<SelfAssessStaff> getSeflAssessStaffByUserDate(String create_by,String date)
    {
        return  selfAccessStaffRepository.getSelfAllByUserDate(create_by,date);
    }

    public List<MemberAssessManager> getListMembersAssessManagerByUsernameRoom(String username, String room_name){return memberAssessManagerRepository.findListMemberAssessManagerByUsernameRoom(username, room_name);}

    public List<LeaderAssessManager> getListLeaderAssessManagerByRoomDate(String room_name){
        return leaderAssessManager.findListLeaderAssessManagerByRoomDate(room_name);
    }
    public List<ResultPersonalKPI> getListResultPersonalKPIByRoomSymbolDate(String symbol, String date) {
        return resultRepo.resultListByRoomSymbolDate(symbol, date);
    }
    public List<ResultPersonalKPI> getListResultPersonalKPIByDate(String date) {
        return resultRepo.resultListByDate(date);}

    public ManagerAssessLeader getObjManagerAssessLeaderByUserNameCodeRoomDate(String username, String code, String room_name, String date){
        return managerAssessDirecRepository.findObjAssessLeaderByStaffCodeRoomMonthYear(username, code, room_name, date);
    }
}
