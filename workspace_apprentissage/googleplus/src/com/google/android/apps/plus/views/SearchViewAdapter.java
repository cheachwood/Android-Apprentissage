package com.google.android.apps.plus.views;

import android.os.Build.VERSION;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.util.SoftInput;
import java.util.ArrayList;
import java.util.Iterator;

public class SearchViewAdapter
  implements TextWatcher, TextView.OnEditorActionListener
{
  protected final ArrayList<OnQueryChangeListener> mListeners = new ArrayList();
  protected boolean mRequestFocus = true;
  private TextView mSearchView;

  protected SearchViewAdapter(View paramView)
  {
    this.mSearchView = ((TextView)paramView);
    if (this.mSearchView != null)
    {
      this.mSearchView.addTextChangedListener(this);
      this.mSearchView.setOnEditorActionListener(this);
      View localView1 = (View)this.mSearchView.getParent();
      if (localView1 != null)
      {
        View localView2 = localView1.findViewById(R.id.search_go_btn);
        if (localView2 != null)
          localView2.setOnClickListener(new View.OnClickListener()
          {
            public final void onClick(View paramAnonymousView)
            {
              SearchViewAdapter.this.onQueryTextSubmit(SearchViewAdapter.this.mSearchView.getText().toString());
              SoftInput.hide(SearchViewAdapter.this.mSearchView);
            }
          });
      }
    }
  }

  public static SearchViewAdapter createInstance(View paramView)
  {
    Object localObject;
    if (Build.VERSION.SDK_INT >= 12)
      localObject = new SearchViewAdapterV12(paramView);
    while (true)
    {
      return localObject;
      if (Build.VERSION.SDK_INT >= 11)
        localObject = new SearchViewAdapterV11(paramView);
      else
        localObject = new SearchViewAdapter(paramView);
    }
  }

  public void addOnChangeListener(OnQueryChangeListener paramOnQueryChangeListener)
  {
    this.mListeners.add(paramOnQueryChangeListener);
  }

  public void afterTextChanged(Editable paramEditable)
  {
  }

  public void beforeTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
  }

  public void hideSoftInput()
  {
    SoftInput.hide(this.mSearchView);
  }

  public boolean onEditorAction(TextView paramTextView, int paramInt, KeyEvent paramKeyEvent)
  {
    if (((paramInt == 6) || (paramInt == 3)) && (this.mSearchView == paramTextView))
    {
      onQueryTextSubmit(this.mSearchView.getText().toString());
      SoftInput.hide(this.mSearchView);
    }
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public boolean onQueryTextChange(String paramString)
  {
    Iterator localIterator = this.mListeners.iterator();
    while (localIterator.hasNext())
      ((OnQueryChangeListener)localIterator.next()).onQueryTextChanged(paramString);
    return false;
  }

  public boolean onQueryTextSubmit(String paramString)
  {
    Iterator localIterator = this.mListeners.iterator();
    while (localIterator.hasNext())
      ((OnQueryChangeListener)localIterator.next()).onQueryTextSubmitted(paramString);
    return false;
  }

  public void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    onQueryTextChange(paramCharSequence.toString());
  }

  public void requestFocus(boolean paramBoolean)
  {
    this.mRequestFocus = paramBoolean;
  }

  public void setQueryHint(int paramInt)
  {
    this.mSearchView.setHint(paramInt);
  }

  public void setQueryText(String paramString)
  {
    this.mSearchView.setText(paramString);
    if (this.mRequestFocus)
      this.mSearchView.requestFocus();
  }

  public void setVisible(boolean paramBoolean)
  {
    setVisible(paramBoolean, this.mSearchView);
  }

  protected final void setVisible(boolean paramBoolean, View paramView)
  {
    int j;
    if (paramView != null)
    {
      int i = paramView.getVisibility();
      if (!paramBoolean)
        break label37;
      j = 0;
      if (i != j)
      {
        paramView.setVisibility(j);
        if (!paramBoolean)
          break label44;
        showSoftInput();
      }
    }
    while (true)
    {
      return;
      label37: j = 8;
      break;
      label44: SoftInput.hide(paramView);
      if (paramView.hasFocus())
        paramView.getRootView().requestFocus();
    }
  }

  protected void showSoftInput()
  {
    this.mSearchView.requestFocus();
    this.mSearchView.postDelayed(new Runnable()
    {
      public final void run()
      {
        SoftInput.show(SearchViewAdapter.this.mSearchView);
      }
    }
    , 50L);
  }

  public static abstract interface OnQueryChangeListener
  {
    public abstract void onQueryClose();

    public abstract void onQueryTextChanged(CharSequence paramCharSequence);

    public abstract void onQueryTextSubmitted(CharSequence paramCharSequence);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.views.SearchViewAdapter
 * JD-Core Version:    0.6.2
 */