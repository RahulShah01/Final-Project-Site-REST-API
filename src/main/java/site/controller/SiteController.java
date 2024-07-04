package site.controller;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j;
import site.controller.model.ProjectData;
import site.controller.model.SiteData;
import site.controller.model.TeamMemberData;
import site.service.SiteService;


/*
 * SiteController class is created.
 * 
 * @RestContoller annotation added to tell the Spring that this is a REST controller.
 * 
 * @RequestMapping annotation added to tell the Spring that the URI for every HTTP request that is
 * mapped to a method in this controller class must start with "/site".
 * 
 * @Slf4j annotation added to create SLF4J logger.
 * 
 * @Autowired added to SiteService as an instance variable.
 * 
 */

@RestController
@Slf4j
@RequestMapping("/site")
public class SiteController {

  @Autowired
  private SiteService siteService;

  // --------------------------------SITE_CRUD_OPERATIONS------------------------------------------//

  /*
   * Method to map HTTP POST request to "/site" with 201 Created response. This method returns a
   * Site object and logs the request. saveSite() method from service class is called that will
   * insert the site data.
   */

  @PostMapping
  @ResponseStatus(code = HttpStatus.CREATED)
  public SiteData createSite(@RequestBody SiteData siteData) {
    log.info("Creating Site {}", siteData);
    return siteService.saveSite(siteData);
  }

  /*
   * Method to update the existing site data using the site Id. This method returns SiteData object
   * and logs the request. saveSite() method from service class is called that will modify the site
   * data to with new values.
   */

  @PutMapping("/{siteId}")
  public SiteData updateSite(@PathVariable Long siteId, @RequestBody SiteData siteData) {
    siteData.setSiteId(siteId);
    log.info("Updating Site {}", siteData);
    return siteService.saveSite(siteData);
  }

  /*
   * Method to list all sites. This methods returns a list of sites.
   * 
   * @GetMapping annotation is added, this annotation does not take a value. Calls retrieveAllSite()
   * method is the service class.
   * 
   */

  @GetMapping
  public List<SiteData> retrieveAllSite() {
    log.info("Retrieve all sites");
    return siteService.retrieveAllSite();
  }

  /*
   * Method to retrieve site by its Id. This method retrieves single site associated with the given
   * site Id.
   * 
   * @GetMapping annotation is added, this annotation takes in the site Id that is passed in to the
   * method as a parameter. Calls the retrieveSiteById() method in the service class.
   * 
   */

  @GetMapping("/{siteId}")
  public SiteData retrieveSiteBySiteId(@PathVariable Long siteId) {
    log.info("Retriving site with ID={}", siteId);
    return siteService.retrieveSiteById(siteId);
  }

  /*
   * Method to delete site by site Id. Takes siteId as parameter.
   * 
   * @DeleteMapping annotation is used to delete site associated with the specific siteId.
   * deleteSiteById() method is called from the service class which takes in siteId as parameter and
   * returns Map<String, String> where the key is "message" and the value is the deletion successful
   * message.
   * 
   */

  @DeleteMapping("/{siteId}")
  public Map<String, String> deleteSiteById(@PathVariable Long siteId) {
    log.info("Deleting Site with ID={}", siteId);
    siteService.deleteSiteById(siteId);
    return Map.of("message", "Deletion of Site with ID=" + siteId + " was successful.");
  }

  // -------------------------------------------PROJECT CRUD OPERATIONS---------------------------//

  /*
   * Method to add projects to site. This method allows a project to be added to a site using HTTP
   * POST request to "/site/{siteId}/project" with 201 created response. This methods calls the
   * saveProject() method in the service class and returns the result of that method call.
   */

  @PostMapping("/{siteId}/project")
  @ResponseStatus(code = HttpStatus.CREATED)
  public ProjectData addProject(@PathVariable Long siteId, @RequestBody ProjectData projectData) {
    log.info("Adding project for site ID:{}", projectData, siteId);
    return siteService.saveProject(siteId, projectData);
  }

  /*
   * Method to update the existing project data using the project Id. This method returns
   * ProjectData object and logs the request. saveProject() method from service class is called that
   * will modify the Project data to with new values.
   */

  @PutMapping("/{siteId}/project/{projectId}")
  public ProjectData updateProject(@PathVariable Long siteId, @PathVariable Long projectId,
      @RequestBody ProjectData projectData) {
    projectData.setProjectId(projectId);
    log.info("Updating Project {}", projectData);
    return siteService.saveProject(siteId, projectData);
  }

  /*
   * Method to list all projects. This methods returns a list of projects.
   * 
   * @GetMapping annotation is added, this annotation does not take a value. Calls
   * retrieveAllProjects() method is the service class.
   * 
   */

