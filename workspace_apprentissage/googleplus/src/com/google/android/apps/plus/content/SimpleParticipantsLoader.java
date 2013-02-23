package com.google.android.apps.plus.content;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;
import com.google.android.apps.plus.phone.EsMatrixCursor;
import com.google.wireless.realtimechat.proto.Data.Participant;
import java.util.Collection;

public final class SimpleParticipantsLoader extends AsyncTaskLoader<Cursor>
{
  EsMatrixCursor mCursor;
  Collection<Data.Participant> mParticipants;
  String[] mProjection;

  public SimpleParticipantsLoader(Context paramContext, Collection<Data.Participant> paramCollection, String[] paramArrayOfString)
  {
    super(paramContext);
    this.mParticipants = paramCollection;
    this.mProjection = paramArrayOfString;
  }

  protected final void onStartLoading()
  {
    if (this.mCursor != null)
      deliverResult(this.mCursor);
    while (true)
    {
      return;
      forceLoad();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.content.SimpleParticipantsLoader
 * JD-Core Version:    0.6.2
 */