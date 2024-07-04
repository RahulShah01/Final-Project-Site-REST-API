package site.controller.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.entity.Project;
import site.entity.TeamMember;

/*
 * The DTO class ProjectData is created below. The fields from the project entity are copied
 * and @Data and @NoArgsConstructor from the lombok package are added.
 */
@Data
@NoArgsConstructor
public class ProjectData {

  private Long projectId;
  private String projectName;
  private String projectLength;
  private String projectType;

  // Data type of the team members field changed to TeamMemeberData.
  private Set<TeamMemberData> teamMembers = new HashSet<>();

  /*
   * Constructor: takes project as a parameter. 
   * 
   * Setting all matching fields in the ProjectData class to the data in the Project class.
   */
  
  public ProjectData(Project project) {
    projectId = project.getProjectId();
    projectName = project.getProjectName();
    projectLength = project.getProjectLength();
    projectType = project.getProjectType();

    /*
     * For loop is used to set team member fields to their respective TeamMemberData.
     */

    for (TeamMember teamMember : project.getTeamMembers()) {
      teamMembers.add(new TeamMemberData(teamMember));
    }
  }
}