  @GetMapping("/project")
  public List<ProjectData> retrieveAllProjects() {
    log.info("Retriving all projects");
    return siteService.retrieveAllProjects();
  }

  /*
   * Method to retrieve project by its Id. This method retrieves single project associated with the
   * given project Id.
   * 
   * @GetMapping annotation is added, this annotation takes in the project Id that is passed in to
   * the method as a parameter. Calls the retrieveProjectById() method in the service class.
   * 
   */

  @GetMapping("/project/{projectId}")
  public ProjectData retrieveProjectById(@PathVariable Long projectId) {
    log.info("Retrieving project with ID={}", projectId);
    return siteService.retrieveProjectById(projectId);
  }

  /*
   * Method to delete project by project Id. Takes projectId as parameter.
   * 
   * @DeleteMapping annotation is used to delete project associated with the specific projectId.
   * deleteProjectById() method is called from the service class which takes in projectId as
   * parameter and returns Map<String, String> where the key is "message" and the value is the
   * deletion successful message.
   * 
   */

  @DeleteMapping("/{siteId}/project/{projectId}")
  public Map<String, String> deleteProjectById(@PathVariable Long projectId) {
    log.info("Deleting project with ID={}", projectId);
    siteService.deleteProjectById(projectId);
    return Map.of("message", "Deletion of Project with ID=" + projectId + " was successful.");
  }

  // --------------------------------Team member CRUD OPERATIONS-------------------------------//

  /*
   * Method to add team member to project. This method allows a team member to be added to a project
   * using HTTP POST request to "/site/project/{projectId}/teamMember" with 201 created response.
   * This methods calls the saveTeamMember() method in the service class and returns the result of
   * that method call.
   */

  @PostMapping("/project/{projectId}/teamMember")
  @ResponseStatus(code = HttpStatus.CREATED)
  public TeamMemberData addTeamMember(@PathVariable Long projectId,
      @RequestBody TeamMemberData teamMemberData) {
    log.info("Adding team member to a project with project ID:{}", teamMemberData, projectId);
    return siteService.saveTeamMember(projectId, teamMemberData);
  }

  /*
   * Method to update the existing team member data using the team member Id. This method returns
   * TeamMemberData object and logs the request. saveTeamMember() method from service class is
   * called that will modify the teamMember data to with new values.
   */

  @PutMapping("/project/{projectId}/teamMember/{teamMemberId}")
  public TeamMemberData updateTeamMember(@PathVariable Long projectId,
      @PathVariable Long teamMemberId, @RequestBody TeamMemberData teamMemberData) {

    teamMemberData.setTeamMemberId(teamMemberId);
    log.info("Updating teamMember {}", teamMemberData);

    return siteService.saveTeamMember(projectId, teamMemberData);
  }

  /*
   * Method to list all team members. This methods returns a list of team members.
   * 
   * @GetMapping annotation is added, this annotation does not take a value. Calls
   * retrieveAllTeamMembers() method is the service class.
   * 
   */

  @GetMapping("/teamMember")
  public List<TeamMemberData> retrieveAllTeamMembers() {
    log.info("Retriving all team members");

    return siteService.retrieveAllTeamMembers();
  }

  /*
   * Method to retrieve team member by its Id. This method retrieves single team member associated
   * with the given team member Id.
   * 
   * @GetMapping annotation is added, this annotation takes in the team member Id that is passed in
   * to the method as a parameter. Calls the retrieveTeamMemberById() method in the service class.
   * 
   */

  @GetMapping("/teamMember/{teamMemberId}")
  public TeamMemberData retrieveTeamMemberById(@PathVariable Long teamMemberId) {
    log.info("Retriving team member with ID={}", teamMemberId);
    return siteService.retrieveTeamMemberById(teamMemberId);
  }

  /*
   * Method to delete team member by team member Id. Takes teamMemberId as parameter.
   * 
   * @DeleteMapping annotation is used to delete team member associated with the specific
   * teamMemberId. deleteTeamMemberById() method is called from the service class which takes in
   * teamMemberId as parameter and returns Map<String, String> where the key is "message" and the
   * value is the deletion successful message.
   * 
   */

  @DeleteMapping("/project/{projectId}/teamMember/{teamMemberId}")
  public Map<String, String> deleteTeamMemberById(@PathVariable Long teamMemberId) {
    log.info("Deleting team member with ID={}", teamMemberId);
    siteService.deleteTeamMemberById(teamMemberId);
    return Map.of("message",
        "Deletion of team member with ID=" + teamMemberId + " was successful.");
  }
}

