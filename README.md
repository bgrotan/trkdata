# trkdata
A webscraper and download client for geotechnical surveys from the municipality of Trondheim

### Sample usage:
```java
// Fetch all report (for each report, we get report number, project name and url to pdf
List<Rapport> rapportList = TrkdataRapporter.getGrunnundersokelseRapporter();
// Download one of the 2000+ pdf reports
IOUtils.download(rapportList.get(42).getReportUrl(),rapportList.get(42).getDownloadUrl());
```
Some of these reportUrls are on the form attachement.ap?<id>
For these URLs we do not get the name of the PDF when downloading. 
Therefor, a filename is generated from a serial and project name to be the stored pdf file 
