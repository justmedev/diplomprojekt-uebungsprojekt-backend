package at.zettasecure.diplomprojekt.pdfeditor.feature.thumbnailgenerator.uc.get;

import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.uc.get.GetPdfInfoByIdUseCase;
import at.zettasecure.diplomprojekt.pdfeditor.shared.UseCase;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class GetThumbnailUseCase implements UseCase<Long, Optional<String>> {

  private final GetPdfInfoByIdUseCase getPdfInfoByIdUseCase;

  @Override
  public Optional<String> execute(Long id) {
    var info = getPdfInfoByIdUseCase.execute(id);
    return info.map(pdfInfo -> {
      Path thumbnail = Paths.get("uploads/thumbnails").normalize().toAbsolutePath().resolve(pdfInfo.getFileUUID() + ".webp");
      String base64;
      try {
        byte[] bytes = Files.readAllBytes(thumbnail);
        base64 = "data:image/webp;base64," + Base64.getEncoder().encodeToString(bytes);
      } catch (Exception e) {
        throw new RuntimeException(e);
      }
      return base64;
    });
  }
}