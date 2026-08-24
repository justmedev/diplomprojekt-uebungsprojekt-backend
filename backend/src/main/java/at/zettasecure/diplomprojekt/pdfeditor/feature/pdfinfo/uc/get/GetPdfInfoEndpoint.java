package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.uc.get;

import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain.PdfInfo;
import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain.PdfInfoDto;
import at.zettasecure.diplomprojekt.pdfeditor.feature.thumbnailgenerator.uc.get.GetThumbnailUseCase;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pdfinfos")
@AllArgsConstructor
public class GetPdfInfoEndpoint {

  private final GetPdfInfoUseCase getPdfInfoUseCase;
  private final GetThumbnailUseCase getThumbnailUseCase;

  @GetMapping()
  public ResponseEntity<List<PdfInfoDto>> getAllPdfInfos() {
    List<PdfInfo> pdfInfos = getPdfInfoUseCase.execute();
    if (pdfInfos.isEmpty()) {
      return ResponseEntity.noContent().build();
    }
    List<PdfInfoDto> pdfInfoDtos = pdfInfos.stream().map(pdf -> {
          String thumbnail = getThumbnailUseCase.execute(pdf.getId()).orElse(null);
          return new PdfInfoDto(pdf, thumbnail);
        }
    ).toList();
    return ResponseEntity.ok(pdfInfoDtos);
  }
}
