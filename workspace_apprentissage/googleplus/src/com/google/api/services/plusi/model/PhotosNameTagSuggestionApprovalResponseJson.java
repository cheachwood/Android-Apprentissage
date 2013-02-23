package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotosNameTagSuggestionApprovalResponseJson extends EsJson<PhotosNameTagSuggestionApprovalResponse>
{
  static final PhotosNameTagSuggestionApprovalResponseJson INSTANCE = new PhotosNameTagSuggestionApprovalResponseJson();

  private PhotosNameTagSuggestionApprovalResponseJson()
  {
    super(PhotosNameTagSuggestionApprovalResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "success" });
  }

  public static PhotosNameTagSuggestionApprovalResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotosNameTagSuggestionApprovalResponseJson
 * JD-Core Version:    0.6.2
 */