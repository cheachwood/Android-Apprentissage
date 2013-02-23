package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class ReportAbusePhotoRequest extends GenericJson
{
  public DataAbuseReport abuseReport;
  public String authkey;
  public ApiaryFields commonFields;
  public Boolean enableTracing;
  public String ownerId;
  public Long photoId;
  public String reportToken;
  public String signedClusterId;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReportAbusePhotoRequest
 * JD-Core Version:    0.6.2
 */