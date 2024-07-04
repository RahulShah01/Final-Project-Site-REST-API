package site.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import site.entity.TeamMember;

/*
 * Data Layer Interface TeamMemberDao is created below. This interface extends JpaRepository. This
 * DAO interface assists to manage the CRUD operations on the team member table and is used in the
 * siteService class.
 */
public interface TeamMemberDao extends JpaRepository<TeamMember, Long> {

}
