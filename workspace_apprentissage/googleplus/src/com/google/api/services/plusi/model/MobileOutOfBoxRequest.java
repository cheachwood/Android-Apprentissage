package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class MobileOutOfBoxRequest extends GenericJson
{
  public OutOfBoxAction action;
  public String clientType;
  public ApiaryFields commonFields;
  public String continueUrl;
  public Boolean enableTracing;
  public List<OutOfBoxInputField> input;
  public Boolean integrated;
  public String invitationToken;
  public String partnerId;
  public String postMessageTargetOrigin;
  public String upgradeOrigin;
  public String webClientPathAndQuery;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MobileOutOfBoxRequest
 * JD-Core Version:    0.6.2
 */