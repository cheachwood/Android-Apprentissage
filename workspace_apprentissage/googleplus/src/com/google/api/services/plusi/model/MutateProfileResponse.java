package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class MutateProfileResponse extends GenericJson
{
  public TraceRecords backendTrace;
  public String errorCode;
  public String errorMessage;
  public SimpleProfile updatedProfile;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MutateProfileResponse
 * JD-Core Version:    0.6.2
 */