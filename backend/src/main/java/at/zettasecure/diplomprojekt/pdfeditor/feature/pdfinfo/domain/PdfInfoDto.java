package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain;

public class PdfInfoDto {
  public Long id;
  public String name;

  public PdfInfoDto(PdfInfo pdfInfo) {
    this.id = pdfInfo.getId();
    this.name = pdfInfo.getName();
  }
}
