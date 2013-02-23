package com.google.android.apps.plus.oob;

import com.google.api.services.plusi.model.MobileOutOfBoxRequest;
import com.google.api.services.plusi.model.OutOfBoxAction;

public abstract interface ActionCallback
{
  public abstract void onAction(OutOfBoxAction paramOutOfBoxAction);

  public abstract void onActionId(String paramString);

  public abstract void onInputChanged$7c32a9fe();

  public abstract void sendOutOfBoxRequest(MobileOutOfBoxRequest paramMobileOutOfBoxRequest);
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.oob.ActionCallback
 * JD-Core Version:    0.6.2
 */