package com.google.android.apps.plus.phone;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.view.ContextThemeWrapper;
import android.widget.ArrayAdapter;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.R.style;
import com.google.android.apps.plus.content.PersonData;
import java.util.ArrayList;

public class InviteContactActivity extends EsProfileGatewayActivity
  implements LoaderManager.LoaderCallbacks<Cursor>
{
  private static final String[] ENTITY_PROJECTION = { "display_name", "mimetype", "data1" };
  private final Handler mHandler = new Handler();

  protected void onActivityResult(int paramInt1, int paramInt2, Intent paramIntent)
  {
    if (paramInt1 == 1)
    {
      int i = 0;
      if (paramInt2 == -1)
      {
        this.mPersonId = paramIntent.getStringExtra("person_id");
        String str = this.mPersonId;
        i = 0;
        if (str != null)
        {
          this.mPersonName = ((PersonData)paramIntent.getParcelableExtra("person_data")).getName();
          showCirclePicker();
          i = 1;
        }
      }
      if (i == 0)
        finish();
    }
    while (true)
    {
      return;
      super.onActivityResult(paramInt1, paramInt2, paramIntent);
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
    return new EsCursorLoader(this, Uri.withAppendedPath((Uri)paramBundle.getParcelable("data_uri"), "entities"), ENTITY_PROJECTION, "mimetype IN ('vnd.android.cursor.item/name','vnd.android.cursor.item/email_v2')", null, null);
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  public final void showCirclePicker(String paramString)
  {
    this.mPersonId = ("e:" + paramString);
    showCirclePicker();
  }

  protected final void showSearchActivity()
  {
    startActivityForResult(Intents.getPeopleSearchActivityIntent(this, this.mAccount, this.mPersonName, true, -1, true, false, true, false, false), 1);
  }

  public static class EmailPickerDialog extends DialogFragment
    implements DialogInterface.OnClickListener
  {
    public EmailPickerDialog()
    {
    }

    public EmailPickerDialog(String[] paramArrayOfString)
    {
      Bundle localBundle = new Bundle();
      localBundle.putStringArray("emails", paramArrayOfString);
      setArguments(localBundle);
    }

    public void onCancel(DialogInterface paramDialogInterface)
    {
      getActivity().finish();
    }

    public void onClick(DialogInterface paramDialogInterface, int paramInt)
    {
      if (paramInt == -2)
        getActivity().finish();
      while (true)
      {
        return;
        Bundle localBundle = getArguments();
        ((InviteContactActivity)getActivity()).showCirclePicker(localBundle.getStringArray("emails")[paramInt]);
      }
    }

    public final Dialog onCreateDialog(Bundle paramBundle)
    {
      AlertDialog.Builder localBuilder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.Theme_EmeraldSea));
      localBuilder.setTitle(R.string.add_to_circle_email_picker_title);
      localBuilder.setAdapter(new ArrayAdapter(getActivity(), 17367058, getArguments().getStringArray("emails")), this);
      localBuilder.setNegativeButton(17039360, this);
      localBuilder.setCancelable(true);
      return localBuilder.create();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.phone.InviteContactActivity
 * JD-Core Version:    0.6.2
 */