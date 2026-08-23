package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.uc.get;

import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain.PdfInfo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/pdfinfos")
@AllArgsConstructor
public class GetPdfInfoByIdEndpoint {
  private final GetPdfInfoByIdUseCase getPdfInfoByIdUseCase;

  @GetMapping("/{id}")
  public ResponseEntity<PdfInfo> getPdfInfoById(@PathVariable Long id) {
    Optional<PdfInfo> pdf = getPdfInfoByIdUseCase.execute(id);
    return ResponseEntity.of(pdf);
  }
}
