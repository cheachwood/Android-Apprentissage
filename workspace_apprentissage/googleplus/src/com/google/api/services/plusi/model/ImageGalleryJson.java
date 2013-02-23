package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ImageGalleryJson extends EsJson<ImageGallery>
{
  static final ImageGalleryJson INSTANCE = new ImageGalleryJson();

  private ImageGalleryJson()
  {
    super(ImageGallery.class, new Object[] { EmbedClientItemJson.class, "about", ImageObjectJson.class, "associatedMedia", "description", "name", "url" });
  }

  public static ImageGalleryJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ImageGalleryJson
 * JD-Core Version:    0.6.2
 */