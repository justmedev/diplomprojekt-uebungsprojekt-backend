package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.delete;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/pdf")
@AllArgsConstructor
public class PdfEndpoint {

    private final DeletePdfUseCase deletePDFUseCase;

    @DeleteMapping("/{fileUUID}")
    public ResponseEntity<DeletePdfResponse> deletePdf(@PathVariable UUID fileUUID) {
        DeletePdfResponse response = deletePDFUseCase.execute(new DeletePdfCommand(fileUUID));
        return ResponseEntity.ok(response);
    }
}
