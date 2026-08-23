package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.delete;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@WebMvcTest(PdfEndpoint.class)
@AutoConfigureMockMvc(addFilters = false)
class PdfEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DeletePDFUseCase deletePDFUseCase;

    @Test
    void delete_pdf_should_return_200_when_successfull() throws Exception {
        UUID fileUUID = UUID.randomUUID();
        when(deletePDFUseCase.execute(any(DeletePDFCommand.class)))
                .thenReturn(new DeletePDFResponse(fileUUID, true));

        mockMvc.perform(delete("/api/pdf/{fileUUID}", fileUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fileUUID").value(fileUUID.toString()))
                .andExpect(jsonPath("$.deleted").value(true));
    }

    @Test
    void delete_pdf_should_return_404_when_not_found() throws Exception {
        UUID fileUUID = UUID.randomUUID();
        when(deletePDFUseCase.execute(any(DeletePDFCommand.class)))
                .thenThrow(new PdfNotFoundException("PDF not found"));

        mockMvc.perform(delete("/api/pdf/{fileUUID}", fileUUID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}