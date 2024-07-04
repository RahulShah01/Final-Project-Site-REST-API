package site.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import site.entity.Site;

/*
 * Data Layer Interface SiteDao is created below. This interface extends JpaRepository and is used
 * in the siteService class. This DAO interface assists to manage the CRUD operations on the site
 * table.
 */
public interface SiteDao extends JpaRepository<Site, Long> {

}
