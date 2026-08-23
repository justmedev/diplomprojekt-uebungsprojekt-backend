package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.domain;

public class PDFDto {
  public String id;
  public String name;

  public PDFDto(PDF pdf) {
    this.id = pdf.getId().toString();
    this.name = pdf.getName();
  }
}
