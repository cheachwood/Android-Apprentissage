package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotosNameTagApprovalResponseJson extends EsJson<PhotosNameTagApprovalResponse>
{
  static final PhotosNameTagApprovalResponseJson INSTANCE = new PhotosNameTagApprovalResponseJson();

  private PhotosNameTagApprovalResponseJson()
  {
    super(PhotosNameTagApprovalResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "success" });
  }

  public static PhotosNameTagApprovalResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotosNameTagApprovalResponseJson
 * JD-Core Version:    0.6.2
 */