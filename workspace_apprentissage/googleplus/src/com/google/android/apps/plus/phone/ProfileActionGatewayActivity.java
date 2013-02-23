package com.google.android.apps.plus.phone;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.PersonData;

public class ProfileActionGatewayActivity extends EsProfileGatewayActivity
  implements LoaderManager.LoaderCallbacks<Cursor>
{
  private static final String[] PROJECTION = { "sourceid", "data5", "display_name" };

  private AudienceData createAudience()
  {
    String str1 = null;
    String str2 = this.mPersonId;
    String str3 = this.mPersonName;
    if (str2.startsWith("e:"))
      str2 = str2.substring(2);
    while (true)
    {
      return new AudienceData(new PersonData(str1, str3, str2));
      boolean bool = str2.startsWith("p:");
      str1 = null;
      if (!bool)
      {
        str1 = EsPeopleData.extractGaiaId(str2);
        str2 = null;
      }
    }
  }

  protected void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    if (isFinishing());
    while (true)
    {
      return;
      Uri localUri = getIntent().getData();
      if (localUri == null)
      {
        finish();
      }
      else
      {
        Bundle localBundle = new Bundle();
        localBundle.putParcelable("data_uri", localUri);
        getSupportLoaderManager().initLoader(0, localBundle, this);
      }
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    return new EsCursorLoader(this, (Uri)paramBundle.getParcelable("data_uri"), PROJECTION, null, null, null);
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.ProfileActionGatewayActivity
 * JD-Core Version:    0.6.2
 */