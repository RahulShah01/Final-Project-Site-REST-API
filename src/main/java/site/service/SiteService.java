package site.service;

import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import site.controller.model.ProjectData;
import site.controller.model.SiteData;
import site.controller.model.TeamMemberData;
import site.dao.ProjectDao;
import site.dao.SiteDao;
import site.dao.TeamMemberDao;
import site.entity.Project;
import site.entity.Site;
import site.entity.TeamMember;

@Service
public class SiteService {

  /*
   * SiteDao, ProjectDao and TeamMemberDao is added as a private instance variable.
   * 
   * The @Autowired annotation is added so that the Spring can inject the DAO object into the
   * variable.
   */

  @Autowired
  private SiteDao siteDao;

  @Autowired
  private ProjectDao projectDao;

  @Autowired
  private TeamMemberDao teamMemberDao;

  // -------------------------------SITE----------------------------------------------------------//

  /*
   * saveSite() method takes SiteData object as a parameter and return new SiteData object.
   */

  @Transactional(readOnly = false)
  public SiteData saveSite(SiteData siteData) {
    Long siteId = siteData.getSiteId();
    Site site = findOrCreateSite(siteId);
    copySiteFields(site, siteData);

    // returning new SiteData object created from the return value of the save() method.
    return new SiteData(siteDao.save(site));
  }

  /*
   * findOrCreateSite() method takes siteId as a parameter and returns a new Site object if the
   * siteId is null. If not null, this method calls the findSiteById() method.
   */

  private Site findOrCreateSite(Long siteId) {
    Site site;

    if (Objects.isNull(siteId)) {
      site = new Site();
    } else {
      site = findSiteById(siteId);
    }
    return site;
  }

  /*
   * findSiteById() method takes in siteId as a parameter and returns Site object if the site with
   * matching Id exists in the database. If no matching site is found, it throws
   * NoSuchElementException.
   */

  private Site findSiteById(Long siteId) {
    return siteDao.findById(siteId).orElseThrow(
        () -> new NoSuchElementException("Site with ID=" + siteId + " does not exist"));
  }

  /*
   * copySiteFields() method takes Site object and SiteData object as parameters. Matching fields
   * are copied from the SiteData object to the Site object.
   */

  private void copySiteFields(Site site, SiteData siteData) {
    site.setSiteId(siteData.getSiteId());
    site.setSiteName(siteData.getSiteName());
    site.setSiteAddress(siteData.getSiteAddress());
    site.setSiteState(siteData.getSiteState());
    site.setSiteZip(siteData.getSiteZip());
    site.setSitePhone(siteData.getSitePhone());
  }

  /*
   * retrieveAllSite() method, lists all the sites in the database. This method takes no parameters.
   * Call is made to findAll() method in the siteDao. List of site object is converted to SiteData
   * object. Summary list of all sites, all the projects related with each site and all the team
   * members related to each project is returned.
   */

  @Transactional(readOnly = true)
  public List<SiteData> retrieveAllSite() {
    List<Site> sites = siteDao.findAll();
    List<SiteData> result = new LinkedList<>();

    for (Site site : sites) {
      SiteData sd = new SiteData(site);
      result.add(sd);
    }
    return result;
  }

  /*
   * retrieveSiteById() method, lists site associated with a particular site Id. This method takes
   * in siteId as a parameter, findSiteById() method is called, result is converted to SiteData
   * object. Summary list of the site associated with the siteId, projects related with the site Id
   * and the team members related with the projects in the particular site are returned.
   */

  @Transactional(readOnly = true)
  public SiteData retrieveSiteById(Long siteId) {
    Site site = findSiteById(siteId);
    return new SiteData(site);
  }

  /*
   * deleteSiteById() method, deletes the site associated with the site Id. This methods takes
   * siteId as a parameter, calls findSiteById() method to retrieve the site entity and calls the
   * delete() method in the SiteDao interface.
   * 
   */

  @Transactional(readOnly = false)
  public void deleteSiteById(Long siteId) {
    Site site = findSiteById(siteId);
    siteDao.delete(site);
  }

  // ------------------------------------------PROJECT---------------------------------------------------//

