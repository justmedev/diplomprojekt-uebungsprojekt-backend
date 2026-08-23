package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.uc.get;

import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain.PdfInfo;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pdfinfos")
@AllArgsConstructor
public class GetPdfInfoEndpoint {
  private final GetPdfInfoUseCase getPdfInfoUseCase;

  @GetMapping()
  public ResponseEntity<List<PdfInfo>> getAllPdfInfos() {
    List<PdfInfo> pdfInfos = getPdfInfoUseCase.execute();
    if (pdfInfos.isEmpty()) return ResponseEntity.noContent().build();
    return ResponseEntity.ok(pdfInfos);
  }
}
