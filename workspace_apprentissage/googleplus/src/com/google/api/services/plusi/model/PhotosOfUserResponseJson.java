package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotosOfUserResponseJson extends EsJson<PhotosOfUserResponse>
{
  static final PhotosOfUserResponseJson INSTANCE = new PhotosOfUserResponseJson();

  private PhotosOfUserResponseJson()
  {
    super(PhotosOfUserResponse.class, new Object[] { DataPhotoJson.class, "approvedPhoto", "approvedQueryResumeToken", TraceRecordsJson.class, "backendTrace", "errorCode", DataPhotoJson.class, "localplusPhoto", DataPhotoJson.class, "localplusPhotoOfViewer", "localplusQueryResumeToken", "localplusViewerPhotosQueryResumeToken", DataPhotoJson.class, "suggestedPhoto", "suggestedQueryResumeToken", DataPhotoJson.class, "unapprovedPhoto", "unapprovedQueryResumeToken" });
  }

  public static PhotosOfUserResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotosOfUserResponseJson
 * JD-Core Version:    0.6.2
 */