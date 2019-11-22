package datadump.accessingdatamongodb;

import org.springframework.data.annotation.Id;

import java.util.HashMap;
import java.util.UUID;

public class Judet //extends HashMap<String, Object>
 {

    @Id
    public String nume;

    private int unitati_inv;
    private int cu_pers_juridica;
    private int arondate;
    private int particulare;
    private int publice;

    public Judet(){

    }
    public Judet(String nume, int unitati_inv, int cu_pers_juridica, int arondate, int particulare, int publice){
        this.setNume(nume);
        this.setUnitati_inv(unitati_inv);
        this.setCu_pers_juridica(cu_pers_juridica);
        this.setArondate(arondate);
        this.setParticulare(particulare);
        this.setPublice(publice);
    }

    @Override
    public String toString() {
        return String.format("Judet[nume=%s, unitati invatamant='%s', cu persoana juridica='%s', arondate='%s', particulare='%s', publice='%s']",
                nume, getUnitati_inv(), getCu_pers_juridica(), getArondate(), getParticulare(), getPublice());
    }

    public String getNume() {
        return nume;
    }

    public int getUnitati_inv() {
        return unitati_inv;
    }

    public int getCu_pers_juridica() {
        return cu_pers_juridica;
    }

    public int getArondate() {
        return arondate;
    }

    public int getParticulare() {
        return particulare;
    }

    public int getPublice() {
        return publice;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public void setUnitati_inv(int unitati_inv) {
        this.unitati_inv = unitati_inv;
    }

    public void setCu_pers_juridica(int cu_pers_juridica) {
        this.cu_pers_juridica = cu_pers_juridica;
    }

    public void setArondate(int arondate) {
        this.arondate = arondate;
    }

    public void setParticulare(int particulare) {
        this.particulare = particulare;
    }

    public void setPublice(int publice) {
        this.publice = publice;
    }
}
