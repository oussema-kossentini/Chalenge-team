package tn.esprit.spring.auth;

import tn.esprit.spring.entities.Etat_DeConexion;

public class UpdateStatusRequest {
    private Etat_DeConexion status; // ONLINE, INACTIVE, ou OFFLINE
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    public UpdateStatusRequest() {
    }


    public Etat_DeConexion getStatus() {
        return status;
    }
/*    public void setStatus(String status) {
        this.status = Etat_DeConexion.valueOf(status.toUpperCase());
    }*/

    public void setStatus(Etat_DeConexion status) {
        this.status = status;
    }

}
