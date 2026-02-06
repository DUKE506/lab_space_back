package kr.co.labspace.lab_space_back.dto.lab_member;

import kr.co.labspace.lab_space_back.entity.ApprovalStatus;
import kr.co.labspace.lab_space_back.entity.LabRole;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LabMemberDto {
    Long id;
    String name;
    ApprovalStatus approvalStatus;
    LabRole labRole;
}
