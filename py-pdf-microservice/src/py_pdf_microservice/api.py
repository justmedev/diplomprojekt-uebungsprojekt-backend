from fastapi import FastAPI, UploadFile, File, HTTPException, status
from fastapi.responses import Response
from .service.render_thumbnail import render_thumbnail

app = FastAPI()

@app.post("/render-thumbnail", response_class=Response)
async def generate_thumbnail(file: UploadFile = File(...)):
    try:
        content = await file.read()
        image_bytes = render_thumbnail(content)
        return Response(content=image_bytes, media_type="image/webp")
    except Exception as e:
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail=f"Thumbnail rendering failed: {str(e)}"
        )