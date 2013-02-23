package com.google.android.apps.plus.views;

import android.content.Context;
import android.content.res.Resources;
import android.database.DataSetObserver;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.fragments.SuggestionGridAdapter;
import com.google.android.apps.plus.fragments.SuggestionGridAdapter.SuggestionCategoryAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

public class SuggestionGridView extends LinearLayout
  implements ColumnGridView.OnScrollListener
{
  private SuggestionGridAdapter mAdapter;
  private DataSetObserver mObserver = new DataSetObserver()
  {
    public final void onChanged()
    {
      SuggestionGridView.this.onDataChanged();
    }
  };
  private HashMap<String, ColumnGridView> mRows = new HashMap();

  public SuggestionGridView(Context paramContext)
  {
    super(paramContext);
    setOrientation(1);
  }

  public SuggestionGridView(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    setOrientation(1);
  }

  public SuggestionGridView(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    setOrientation(1);
  }

  public final ScrollPositions getScrollPositions()
  {
    ScrollPositions localScrollPositions = new ScrollPositions();
    Iterator localIterator = this.mRows.entrySet().iterator();
    if (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = (String)localEntry.getKey();
      ColumnGridView localColumnGridView = (ColumnGridView)localEntry.getValue();
      View localView = localColumnGridView.getChildAt(0);
      if (localView != null);
      for (int i = localView.getLeft(); ; i = 0)
      {
        localScrollPositions.setScrollPosition(str, localColumnGridView.getFirstVisiblePosition(), i);
        break;
      }
    }
    return localScrollPositions;
  }

  protected final void onDataChanged()
  {
    ArrayList localArrayList = this.mAdapter.getCategories();
    int i = localArrayList.size();
    if (i > getChildCount())
    {
      LayoutInflater localLayoutInflater = LayoutInflater.from(getContext());
      while (i > getChildCount())
      {
        View localView2 = localLayoutInflater.inflate(R.layout.suggestion_category, this, false);
        ColumnGridView localColumnGridView2 = (ColumnGridView)localView2.findViewById(R.id.suggestion_row);
        localColumnGridView2.setOrientation(1);
        localColumnGridView2.setMinColumnWidth(getResources().getDimensionPixelSize(R.dimen.person_card_min_height));
        localColumnGridView2.setColumnCount(1);
        localColumnGridView2.setOnScrollListener(this);
        addView(localView2);
      }
    }
    while (getChildCount() > i)
      removeViewAt(-1 + getChildCount());
    this.mRows.clear();
    int j = 0;
    if (j < i)
    {
      SuggestionGridAdapter.SuggestionCategoryAdapter localSuggestionCategoryAdapter = (SuggestionGridAdapter.SuggestionCategoryAdapter)localArrayList.get(j);
      View localView1 = getChildAt(j);
      TextView localTextView = (TextView)localView1.findViewById(R.id.category_label);
      label201: ColumnGridView localColumnGridView1;
      if ("#".equals(localSuggestionCategoryAdapter.getCategory()))
      {
        localTextView.setText(getContext().getString(R.string.suggestion_category_friends).toUpperCase());
        localColumnGridView1 = (ColumnGridView)localView1.findViewById(R.id.suggestion_row);
        if (localColumnGridView1.getAdapter() == localSuggestionCategoryAdapter)
          break label268;
        localColumnGridView1.setAdapter(localSuggestionCategoryAdapter);
      }
      while (true)
      {
        this.mRows.put(localSuggestionCategoryAdapter.getCategory(), localColumnGridView1);
        j++;
        break;
        localTextView.setText(localSuggestionCategoryAdapter.getCategoryLabel().toUpperCase());
        break label201;
        label268: localSuggestionCategoryAdapter.notifyDataSetChanged();
      }
    }
  }

  public final void onScroll(ColumnGridView paramColumnGridView, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5)
  {
  }

  public final void onScrollStateChanged(ColumnGridView paramColumnGridView, int paramInt)
  {
    if (paramInt == 1)
      requestDisallowInterceptTouchEvent(true);
  }

  public void setAdapter(SuggestionGridAdapter paramSuggestionGridAdapter)
  {
    if (this.mAdapter != null)
      this.mAdapter.unregisterDataSetObserver(this.mObserver);
    this.mAdapter = paramSuggestionGridAdapter;
    this.mAdapter.registerDataSetObserver(this.mObserver);
  }

  public void setScrollPositions(ScrollPositions paramScrollPositions)
  {
    Iterator localIterator = this.mRows.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      String str = (String)localEntry.getKey();
      Integer localInteger1 = (Integer)paramScrollPositions.positions.get(str);
      Integer localInteger2 = (Integer)paramScrollPositions.offsets.get(str);
      if ((localInteger1 != null) && (localInteger2 != null))
        ((ColumnGridView)localEntry.getValue()).setSelectionFromTop(localInteger1.intValue(), localInteger2.intValue());
    }
  }

  public static class ScrollPositions
    implements Parcelable
  {
    public static final Parcelable.Creator<ScrollPositions> CREATOR = new Parcelable.Creator()
    {
    };
    private HashMap<String, Integer> offsets = new HashMap();
    private HashMap<String, Integer> positions = new HashMap();

    public ScrollPositions()
    {
    }

    public ScrollPositions(Parcel paramParcel)
    {
      int i = paramParcel.readInt();
      for (int j = 0; j < i; j++)
        setScrollPosition(paramParcel.readString(), paramParcel.readInt(), paramParcel.readInt());
    }

    public int describeContents()
    {
      return 0;
    }

    public final void setScrollPosition(String paramString, int paramInt1, int paramInt2)
    {
      this.positions.put(paramString, Integer.valueOf(paramInt1));
      this.offsets.put(paramString, Integer.valueOf(paramInt2));
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      paramParcel.writeInt(this.positions.size());
      Iterator localIterator = this.positions.entrySet().iterator();
      while (localIterator.hasNext())
      {
        Map.Entry localEntry = (Map.Entry)localIterator.next();
        String str = (String)localEntry.getKey();
        paramParcel.writeString(str);
        paramParcel.writeInt(((Integer)localEntry.getValue()).intValue());
        paramParcel.writeInt(((Integer)this.offsets.get(str)).intValue());
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.SuggestionGridView
 * JD-Core Version:    0.6.2
 */