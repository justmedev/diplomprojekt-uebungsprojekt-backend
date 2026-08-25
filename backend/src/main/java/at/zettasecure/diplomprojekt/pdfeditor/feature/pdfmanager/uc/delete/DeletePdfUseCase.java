package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.delete;

import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain.PdfInfo;
import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain.PdfInfoRepository;
import at.zettasecure.diplomprojekt.pdfeditor.shared.UseCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DeletePdfUseCase implements UseCase<DeletePdfCommand, DeletePdfResponse> {

    private final PdfInfoRepository pdfInfoRepository;

    @Override
    @Transactional
    public DeletePdfResponse execute(DeletePdfCommand input) {
        if (input == null || input.fileUUID() == null) {
            throw new IllegalArgumentException("File UUID must not be null.");
        }

        UUID fileUUID = input.fileUUID();

        PdfInfo pdfInfo = pdfInfoRepository.findByFileUUID(fileUUID)
                .orElseThrow(() -> new PdfNotFoundException("PDF with UUID " + fileUUID + " not found."));

        pdfInfoRepository.delete(pdfInfo);

        Path rootLocation = Paths.get("uploads").toAbsolutePath().normalize();
        Path fileToDelete = rootLocation.resolve(fileUUID + ".pdf").normalize();

        try {
            Files.deleteIfExists(fileToDelete);
        } catch (IOException e) {
            throw new RuntimeException("Could not delete physical PDF file from disk", e);
        }

        return new DeletePdfResponse(fileUUID, true);
    }
}
