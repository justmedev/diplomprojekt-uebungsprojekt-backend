package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.uc.get;

import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain.PdfInfo;
import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfinfo.domain.PdfInfoRepository;
import at.zettasecure.diplomprojekt.pdfeditor.shared.NoInputUseCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GetPdfInfoUseCase implements NoInputUseCase<List<PdfInfo>> {
  private final PdfInfoRepository pdfInfoRepository;

  @Transactional
  @Override
  public List<PdfInfo> execute() {
    return pdfInfoRepository.findAll();
  }
}
