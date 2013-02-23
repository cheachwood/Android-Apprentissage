package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import com.google.android.apps.plus.content.EsAccountsData;

public class ViewStreamItemActivity extends FragmentActivity
  implements LoaderManager.LoaderCallbacks<Cursor>
{
  private static final String[] STREAM_ITEMS_PROJECTION = { "stream_item_sync1" };

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Uri localUri = getIntent().getData();
    if (localUri == null)
      finish();
    if (EsAccountsData.getActiveAccount(this) == null)
      finish();
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("stream_item_uri", localUri);
    getSupportLoaderManager().initLoader(0, localBundle, this);
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new EsCursorLoader(this, (Uri)paramBundle.getParcelable("stream_item_uri"), STREAM_ITEMS_PROJECTION, null, null, null);
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ViewStreamItemActivity
 * JD-Core Version:    0.6.2
 */