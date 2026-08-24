package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.uc.get;

import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain.PdfInfo;
import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain.PdfInfoDto;
import at.zettasecure.diplomprojekt.pdfeditor.feature.thumbnailgenerator.uc.get.GetThumbnailUseCase;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/pdfinfos")
@AllArgsConstructor
public class GetPdfInfoByIdEndpoint {

  private final GetPdfInfoByIdUseCase getPdfInfoByIdUseCase;
  private final GetThumbnailUseCase getThumbnailUseCase;

  @GetMapping("/{id}")
  public ResponseEntity<PdfInfoDto> getPdfInfoById(@PathVariable Long id) {
    Optional<PdfInfo> pdf = getPdfInfoByIdUseCase.execute(id);
    if (pdf.isEmpty()) {
      return ResponseEntity.notFound().build();
    }
    String thumbnail = getThumbnailUseCase.execute(id).orElse(null);

    return ResponseEntity.ok(new PdfInfoDto(pdf.get(), thumbnail));
  }
}
