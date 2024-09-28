package net.javaspringboot.kpis_be01.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaspringboot.kpis_be01.entity.ManagerAssessLeader;
import net.javaspringboot.kpis_be01.entity.ManagerAssessMember;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ManagerAssesListRequest {

    //for save manager assess member list value
    public List<ManagerAssessMember> managerAssessMemberList = new ArrayList<>();

    //for save manager assess director list value
    public List<ManagerAssessLeader> managerAssessLeaderList = new ArrayList<>();
}
