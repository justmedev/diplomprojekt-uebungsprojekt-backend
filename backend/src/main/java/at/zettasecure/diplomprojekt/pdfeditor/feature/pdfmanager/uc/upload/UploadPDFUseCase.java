package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.upload;

import at.zettasecure.diplomprojekt.pdfeditor.shared.UseCase;
import at.zettasecure.diplomprojekt.pdfeditor.shared.exceptions.WrongMediaType;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import org.springframework.stereotype.Service;

@Service
public class UploadPDFUseCase implements UseCase<UploadPDFCommand, Void> {

  @Override
  public Void execute(UploadPDFCommand input) throws WrongMediaType {
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

    Path rootLocation = Paths.get("uploads");
    Path destinationFile;
    try {
      Files.createDirectories(rootLocation);
      destinationFile = rootLocation.resolve(
              Paths.get(Objects.requireNonNull(originalFilename)))
          .normalize().toAbsolutePath();
      if (!destinationFile.getParent().equals(rootLocation.toAbsolutePath())) {
        throw new SecurityException("Cannot store file outside current directory.");
      }
      try (InputStream inputStream = input.file().getInputStream()) {
        Files.copy(inputStream, destinationFile, StandardCopyOption.REPLACE_EXISTING);
      }
    } catch (IOException e) {
      throw new RuntimeException("Failed to store file", e);
    }

    try (InputStream is = Files.newInputStream(destinationFile)) {
      byte[] header = new byte[4];
      int bytesRead = is.read(header);
      if (bytesRead < 4 || !new String(header, 0, 4, StandardCharsets.US_ASCII).equals("%PDF")) {
        Files.deleteIfExists(destinationFile);
        throw new WrongMediaType("Failed to store file. File content is not a valid PDF!");
      }
    } catch (IOException e) {
      try {
        Files.deleteIfExists(destinationFile);
      } catch (IOException ignored) {
      }
      throw new RuntimeException("Could not verify file content", e);
    }

    return null;
  }
}

