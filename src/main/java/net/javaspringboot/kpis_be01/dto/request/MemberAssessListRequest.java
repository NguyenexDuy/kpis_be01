package net.javaspringboot.kpis_be01.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.javaspringboot.kpis_be01.entity.MemberAssessment;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class MemberAssessListRequest {
    //for save member assess list value
    public List<MemberAssessment> membersAssessList = new ArrayList<>();
}
