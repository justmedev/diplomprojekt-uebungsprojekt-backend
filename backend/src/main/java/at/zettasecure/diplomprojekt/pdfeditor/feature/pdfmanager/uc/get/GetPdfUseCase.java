package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.get;

import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.uc.get.GetPdfInfoByIdUseCase;
import at.zettasecure.diplomprojekt.pdfeditor.shared.UseCase;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.file.Paths;
import java.util.Optional;

@AllArgsConstructor
@Service
public class GetPdfUseCase implements UseCase<Long, Optional<File>> {
  private final GetPdfInfoByIdUseCase getPdfInfoByIdUseCase;

  @Override
  public Optional<File> execute(Long id) {
    var info = getPdfInfoByIdUseCase.execute(id);
    return info.map(pdfInfo -> new File(Paths.get("uploads/pdfs").normalize().resolve(pdfInfo.getFileUUID() + ".pdf").toString()));
  }
}
