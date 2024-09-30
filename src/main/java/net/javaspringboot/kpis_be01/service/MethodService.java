package net.javaspringboot.kpis_be01.service;

import net.javaspringboot.kpis_be01.entity.MemberAssessManager;
import net.javaspringboot.kpis_be01.entity.MemberAssessment;
import net.javaspringboot.kpis_be01.entity.Staffs;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

@Service
public class MethodService {
    @Autowired
    private AssessmentService assessmentService;



    public double getMarkMemberAssessMemberAverageByStaffcodeRoomDate(String code,String room_name,String date){
        List<MemberAssessment> memberAssessList = assessmentService.getMark3MemberAssessByCodeRoomDate(code, room_name, date);
        List<Staffs> memberSize = assessmentService.getStaffListByRoom(room_name);
        Iterator<Staffs> iterator = memberSize.iterator();
        while (iterator.hasNext()){
            Staffs s = iterator.next();
            if (s.getUsername().isStatus()==false){//if account was disabled, remove from memberSize
                iterator.remove();
            }
        }
        double average = 0;
        int size = memberSize.size();
        int ms = 0;//số ng đã thực hiện đánh giá
        //xét user là role user hay manager
        Staffs s=assessmentService.getStaffByStaffCode(code).get();
        if (s.getUsername().getRole_name().getRolename().equalsIgnoreCase("manager")){
            //trường hợp user tính điểm tb là manager thì ko tính manager vào giá trị trung bình cộng
            if (size <= 1){
                return 0;
            }
            size -= 1;
        } else {
            // <=2 vì ko tính bản thân user đó và manager (điểm manager cho tính riêng) vào giá trị trung bình cộng
            if (size <= 2){
                return 0;
            }
            size-=2;
        }
        for (MemberAssessment m: memberAssessList) {
            average += m.getMark_member_assess();
            ms++;
        }
        if (ms == 0){// to avoid error divide by 0
            average = 0;
        } else {
            average = Math.round((average/(ms))*100)/100d;//làm tròn đến 2 chữ số
        }
        return average;
    }

    public double getMarkMemberAssessManagerAverageByStaffcodeRoomDate(String code,String room_name,String date){
        List<MemberAssessManager> memberAssessManagerList = assessmentService.getListMemberAssessManagerByCodeRoomDate(code, room_name, date);
        List<Staffs> memberSize = assessmentService.getStaffListByRoom(room_name);
        Iterator<Staffs> iterator = memberSize.iterator();
        while (iterator.hasNext()){
            Staffs s = iterator.next();
            if (!s.getUsername().isStatus()){//if account was disabled, remove from memberSize
                iterator.remove();
            }
        }
        double average = 0;
        int size = memberSize.size();
        int ms = 0;//số ng đã thực hiện đánh giá

        for (MemberAssessManager m: memberAssessManagerList) {
            average += m.getTb_gan_ket_va_phan_cong();
            ms++;
        }
        if (ms == 0){// to avoid error divide by 0
            average = 0;
        } else {
            average = Math.round((average/(ms))*100)/100d;//làm tròn đến 2 chữ số
        }
        return average;
    }
}
