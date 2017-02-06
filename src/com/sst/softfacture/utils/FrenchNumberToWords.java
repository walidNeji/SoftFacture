/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sst.softfacture.utils;

/**
 *
 * @author Neji Med Walid <<neji.walid@gmail.com>>
 */
import com.ibm.icu.text.RuleBasedNumberFormat;
import java.util.Locale;

public class FrenchNumberToWords {

    public static String convert(float nombre) {
        double avant_v = 0, apres_v = 0;
        int zav = 0;
        int formatter = RuleBasedNumberFormat.SPELLOUT;
        String nombre_chaine = String.valueOf(nombre);
        String rst;
        if (nombre_chaine.indexOf(".") >= 0) {
            String[] nbs = nombre_chaine.replace(".", ",").split(",");
            int[] k = new int[2];
            for (int i = 0; i < nbs.length; i++) {
                k[i] = Integer.parseInt(nbs[i]);
            }
            avant_v = k[0];
            apres_v = k[1];
            if (nbs[1].charAt(0) == '0') {
                zav = 1;
                if (nbs[1].length() > 1) {
                    if (nbs[1].charAt(1) == '0') {
                        zav = 2;
                    }
                }

            }


        } else {
            avant_v = nombre;
        }
        System.out.println("nombre " + correctionApresVirgule(apres_v, zav));
        com.ibm.icu.text.NumberFormat nf = new RuleBasedNumberFormat(Locale.FRANCE, formatter);
        if (apres_v > 0) {
            rst = nf.format(avant_v).toUpperCase() + " DINARS " + nf.format(correctionApresVirgule(apres_v, zav)).toUpperCase() + " MILLIMES";
        } else {
            rst = nf.format(avant_v).toUpperCase() + " DINARS";
        }
        return rst;
    }

    public static double correctionApresVirgule(double n, int zav) {
        int nbre = (int) n;
        if (zav == 0) {
//        System.out.println("NBRE " + nbre);
            if (String.valueOf(nbre).length() == 1) {
                String rst = String.valueOf(nbre).concat("00");
//            System.out.println("RST" + rst);
                return Double.parseDouble(rst);
            } else if (String.valueOf(nbre).length() == 2) {
                String rst = String.valueOf(nbre).concat("0");
//            System.out.println("RST" + rst);
                return Double.parseDouble(rst);
            } else {
                return n;
            }
        } else if (zav == 1) {
            if (String.valueOf(nbre).length() == 1) {
                String rst = String.valueOf(nbre).concat("0");
//            System.out.println("RST" + rst);
                return Double.parseDouble(rst);
            } else {
                return n;
            }
        } else {
            return n;
        }
    }
}