**Spring Boot Week 18 Final coding Project**

**Project Name: Site-Rest-API**

**Clone the repository**

https://github.com/RahulShah01/Final-Project-Site-REST-API.git

**Entities and tables:**

Site, Project, Team Member

Join table: project_team_member.

**Relationships:**

One site can have multiple projects (OneToMany relationship).

Many projects can belong to one site (ManyToOne relationship).

Many projects can have many team members (ManyToMany relationship).

If a site is deleted, all the projects associated with the site will be deleted. 

If a project is deleted, all the entries in the join table project team member will be deleted.

If a team member is deleted, all the entries in the join table project team member will be deleted.

**CRUD operations written and tested with ARC and endpoint:**

**Site:**

Create/Add a site: **POST /site**

Update a site with siteId: **PUT /site/{siteId}**

Get list of all sites: **GET /site**

Get site with siteId: **GET /site/{siteId}**

Delete site with siteId: **DELETE /site/{siteId}**

**Project:**

Create/Add a project to a site: **POST /site/{siteId}/project** 

Update project with projectId in a site (a specific project): **PUT /site/{siteId}/project/projectId**

Get list of all projects: **GET /site/project**

Get project with projectId: **GET /site/project/{projectId}**

Delete project with projectId in a site (a specific project): **DELETE /site/{siteId}/project/projectId**

**Team Member:**

Create/add a team member to a project: **POST /site/project/{projectId}/teamMember**

Update team member with teamMemberId in a project (a specific team member): **PUT /site/project/{projectId)/teamMember/{teamMemberId}**

Get list of all team members: **GET /site/teamMember**

Get team member with teamMemberId: **GET /site/teamMember/{teamMemberId}**

Delete team with teamMemberId in a project (a specific teamMember): **DELETE /site/project/{projectId}/teamMember/{teamMemberId}**
