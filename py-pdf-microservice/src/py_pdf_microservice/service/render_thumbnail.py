import io
import pypdfium2 as pdfium

def render_thumbnail(pdf_bytes: bytes, target_width: int = 400) -> bytes:
    pdf = pdfium.PdfDocument(pdf_bytes)
    page = pdf[0]

    scale = target_width / page.get_width()
    image = page.render(scale=scale).to_pil()

    output = io.BytesIO()
    image.save(output, format="WEBP")
    return output.getvalue()