  /*
   * Methods associated with adding/updating Project.
   * 
   * saveProject() method: takes siteId and projectData as parameters. returns new ProjectData.
   * 
   * findOrCreateProject(): takes projectId and siteId as parameters. returns project object. If
   * project Id is null, new project object is returned. if the projectId is not null,
   * findProjectById() method is called.
   * 
   * findProjectById() method: takes siteId and projectId as parameters. Using the projectDao method
   * findById(), project object is returned. If the project isn't found, NoSuchElementException() is
   * thrown.
   * 
   * If we try to update a project not associated with the correct siteId, IllegalArgumentException
   * is thrown.
   * 
   */

  @Transactional(readOnly = false)

  public ProjectData saveProject(Long siteId, ProjectData projectData) {
    Site site = findSiteById(siteId);
    Project project = findOrCreateProject(projectData.getProjectId(), siteId);
    copyProjectFields(project, projectData);
    project.setSite(site);
    site.getProjects().add(project);
    Project dbProject = projectDao.save(project);

    return new ProjectData(dbProject);
  }

  private Project findOrCreateProject(Long projectId, Long siteId) {
    Project project;

    if (Objects.isNull(projectId)) {
      project = new Project();
    } else {
      project = findProjectById(siteId, projectId);
    }
    return project;
  }

  private Project findProjectById(Long siteId, Long projectId) {
    Project project = projectDao.findById(projectId).orElseThrow(
        () -> new NoSuchElementException("Project with ID=" + projectId + " does not exist"));

    if (project.getSite().getSiteId() != siteId) {
      throw new IllegalArgumentException(
          "Project with ID= " + projectId + " does not belong to site with ID=" + siteId);
    }
    return project;
  }

  /*
   * copyProjectFields() method takes Project object and ProjectData object as parameters. Matching
   * fields are copied from the ProjectData object to the project object.
   */

  private void copyProjectFields(Project project, ProjectData projectData) {
    project.setProjectName(projectData.getProjectName());
    project.setProjectLength(projectData.getProjectLength());
    project.setProjectType(projectData.getProjectType());
  }

  /*
   * retrieveAllProjects() method, lists all the projects in the database. This method takes no
   * parameters. Call in made to findAll() method in the projectDao. List of project object is
   * converted to ProjectData object. Summary list of all projects and all the team members related
   * with each project returned.
   */

  @Transactional(readOnly = true)
  public List<ProjectData> retrieveAllProjects() {
    List<Project> projectEntities = projectDao.findAll();
    List<ProjectData> projectNew = new LinkedList<>();

    for (Project project : projectEntities) {
      ProjectData projectData = new ProjectData(project);
      projectNew.add(projectData);
    }
    return projectNew;

  }

  /*
   * retrieveProjectById() method, lists project associated with a particular project Id. This
   * method takes in projectId as a parameter, findProjectById() method is called, result is
   * converted to ProjectData object. Project associated with the project Id and team members
   * associate with the give project is returned. If no project with the give Id is found,
   * NoSuchElementException is thrown.
   */

  @Transactional(readOnly = true)
  public ProjectData retrieveProjectById(Long projectId) {
    Project project = findProjectById(projectId);
    return new ProjectData(project);
  }

  private Project findProjectById(Long projectId) {
    return projectDao.findById(projectId).orElseThrow(
        () -> new NoSuchElementException("Project with ID=" + projectId + " was not found"));

  }

  /*
   * deleteProjectById() method, deletes the project associated with the project Id. This methods
   * takes projectId as a parameter, call findProjectById() method to retrieve the project entity
   * and calls the delete() method in the ProjectDao interface.
   * 
   */

  @Transactional(readOnly = false)
  public void deleteProjectById(Long projectId) {
    Project project = findProjectById(projectId);
    projectDao.delete(project);
  }

  // --------------------------------------Team_Member-----------------------------------------------------------//

  /*
   * Methods associated with adding/updating Team Member.
   * 
   * saveTeamMember() method: takes a projectId and TeamMemberData as parameters. returns new
   * TeamMemberData.
   * 
   * findOrCreateTeamMember(): takes teamMemberId and projectId as parameters. returns teamMember
   * object. If teamMember Id is null, new teamMember object is returned. if the teamMemberId is not
   * null, findTeamMemberById() method is called.
   * 
   * findTeamMemberById() method: takes projectId and teamMemberId as parameters. Using the
   * teamMemberDao method findById(), teamMember object is returned. If the teamMember isn't found,
   * NoSuchElementException is thrown.
   * 
   */

