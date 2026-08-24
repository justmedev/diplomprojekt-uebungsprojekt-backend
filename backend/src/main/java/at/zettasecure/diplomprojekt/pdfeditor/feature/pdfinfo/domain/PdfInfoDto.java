package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain;

public class PdfInfoDto {
  public Long id;
  public String name;
  public String thumbnailBase64;

  public PdfInfoDto(PdfInfo pdfInfo, String thumbnailBase64) {
    this.id = pdfInfo.getId();
    this.name = pdfInfo.getName();
    this.thumbnailBase64 = thumbnailBase64;
  }
}
