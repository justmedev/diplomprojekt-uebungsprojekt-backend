package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.upload;

import static org.springframework.http.HttpStatus.CREATED;
import at.zettasecure.diplomprojekt.pdfeditor.shared.exceptions.WrongMediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/pdfmanager")
public class UploadPDFEndpoint {
  private final UploadPDFUseCase uploadPDFUseCase;
  public UploadPDFEndpoint(UploadPDFUseCase uploadPDFUseCase) {
    this.uploadPDFUseCase = uploadPDFUseCase;
  }

  @PostMapping(value = "/")
  @ResponseStatus(CREATED)
  public ResponseEntity<Void> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
    try {
      uploadPDFUseCase.execute(new UploadPDFCommand(file, name));
    }
    catch (WrongMediaType e) {
      return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
    }
    return ResponseEntity.status(CREATED).build();
  }
}