  @Transactional(readOnly = false)
  public TeamMemberData saveTeamMember(Long projectId, TeamMemberData teamMemberData) {
    Project project = findProjectById(projectId);
    TeamMember teamMember = findOrCreateTeamMember(teamMemberData.getTeamMemberId(), projectId);
    copyTeamMemberFields(teamMember, teamMemberData);
    teamMember.getProject().add(project);
    project.getTeamMembers().add(teamMember);
    TeamMember dbTeamMember = teamMemberDao.save(teamMember);

    return new TeamMemberData(dbTeamMember);
  }

  private TeamMember findOrCreateTeamMember(Long teamMemberId, Long projectId) {
    TeamMember teamMember;

    if (Objects.isNull(teamMemberId)) {
      teamMember = new TeamMember();
    } else {
      teamMember = findTeamMemberById(projectId, teamMemberId);
    }
    return teamMember;
  }

  private TeamMember findTeamMemberById(Long projectId, Long teamMemberId) {
    TeamMember teamMember =
        teamMemberDao.findById(teamMemberId).orElseThrow(() -> new NoSuchElementException(
            "Team member with ID=" + teamMemberId + " does not exist"));

    return teamMember;
  }

  /*
   * copyTeamMembersFields() method takes TeamMember object and TeamMemberData object as parameters.
   * Matching fields are copied from the TeamMemberData object to the teamMember object.
   */

  private void copyTeamMemberFields(TeamMember teamMember, TeamMemberData teamMemberData) {
    teamMember.setTeamMemberId(teamMemberData.getTeamMemberId());
    teamMember.setTeamMemberRole(teamMemberData.getTeamMemberRole());
    teamMember.setTeamMemberName(teamMemberData.getTeamMemberName());
    teamMember.setTeamMemberPhone(teamMemberData.getTeamMemberPhone());

  }

  /*
   * retrieveAllTeamMembers() method, lists all the teamMembers in the database. This method takes
   * no parameters. Call in made to findAll() method in the teamMemberDao. List of teamMember object
   * is converted to TeamMemberData object. Summary list of all team members is returned.
   */

  @Transactional(readOnly = true)
  public List<TeamMemberData> retrieveAllTeamMembers() {
    List<TeamMember> teamMemberEntities = teamMemberDao.findAll();
    List<TeamMemberData> teamMemberNew = new LinkedList<>();

    for (TeamMember teamMember : teamMemberEntities) {
      TeamMemberData teamMemberData = new TeamMemberData(teamMember);
      teamMemberNew.add(teamMemberData);
    }
    return teamMemberNew;
  }

  /*
   * retrieveTeamMemberById() method, lists team member associated with a particular team member Id.
   * This method takes in teamMemberId as a parameter, findTeamMemberById() method is called, result
   * is converted to TeamMemberData object. Team member associated with the team member Id is
   * returned. If no team member with the give Id is found, NoSuchElementException is thrown.
   */

  @Transactional(readOnly = true)
  public TeamMemberData retrieveTeamMemberById(Long teamMemberId) {
    TeamMember teamMember = findTeamMemberById(teamMemberId);
    return new TeamMemberData(teamMember);
  }

  private TeamMember findTeamMemberById(Long teamMemberId) {
    return teamMemberDao.findById(teamMemberId).orElseThrow(
        () -> new NoSuchElementException("Team member with ID=" + teamMemberId + " was not found"));
  }

  /*
   * deleteTeamMemberById() method, deletes the team member associated with the team member Id. This
   * methods takes teamMemberId as a parameter, call findTeamMemberById() method to retrieve the
   * team member entity and calls the delete() method in the TeamMemberDao interface.
   * 
   */

  @Transactional(readOnly = false)
  public void deleteTeamMemberById(Long teamMemberId) {
    TeamMember teamMember = findTeamMemberById(teamMemberId);
    for (Project project : teamMember.getProject()) {
      project.getTeamMembers().remove(teamMember);
    }
    teamMemberDao.delete(teamMember);
  }
}


