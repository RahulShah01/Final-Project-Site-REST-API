package site.controller.model;

import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.entity.Project;
import site.entity.Site;

/*
 * The DTO class SiteData is created below. The fields from the Site entity are copied and @Data
 * and @NoArgsConstructor from the lombok package are added.
 */
@Data
@NoArgsConstructor
public class SiteData {

  private Long siteId;
  private String siteName;
  private String siteAddress;
  private String siteState;
  private String siteZip;
  private String sitePhone;


  // Data type of the Project field changed to ProjectData.
  private Set<ProjectData> projects = new HashSet<>();

  /*
   * Constructor: takes site as a parameter.
   * 
   * Setting all matching fields in the SiteData class to the data in the Site class.
   */
  
  public SiteData(Site site) {
    siteId = site.getSiteId();
    siteName = site.getSiteName();
    siteAddress = site.getSiteAddress();
    siteState = site.getSiteState();
    siteZip = site.getSiteZip();
    sitePhone = site.getSitePhone();

    /*
     * For loop is used to set project fields to their respective ProjectData.
     */
    
    for (Project project : site.getProjects()) {
      projects.add(new ProjectData(project));
    }
  }
}
