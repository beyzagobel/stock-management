package beyzagobel.depo;

import java.io.Serializable;

public class Urunler implements Serializable {

    private String urunAdi;
    private long urunAdeti;
    private long barkod;
    private String urunId;

    public Urunler(String urunId,String urunAdi, long urunAdeti,long barkod){
        this.urunId = urunId;
        this.urunAdi = urunAdi;
        this.urunAdeti = urunAdeti ;
        this.barkod = barkod ;
    }
    public Urunler(){}


    public String getUrunId() {
        return urunId;
    }

    public void setUrunId(String urunId) {
        this.urunId = urunId;
    }

    public String getUrunAdi() {
        return urunAdi;
    }

    public void setUrunAdi(String urunAdi) {
        this.urunAdi = urunAdi;
    }


    public void setUrunAdeti(long urunAdeti) {
        this.urunAdeti = urunAdeti;
    }

    public long getUrunAdeti() {
        return urunAdeti;
    }

    public void setBarkod(long barkod) {
        this.barkod = barkod;
    }

    public long getBarkod() {
        return barkod;
    }

}
