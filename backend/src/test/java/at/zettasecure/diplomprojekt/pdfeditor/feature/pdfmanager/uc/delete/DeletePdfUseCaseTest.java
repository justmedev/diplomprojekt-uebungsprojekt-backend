package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.delete;

import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain.PdfInfo;
import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain.PdfInfoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeletePdfUseCaseTest {

    @Mock
    private PdfInfoRepository pdfInfoRepository;

    private DeletePdfUseCase deletePDFUseCase;

    @BeforeEach
    void setUp() {
        deletePDFUseCase = new DeletePdfUseCase(pdfInfoRepository);
    }

    @Test
    void execute_ShouldDeletePdf_WhenPdfExists() {
        UUID fileUUID = UUID.randomUUID();
        PdfInfo pdfInfo = PdfInfo.builder().name("test.pdf").fileUUID(fileUUID).build();

        when(pdfInfoRepository.findByFileUUID(fileUUID)).thenReturn(Optional.of(pdfInfo));

        DeletePdfResponse response = deletePDFUseCase.execute(new DeletePdfCommand(fileUUID));

        assertTrue(response.deleted());
        assertEquals(fileUUID, response.fileUUID());
        verify(pdfInfoRepository, times(1)).delete(pdfInfo);
    }

    @Test
    void execute_ShouldThrowException_WhenPdfNotFound() {
        UUID fileUUID = UUID.randomUUID();

        when(pdfInfoRepository.findByFileUUID(fileUUID)).thenReturn(Optional.empty());

        assertThrows(PdfNotFoundException.class, () ->
                deletePDFUseCase.execute(new DeletePdfCommand(fileUUID))
        );

        verify(pdfInfoRepository, never()).delete(any());
    }

    @Test
    void execute_ShouldThrowException_WhenInputIsNull() {
        assertThrows(IllegalArgumentException.class, () -> deletePDFUseCase.execute(null));
    }
}