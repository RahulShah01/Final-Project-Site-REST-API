package site.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import site.entity.Project;

/*
 * Data Layer Interface ProjectDao is created below. This interface extends JpaRepository. This DAO
 * interface assists to manage the CRUD operations on the project table and is used in the
 * siteService class.
 */

public interface ProjectDao extends JpaRepository<Project, Long> {

}
