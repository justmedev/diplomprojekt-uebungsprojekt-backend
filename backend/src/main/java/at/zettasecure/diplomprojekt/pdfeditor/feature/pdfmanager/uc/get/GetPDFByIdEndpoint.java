package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.get;

import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.domain.PDF;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/pdfs")
@AllArgsConstructor
public class GetPDFByIdEndpoint {
  private final GetPDFByIdUseCase getPDFByIdUseCase;

  @GetMapping("/{id}")
  public ResponseEntity<PDF> getPDFById(@PathVariable Long id) {
    Optional<PDF> pdf = getPDFByIdUseCase.execute(id);
    return ResponseEntity.of(pdf);
  }
}
