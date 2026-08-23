package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.get;

import lombok.AllArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/pdfs")
@AllArgsConstructor
public class GetPdfEndpoint {
  private final GetPdfUseCase getPdfUseCase;

  @GetMapping("/{id}")
  public ResponseEntity<InputStreamResource> getPdfFileById(@PathVariable Long id) {
    var file = getPdfUseCase.execute(id);
    if (file.isEmpty()) return ResponseEntity.notFound().build();
    try {
      InputStreamResource resource = new InputStreamResource(new FileInputStream(file.get()));
      String contentType = "application/octet-stream";
      String headerValue = "attachment; filename=\"" + resource.getFilename() + "\"";
      return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType)).header(HttpHeaders.CONTENT_DISPOSITION, headerValue).body(resource);
    } catch (FileNotFoundException e) {
      return ResponseEntity.notFound().build();
    }
  }
}
