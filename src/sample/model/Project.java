package sample.model;

import java.util.List;

public class Project {
    String project;
    String por_desc;

    public Project(String project, String por_desc) {
        this.project = project;
        this.por_desc = por_desc;
    }


    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getPor_desc() {
        return por_desc;
    }

    public void setPor_desc(String por_desc) {
        this.por_desc = por_desc;
    }
}
