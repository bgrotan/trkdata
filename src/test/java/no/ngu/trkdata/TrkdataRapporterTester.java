package no.ngu.trkdata;

import junit.framework.TestCase;
import no.ngu.trkdata.model.Rapport;

import java.io.IOException;
import java.util.List;

/**
 * Created by Grotan_Bjorn_Ove on 10.12.2017.
 */
public class TrkdataRapporterTester extends TestCase {

    public void testGetGrunnundersokelseRapporter() throws IOException{
        List<Rapport> rapportList = TrkdataRapporter.getGrunnundersokelseRapporter();
        assertNotNull(rapportList);
        assertTrue(rapportList.size() > 2000);
        System.out.println(rapportList.get(42).getReportUrl());
        System.out.println(rapportList.get(42).getDownloadUrl());
    }

    public void testDownloadOneReport() throws IOException {
        List<Rapport> rapportList = TrkdataRapporter.getGrunnundersokelseRapporter();
        assertNotNull(rapportList);
        assertTrue(rapportList.size() > 2000);
        IOUtils.download(rapportList.get(42).getReportUrl(),rapportList.get(42).getDownloadUrl());
    }
}
