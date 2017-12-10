package no.ngu.trkdata;

import no.ngu.trkdata.model.Rapport;

import java.io.BufferedWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Webscrape geotechnical survey reports from the municipality of Trondheim
 */
public class TrkdataRapporter {

    // The municipality store survey reports on three different URLs on their content management system.
    private static String oldReports = "https://www.trondheim.kommune.no/content/1117723652/Geotekniske-rapporter-R.0001---R.1499";
    private static String currentReports = "https://www.trondheim.kommune.no/content/1117741281/Geotekniske-rapporter-R.1500";
    private static String customReports = "https://www.trondheim.kommune.no/content/1117744503/Konsulentrapporter-for-Trondheim-kommune";

    public static List<Rapport> getGrunnundersokelseRapporter() throws IOException {
        ArrayList<Rapport> rapporter = new ArrayList<Rapport>();
        rapporter.addAll(getGrunnundersokelseRapporter(oldReports));
        rapporter.addAll(getGrunnundersokelseRapporter(customReports));
        rapporter.addAll(getGrunnundersokelseRapporter(currentReports));
        return rapporter;
    }
    private static List<Rapport> getGrunnundersokelseRapporter(String url) throws IOException{
        ArrayList<Rapport> rapporter = new ArrayList<Rapport>();

        Document doc = Jsoup.connect(url).get();
        Elements reports = doc.select("tr");
        for (int i=0;i<reports.size();i++) {
            Element report = reports.get(i);
            Elements columns = report.select("td");
            Element nrColumn = columns.get(0);
            Element nameColumn = columns.get(1);
            Elements links = nrColumn.select("a[href]");
            if (links.size() < 1) {
                continue;
            }
            String fileUrl = links.first().attr("abs:href");
            String fileName = FilenameUtils.getName(new URL(links.first().attr("abs:href")).getPath());
            Rapport rapport = new Rapport();
            rapport.setReportNumber(nrColumn.text().trim());
            rapport.setReportName(nameColumn.text().trim());
            rapport.setReportUrl(fileUrl);
            String workingDirectory = System.getProperty("user.dir").replace("\\","/");
            String fileDir = workingDirectory+url.substring(url.lastIndexOf("/")).concat("/");
            if (fileName.equals("attachment.ap")) {
                rapport.setDownloadUrl(fileDir+i+"-"+nameColumn.text().trim()+".pdf");
            } else {
                rapport.setDownloadUrl(fileDir+FilenameUtils.getName(new URL(links.first().attr("abs:href")).getPath()));
            }
            rapporter.add(rapport);
        }
        return rapporter;
    }


}
