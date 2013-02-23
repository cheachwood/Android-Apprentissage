package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class MobileSettingsUser extends GenericJson
{
  public MobileSettingsUserInfo info;
  public Boolean isChild;
  public Boolean isPlusPage;
  public List<MobileSettingsUserInfo> plusPageInfo;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MobileSettingsUser
 * JD-Core Version:    0.6.2
 */