package fr.potrunks.gestiondepensebackend.model;

import fr.potrunks.gestiondepensebackend.resources.Status;
import lombok.Data;

@Data
public class Report {

    public Report() {
        this.status = Status.OK;
    }

    private Status status;

    private String message;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
