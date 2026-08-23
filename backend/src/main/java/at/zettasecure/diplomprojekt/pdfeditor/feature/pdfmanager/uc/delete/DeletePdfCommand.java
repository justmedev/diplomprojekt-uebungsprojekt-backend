package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.delete;

import java.util.UUID;

public record DeletePdfCommand(UUID fileUUID) {
}
