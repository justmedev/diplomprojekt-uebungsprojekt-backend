package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.upload;

import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain.PdfInfo;
import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain.PdfInfoRepository;
import at.zettasecure.diplomprojekt.pdfeditor.shared.UseCase;
import at.zettasecure.diplomprojekt.pdfeditor.shared.exceptions.WrongMediaType;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UploadPDFUseCase implements UseCase<UploadPDFCommand, PdfInfo> {
  private final PdfInfoRepository pdfInfoRepository;

  @Override
  @Transactional
  public PdfInfo execute(UploadPDFCommand input) throws WrongMediaType {
    if (input == null || input.file() == null || input.file().isEmpty()) {
      throw new IllegalArgumentException("Failed to store empty file.");
    }

    String originalFilename = input.file().getOriginalFilename();
    if (originalFilename == null || !originalFilename.toLowerCase().endsWith(".pdf")) {
      throw new WrongMediaType("Failed to store file. Only PDF files are allowed!");
    }

    String contentType = input.file().getContentType();
    if (contentType != null
        && !contentType.equalsIgnoreCase("application/pdf")
        && !contentType.equalsIgnoreCase("application/x-pdf")
        && !contentType.equalsIgnoreCase("application/octet-stream")) {
      throw new WrongMediaType("Failed to store file. Only PDF files are allowed!");
    }

    try (InputStream is = input.file().getInputStream()) {
      byte[] header = new byte[4];
      int bytesRead = is.read(header);
      if (bytesRead < 4 || !new String(header, 0, 4, StandardCharsets.US_ASCII).equals("%PDF")) {
        throw new WrongMediaType("Failed to store file. File content is not a valid PDF!");
      }
    } catch (IOException e) {
      throw new RuntimeException("Could not verify file content", e);
    }

    UUID fileUUID = UUID.randomUUID();
    Path rootLocation = Paths.get("uploads").toAbsolutePath().normalize();
    try {
      Files.createDirectories(rootLocation);
      Path destinationFile = rootLocation.resolve(fileUUID + ".pdf").normalize();

      try (InputStream inputStream = input.file().getInputStream()) {
        //noinspection JvmTaintAnalysis
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      }

      return persistInfo(input.name(), fileUUID);
    } catch (IOException e) {
      throw new RuntimeException("Failed to store file", e);
    }
  }

  @Transactional
  public PdfInfo persistInfo(String name, UUID fileUUID) {
    return pdfInfoRepository.save(PdfInfo.builder().name(name).fileUUID(fileUUID).build());
  }
}

