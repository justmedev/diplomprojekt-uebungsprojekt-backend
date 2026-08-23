package at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.uc.get;

import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.domain.PDF;
import at.zettasecure.diplomprojekt.pdfeditor.feature.pdfmanager.domain.PDFRepository;
import at.zettasecure.diplomprojekt.pdfeditor.shared.NoInputUseCase;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GetPDFUseCase implements NoInputUseCase<List<PDF>> {
  private final PDFRepository pdfRepository;

  @Transactional
  @Override
  public List<PDF> execute() {
    return pdfRepository.findAll();
  }
}
