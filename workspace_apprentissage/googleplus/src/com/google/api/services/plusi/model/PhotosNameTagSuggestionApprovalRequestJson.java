package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotosNameTagSuggestionApprovalRequestJson extends EsJson<PhotosNameTagSuggestionApprovalRequest>
{
  static final PhotosNameTagSuggestionApprovalRequestJson INSTANCE = new PhotosNameTagSuggestionApprovalRequestJson();

  private PhotosNameTagSuggestionApprovalRequestJson()
  {
    super(PhotosNameTagSuggestionApprovalRequest.class, new Object[] { "approve", ApiaryFieldsJson.class, "commonFields", "enableTracing", "ownerId", "photoId", "shapeId", "taggeeId", "taggeeName" });
  }

  public static PhotosNameTagSuggestionApprovalRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotosNameTagSuggestionApprovalRequestJson
 * JD-Core Version:    0.6.2
 */