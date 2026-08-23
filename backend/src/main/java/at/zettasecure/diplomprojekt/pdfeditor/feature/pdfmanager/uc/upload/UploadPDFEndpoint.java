package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.upload;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.http.HttpStatus.CREATED;

import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain.PdfInfoDto;
import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.uc.get.GetPdfInfoByIdEndpoint;
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
@RequestMapping("/api/pdfs")
public class UploadPDFEndpoint {
  private final UploadPDFUseCase uploadPDFUseCase;
  public UploadPDFEndpoint(UploadPDFUseCase uploadPDFUseCase) {
    this.uploadPDFUseCase = uploadPDFUseCase;
  }

  @PostMapping(value = "/")
  @ResponseStatus(CREATED)
  public ResponseEntity<PdfInfoDto> handleFileUpload(@RequestParam("file") MultipartFile file, @RequestParam("name") String name) {
    try {
      var pdf = uploadPDFUseCase.execute(new UploadPDFCommand(file, name));
      return ResponseEntity.created(linkTo(methodOn(GetPdfInfoByIdEndpoint.class).getPdfInfoById(pdf.getId())).toUri()).body(new PdfInfoDto(pdf));
    }
    catch (WrongMediaType e) {
      return ResponseEntity.status(HttpStatus.UNSUPPORTED_MEDIA_TYPE).build();
    }
  }
}
