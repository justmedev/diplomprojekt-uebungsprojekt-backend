package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain;

public class PdfInfoDto {
  public String id;
  public String name;

  public PdfInfoDto(PdfInfo pdfInfo) {
    this.id = pdfInfo.getId().toString();
    this.name = pdfInfo.getName();
  }
}
