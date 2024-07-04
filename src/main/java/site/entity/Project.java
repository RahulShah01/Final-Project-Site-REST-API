package site.entity;

import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/*
 * This section contains the project entity for the project table. JPA will create the table for us
 * based on the relationship defined below. The project table has ManyToOne relationship with the
 * site table. The project table also had ManyToMany relationship with the team_member table. The
 * project table and team_member table are joined by the join table called project_team_member
 * table.
 */

@Entity
@Data
public class Project {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long projectId;
  private String projectName;
  private String projectLength;
  private String projectType;

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToMany(cascade = CascadeType.PERSIST)
  @JoinTable(name = "project_team_member", joinColumns = @JoinColumn(name = "project_id"),
      inverseJoinColumns = @JoinColumn(name = "team_member_id"))
  private Set<TeamMember> teamMembers = new HashSet<>();

  @EqualsAndHashCode.Exclude
  @ToString.Exclude
  @ManyToOne(cascade = CascadeType.PERSIST)
  @JoinColumn(name = "site_id")
  private Site site;

}
