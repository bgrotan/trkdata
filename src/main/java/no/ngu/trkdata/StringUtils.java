package no.ngu.trkdata;

/**
 * Created by Grotan_Bjorn_Ove on 10.12.2017.
 */
public class StringUtils {
    public static String trim(String s, int width) {
        if (s.length() > width)
            return s.substring(0, width-1) + ".";
        else
            return s;
    }

}
