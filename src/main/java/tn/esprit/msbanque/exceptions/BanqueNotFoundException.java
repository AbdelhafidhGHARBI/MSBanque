package tn.esprit.msbanque.exceptions;

public class BanqueNotFoundException extends RuntimeException {
    public BanqueNotFoundException(String id) {
        super("Banque non trouvée avec l'id: " + id);
    }
}
