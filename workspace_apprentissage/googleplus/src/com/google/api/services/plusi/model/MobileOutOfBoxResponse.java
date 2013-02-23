package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class MobileOutOfBoxResponse extends GenericJson
{
  public TraceRecords backendTrace;
  public String continueUrl;
  public OutOfBoxView failureView;
  public String postMessageTargetOrigin;
  public String redirectUrl;
  public Boolean signupComplete;
  public Boolean smsSent;
  public OutOfBoxView view;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MobileOutOfBoxResponse
 * JD-Core Version:    0.6.2
 */