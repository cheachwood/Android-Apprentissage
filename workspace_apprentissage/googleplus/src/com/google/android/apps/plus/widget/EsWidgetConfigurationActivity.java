package com.google.android.apps.plus.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.fragments.CircleListLoader;

public class EsWidgetConfigurationActivity extends FragmentActivity
  implements LoaderManager.LoaderCallbacks<Cursor>, AdapterView.OnItemClickListener
{
  private CirclesCursorAdapter mAdapter;
  private final Object mAdapterLock = new Object();
  private int mAppWidgetId;
  private boolean mDisplayingEmptyView;
  private ListView mListView;

  private void updateDisplay()
  {
    if (this.mDisplayingEmptyView)
    {
      this.mListView.setVisibility(4);
      findViewById(16908292).setVisibility(0);
      findViewById(R.id.list_empty_text).setVisibility(8);
      findViewById(R.id.list_empty_progress).setVisibility(0);
    }
    while (true)
    {
      return;
      this.mListView.setVisibility(0);
      findViewById(16908292).setVisibility(8);
      findViewById(R.id.list_empty_text).setVisibility(8);
      findViewById(R.id.list_empty_progress).setVisibility(8);
    }
  }

  public void onCreate(Bundle paramBundle)
  {
    super.onCreate(paramBundle);
    Bundle localBundle = getIntent().getExtras();
    if (localBundle != null)
    {
      this.mAppWidgetId = localBundle.getInt("appWidgetId", 0);
      if (this.mAppWidgetId != 0)
        break label48;
      finish();
    }
    while (true)
    {
      return;
      this.mAppWidgetId = 0;
      break;
      label48: if (EsAccountsData.getActiveAccount(this) == null)
      {
        EsWidgetUtils.saveCircleInfo(this, this.mAppWidgetId, null, null);
        EsWidgetProvider.configureWidget(this, EsAccountsData.getActiveAccount(this), this.mAppWidgetId);
        Intent localIntent = new Intent();
        localIntent.putExtra("appWidgetId", this.mAppWidgetId);
        setResult(-1, localIntent);
        finish();
      }
      setContentView(R.layout.widget_configuration_activity);
      this.mDisplayingEmptyView = true;
      this.mListView = ((ListView)findViewById(16908298));
      this.mListView.setOnItemClickListener(this);
      View localView = LayoutInflater.from(this).inflate(R.layout.widget_configuration_entry, null);
      localView.findViewById(R.id.circle_icon).setVisibility(8);
      ((TextView)localView.findViewById(R.id.circle_name)).setText(R.string.widget_all_circles);
      localView.findViewById(R.id.circle_member_count).setVisibility(8);
      this.mListView.addHeaderView(localView, null, true);
      this.mAdapter = new CirclesCursorAdapter(this);
      this.mListView.setAdapter(this.mAdapter);
      updateDisplay();
      getSupportLoaderManager().initLoader(0, null, this);
    }
  }

  public final Loader<Cursor> onCreateLoader(int paramInt, Bundle paramBundle)
  {
    Object localObject = null;
    switch (paramInt)
    {
    default:
    case 0:
    }
    while (true)
    {
      return localObject;
      EsAccount localEsAccount = EsAccountsData.getActiveAccount(this);
      localObject = null;
      if (localEsAccount != null)
        localObject = new CircleListLoader(this, EsAccountsData.getActiveAccount(this), 4, WidgetCircleQuery.PROJECTION);
    }
  }

  public void onItemClick(AdapterView<?> paramAdapterView, View paramView, int paramInt, long paramLong)
  {
    int i = paramInt - this.mListView.getHeaderViewsCount();
    String str1;
    Object localObject3;
    if (i < 0)
    {
      str1 = "v.all.circles";
      localObject3 = getString(R.string.stream_circles);
      EsWidgetUtils.saveCircleInfo(this, this.mAppWidgetId, str1, (String)localObject3);
      EsWidgetProvider.configureWidget(this, EsAccountsData.getActiveAccount(this), this.mAppWidgetId);
      Intent localIntent = new Intent();
      localIntent.putExtra("appWidgetId", this.mAppWidgetId);
      setResult(-1, localIntent);
      finish();
    }
    label157: label197: 
    while (true)
    {
      return;
      Cursor localCursor;
      synchronized (this.mAdapterLock)
      {
        if ((this.mAdapter == null) || (this.mAdapter.getCursor() == null))
          break label197;
        localCursor = this.mAdapter.getCursor();
        if (!localCursor.isClosed())
          if (localCursor.getCount() > i)
            break label157;
      }
      localCursor.moveToPosition(i);
      str1 = localCursor.getString(1);
      String str2 = localCursor.getString(2);
      localObject3 = str2;
      break;
    }
  }

  public final void onLoaderReset(Loader<Cursor> paramLoader)
  {
  }

  private final class CirclesCursorAdapter extends CursorAdapter
  {
    public CirclesCursorAdapter(Context arg2)
    {
      super(null, 0);
    }

    public final void bindView(View paramView, Context paramContext, Cursor paramCursor)
    {
      ((TextView)paramView.findViewById(R.id.circle_name)).setText(paramCursor.getString(2));
      TextView localTextView = (TextView)paramView.findViewById(R.id.circle_member_count);
      ImageView localImageView;
      if ((0x1 & paramCursor.getInt(4)) != 0)
      {
        localTextView.setText(null);
        localImageView = (ImageView)paramView.findViewById(R.id.circle_icon);
        if (!"v.whatshot".equals(paramCursor.getString(1)))
          break label121;
        localImageView.setImageResource(R.drawable.list_whats_hot);
      }
      while (true)
      {
        return;
        localTextView.setText("(" + paramCursor.getInt(3) + ")");
        break;
        label121: localImageView.setImageResource(R.drawable.list_circle);
      }
    }

    public final View newView(Context paramContext, Cursor paramCursor, ViewGroup paramViewGroup)
    {
      return LayoutInflater.from(paramContext).inflate(R.layout.widget_configuration_entry, null);
    }
  }

  public static abstract interface WidgetCircleQuery
  {
    public static final String[] PROJECTION = { "_id", "circle_id", "circle_name", "contact_count", "semantic_hints" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.widget.EsWidgetConfigurationActivity
 * JD-Core Version:    0.6.2
 */