package site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * This class contains the main method to start the SpringBoot Application. The
 * SpringBootApplication annotation starts the component scan in the site package.
 */

@SpringBootApplication
public class SiteApplication {

  public static void main(String[] args) {
    SpringApplication.run(SiteApplication.class, args);
  }
}
