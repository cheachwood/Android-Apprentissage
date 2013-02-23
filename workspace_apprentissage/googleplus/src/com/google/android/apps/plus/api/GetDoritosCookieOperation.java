package com.google.android.apps.plus.api;

import android.content.Context;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.util.Property;
import org.apache.http.cookie.Cookie;

public final class GetDoritosCookieOperation extends EsOperation
{
  private Cookie mDoritosCookie;

  public GetDoritosCookieOperation(Context paramContext, EsAccount paramEsAccount)
  {
    super(paramContext, paramEsAccount, "POST", Property.SOCIAL_ADS_URL.get(), null, null, null);
  }

  public final Cookie getDoritosCookie()
  {
    return this.mDoritosCookie;
  }

  public final void onHttpCookie(Cookie paramCookie)
  {
    super.onHttpCookie(paramCookie);
    if ((paramCookie != null) && (paramCookie.getName() != null) && (paramCookie.getName().startsWith("_drt_")))
      this.mDoritosCookie = paramCookie;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetDoritosCookieOperation
 * JD-Core Version:    0.6.2
 */