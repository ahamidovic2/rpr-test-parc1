package ba.unsa.etf.rpr.p1;

import java.util.*;

public class Masina {
    private String naziv;
    private Integer serijski;
    private Map<String, Integer> materijali = new TreeMap<>();
    private boolean upaljena = false;
    private Integer sati = 0;

    public void upali() throws WrongMachineState {
        if (upaljena) throw new WrongMachineState();
        sati = 8;
        upaljena = true;
    }

    public void ugasi() throws WrongMachineState {
        if (!upaljena) throw new WrongMachineState();
        upaljena = false;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public Integer getSerijski() {
        return serijski;
    }

    public void setSerijski(Integer serijski) {
        this.serijski = serijski;
    }

    public Map<String, Integer> getMaterijali() {
        return materijali;
    }

    public void setMaterijali(Map<String, Integer> materijali) {
        this.materijali = materijali;
    }

    public void proizvedi(String m1) {
        Integer brojPotrebnihSati = cijena(m1);
        if(brojPotrebnihSati > sati) throw new IllegalArgumentException();
        sati -= brojPotrebnihSati;



    }

    public int cijena(String materijal) {
        for (Map.Entry<String, Integer> m : materijali.entrySet()) {
            String key = m.getKey();
            Integer value = m.getValue();
            if (key.equals(materijal)) return value;
        }
        throw new IllegalArgumentException();
    }


    public void resetuj() throws WrongMachineState {
        if (!upaljena) throw new WrongMachineState();
        ugasi();
        upali();

    }

    public int preostaloSati() {
        if (!upaljena) sati = 0;
        return sati;
    }

    public void registrujMaterijal(String naziv, int cijena) {
        for (Map.Entry<String, Integer> m : materijali.entrySet()) {
            String key = m.getKey();
            Integer value = m.getValue();
            if (key.equals(naziv)) return;
        }
        materijali.put(naziv, cijena);
    }

    public Set<String> dajMaterijaleMoguceZaProizvesti() {
        TreeSet<String> moguciZaProizvest = new TreeSet<>();
        for (Map.Entry<String, Integer> m : materijali.entrySet()) {
            String key = m.getKey();
            Integer value = m.getValue();
            if (value <= preostaloSati()) moguciZaProizvest.add(key);
        }
        return moguciZaProizvest;
    }

    public boolean isUpaljena() {
        return upaljena;
    }

    public void setUpaljena(boolean upaljena) {
        this.upaljena = upaljena;
    }

    public Integer getSati() {
        return sati;
    }

    public void setSati(Integer sati) {
        this.sati = sati;
    }

    public Map<String, Integer> dajMogucnostProizvodnje() {
        Map<String, Integer> mogucnostiProizvodnjeMaterijala = new TreeMap<>();
        for (Map.Entry<String, Integer> m : materijali.entrySet()) {
            String key = m.getKey();
            Integer value = m.getValue();
            mogucnostiProizvodnjeMaterijala.put(key, preostaloSati() / value);
        }
        return mogucnostiProizvodnjeMaterijala;
    }
    public String dajNajveciMoguci(){
        Map<String, Integer> mogucnostProizvodnje = dajMogucnostProizvodnje();
        Integer max = -1;
        String maxMaterijal = "";
        Iterator zaProlaz = mogucnostProizvodnje.entrySet().iterator();
        while (zaProlaz.hasNext()){
            Map.Entry<String, Integer> element =(Map.Entry<String, Integer>) zaProlaz.next();
            String key = element.getKey();
            Integer value = element.getValue();
            if(value > max) {
                max = value;
                maxMaterijal= key;
            }

        }
        return maxMaterijal;
    }

    @Override
    public String toString() {
        String s= "Masina " + naziv + " je ";
        if(upaljena) s+="upaljena.";
        else s+="ugasena.";
        s+=" Ona moze proizvesti ";
        int brojac = 0;
        int velicina = materijali.size();
        for (Map.Entry<String, Integer> m : materijali.entrySet() ){
            String key = m.getKey();
            Integer value = m.getValue();
            s+=key + "(" + value + ")";
            if(brojac<velicina-1) s+=", ";
            else s+=".";
            brojac++;
        }
        return s;
    }
}
