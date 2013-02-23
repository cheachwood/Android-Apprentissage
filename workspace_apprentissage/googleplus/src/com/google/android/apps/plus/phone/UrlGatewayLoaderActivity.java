package com.google.android.apps.plus.phone;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.MenuItem;
import android.view.View;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData.ProfileAndContactData;
import com.google.android.apps.plus.fragments.DesktopActivityIdLoader;
import com.google.android.apps.plus.fragments.ProfileLoader;

public class UrlGatewayLoaderActivity extends EsUrlGatewayActivity
{
  private final LoaderManager.LoaderCallbacks<Cursor> mDesktopActivityIdLoaderCallbacks = new LoaderManager.LoaderCallbacks()
  {
    public final Loader<Cursor> onCreateLoader(int paramAnonymousInt, Bundle paramAnonymousBundle)
    {
      if (UrlGatewayLoaderActivity.this.mGaiaId == null);
      for (Object localObject = null; ; localObject = new DesktopActivityIdLoader(UrlGatewayLoaderActivity.access$000(UrlGatewayLoaderActivity.this), UrlGatewayLoaderActivity.this.mAccount, UrlGatewayLoaderActivity.this.mDesktopActivityId, UrlGatewayLoaderActivity.this.mGaiaId))
        return localObject;
    }

    public final void onLoaderReset(Loader<Cursor> paramAnonymousLoader)
    {
    }
  };
  private final LoaderManager.LoaderCallbacks<EsPeopleData.ProfileAndContactData> mProfileLoaderCallbacks = new LoaderManager.LoaderCallbacks()
  {
    public final Loader<EsPeopleData.ProfileAndContactData> onCreateLoader(int paramAnonymousInt, Bundle paramAnonymousBundle)
    {
      if (UrlGatewayLoaderActivity.this.mProfileId.startsWith("+"));
      for (Object localObject = new UrlGatewayLoaderActivity.VanityUrlLoader(UrlGatewayLoaderActivity.access$000(UrlGatewayLoaderActivity.this), UrlGatewayLoaderActivity.this.mAccount, UrlGatewayLoaderActivity.this.mProfileId); ; localObject = new ProfileLoader(UrlGatewayLoaderActivity.access$000(UrlGatewayLoaderActivity.this), UrlGatewayLoaderActivity.this.mAccount, "g:" + UrlGatewayLoaderActivity.this.mProfileId, false))
        return localObject;
    }

    public final void onLoaderReset(Loader<EsPeopleData.ProfileAndContactData> paramAnonymousLoader)
    {
    }
  };

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (isFinishing());
    while (true)
    {
      return;
      setContentView(R.layout.url_gateway_loader_activity);
      findViewById(R.id.list_empty_progress).setVisibility(0);
      LoaderManager localLoaderManager = getSupportLoaderManager();
      if ((this.mDesktopActivityId != null) && (this.mActivityId == null))
      {
        if ((this.mGaiaId == null) && (this.mProfileId.startsWith("+")))
        {
          localLoaderManager.initLoader(0, null, this.mProfileLoaderCallbacks);
        }
        else
        {
          this.mGaiaId = this.mProfileId;
          localLoaderManager.initLoader(1, null, this.mDesktopActivityIdLoaderCallbacks);
        }
      }
      else if (this.mProfileId != null)
        localLoaderManager.initLoader(0, null, this.mProfileLoaderCallbacks);
    }
  }

  public boolean onOptionsItemSelected(MenuItem paramMenuItem)
  {
    if (paramMenuItem.getItemId() == 16908332)
      goHome(this.mAccount);
    for (boolean bool = true; ; bool = super.onOptionsItemSelected(paramMenuItem))
      return bool;
  }

  protected final void onTitlebarLabelClick()
  {
    goHome(this.mAccount);
  }

  public static final class VanityUrlLoader extends EsAsyncTaskLoader<EsPeopleData.ProfileAndContactData>
  {
    private final EsAccount mAccount;
    private EsPeopleData.ProfileAndContactData mData;
    private final String mVanityId;

    public VanityUrlLoader(Context paramContext, EsAccount paramEsAccount, String paramString)
    {
      super();
      this.mAccount = paramEsAccount;
      this.mVanityId = paramString;
    }

    protected final void onStartLoading()
    {
      if (this.mData == null)
        forceLoad();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.UrlGatewayLoaderActivity
 * JD-Core Version:    0.6.2
 */