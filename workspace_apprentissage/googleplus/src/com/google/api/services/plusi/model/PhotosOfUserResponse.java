package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class PhotosOfUserResponse extends GenericJson
{
  public List<DataPhoto> approvedPhoto;
  public String approvedQueryResumeToken;
  public TraceRecords backendTrace;
  public String errorCode;
  public List<DataPhoto> localplusPhoto;
  public List<DataPhoto> localplusPhotoOfViewer;
  public String localplusQueryResumeToken;
  public String localplusViewerPhotosQueryResumeToken;
  public List<DataPhoto> suggestedPhoto;
  public String suggestedQueryResumeToken;
  public List<DataPhoto> unapprovedPhoto;
  public String unapprovedQueryResumeToken;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotosOfUserResponse
 * JD-Core Version:    0.6.2
 */