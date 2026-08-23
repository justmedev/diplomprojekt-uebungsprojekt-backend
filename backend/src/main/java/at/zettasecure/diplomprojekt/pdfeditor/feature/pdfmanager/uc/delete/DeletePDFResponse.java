package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.delete;

import java.util.UUID;

public record DeletePDFResponse(UUID fileUUID, boolean deleted) {
}
