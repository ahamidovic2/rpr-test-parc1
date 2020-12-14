package ba.unsa.etf.rpr.p1;

import com.sun.source.doctree.SeeTree;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Fabrika {
    private List<Masina> masine = new ArrayList<>();

    public Masina dodajKupljenuMasinu(String naziv, int serijskiBroj) {
        if (serijskiBroj <= 0 || serijskiBroj >= 100000) throw new IllegalArgumentException();
        if (naziv.length() < 2) throw new IllegalArgumentException();
        for (int i = 0; i < naziv.length(); i++) {
            if (!Character.isAlphabetic(naziv.charAt(i))) throw new IllegalArgumentException();
        }
        Masina domaca = new Kupljena();
        domaca.setNaziv(naziv);
        domaca.setSerijski(serijskiBroj);
        for (Masina m : masine) {
            if (m.getSerijski() == serijskiBroj) {
                m.setNaziv(naziv);
                return m;
            }
        }
        masine.add(domaca);
        return domaca;
    }

    public Masina dodajDomacuMasinu(String naziv, int serijskiBroj) {
        if (serijskiBroj <= 0 || serijskiBroj >= 100000) throw new IllegalArgumentException();
        if (naziv.length() < 2) throw new IllegalArgumentException();
        for (int i = 0; i < naziv.length(); i++) {
            if (!Character.isAlphabetic(naziv.charAt(i))) throw new IllegalArgumentException();
        }
        Masina domaca = new Domaca();
        domaca.setNaziv(naziv);
        domaca.setSerijski(serijskiBroj);
        for (Masina m : masine) {
            if (m.getSerijski() == serijskiBroj) {
                m.setNaziv(naziv);
                return m;
            }
        }
        masine.add(domaca);
        return domaca;
    }

    public void dodajMaterijal(String masina, String materijal, int kolicina) {
        boolean usao = false;
        for(Masina m: masine){
            if(m.getNaziv().equals(masina)) {
                m.registrujMaterijal(materijal,kolicina );
                usao = true;
            }
        }
        if(!usao) throw new IllegalArgumentException();
    }

    public Map<Masina, String> najviseProizvoda() {
        Map<Masina, String> mapa = new TreeMap<>();
        for (Masina m : masine) {
            if (m.isUpaljena() && m.getMaterijali().size() > 0) mapa.put(m, m.dajNajveciMoguci());
        }
        return mapa;
    }

    public Set<Masina> dajMasine(Predicate<Masina> o) {
        TreeSet<Masina> skupMasine = new TreeSet<>(new Comparator<Masina>() {
            @Override
            public int compare(Masina o1, Masina o2) {
                if(o1.getSerijski()<o2.getSerijski()) return -1;
                else if(o1.getSerijski()>o2.getSerijski()) return 1;
                return 0;
            }

        });
        skupMasine.addAll(masine);
        if(o==null) return skupMasine;
        return skupMasine.stream().filter(o).collect(Collectors.toSet());



    }

    public Map<Masina, Integer> cijenaZaMaterijal(String m1) {
        return null;
    }
}
