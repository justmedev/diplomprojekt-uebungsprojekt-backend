package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.delete;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class PdfNotFoundException extends RuntimeException {
    public PdfNotFoundException(String message) {
        super(message);
    }
}