package net.javaspringboot.kpis_be01.service;

import lombok.extern.slf4j.Slf4j;
import net.javaspringboot.kpis_be01.dto.request.SelfAssessStaffRequest;
import net.javaspringboot.kpis_be01.entity.*;
import net.javaspringboot.kpis_be01.repository.*;
import net.javaspringboot.kpis_be01.repository.LeaderAssessManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
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
    @Autowired
    private  SelfAssessStaffRepository  selfAssessStaffRepository;
    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private  RankStaffRepository rankStaffRepository;

    @Autowired
    private  UserInRoleRepository userInRoleRepository;


    public  Optional<User> getUserInformation(String username){
        return userRepository.findByUsername(username);
    }
    public List<ManagerAssessMember> getAllResultManagerAssesMemberByRoom_Date(String room_name,String date){
        return  managerAssessRepository.getAllResultManagerAssesMemberByRoom_Date(room_name,date);
    }

    public void SaveOrUpdateRoom(RoomType roomType){roomTypeRepository.save(roomType);}

    public  RoomType getRoomTypeBySymbol(String symbol){
      return   roomTypeRepository.findRoomTypeByRoomSymbol(symbol);
    }
    public List<RoomType> showAllRoom(){
        return roomTypeRepository.findAll();
    }


    public List<User> getAllUsersByRole(String role){return userRepository.findAllUserByRoleName(role);}
    public void SaveOrUpdateRole(UserInRole userInRole){ userInRoleRepository.save(userInRole); }
    public List<Staffs> getAllStaff(){
        return  staffRepository.findAll();
    }
    public  List<RoomType> getAllRoomType(){
        return roomTypeRepository.findAll();
    }

        public Role getRoleById(Long roleId) {return roleRepository.findById(roleId).get(); }

    public  RankStaff getRankStaff(Long id_rank)
    {
        return  rankStaffRepository.findById(id_rank).get();
    }
    public  RoomType getRoomTypeById(Long id_roomType){
        return  roomTypeRepository.findById(id_roomType).get();
    }
    public List<Staffs> getStaffListByRoom(String room_name){return staffRepository.findByRoomNameLike(room_name);}

    public List<Staffs> getStaffListByRoomGroup(String room_name, String group){return staffRepository.findStaffListByRoomGroupWork(room_name, group);}
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

    public List<MemberAssessManager> getListMemberAssessManagerByUsernameDate(String username, String date){
        return memberAssessManagerRepository.findListMemberAssessManagerByUserDate(username, date);
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

    public  Optional<SelfAssessStaff> getObjSelfAsStaff(String username,String date){
        return  selfAccessStaffRepository.getSelfAssessStaffByUserNameDate(username, date);
    }

    public void SaveSelfAsStaff(SelfAssessStaffRequest selfAssessStaff){
        log.info("thuc hien SaveSelfAsStaff");
            SelfAssessStaff selfAssess=new SelfAssessStaff();
        selfAssess.setStaff_code(selfAssessStaff.getStaff_code());
        selfAssess.setName(selfAssessStaff.getName());
        selfAssess.setRank(selfAssessStaff.getRank());
        selfAssess.setGroup_rank(selfAssessStaff.getGroup_rank());
        selfAssess.setMonth(selfAssessStaff.getMonth());
        selfAssess.setYear(selfAssessStaff.getYear());
        selfAssess.setKy_luat_va_thuong(selfAssessStaff.getKy_luat_va_thuong());
        selfAssess.setMuc_do_phoi_hop(selfAssessStaff.getMuc_do_phoi_hop());
        selfAssess.setChat_luong_chuyen_mon(selfAssessStaff.getChat_luong_chuyen_mon());
        selfAssess.setDiem_muc_do_hoc_tap_pt(selfAssessStaff.getDiem_muc_do_hoc_tap_pt());
        selfAssess.setNote(selfAssessStaff.getNote());
        selfAssess.setRoom_name(selfAssessStaff.getRoom_name());
        selfAssess.setRoom_symbol(selfAssessStaff.getRoom_symbol());
        selfAssess.setCreated_by(selfAssessStaff.getCreated_by());
        selfAssess.setCreated_at(selfAssessStaff.getMonth()+"/"+selfAssessStaff.getYear());
        selfAssess.setTime_submit(LocalDate.now().toString());
        log.info("Saving SelfAssessStaff object: {}", selfAssess);

        selfAssessStaffRepository.save(selfAssess);
    }
}
