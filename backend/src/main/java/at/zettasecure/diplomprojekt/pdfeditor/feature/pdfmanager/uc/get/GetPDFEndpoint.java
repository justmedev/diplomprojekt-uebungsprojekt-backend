package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.get;

import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.domain.PDF;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/pdfs")
@AllArgsConstructor
public class GetPDFEndpoint {
  private final GetPDFUseCase getPDFUseCase;

  @GetMapping()
  public ResponseEntity<List<PDF>> getAllPDFs() {
    List<PDF> pdfs = getPDFUseCase.execute();
    if (pdfs.isEmpty()) return ResponseEntity.noContent().build();
    return ResponseEntity.ok(pdfs);
  }
}
