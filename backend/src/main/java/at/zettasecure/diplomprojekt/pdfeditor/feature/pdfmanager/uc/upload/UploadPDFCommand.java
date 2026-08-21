package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.upload;

import org.springframework.web.multipart.MultipartFile;

public record UploadPDFCommand(
    MultipartFile file
) {}
