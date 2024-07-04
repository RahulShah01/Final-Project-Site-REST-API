package site.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import site.entity.TeamMember;

/*
 * The DTO class TeamMemberData is created below. The fields from the team member entity class are
 * copied and added @Data and @NoArgsConstructor from the lombok package.
 */
@Data
@NoArgsConstructor
public class TeamMemberData {

  private Long teamMemberId;
  private String teamMemberRole;
  private String teamMemberName;
  private String teamMemberPhone;

  /*
   * Constructor: takes a teamMember object as a parameter.
   * 
   * Setting all matching filed in the TeamMemberData class to the data in the TeamMember class.
   */
  
  public TeamMemberData(TeamMember teamMember) {
    teamMemberId = teamMember.getTeamMemberId();
    teamMemberRole = teamMember.getTeamMemberRole();
    teamMemberName = teamMember.getTeamMemberName();
    teamMemberPhone = teamMember.getTeamMemberPhone();

  }
}
