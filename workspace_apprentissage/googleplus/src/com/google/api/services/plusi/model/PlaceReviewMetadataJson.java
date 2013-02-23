package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlaceReviewMetadataJson extends EsJson<PlaceReviewMetadata>
{
  static final PlaceReviewMetadataJson INSTANCE = new PlaceReviewMetadataJson();

  private PlaceReviewMetadataJson()
  {
    super(PlaceReviewMetadata.class, new Object[] { "encryptedZipitAnnotationId" });
  }

  public static PlaceReviewMetadataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlaceReviewMetadataJson
 * JD-Core Version:    0.6.2
 */