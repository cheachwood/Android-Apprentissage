package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;

public class ViewCircleActivity extends FragmentActivity
  implements LoaderManager.LoaderCallbacks<Cursor>
{
  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Uri localUri = getIntent().getData();
    if (localUri == null)
      finish();
    Bundle localBundle = new Bundle();
    localBundle.putParcelable("group_uri", localUri);
    getSupportLoaderManager().initLoader(0, localBundle, this);
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new EsCursorLoader(this, (Uri)paramBundle.getParcelable("group_uri"), new String[] { "sourceid" }, null, null, null);
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ViewCircleActivity
 * JD-Core Version:    0.6.2
 */