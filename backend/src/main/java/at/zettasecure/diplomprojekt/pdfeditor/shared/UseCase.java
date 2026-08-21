package at.zettasecure.diplomprojekt.pdfeditor.shared;

@FunctionalInterface
public interface UseCase<I, O> {
  O execute(I input);
}