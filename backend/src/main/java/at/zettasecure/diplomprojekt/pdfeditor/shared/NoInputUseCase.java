package at.zettasecure.diplomprojekt.pdfeditor.shared;

@FunctionalInterface
public interface NoInputUseCase<O> {
  O execute();
}