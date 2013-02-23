package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotosNameTagApprovalRequestJson extends EsJson<PhotosNameTagApprovalRequest>
{
  static final PhotosNameTagApprovalRequestJson INSTANCE = new PhotosNameTagApprovalRequestJson();

  private PhotosNameTagApprovalRequestJson()
  {
    super(PhotosNameTagApprovalRequest.class, arrayOfObject);
  }

  public static PhotosNameTagApprovalRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotosNameTagApprovalRequestJson
 * JD-Core Version:    0.6.2
 */