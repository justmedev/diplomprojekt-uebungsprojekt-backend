package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.uc.get;

import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain.PdfInfo;
import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain.PdfInfoRepository;
import at.zettasecure.diplomprojekt.pdfeditor.shared.UseCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;

@AllArgsConstructor
@Service
public class GetPdfInfoByIdUseCase implements UseCase<Long, Optional<PdfInfo>> {
  private final PdfInfoRepository pdfInfoRepository;

  @Transactional
  @Override
  public Optional<PdfInfo> execute(Long id) {
    return pdfInfoRepository.findById(id);
  }
}
