package beyzagobel.depo;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class Depolar implements Serializable {

    private String depoAdi;
    private String depoId;

    public Depolar(){}

    public String getDepoId() {
        return depoId;
    }

    public void setDepoId(String depoId) {
        this.depoId = depoId;
    }

    public Depolar(String depoId, String depoAdi){
        this.depoAdi = depoAdi;
        this.depoId = depoId;
    }

    public String getDepoAdi() {
        return depoAdi;
    }

    public void setDepoAdi(String depoAdi) {
        this.depoAdi = depoAdi;
    }


}
