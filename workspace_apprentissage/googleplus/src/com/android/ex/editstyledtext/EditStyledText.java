package com.android.ex.editstyledtext;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.content.DialogInterface.OnClickListener;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RectShape;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.text.ClipboardManager;
import android.text.Editable;
import android.text.Html;
import android.text.Html.ImageGetter;
import android.text.Html.TagHandler;
import android.text.Layout;
import android.text.Layout.Alignment;
import android.text.NoCopySpan.Concrete;
import android.text.Selection;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.ArrowKeyMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.AlignmentSpan;
import android.text.style.AlignmentSpan.Standard;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.ParagraphStyle;
import android.text.style.QuoteSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.MenuItem.OnMenuItemClickListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.BaseSavedState;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import java.io.InputStream;
import java.util.HashMap;

public class EditStyledText extends EditText
{
  private static final NoCopySpan.Concrete SELECTING = new NoCopySpan.Concrete();
  private static CharSequence STR_CLEARSTYLES;
  private static CharSequence STR_HORIZONTALLINE;
  private static CharSequence STR_PASTE;
  private StyledTextConverter mConverter;
  private Drawable mDefaultBackground;
  private StyledTextDialog mDialog;
  private InputConnection mInputConnection;
  private EditorManager mManager;
  private float mPaddingScale = 0.0F;

  public EditStyledText(Context paramContext)
  {
    super(paramContext);
    init();
  }

  public EditStyledText(Context paramContext, AttributeSet paramAttributeSet)
  {
    super(paramContext, paramAttributeSet);
    init();
  }

  public EditStyledText(Context paramContext, AttributeSet paramAttributeSet, int paramInt)
  {
    super(paramContext, paramAttributeSet, paramInt);
    init();
  }

  private int dipToPx(int paramInt)
  {
    if (this.mPaddingScale <= 0.0F)
      this.mPaddingScale = getContext().getResources().getDisplayMetrics().density;
    float f = paramInt;
    if (this.mPaddingScale <= 0.0F)
      this.mPaddingScale = getContext().getResources().getDisplayMetrics().density;
    return (int)(0.5D + f * this.mPaddingScale);
  }

  private void init()
  {
    this.mConverter = new StyledTextConverter(this, new StyledTextHtmlStandard((byte)0));
    this.mDialog = new StyledTextDialog(this);
    this.mManager = new EditorManager(this, this.mDialog);
    setMovementMethod(new StyledTextArrowKeyMethod(this.mManager));
    this.mDefaultBackground = getBackground();
    requestFocus();
  }

  private void onStartCopy()
  {
    this.mManager.onAction(1);
  }

  private void onStartCut()
  {
    this.mManager.onAction(7);
  }

  protected void drawableStateChanged()
  {
    super.drawableStateChanged();
    if (this.mManager != null)
      this.mManager.onRefreshStyles();
  }

  public final int getBackgroundColor()
  {
    return this.mManager.getBackgroundColor();
  }

  public final int getForegroundColor(int paramInt)
  {
    int i = -16777216;
    if ((paramInt < 0) || (paramInt > getText().length()));
    while (true)
    {
      return i;
      ForegroundColorSpan[] arrayOfForegroundColorSpan = (ForegroundColorSpan[])getText().getSpans(paramInt, paramInt, ForegroundColorSpan.class);
      if (arrayOfForegroundColorSpan.length > 0)
        i = arrayOfForegroundColorSpan[0].getForegroundColor();
    }
  }

  public final boolean isButtonsFocused()
  {
    return false;
  }

  public final boolean isEditting()
  {
    return this.mManager.isEditting();
  }

  public final boolean isSoftKeyBlocked()
  {
    return this.mManager.isSoftKeyBlocked();
  }

  protected void onCreateContextMenu(ContextMenu paramContextMenu)
  {
    super.onCreateContextMenu(paramContextMenu);
    MenuHandler localMenuHandler = new MenuHandler((byte)0);
    if (STR_HORIZONTALLINE != null)
      paramContextMenu.add(0, 16776961, 0, STR_HORIZONTALLINE).setOnMenuItemClickListener(localMenuHandler);
    if ((this.mManager.isStyledText()) && (STR_CLEARSTYLES != null))
      paramContextMenu.add(0, 16776962, 0, STR_CLEARSTYLES).setOnMenuItemClickListener(localMenuHandler);
    if (this.mManager.canPaste())
      paramContextMenu.add(0, 16908322, 0, STR_PASTE).setOnMenuItemClickListener(localMenuHandler).setAlphabeticShortcut('v');
  }

  public InputConnection onCreateInputConnection(EditorInfo paramEditorInfo)
  {
    this.mInputConnection = new StyledTextInputConnection(super.onCreateInputConnection(paramEditorInfo), this);
    return this.mInputConnection;
  }

  public final void onEndEdit()
  {
    this.mManager.onAction(21);
  }

  protected void onFocusChanged(boolean paramBoolean, int paramInt, Rect paramRect)
  {
    super.onFocusChanged(paramBoolean, paramInt, paramRect);
    if (paramBoolean)
      onStartEdit();
    while (true)
    {
      return;
      onEndEdit();
    }
  }

  public void onRestoreInstanceState(Parcelable paramParcelable)
  {
    if (!(paramParcelable instanceof SavedStyledTextState))
      super.onRestoreInstanceState(paramParcelable);
    while (true)
    {
      return;
      SavedStyledTextState localSavedStyledTextState = (SavedStyledTextState)paramParcelable;
      super.onRestoreInstanceState(localSavedStyledTextState.getSuperState());
      setBackgroundColor(localSavedStyledTextState.mBackgroundColor);
    }
  }

  public Parcelable onSaveInstanceState()
  {
    SavedStyledTextState localSavedStyledTextState = new SavedStyledTextState(super.onSaveInstanceState());
    localSavedStyledTextState.mBackgroundColor = this.mManager.getBackgroundColor();
    return localSavedStyledTextState;
  }

  public final void onStartEdit()
  {
    this.mManager.onAction(20);
  }

  protected void onTextChanged(CharSequence paramCharSequence, int paramInt1, int paramInt2, int paramInt3)
  {
    if (this.mManager != null)
    {
      this.mManager.updateSpanNextToCursor(getText(), paramInt1, paramInt2, paramInt3);
      this.mManager.updateSpanPreviousFromCursor(getText(), paramInt1, paramInt2, paramInt3);
      if (paramInt3 <= paramInt2)
        break label95;
      this.mManager.setTextComposingMask(paramInt1, paramInt1 + paramInt3);
      if (this.mManager.isWaitInput())
      {
        if (paramInt3 <= paramInt2)
          break label111;
        this.mManager.onCursorMoved();
        this.mManager.onFixSelectedItem();
      }
    }
    while (true)
    {
      super.onTextChanged(paramCharSequence, paramInt1, paramInt2, paramInt3);
      return;
      label95: if (paramInt2 >= paramInt3)
        break;
      this.mManager.unsetTextComposingMask();
      break;
      label111: if (paramInt3 < paramInt2)
        this.mManager.onAction(22);
    }
  }

  public boolean onTextContextMenuItem(int paramInt)
  {
    boolean bool1 = true;
    boolean bool2;
    if (getSelectionStart() != getSelectionEnd())
    {
      bool2 = bool1;
      switch (paramInt)
      {
      default:
        label108: bool1 = super.onTextContextMenuItem(paramInt);
      case 16908319:
      case 16908328:
      case 16908329:
      case 16908322:
      case 16908321:
      case 16908320:
      case 16776961:
      case 16776962:
      case 16776963:
      case 16776964:
      }
    }
    while (true)
    {
      return bool1;
      bool2 = false;
      break;
      this.mManager.onStartSelectAll(bool1);
      continue;
      this.mManager.onStartSelect(bool1);
      this.mManager.blockSoftKey();
      break label108;
      this.mManager.onFixSelectedItem();
      break label108;
      this.mManager.onAction(2);
      continue;
      if (bool2)
      {
        onStartCopy();
      }
      else
      {
        this.mManager.onStartSelectAll(false);
        onStartCopy();
        continue;
        if (bool2)
        {
          onStartCut();
        }
        else
        {
          this.mManager.onStartSelectAll(false);
          onStartCut();
          continue;
          this.mManager.onAction(12);
          continue;
          this.mManager.onClearStyles();
          continue;
          onStartEdit();
          continue;
          onEndEdit();
        }
      }
    }
  }

  public boolean onTouchEvent(MotionEvent paramMotionEvent)
  {
    int i;
    int j;
    boolean bool1;
    if (paramMotionEvent.getAction() == 1)
    {
      cancelLongPress();
      boolean bool2 = this.mManager.isEditting();
      if (!bool2)
        onStartEdit();
      i = Selection.getSelectionStart(getText());
      j = Selection.getSelectionEnd(getText());
      bool1 = super.onTouchEvent(paramMotionEvent);
      if ((isFocused()) && (this.mManager.getSelectState() == 0))
      {
        if (bool2)
          this.mManager.showSoftKey(Selection.getSelectionStart(getText()), Selection.getSelectionEnd(getText()));
      }
      else
      {
        this.mManager.onCursorMoved();
        this.mManager.unsetTextComposingMask();
      }
    }
    while (true)
    {
      return bool1;
      this.mManager.showSoftKey(i, j);
      break;
      bool1 = super.onTouchEvent(paramMotionEvent);
    }
  }

  public void setAlignAlertParams(CharSequence paramCharSequence, CharSequence[] paramArrayOfCharSequence)
  {
    this.mDialog.setAlignAlertParams(paramCharSequence, paramArrayOfCharSequence);
  }

  public void setAlignment(Layout.Alignment paramAlignment)
  {
    this.mManager.setAlignment(paramAlignment);
  }

  public void setBackgroundColor(int paramInt)
  {
    if (paramInt != 16777215)
      super.setBackgroundColor(paramInt);
    while (true)
    {
      this.mManager.setBackgroundColor(paramInt);
      this.mManager.onRefreshStyles();
      return;
      setBackgroundDrawable(this.mDefaultBackground);
    }
  }

  public void setBuilder(AlertDialog.Builder paramBuilder)
  {
    this.mDialog.setBuilder(paramBuilder);
  }

  public void setColorAlertParams(CharSequence paramCharSequence1, CharSequence[] paramArrayOfCharSequence1, CharSequence[] paramArrayOfCharSequence2, CharSequence paramCharSequence2)
  {
    this.mDialog.setColorAlertParams(paramCharSequence1, paramArrayOfCharSequence1, paramArrayOfCharSequence2, paramCharSequence2);
  }

  public void setContextMenuStrings(CharSequence paramCharSequence1, CharSequence paramCharSequence2, CharSequence paramCharSequence3)
  {
    STR_HORIZONTALLINE = paramCharSequence1;
    STR_CLEARSTYLES = paramCharSequence2;
    STR_PASTE = paramCharSequence3;
  }

  public void setHtml(String paramString)
  {
    this.mConverter.SetHtml(paramString);
  }

  public void setItemColor(int paramInt)
  {
    this.mManager.setItemColor(paramInt, true);
  }

  public void setItemSize(int paramInt)
  {
    this.mManager.setItemSize(paramInt, true);
  }

  public void setMarquee(int paramInt)
  {
    this.mManager.setMarquee(paramInt);
  }

  public void setMarqueeAlertParams(CharSequence paramCharSequence, CharSequence[] paramArrayOfCharSequence)
  {
    this.mDialog.setMarqueeAlertParams(paramCharSequence, paramArrayOfCharSequence);
  }

  public void setSizeAlertParams(CharSequence paramCharSequence, CharSequence[] paramArrayOfCharSequence1, CharSequence[] paramArrayOfCharSequence2, CharSequence[] paramArrayOfCharSequence3)
  {
    this.mDialog.setSizeAlertParams(paramCharSequence, paramArrayOfCharSequence1, paramArrayOfCharSequence2, paramArrayOfCharSequence3);
  }

  public void setStyledTextHtmlConverter(StyledTextHtmlConverter paramStyledTextHtmlConverter)
  {
    this.mConverter.setStyledTextHtmlConverter(paramStyledTextHtmlConverter);
  }

  public static final class ColorPaletteDrawable extends ShapeDrawable
  {
    private Rect mRect;

    public ColorPaletteDrawable(int paramInt1, int paramInt2, int paramInt3, int paramInt4)
    {
      super();
      this.mRect = new Rect(paramInt4, paramInt4, paramInt2 - paramInt4, paramInt3 - paramInt4);
      getPaint().setColor(paramInt1);
    }

    public final void draw(Canvas paramCanvas)
    {
      paramCanvas.drawRect(this.mRect, getPaint());
    }
  }

  public final class EditModeActions
  {
    private HashMap<Integer, EditModeActionBase> mActionMap = new HashMap();
    private AlignAction mAlignAction = new AlignAction();
    private BackgroundColorAction mBackgroundColorAction = new BackgroundColorAction();
    private CancelAction mCancelEditAction = new CancelAction();
    private ClearStylesAction mClearStylesAction = new ClearStylesAction();
    private ColorAction mColorAction = new ColorAction();
    private CopyAction mCopyAction = new CopyAction();
    private CutAction mCutAction = new CutAction();
    private EditStyledText.StyledTextDialog mDialog;
    private EditStyledText mEST;
    private EndEditAction mEndEditAction = new EndEditAction();
    private HorizontalLineAction mHorizontalLineAction = new HorizontalLineAction();
    private ImageAction mImageAction = new ImageAction();
    private EditStyledText.EditorManager mManager;
    private MarqueeDialogAction mMarqueeDialogAction = new MarqueeDialogAction();
    private int mMode = 0;
    private NothingAction mNothingAction = new NothingAction();
    private PasteAction mPasteAction = new PasteAction();
    private PreviewAction mPreviewAction = new PreviewAction();
    private ResetAction mResetAction = new ResetAction();
    private SelectAction mSelectAction = new SelectAction();
    private SelectAllAction mSelectAllAction = new SelectAllAction();
    private ShowMenuAction mShowMenuAction = new ShowMenuAction();
    private SizeAction mSizeAction = new SizeAction();
    private StartEditAction mStartEditAction = new StartEditAction();
    private StopSelectionAction mStopSelectionAction = new StopSelectionAction();
    private SwingAction mSwingAction = new SwingAction();
    private TelopAction mTelopAction = new TelopAction();
    private TextViewAction mTextViewAction = new TextViewAction();

    EditModeActions(EditStyledText paramEditorManager, EditStyledText.EditorManager paramStyledTextDialog, EditStyledText.StyledTextDialog arg4)
    {
      this.mEST = paramEditorManager;
      this.mManager = paramStyledTextDialog;
      Object localObject;
      this.mDialog = localObject;
      this.mActionMap.put(Integer.valueOf(0), this.mNothingAction);
      this.mActionMap.put(Integer.valueOf(1), this.mCopyAction);
      this.mActionMap.put(Integer.valueOf(2), this.mPasteAction);
      this.mActionMap.put(Integer.valueOf(5), this.mSelectAction);
      this.mActionMap.put(Integer.valueOf(7), this.mCutAction);
      this.mActionMap.put(Integer.valueOf(11), this.mSelectAllAction);
      this.mActionMap.put(Integer.valueOf(12), this.mHorizontalLineAction);
      this.mActionMap.put(Integer.valueOf(13), this.mStopSelectionAction);
      this.mActionMap.put(Integer.valueOf(14), this.mClearStylesAction);
      this.mActionMap.put(Integer.valueOf(15), this.mImageAction);
      this.mActionMap.put(Integer.valueOf(16), this.mBackgroundColorAction);
      this.mActionMap.put(Integer.valueOf(17), this.mPreviewAction);
      this.mActionMap.put(Integer.valueOf(18), this.mCancelEditAction);
      this.mActionMap.put(Integer.valueOf(19), this.mTextViewAction);
      this.mActionMap.put(Integer.valueOf(20), this.mStartEditAction);
      this.mActionMap.put(Integer.valueOf(21), this.mEndEditAction);
      this.mActionMap.put(Integer.valueOf(22), this.mResetAction);
      this.mActionMap.put(Integer.valueOf(23), this.mShowMenuAction);
      this.mActionMap.put(Integer.valueOf(6), this.mAlignAction);
      this.mActionMap.put(Integer.valueOf(8), this.mTelopAction);
      this.mActionMap.put(Integer.valueOf(9), this.mSwingAction);
      this.mActionMap.put(Integer.valueOf(10), this.mMarqueeDialogAction);
      this.mActionMap.put(Integer.valueOf(4), this.mColorAction);
      this.mActionMap.put(Integer.valueOf(3), this.mSizeAction);
    }

    private EditModeActionBase getAction(int paramInt)
    {
      if (this.mActionMap.containsKey(Integer.valueOf(paramInt)));
      for (EditModeActionBase localEditModeActionBase = (EditModeActionBase)this.mActionMap.get(Integer.valueOf(paramInt)); ; localEditModeActionBase = null)
        return localEditModeActionBase;
    }

    public final boolean doNext()
    {
      return doNext(this.mMode);
    }

    public final boolean doNext(int paramInt)
    {
      boolean bool = false;
      Log.d("EditModeActions", "--- do the next action: " + paramInt + "," + this.mManager.getSelectState());
      EditModeActionBase localEditModeActionBase = getAction(paramInt);
      if (localEditModeActionBase == null)
        Log.e("EditModeActions", "--- invalid action error.");
      while (true)
      {
        return bool;
        switch (this.mManager.getSelectState())
        {
        default:
          bool = false;
          break;
        case 0:
          bool = localEditModeActionBase.doNotSelected();
          break;
        case 1:
          bool = localEditModeActionBase.doStartPosIsSelected();
          break;
        case 2:
          bool = localEditModeActionBase.doEndPosIsSelected();
          break;
        case 3:
          if (this.mManager.isWaitInput())
            bool = localEditModeActionBase.doSelectionIsFixedAndWaitingInput();
          else
            bool = localEditModeActionBase.doSelectionIsFixed();
          break;
        }
      }
    }

    public final void onAction(int paramInt)
    {
      getAction(paramInt).addParams(null);
      this.mMode = paramInt;
      doNext(paramInt);
    }

    public final void onSelectAction()
    {
      doNext(5);
    }

    public final class AlignAction extends EditStyledText.EditModeActions.SetSpanActionBase
    {
      public AlignAction()
      {
        super();
      }

      protected final boolean doSelectionIsFixed()
      {
        if (super.doSelectionIsFixed());
        while (true)
        {
          return true;
          EditStyledText.StyledTextDialog.access$4600(EditStyledText.EditModeActions.this.mDialog);
        }
      }
    }

    public final class BackgroundColorAction extends EditStyledText.EditModeActions.EditModeActionBase
    {
      public BackgroundColorAction()
      {
        super();
      }

      protected final boolean doNotSelected()
      {
        EditStyledText.StyledTextDialog.access$4000(EditStyledText.EditModeActions.this.mDialog);
        return true;
      }
    }

    public final class CancelAction extends EditStyledText.EditModeActions.EditModeActionBase
    {
      public CancelAction()
      {
        super();
      }

      protected final boolean doNotSelected()
      {
        EditStyledText.access$3500(EditStyledText.this);
        return true;
      }
    }

    public final class ClearStylesAction extends EditStyledText.EditModeActions.EditModeActionBase
    {
      public ClearStylesAction()
      {
        super();
      }

      protected final boolean doNotSelected()
      {
        EditStyledText.EditorManager.access$3300(EditStyledText.EditModeActions.this.mManager);
        return true;
      }
    }

    public final class ColorAction extends EditStyledText.EditModeActions.SetSpanActionBase
    {
      public ColorAction()
      {
        super();
      }

      protected final boolean doSelectionIsFixed()
      {
        if (super.doSelectionIsFixed());
        while (true)
        {
          return true;
          EditStyledText.StyledTextDialog.access$4800(EditStyledText.EditModeActions.this.mDialog);
        }
      }

      protected final boolean doSelectionIsFixedAndWaitingInput()
      {
        if (super.doSelectionIsFixedAndWaitingInput());
        while (true)
        {
          return true;
          int i = EditStyledText.EditModeActions.this.mManager.getSizeWaitInput();
          EditStyledText.EditModeActions.this.mManager.setItemColor(EditStyledText.EditModeActions.this.mManager.getColorWaitInput(), false);
          if (!EditStyledText.EditModeActions.this.mManager.isWaitInput())
          {
            EditStyledText.EditModeActions.this.mManager.setItemSize(i, false);
            EditStyledText.EditorManager.access$2400(EditStyledText.EditModeActions.this.mManager);
          }
          else
          {
            fixSelection();
            EditStyledText.StyledTextDialog.access$4800(EditStyledText.EditModeActions.this.mDialog);
          }
        }
      }
    }

    public final class CopyAction extends EditStyledText.EditModeActions.TextViewActionBase
    {
      public CopyAction()
      {
        super();
      }

      protected final boolean doEndPosIsSelected()
      {
        if (super.doEndPosIsSelected());
        while (true)
        {
          return true;
          EditStyledText.EditorManager.access$2500(EditStyledText.EditModeActions.this.mManager);
          EditStyledText.EditorManager.access$2400(EditStyledText.EditModeActions.this.mManager);
        }
      }
    }

    public final class CutAction extends EditStyledText.EditModeActions.TextViewActionBase
    {
      public CutAction()
      {
        super();
      }

      protected final boolean doEndPosIsSelected()
      {
        if (super.doEndPosIsSelected());
        while (true)
        {
          return true;
          EditStyledText.EditorManager.access$2600(EditStyledText.EditModeActions.this.mManager);
          EditStyledText.EditorManager.access$2400(EditStyledText.EditModeActions.this.mManager);
        }
      }
    }

    public class EditModeActionBase
    {
      private Object[] mParams;

      public EditModeActionBase()
      {
      }

      protected final void addParams(Object[] paramArrayOfObject)
      {
        this.mParams = paramArrayOfObject;
      }

      protected boolean doEndPosIsSelected()
      {
        return doStartPosIsSelected();
      }

      protected boolean doNotSelected()
      {
        return false;
      }

      protected boolean doSelectionIsFixed()
      {
        return doEndPosIsSelected();
      }

      protected boolean doSelectionIsFixedAndWaitingInput()
      {
        return doEndPosIsSelected();
      }

      protected boolean doStartPosIsSelected()
      {
        return doNotSelected();
      }

      protected final boolean fixSelection()
      {
        EditStyledText.access$1900(EditStyledText.this);
        EditStyledText.EditorManager.access$2100(EditStyledText.EditModeActions.this.mManager, 3);
        return true;
      }

      protected final Object getParam(int paramInt)
      {
        if ((this.mParams == null) || (this.mParams.length < 0))
          Log.d("EditModeActions", "--- Number of the parameter is out of bound.");
        for (Object localObject = null; ; localObject = this.mParams[0])
          return localObject;
      }
    }

    public final class EndEditAction extends EditStyledText.EditModeActions.EditModeActionBase
    {
      public EndEditAction()
      {
        super();
      }

      protected final boolean doNotSelected()
      {
        EditStyledText.EditorManager.access$4300(EditStyledText.EditModeActions.this.mManager);
        return true;
      }
    }

    public final class HorizontalLineAction extends EditStyledText.EditModeActions.EditModeActionBase
    {
      public HorizontalLineAction()
      {
        super();
      }

      protected final boolean doNotSelected()
      {
        EditStyledText.EditorManager.access$3200(EditStyledText.EditModeActions.this.mManager);
        return true;
      }
    }

    public final class ImageAction extends EditStyledText.EditModeActions.EditModeActionBase
    {
      public ImageAction()
      {
        super();
      }

      protected final boolean doNotSelected()
      {
        Object localObject = getParam(0);
        if (localObject != null)
          if ((localObject instanceof Uri))
            EditStyledText.EditorManager.access$3600(EditStyledText.EditModeActions.this.mManager, (Uri)localObject);
        while (true)
        {
          return true;
          if ((localObject instanceof Integer))
          {
            EditStyledText.EditorManager.access$3700(EditStyledText.EditModeActions.this.mManager, ((Integer)localObject).intValue());
            continue;
            EditStyledText.access$3800(EditStyledText.this);
          }
        }
      }
    }

    public final class MarqueeDialogAction extends EditStyledText.EditModeActions.SetSpanActionBase
    {
      public MarqueeDialogAction()
      {
        super();
      }

      protected final boolean doSelectionIsFixed()
      {
        if (super.doSelectionIsFixed());
        while (true)
        {
          return true;
          EditStyledText.StyledTextDialog.access$4700(EditStyledText.EditModeActions.this.mDialog);
        }
      }
    }

    public final class NothingAction extends EditStyledText.EditModeActions.EditModeActionBase
    {
      public NothingAction()
      {
        super();
      }
    }

    public final class PasteAction extends EditStyledText.EditModeActions.EditModeActionBase
    {
      public PasteAction()
      {
        super();
      }

      protected final boolean doNotSelected()
      {
        EditStyledText.EditorManager.access$3000(EditStyledText.EditModeActions.this.mManager);
        EditStyledText.EditorManager.access$2400(EditStyledText.EditModeActions.this.mManager);
        return true;
      }
    }

    public final class PreviewAction extends EditStyledText.EditModeActions.EditModeActionBase
    {
      public PreviewAction()
      {
        super();
      }

      protected final boolean doNotSelected()
      {
        EditStyledText.access$4100(EditStyledText.this);
        return true;
      }
    }

    public final class ResetAction extends EditStyledText.EditModeActions.EditModeActionBase
    {
      public ResetAction()
      {
        super();
      }

      protected final boolean doNotSelected()
      {
        EditStyledText.EditorManager.access$2400(EditStyledText.EditModeActions.this.mManager);
        return true;
      }
    }

    public final class SelectAction extends EditStyledText.EditModeActions.EditModeActionBase
    {
      public SelectAction()
      {
        super();
      }

      protected final boolean doNotSelected()
      {
        if (EditStyledText.EditorManager.access$2700(EditStyledText.EditModeActions.this.mManager))
          Log.e("EditModeActions", "Selection is off, but selected");
        EditStyledText.EditorManager.access$2800(EditStyledText.EditModeActions.this.mManager);
        EditStyledText.access$900(EditStyledText.this, 3);
        return true;
      }

      protected final boolean doSelectionIsFixed()
      {
        return false;
      }

      protected final boolean doStartPosIsSelected()
      {
        if (EditStyledText.EditorManager.access$2700(EditStyledText.EditModeActions.this.mManager))
          Log.e("EditModeActions", "Selection now start, but selected");
        EditStyledText.EditorManager.access$2900(EditStyledText.EditModeActions.this.mManager);
        EditStyledText.access$900(EditStyledText.this, 4);
        if (EditStyledText.EditModeActions.this.mManager.getEditMode() != 5)
          EditStyledText.EditModeActions.this.doNext(EditStyledText.EditModeActions.this.mManager.getEditMode());
        return true;
      }
    }

    public final class SelectAllAction extends EditStyledText.EditModeActions.EditModeActionBase
    {
      public SelectAllAction()
      {
        super();
      }

      protected final boolean doNotSelected()
      {
        EditStyledText.EditorManager.access$3100(EditStyledText.EditModeActions.this.mManager);
        return true;
      }
    }

    public class SetSpanActionBase extends EditStyledText.EditModeActions.EditModeActionBase
    {
      public SetSpanActionBase()
      {
        super();
      }

      protected final boolean doEndPosIsSelected()
      {
        if ((EditStyledText.EditModeActions.this.mManager.getEditMode() == 0) || (EditStyledText.EditModeActions.this.mManager.getEditMode() == 5))
        {
          EditStyledText.EditorManager.access$2300(EditStyledText.EditModeActions.this.mManager, EditStyledText.EditModeActions.this.mMode);
          fixSelection();
          EditStyledText.EditModeActions.this.doNext();
        }
        for (boolean bool = true; ; bool = doStartPosIsSelected())
          return bool;
      }

      protected final boolean doNotSelected()
      {
        boolean bool = true;
        if ((EditStyledText.EditModeActions.this.mManager.getEditMode() == 0) || (EditStyledText.EditModeActions.this.mManager.getEditMode() == 5))
        {
          EditStyledText.EditorManager.access$2300(EditStyledText.EditModeActions.this.mManager, EditStyledText.EditModeActions.this.mMode);
          EditStyledText.EditorManager.access$4500(EditStyledText.EditModeActions.this.mManager, EditStyledText.this.getSelectionStart(), EditStyledText.this.getSelectionEnd());
          fixSelection();
          EditStyledText.EditModeActions.this.doNext();
        }
        while (true)
        {
          return bool;
          if (EditStyledText.EditModeActions.this.mManager.getEditMode() != EditStyledText.EditModeActions.this.mMode)
          {
            Log.d("EditModeActions", "--- setspanactionbase" + EditStyledText.EditModeActions.this.mManager.getEditMode() + "," + EditStyledText.EditModeActions.this.mMode);
            if (!EditStyledText.EditModeActions.this.mManager.isWaitInput())
            {
              EditStyledText.EditorManager.access$2400(EditStyledText.EditModeActions.this.mManager);
              EditStyledText.EditorManager.access$2300(EditStyledText.EditModeActions.this.mManager, EditStyledText.EditModeActions.this.mMode);
            }
            while (true)
            {
              EditStyledText.EditModeActions.this.doNext();
              break;
              EditStyledText.EditorManager.access$2300(EditStyledText.EditModeActions.this.mManager, 0);
              EditStyledText.EditorManager.access$2100(EditStyledText.EditModeActions.this.mManager, 0);
            }
          }
          bool = false;
        }
      }

      protected boolean doSelectionIsFixed()
      {
        if (doEndPosIsSelected());
        for (boolean bool = true; ; bool = false)
        {
          return bool;
          EditStyledText.access$900(EditStyledText.this, 0);
        }
      }

      protected final boolean doStartPosIsSelected()
      {
        if ((EditStyledText.EditModeActions.this.mManager.getEditMode() == 0) || (EditStyledText.EditModeActions.this.mManager.getEditMode() == 5))
        {
          EditStyledText.EditorManager.access$2300(EditStyledText.EditModeActions.this.mManager, EditStyledText.EditModeActions.this.mMode);
          EditStyledText.EditModeActions.this.onSelectAction();
        }
        for (boolean bool = true; ; bool = doNotSelected())
          return bool;
      }
    }

    public final class ShowMenuAction extends EditStyledText.EditModeActions.EditModeActionBase
    {
      public ShowMenuAction()
      {
        super();
      }

      protected final boolean doNotSelected()
      {
        EditStyledText.access$4400(EditStyledText.this);
        return true;
      }
    }

    public final class SizeAction extends EditStyledText.EditModeActions.SetSpanActionBase
    {
      public SizeAction()
      {
        super();
      }

      protected final boolean doSelectionIsFixed()
      {
        if (super.doSelectionIsFixed());
        while (true)
        {
          return true;
          EditStyledText.StyledTextDialog.access$4900(EditStyledText.EditModeActions.this.mDialog);
        }
      }

      protected final boolean doSelectionIsFixedAndWaitingInput()
      {
        if (super.doSelectionIsFixedAndWaitingInput());
        while (true)
        {
          return true;
          int i = EditStyledText.EditModeActions.this.mManager.getColorWaitInput();
          EditStyledText.EditModeActions.this.mManager.setItemSize(EditStyledText.EditModeActions.this.mManager.getSizeWaitInput(), false);
          if (!EditStyledText.EditModeActions.this.mManager.isWaitInput())
          {
            EditStyledText.EditModeActions.this.mManager.setItemColor(i, false);
            EditStyledText.EditorManager.access$2400(EditStyledText.EditModeActions.this.mManager);
          }
          else
          {
            fixSelection();
            EditStyledText.StyledTextDialog.access$4900(EditStyledText.EditModeActions.this.mDialog);
          }
        }
      }
    }

    public final class StartEditAction extends EditStyledText.EditModeActions.EditModeActionBase
    {
      public StartEditAction()
      {
        super();
      }

      protected final boolean doNotSelected()
      {
        EditStyledText.EditorManager.access$4200(EditStyledText.EditModeActions.this.mManager);
        return true;
      }
    }

    public final class StopSelectionAction extends EditStyledText.EditModeActions.EditModeActionBase
    {
      public StopSelectionAction()
      {
        super();
      }

      protected final boolean doNotSelected()
      {
        EditStyledText.EditorManager.access$3400(EditStyledText.EditModeActions.this.mManager);
        return true;
      }
    }

    public final class SwingAction extends EditStyledText.EditModeActions.SetSpanActionBase
    {
      public SwingAction()
      {
        super();
      }

      protected final boolean doSelectionIsFixed()
      {
        if (super.doSelectionIsFixed());
        while (true)
        {
          return true;
          EditStyledText.EditModeActions.this.mManager.setSwing();
        }
      }
    }

    public final class TelopAction extends EditStyledText.EditModeActions.SetSpanActionBase
    {
      public TelopAction()
      {
        super();
      }

      protected final boolean doSelectionIsFixed()
      {
        if (super.doSelectionIsFixed());
        while (true)
        {
          return true;
          EditStyledText.EditModeActions.this.mManager.setTelop();
        }
      }
    }

    public final class TextViewAction extends EditStyledText.EditModeActions.TextViewActionBase
    {
      public TextViewAction()
      {
        super();
      }

      protected final boolean doEndPosIsSelected()
      {
        if (super.doEndPosIsSelected());
        while (true)
        {
          return true;
          Object localObject = getParam(0);
          if ((localObject != null) && ((localObject instanceof Integer)))
            EditStyledText.this.onTextContextMenuItem(((Integer)localObject).intValue());
          EditStyledText.EditorManager.access$2400(EditStyledText.EditModeActions.this.mManager);
        }
      }
    }

    public class TextViewActionBase extends EditStyledText.EditModeActions.EditModeActionBase
    {
      public TextViewActionBase()
      {
        super();
      }

      protected boolean doEndPosIsSelected()
      {
        boolean bool = true;
        if ((EditStyledText.EditModeActions.this.mManager.getEditMode() == 0) || (EditStyledText.EditModeActions.this.mManager.getEditMode() == 5))
        {
          EditStyledText.EditorManager.access$2300(EditStyledText.EditModeActions.this.mManager, EditStyledText.EditModeActions.this.mMode);
          fixSelection();
          EditStyledText.EditModeActions.this.doNext();
        }
        while (true)
        {
          return bool;
          if (EditStyledText.EditModeActions.this.mManager.getEditMode() != EditStyledText.EditModeActions.this.mMode)
          {
            EditStyledText.EditorManager.access$2400(EditStyledText.EditModeActions.this.mManager);
            EditStyledText.EditorManager.access$2300(EditStyledText.EditModeActions.this.mManager, EditStyledText.EditModeActions.this.mMode);
            EditStyledText.EditModeActions.this.doNext();
          }
          else
          {
            bool = false;
          }
        }
      }

      protected final boolean doNotSelected()
      {
        if ((EditStyledText.EditModeActions.this.mManager.getEditMode() == 0) || (EditStyledText.EditModeActions.this.mManager.getEditMode() == 5))
        {
          EditStyledText.EditorManager.access$2300(EditStyledText.EditModeActions.this.mManager, EditStyledText.EditModeActions.this.mMode);
          EditStyledText.EditModeActions.this.onSelectAction();
        }
        for (boolean bool = true; ; bool = false)
          return bool;
      }
    }
  }

  public static final class EditStyledTextSpans
  {
    public static final class HorizontalLineDrawable extends ShapeDrawable
    {
      private static boolean DBG_HL = false;
      private Spannable mSpannable;
      private int mWidth;

      public HorizontalLineDrawable(int paramInt1, int paramInt2, Spannable paramSpannable)
      {
        super();
        this.mSpannable = paramSpannable;
        this.mWidth = paramInt2;
        renewColor(paramInt1);
        renewBounds(paramInt2);
      }

      private void renewColor(int paramInt)
      {
        getPaint().setColor(paramInt);
      }

      public final void draw(Canvas paramCanvas)
      {
        Spannable localSpannable1 = this.mSpannable;
        EditStyledText.EditStyledTextSpans.HorizontalLineSpan[] arrayOfHorizontalLineSpan = (EditStyledText.EditStyledTextSpans.HorizontalLineSpan[])localSpannable1.getSpans(0, localSpannable1.length(), EditStyledText.EditStyledTextSpans.HorizontalLineSpan.class);
        int j;
        EditStyledText.EditStyledTextSpans.HorizontalLineSpan localHorizontalLineSpan2;
        if (arrayOfHorizontalLineSpan.length > 0)
        {
          int i = arrayOfHorizontalLineSpan.length;
          j = 0;
          if (j < i)
          {
            localHorizontalLineSpan2 = arrayOfHorizontalLineSpan[j];
            if (localHorizontalLineSpan2.getDrawable() != this);
          }
        }
        for (EditStyledText.EditStyledTextSpans.HorizontalLineSpan localHorizontalLineSpan1 = localHorizontalLineSpan2; ; localHorizontalLineSpan1 = null)
        {
          Spannable localSpannable2 = this.mSpannable;
          ForegroundColorSpan[] arrayOfForegroundColorSpan = (ForegroundColorSpan[])localSpannable2.getSpans(localSpannable2.getSpanStart(localHorizontalLineSpan1), localSpannable2.getSpanEnd(localHorizontalLineSpan1), ForegroundColorSpan.class);
          if (arrayOfForegroundColorSpan.length > 0)
            renewColor(arrayOfForegroundColorSpan[(-1 + arrayOfForegroundColorSpan.length)].getForegroundColor());
          paramCanvas.drawRect(new Rect(0, 9, this.mWidth, 11), getPaint());
          return;
          j++;
          break;
          Log.e("EditStyledTextSpan", "---renewBounds: Couldn't find");
        }
      }

      public final void renewBounds(int paramInt)
      {
        if (paramInt > 20)
          paramInt -= 20;
        this.mWidth = paramInt;
        setBounds(0, 0, paramInt, 20);
      }
    }

    public static class HorizontalLineSpan extends DynamicDrawableSpan
    {
      EditStyledText.EditStyledTextSpans.HorizontalLineDrawable mDrawable;

      public HorizontalLineSpan(int paramInt1, int paramInt2, Spannable paramSpannable)
      {
        super();
        this.mDrawable = new EditStyledText.EditStyledTextSpans.HorizontalLineDrawable(-16777216, paramInt2, paramSpannable);
      }

      public Drawable getDrawable()
      {
        return this.mDrawable;
      }

      public final void resetWidth(int paramInt)
      {
        this.mDrawable.renewBounds(paramInt);
      }
    }

    public static class MarqueeSpan extends CharacterStyle
    {
      private int mMarqueeColor;
      private int mType;

      public MarqueeSpan(int paramInt1, int paramInt2)
      {
        this.mType = paramInt1;
        if ((paramInt1 == 0) || (paramInt1 == 1));
        while (true)
        {
          this.mMarqueeColor = getMarqueeColor(paramInt1, paramInt2);
          return;
          Log.e("EditStyledTextSpan", "--- Invalid type of MarqueeSpan");
        }
      }

      private static int getMarqueeColor(int paramInt1, int paramInt2)
      {
        int i = 16777215;
        int j = Color.alpha(paramInt2);
        int k = Color.red(paramInt2);
        int m = Color.green(paramInt2);
        int n = Color.blue(paramInt2);
        if (j == 0)
          j = 128;
        switch (paramInt1)
        {
        default:
          Log.e("EditStyledText", "--- getMarqueeColor: got illigal marquee ID.");
        case 2:
          return i;
        case 0:
          if (k > 128)
            k /= 2;
          break;
        case 1:
        }
        while (true)
        {
          i = Color.argb(j, k, m, n);
          break;
          k = (255 - k) / 2;
          continue;
          if (m > 128)
            m /= 2;
          else
            m = (255 - m) / 2;
        }
      }

      public final void resetColor(int paramInt)
      {
        this.mMarqueeColor = getMarqueeColor(this.mType, paramInt);
      }

      public void updateDrawState(TextPaint paramTextPaint)
      {
        paramTextPaint.bgColor = this.mMarqueeColor;
      }
    }

    public static final class RescalableImageSpan extends ImageSpan
    {
      private final int MAXWIDTH;
      Uri mContentUri;
      private Context mContext;
      private Drawable mDrawable;
      public int mIntrinsicHeight = -1;
      public int mIntrinsicWidth = -1;

      public RescalableImageSpan(Context paramContext, int paramInt1, int paramInt2)
      {
        super(paramInt1);
        this.mContext = paramContext;
        this.MAXWIDTH = paramInt2;
      }

      public RescalableImageSpan(Context paramContext, Uri paramUri, int paramInt)
      {
        super(paramUri);
        this.mContext = paramContext;
        this.mContentUri = paramUri;
        this.MAXWIDTH = paramInt;
      }

      public final Uri getContentUri()
      {
        return this.mContentUri;
      }

      public final Drawable getDrawable()
      {
        Drawable localDrawable2;
        if (this.mDrawable != null)
        {
          localDrawable2 = this.mDrawable;
          return localDrawable2;
        }
        if (this.mContentUri != null)
          System.gc();
        while (true)
        {
          try
          {
            InputStream localInputStream1 = this.mContext.getContentResolver().openInputStream(this.mContentUri);
            BitmapFactory.Options localOptions = new BitmapFactory.Options();
            localOptions.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(localInputStream1, null, localOptions);
            localInputStream1.close();
            InputStream localInputStream2 = this.mContext.getContentResolver().openInputStream(this.mContentUri);
            int k = localOptions.outWidth;
            int m = localOptions.outHeight;
            this.mIntrinsicWidth = k;
            this.mIntrinsicHeight = m;
            if (localOptions.outWidth > this.MAXWIDTH)
            {
              k = this.MAXWIDTH;
              m = m * this.MAXWIDTH / localOptions.outWidth;
              localObject = BitmapFactory.decodeStream(localInputStream2, new Rect(0, 0, k, m), null);
              this.mDrawable = new BitmapDrawable(this.mContext.getResources(), (Bitmap)localObject);
              this.mDrawable.setBounds(0, 0, k, m);
              localInputStream2.close();
              localDrawable2 = this.mDrawable;
              break;
            }
            Bitmap localBitmap = BitmapFactory.decodeStream(localInputStream2);
            Object localObject = localBitmap;
            continue;
          }
          catch (Exception localException)
          {
            Log.e("EditStyledTextSpan", "Failed to loaded content " + this.mContentUri, localException);
            localDrawable2 = null;
          }
          catch (OutOfMemoryError localOutOfMemoryError)
          {
            Log.e("EditStyledTextSpan", "OutOfMemoryError");
            localDrawable2 = null;
          }
          break;
          this.mDrawable = super.getDrawable();
          Drawable localDrawable1 = this.mDrawable;
          Log.d("EditStyledTextSpan", "--- rescaleBigImage:");
          if (this.MAXWIDTH >= 0)
          {
            int i = localDrawable1.getIntrinsicWidth();
            int j = localDrawable1.getIntrinsicHeight();
            Log.d("EditStyledTextSpan", "--- rescaleBigImage:" + i + "," + j + "," + this.MAXWIDTH);
            if (i > this.MAXWIDTH)
            {
              i = this.MAXWIDTH;
              j = j * this.MAXWIDTH / i;
            }
            localDrawable1.setBounds(0, 0, i, j);
          }
          this.mIntrinsicWidth = this.mDrawable.getIntrinsicWidth();
          this.mIntrinsicHeight = this.mDrawable.getIntrinsicHeight();
        }
      }
    }
  }

  private final class EditorManager
  {
    private EditStyledText.EditModeActions mActions;
    private int mBackgroundColor = 16777215;
    private int mColorWaitInput = 16777215;
    private BackgroundColorSpan mComposingTextMask;
    private SpannableStringBuilder mCopyBuffer;
    private int mCurEnd = 0;
    private int mCurStart = 0;
    private EditStyledText mEST;
    private boolean mEditFlag = false;
    private boolean mKeepNonLineSpan = false;
    private int mMode = 0;
    private int mSizeWaitInput = 0;
    private EditStyledText.SoftKeyReceiver mSkr;
    private boolean mSoftKeyBlockFlag = false;
    private int mState = 0;
    private boolean mTextIsFinishedFlag = false;
    private boolean mWaitInputFlag = false;

    EditorManager(EditStyledText paramStyledTextDialog, EditStyledText.StyledTextDialog arg3)
    {
      this.mEST = paramStyledTextDialog;
      EditStyledText.StyledTextDialog localStyledTextDialog;
      this.mActions = new EditStyledText.EditModeActions(EditStyledText.this, this.mEST, this, localStyledTextDialog);
      this.mSkr = new EditStyledText.SoftKeyReceiver(this.mEST);
    }

    private void addMarquee(int paramInt)
    {
      Log.d("EditStyledText.EditorManager", "--- addMarquee:" + paramInt);
      setLineStyledTextSpan(new EditStyledText.EditStyledTextSpans.MarqueeSpan(paramInt, this.mEST.getBackgroundColor()));
    }

    private void copyToClipBoard()
    {
      int i = Math.min(this.mCurStart, this.mCurEnd);
      int j = Math.max(this.mCurStart, this.mCurEnd);
      this.mCopyBuffer = ((SpannableStringBuilder)this.mEST.getText().subSequence(i, j));
      SpannableStringBuilder localSpannableStringBuilder = removeImageChar(this.mCopyBuffer);
      ((ClipboardManager)EditStyledText.this.getContext().getSystemService("clipboard")).setText(localSpannableStringBuilder);
      dumpSpannableString(localSpannableStringBuilder);
      dumpSpannableString(this.mCopyBuffer);
    }

    private static void dumpSpannableString(CharSequence paramCharSequence)
    {
      if ((paramCharSequence instanceof Spannable))
      {
        Spannable localSpannable = (Spannable)paramCharSequence;
        int i = localSpannable.length();
        Log.d("EditStyledText", "--- dumpSpannableString, txt:" + localSpannable + ", len:" + i);
        for (Object localObject : localSpannable.getSpans(0, i, Object.class))
          Log.d("EditStyledText", "--- dumpSpannableString, class:" + localObject + "," + localSpannable.getSpanStart(localObject) + "," + localSpannable.getSpanEnd(localObject) + "," + localSpannable.getSpanFlags(localObject));
      }
    }

    private void endEdit()
    {
      Log.d("EditStyledText.EditorManager", "--- handleCancel");
      this.mMode = 0;
      this.mState = 0;
      this.mEditFlag = false;
      this.mColorWaitInput = 16777215;
      this.mSizeWaitInput = 0;
      this.mWaitInputFlag = false;
      this.mSoftKeyBlockFlag = false;
      this.mKeepNonLineSpan = false;
      this.mTextIsFinishedFlag = false;
      unsetSelect();
      this.mEST.setOnClickListener(null);
      Log.d("EditStyledText.EditorManager", "--- unblockSoftKey:");
      this.mSoftKeyBlockFlag = false;
    }

    private static int findLineEnd(Editable paramEditable, int paramInt)
    {
      for (int i = paramInt; ; i++)
        if (i < paramEditable.length())
        {
          if (paramEditable.charAt(i) == '\n')
            i++;
        }
        else
        {
          Log.d("EditStyledText.EditorManager", "--- findLineEnd:" + paramInt + "," + paramEditable.length() + "," + i);
          return i;
        }
    }

    private static int findLineStart(Editable paramEditable, int paramInt)
    {
      for (int i = paramInt; (i > 0) && (paramEditable.charAt(i - 1) != '\n'); i--);
      Log.d("EditStyledText.EditorManager", "--- findLineStart:" + paramInt + "," + paramEditable.length() + "," + i);
      return i;
    }

    private void fixSelectionAndDoNextAction()
    {
      Log.d("EditStyledText.EditorManager", "--- handleComplete:" + this.mCurStart + "," + this.mCurEnd);
      if (!this.mEditFlag);
      while (true)
      {
        return;
        if (this.mCurStart == this.mCurEnd)
        {
          Log.d("EditStyledText.EditorManager", "--- cancel handle complete:" + this.mCurStart);
          resetEdit();
        }
        else
        {
          if (this.mState == 2)
            this.mState = 3;
          this.mActions.doNext(this.mMode);
          EditStyledText.access$700(this.mEST, this.mEST.getText());
        }
      }
    }

    private void insertImageSpan(DynamicDrawableSpan paramDynamicDrawableSpan, int paramInt)
    {
      Log.d("EditStyledText.EditorManager", "--- insertImageSpan:");
      if ((paramDynamicDrawableSpan != null) && (paramDynamicDrawableSpan.getDrawable() != null))
      {
        this.mEST.getText().insert(paramInt, "￼");
        this.mEST.getText().setSpan(paramDynamicDrawableSpan, paramInt, paramInt + 1, 33);
        EditStyledText.access$300(this.mEST, this.mMode, this.mState);
      }
      while (true)
      {
        return;
        Log.e("EditStyledText.EditorManager", "--- insertImageSpan: null span was inserted");
        EditStyledText.access$900(this.mEST, 5);
      }
    }

    private boolean isWaitingNextAction()
    {
      int i = 1;
      Log.d("EditStyledText.EditorManager", "--- waitingNext:" + this.mCurStart + "," + this.mCurEnd + "," + this.mState);
      if ((this.mCurStart == this.mCurEnd) && (this.mState == 3))
      {
        Log.d("EditStyledText.EditorManager", "--- waitSelection");
        this.mWaitInputFlag = i;
        if (this.mCurStart == this.mCurEnd)
        {
          this.mState = i;
          EditStyledText.access$800(this.mEST, this.mEST.getText());
        }
      }
      while (true)
      {
        return i;
        this.mState = 2;
        break;
        Log.d("EditStyledText.EditorManager", "--- resumeSelection");
        this.mWaitInputFlag = false;
        this.mState = 3;
        EditStyledText.access$700(this.mEST, this.mEST.getText());
        i = 0;
      }
    }

    private static SpannableStringBuilder removeImageChar(SpannableStringBuilder paramSpannableStringBuilder)
    {
      SpannableStringBuilder localSpannableStringBuilder = new SpannableStringBuilder(paramSpannableStringBuilder);
      for (DynamicDrawableSpan localDynamicDrawableSpan : (DynamicDrawableSpan[])localSpannableStringBuilder.getSpans(0, localSpannableStringBuilder.length(), DynamicDrawableSpan.class))
        if (((localDynamicDrawableSpan instanceof EditStyledText.EditStyledTextSpans.HorizontalLineSpan)) || ((localDynamicDrawableSpan instanceof EditStyledText.EditStyledTextSpans.RescalableImageSpan)))
          localSpannableStringBuilder.replace(localSpannableStringBuilder.getSpanStart(localDynamicDrawableSpan), localSpannableStringBuilder.getSpanEnd(localDynamicDrawableSpan), "");
      return localSpannableStringBuilder;
    }

    private void resetEdit()
    {
      endEdit();
      this.mEditFlag = true;
      EditStyledText.access$300(this.mEST, this.mMode, this.mState);
    }

    private void setLineStyledTextSpan(Object paramObject)
    {
      int i = Math.min(this.mCurStart, this.mCurEnd);
      int j = Math.max(this.mCurStart, this.mCurEnd);
      int k = this.mEST.getSelectionStart();
      int m = findLineStart(this.mEST.getText(), i);
      int n = findLineEnd(this.mEST.getText(), j);
      if (m == n)
      {
        this.mEST.getText().insert(n, "\n");
        setStyledTextSpan(paramObject, m, n + 1);
      }
      while (true)
      {
        Selection.setSelection(this.mEST.getText(), k);
        return;
        setStyledTextSpan(paramObject, m, n);
      }
    }

    private void setStyledTextSpan(Object paramObject, int paramInt1, int paramInt2)
    {
      Log.d("EditStyledText.EditorManager", "--- setStyledTextSpan:" + this.mMode + "," + paramInt1 + "," + paramInt2);
      int i = Math.min(paramInt1, paramInt2);
      int j = Math.max(paramInt1, paramInt2);
      this.mEST.getText().setSpan(paramObject, i, j, 33);
      Selection.setSelection(this.mEST.getText(), j);
    }

    private void unsetSelect()
    {
      Log.d("EditStyledText.EditorManager", "--- offSelect");
      EditStyledText.access$700(this.mEST, this.mEST.getText());
      int i = this.mEST.getSelectionStart();
      this.mEST.setSelection(i, i);
      this.mState = 0;
    }

    public final void blockSoftKey()
    {
      Log.d("EditStyledText.EditorManager", "--- blockSoftKey:");
      Log.d("EditStyledText.EditorManager", "--- hidesoftkey");
      if (this.mEST.isFocused())
      {
        this.mSkr.mNewStart = Selection.getSelectionStart(this.mEST.getText());
        this.mSkr.mNewEnd = Selection.getSelectionEnd(this.mEST.getText());
        ((InputMethodManager)this.mEST.getContext().getSystemService("input_method")).hideSoftInputFromWindow(this.mEST.getWindowToken(), 0, this.mSkr);
      }
      this.mSoftKeyBlockFlag = true;
    }

    public final boolean canPaste()
    {
      if ((this.mCopyBuffer != null) && (this.mCopyBuffer.length() > 0) && (removeImageChar(this.mCopyBuffer).length() == 0));
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final int getBackgroundColor()
    {
      return this.mBackgroundColor;
    }

    public final int getColorWaitInput()
    {
      return this.mColorWaitInput;
    }

    public final int getEditMode()
    {
      return this.mMode;
    }

    public final int getSelectState()
    {
      return this.mState;
    }

    public final int getSelectionStart()
    {
      return this.mCurStart;
    }

    public final int getSizeWaitInput()
    {
      return this.mSizeWaitInput;
    }

    public final boolean isEditting()
    {
      return this.mEditFlag;
    }

    public final boolean isSoftKeyBlocked()
    {
      return this.mSoftKeyBlockFlag;
    }

    public final boolean isStyledText()
    {
      Editable localEditable = this.mEST.getText();
      int i = localEditable.length();
      if ((((ParagraphStyle[])localEditable.getSpans(0, i, ParagraphStyle.class)).length > 0) || (((QuoteSpan[])localEditable.getSpans(0, i, QuoteSpan.class)).length > 0) || (((CharacterStyle[])localEditable.getSpans(0, i, CharacterStyle.class)).length > 0) || (this.mBackgroundColor != 16777215));
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean isWaitInput()
    {
      return this.mWaitInputFlag;
    }

    public final void onAction(int paramInt)
    {
      this.mActions.onAction(paramInt);
      EditStyledText.access$300(this.mEST, this.mMode, this.mState);
    }

    public final void onClearStyles()
    {
      this.mActions.onAction(14);
    }

    public final void onCursorMoved()
    {
      Log.d("EditStyledText.EditorManager", "--- onClickView");
      if ((this.mState == 1) || (this.mState == 2))
      {
        this.mActions.onSelectAction();
        EditStyledText.access$300(this.mEST, this.mMode, this.mState);
      }
    }

    public final void onFixSelectedItem()
    {
      Log.d("EditStyledText.EditorManager", "--- onFixSelectedItem");
      fixSelectionAndDoNextAction();
      EditStyledText.access$300(this.mEST, this.mMode, this.mState);
    }

    public final void onRefreshStyles()
    {
      Log.d("EditStyledText.EditorManager", "--- onRefreshStyles");
      Editable localEditable = this.mEST.getText();
      int i = localEditable.length();
      int j = this.mEST.getWidth();
      EditStyledText.EditStyledTextSpans.HorizontalLineSpan[] arrayOfHorizontalLineSpan = (EditStyledText.EditStyledTextSpans.HorizontalLineSpan[])localEditable.getSpans(0, i, EditStyledText.EditStyledTextSpans.HorizontalLineSpan.class);
      int k = arrayOfHorizontalLineSpan.length;
      for (int m = 0; m < k; m++)
        arrayOfHorizontalLineSpan[m].resetWidth(j);
      EditStyledText.EditStyledTextSpans.MarqueeSpan[] arrayOfMarqueeSpan = (EditStyledText.EditStyledTextSpans.MarqueeSpan[])localEditable.getSpans(0, i, EditStyledText.EditStyledTextSpans.MarqueeSpan.class);
      int n = arrayOfMarqueeSpan.length;
      for (int i1 = 0; i1 < n; i1++)
        arrayOfMarqueeSpan[i1].resetColor(this.mEST.getBackgroundColor());
      if (arrayOfHorizontalLineSpan.length > 0)
        localEditable.replace(0, 1, localEditable.charAt(0));
    }

    public final void onStartSelect(boolean paramBoolean)
    {
      Log.d("EditStyledText.EditorManager", "--- onClickSelect");
      this.mMode = 5;
      if (this.mState == 0)
        this.mActions.onSelectAction();
      while (true)
      {
        EditStyledText.access$300(this.mEST, this.mMode, this.mState);
        return;
        unsetSelect();
        this.mActions.onSelectAction();
      }
    }

    public final void onStartSelectAll(boolean paramBoolean)
    {
      Log.d("EditStyledText.EditorManager", "--- onClickSelectAll");
      if (this.mEditFlag)
        this.mActions.onAction(11);
      if (paramBoolean)
        EditStyledText.access$300(this.mEST, this.mMode, this.mState);
    }

    public final void setAlignment(Layout.Alignment paramAlignment)
    {
      if ((this.mState == 2) || (this.mState == 3))
      {
        setLineStyledTextSpan(new AlignmentSpan.Standard(paramAlignment));
        resetEdit();
      }
    }

    public final void setBackgroundColor(int paramInt)
    {
      this.mBackgroundColor = paramInt;
    }

    public final void setEndPos(int paramInt)
    {
      Log.d("EditStyledText.EditorManager", "--- setSelectedEndPos:" + paramInt);
      this.mCurEnd = paramInt;
      Log.d("EditStyledText.EditorManager", "--- onSelect:" + this.mCurStart + "," + this.mCurEnd);
      if ((this.mCurStart >= 0) && (this.mCurStart <= this.mEST.getText().length()) && (this.mCurEnd >= 0) && (this.mCurEnd <= this.mEST.getText().length()))
        if (this.mCurStart < this.mCurEnd)
        {
          this.mEST.setSelection(this.mCurStart, this.mCurEnd);
          this.mState = 2;
        }
      while (true)
      {
        return;
        if (this.mCurStart > this.mCurEnd)
        {
          this.mEST.setSelection(this.mCurEnd, this.mCurStart);
          this.mState = 2;
        }
        else
        {
          this.mState = 1;
          continue;
          Log.e("EditStyledText.EditorManager", "Select is on, but cursor positions are illigal.:" + this.mEST.getText().length() + "," + this.mCurStart + "," + this.mCurEnd);
        }
      }
    }

    public final void setItemColor(int paramInt, boolean paramBoolean)
    {
      Log.d("EditStyledText.EditorManager", "--- setItemColor");
      if (isWaitingNextAction())
        this.mColorWaitInput = paramInt;
      label86: label96: 
      while (true)
      {
        return;
        if ((this.mState == 2) || (this.mState == 3))
          if (paramInt != 16777215)
          {
            if (this.mCurStart == this.mCurEnd)
              break label86;
            setStyledTextSpan(new ForegroundColorSpan(paramInt), this.mCurStart, this.mCurEnd);
          }
        while (true)
        {
          if (!paramBoolean)
            break label96;
          resetEdit();
          break;
          break;
          Log.e("EditStyledText.EditorManager", "---changeColor: Size of the span is zero");
        }
      }
    }

    public final void setItemSize(int paramInt, boolean paramBoolean)
    {
      Log.d("EditStyledText.EditorManager", "--- setItemSize");
      if (isWaitingNextAction())
        this.mSizeWaitInput = paramInt;
      label84: label94: 
      while (true)
      {
        return;
        if ((this.mState == 2) || (this.mState == 3))
          if (paramInt > 0)
          {
            if (this.mCurStart == this.mCurEnd)
              break label84;
            setStyledTextSpan(new AbsoluteSizeSpan(paramInt), this.mCurStart, this.mCurEnd);
          }
        while (true)
        {
          if (!paramBoolean)
            break label94;
          resetEdit();
          break;
          break;
          Log.e("EditStyledText.EditorManager", "---changeSize: Size of the span is zero");
        }
      }
    }

    public final void setMarquee(int paramInt)
    {
      if ((this.mState == 2) || (this.mState == 3))
      {
        addMarquee(paramInt);
        resetEdit();
      }
    }

    public final void setSwing()
    {
      if ((this.mState == 2) || (this.mState == 3))
      {
        addMarquee(0);
        resetEdit();
      }
    }

    public final void setTelop()
    {
      if ((this.mState == 2) || (this.mState == 3))
      {
        addMarquee(1);
        resetEdit();
      }
    }

    public final void setTextComposingMask(int paramInt1, int paramInt2)
    {
      Log.d("EditStyledText", "--- setTextComposingMask:" + paramInt1 + "," + paramInt2);
      int i = Math.min(paramInt1, paramInt2);
      int j = Math.max(paramInt1, paramInt2);
      if ((this.mWaitInputFlag) && (this.mColorWaitInput != 16777215));
      for (int k = this.mColorWaitInput; ; k = this.mEST.getForegroundColor(i))
      {
        int m = this.mEST.getBackgroundColor();
        Log.d("EditStyledText", "--- fg:" + Integer.toHexString(k) + ",bg:" + Integer.toHexString(m) + "," + this.mWaitInputFlag + ",," + this.mMode);
        if (k == m)
        {
          int n = 0x80000000 | 0xFFFFFFFF ^ (0xFF000000 | m);
          if ((this.mComposingTextMask == null) || (this.mComposingTextMask.getBackgroundColor() != n))
            this.mComposingTextMask = new BackgroundColorSpan(n);
          this.mEST.getText().setSpan(this.mComposingTextMask, i, j, 33);
        }
        return;
      }
    }

    public final void showSoftKey(int paramInt1, int paramInt2)
    {
      Log.d("EditStyledText.EditorManager", "--- showsoftkey");
      if ((!this.mEST.isFocused()) || (this.mSoftKeyBlockFlag));
      while (true)
      {
        return;
        this.mSkr.mNewStart = Selection.getSelectionStart(this.mEST.getText());
        this.mSkr.mNewEnd = Selection.getSelectionEnd(this.mEST.getText());
        if ((((InputMethodManager)EditStyledText.this.getContext().getSystemService("input_method")).showSoftInput(this.mEST, 0, this.mSkr)) && (this.mSkr != null))
          Selection.setSelection(EditStyledText.this.getText(), paramInt1, paramInt2);
      }
    }

    public final void unsetTextComposingMask()
    {
      Log.d("EditStyledText", "--- unsetTextComposingMask");
      if (this.mComposingTextMask != null)
      {
        this.mEST.getText().removeSpan(this.mComposingTextMask);
        this.mComposingTextMask = null;
      }
    }

    public final void updateSpanNextToCursor(Editable paramEditable, int paramInt1, int paramInt2, int paramInt3)
    {
      Log.d("EditStyledText.EditorManager", "updateSpanNext:" + paramInt1 + "," + paramInt2 + "," + paramInt3);
      int i = paramInt1 + paramInt3;
      int j = Math.min(paramInt1, i);
      int k = Math.max(paramInt1, i);
      Object[] arrayOfObject = paramEditable.getSpans(k, k, Object.class);
      int m = arrayOfObject.length;
      int n = 0;
      if (n < m)
      {
        Object localObject = arrayOfObject[n];
        int i1;
        int i2;
        if (((localObject instanceof EditStyledText.EditStyledTextSpans.MarqueeSpan)) || ((localObject instanceof AlignmentSpan)))
        {
          i1 = paramEditable.getSpanStart(localObject);
          i2 = paramEditable.getSpanEnd(localObject);
          Log.d("EditStyledText.EditorManager", "spantype:" + localObject.getClass() + "," + i2);
          int i3 = j;
          if (((localObject instanceof EditStyledText.EditStyledTextSpans.MarqueeSpan)) || ((localObject instanceof AlignmentSpan)))
            i3 = findLineStart(this.mEST.getText(), j);
          if ((i3 < i1) && (paramInt2 > paramInt3))
            paramEditable.removeSpan(localObject);
        }
        while (true)
        {
          n++;
          break;
          if (i1 > j)
          {
            paramEditable.setSpan(localObject, j, i2, 33);
            continue;
            if (((localObject instanceof EditStyledText.EditStyledTextSpans.HorizontalLineSpan)) && (paramEditable.getSpanStart(localObject) == i) && (i > 0) && (this.mEST.getText().charAt(i - 1) != '\n'))
            {
              this.mEST.getText().insert(i, "\n");
              this.mEST.setSelection(i);
            }
          }
        }
      }
    }

    public final void updateSpanPreviousFromCursor(Editable paramEditable, int paramInt1, int paramInt2, int paramInt3)
    {
      Log.d("EditStyledText.EditorManager", "updateSpanPrevious:" + paramInt1 + "," + paramInt2 + "," + paramInt3);
      int i = paramInt1 + paramInt3;
      int j = Math.min(paramInt1, i);
      int k = Math.max(paramInt1, i);
      Object[] arrayOfObject = paramEditable.getSpans(j, j, Object.class);
      int m = arrayOfObject.length;
      int n = 0;
      if (n < m)
      {
        Object localObject = arrayOfObject[n];
        int i2;
        int i3;
        if (((localObject instanceof ForegroundColorSpan)) || ((localObject instanceof AbsoluteSizeSpan)) || ((localObject instanceof EditStyledText.EditStyledTextSpans.MarqueeSpan)) || ((localObject instanceof AlignmentSpan)))
        {
          int i1 = paramEditable.getSpanStart(localObject);
          i2 = paramEditable.getSpanEnd(localObject);
          Log.d("EditStyledText.EditorManager", "spantype:" + localObject.getClass() + "," + i1);
          i3 = k;
          if (((localObject instanceof EditStyledText.EditStyledTextSpans.MarqueeSpan)) || ((localObject instanceof AlignmentSpan)))
          {
            i3 = findLineEnd(this.mEST.getText(), k);
            label223: if (i2 < i3)
            {
              Log.d("EditStyledText.EditorManager", "updateSpanPrevious: extend span");
              paramEditable.setSpan(localObject, i1, i3, 33);
            }
          }
        }
        while (true)
        {
          n++;
          break;
          if (!this.mKeepNonLineSpan)
            break label223;
          i3 = i2;
          break label223;
          if ((localObject instanceof EditStyledText.EditStyledTextSpans.HorizontalLineSpan))
          {
            int i4 = paramEditable.getSpanStart(localObject);
            int i5 = paramEditable.getSpanEnd(localObject);
            if (paramInt2 > paramInt3)
            {
              paramEditable.replace(i4, i5, "");
              paramEditable.removeSpan(localObject);
            }
            else if ((i5 == i) && (i < paramEditable.length()) && (this.mEST.getText().charAt(i) != '\n'))
            {
              this.mEST.getText().insert(i, "\n");
            }
          }
        }
      }
    }
  }

  private final class MenuHandler
    implements MenuItem.OnMenuItemClickListener
  {
    private MenuHandler()
    {
    }

    public final boolean onMenuItemClick(MenuItem paramMenuItem)
    {
      return EditStyledText.this.onTextContextMenuItem(paramMenuItem.getItemId());
    }
  }

  public static class SavedStyledTextState extends View.BaseSavedState
  {
    public int mBackgroundColor;

    SavedStyledTextState(Parcelable paramParcelable)
    {
      super();
    }

    public String toString()
    {
      return "EditStyledText.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " bgcolor=" + this.mBackgroundColor + "}";
    }

    public void writeToParcel(Parcel paramParcel, int paramInt)
    {
      super.writeToParcel(paramParcel, paramInt);
      paramParcel.writeInt(this.mBackgroundColor);
    }
  }

  private static class SoftKeyReceiver extends ResultReceiver
  {
    EditStyledText mEST;
    int mNewEnd;
    int mNewStart;

    SoftKeyReceiver(EditStyledText paramEditStyledText)
    {
      super();
      this.mEST = paramEditStyledText;
    }

    protected void onReceiveResult(int paramInt, Bundle paramBundle)
    {
      if (paramInt != 2)
        Selection.setSelection(this.mEST.getText(), this.mNewStart, this.mNewEnd);
    }
  }

  private static final class StyledTextArrowKeyMethod extends ArrowKeyMovementMethod
  {
    String LOG_TAG = "StyledTextArrowKeyMethod";
    EditStyledText.EditorManager mManager;

    StyledTextArrowKeyMethod(EditStyledText.EditorManager paramEditorManager)
    {
      this.mManager = paramEditorManager;
    }

    private int getEndPos(TextView paramTextView)
    {
      if (paramTextView.getSelectionStart() == this.mManager.getSelectionStart());
      for (int i = paramTextView.getSelectionEnd(); ; i = paramTextView.getSelectionStart())
        return i;
    }

    protected final boolean down(TextView paramTextView, Spannable paramSpannable)
    {
      Log.d(this.LOG_TAG, "--- down:");
      Layout localLayout = paramTextView.getLayout();
      int i = getEndPos(paramTextView);
      int j = localLayout.getLineForOffset(i);
      float f;
      if (j < -1 + localLayout.getLineCount())
      {
        if (localLayout.getParagraphDirection(j) != localLayout.getParagraphDirection(j + 1))
          break label103;
        f = localLayout.getPrimaryHorizontal(i);
      }
      label103: for (int k = localLayout.getOffsetForHorizontal(j + 1, f); ; k = localLayout.getLineStart(j + 1))
      {
        this.mManager.setEndPos(k);
        this.mManager.onCursorMoved();
        return true;
      }
    }

    protected final boolean left(TextView paramTextView, Spannable paramSpannable)
    {
      Log.d(this.LOG_TAG, "--- left:");
      int i = paramTextView.getLayout().getOffsetToLeftOf(getEndPos(paramTextView));
      this.mManager.setEndPos(i);
      this.mManager.onCursorMoved();
      return true;
    }

    public final boolean onKeyDown(TextView paramTextView, Spannable paramSpannable, int paramInt, KeyEvent paramKeyEvent)
    {
      int i = 1;
      Log.d(this.LOG_TAG, "---onkeydown:" + paramInt);
      this.mManager.unsetTextComposingMask();
      if ((this.mManager.getSelectState() == i) || (this.mManager.getSelectState() == 2))
      {
        Log.d(this.LOG_TAG, "--- executeDown: " + paramInt);
        switch (paramInt)
        {
        default:
          i = 0;
        case 19:
        case 20:
        case 21:
        case 22:
        case 23:
        }
      }
      while (true)
      {
        return i;
        boolean bool = false | up(paramTextView, paramSpannable);
        continue;
        bool = false | down(paramTextView, paramSpannable);
        continue;
        bool = false | left(paramTextView, paramSpannable);
        continue;
        bool = false | right(paramTextView, paramSpannable);
        continue;
        this.mManager.onFixSelectedItem();
        continue;
        bool = super.onKeyDown(paramTextView, paramSpannable, paramInt, paramKeyEvent);
      }
    }

    protected final boolean right(TextView paramTextView, Spannable paramSpannable)
    {
      Log.d(this.LOG_TAG, "--- right:");
      int i = paramTextView.getLayout().getOffsetToRightOf(getEndPos(paramTextView));
      this.mManager.setEndPos(i);
      this.mManager.onCursorMoved();
      return true;
    }

    protected final boolean up(TextView paramTextView, Spannable paramSpannable)
    {
      Log.d(this.LOG_TAG, "--- up:");
      Layout localLayout = paramTextView.getLayout();
      int i = getEndPos(paramTextView);
      int j = localLayout.getLineForOffset(i);
      float f;
      if (j > 0)
      {
        if (localLayout.getParagraphDirection(j) != localLayout.getParagraphDirection(j - 1))
          break label96;
        f = localLayout.getPrimaryHorizontal(i);
      }
      label96: for (int k = localLayout.getOffsetForHorizontal(j - 1, f); ; k = localLayout.getLineStart(j - 1))
      {
        this.mManager.setEndPos(k);
        this.mManager.onCursorMoved();
        return true;
      }
    }
  }

  private final class StyledTextConverter
  {
    private EditStyledText mEST;
    private EditStyledText.StyledTextHtmlConverter mHtml;

    public StyledTextConverter(EditStyledText paramStyledTextHtmlConverter, EditStyledText.StyledTextHtmlConverter arg3)
    {
      this.mEST = paramStyledTextHtmlConverter;
      Object localObject;
      this.mHtml = localObject;
    }

    public final void SetHtml(String paramString)
    {
      Spanned localSpanned = this.mHtml.fromHtml(paramString, new Html.ImageGetter()
      {
        public final Drawable getDrawable(String paramAnonymousString)
        {
          Log.d("EditStyledText", "--- sethtml: src=" + paramAnonymousString);
          BitmapDrawable localBitmapDrawable;
          if (paramAnonymousString.startsWith("content://"))
          {
            Uri localUri = Uri.parse(paramAnonymousString);
            try
            {
              System.gc();
              InputStream localInputStream1 = EditStyledText.this.getContext().getContentResolver().openInputStream(localUri);
              BitmapFactory.Options localOptions = new BitmapFactory.Options();
              localOptions.inJustDecodeBounds = true;
              BitmapFactory.decodeStream(localInputStream1, null, localOptions);
              localInputStream1.close();
              InputStream localInputStream2 = EditStyledText.this.getContext().getContentResolver().openInputStream(localUri);
              int i = localOptions.outWidth;
              int j = localOptions.outHeight;
              if (localOptions.outWidth > EditStyledText.this.dipToPx())
              {
                i = EditStyledText.this.dipToPx();
                j = j * EditStyledText.this.dipToPx() / localOptions.outWidth;
              }
              Bitmap localBitmap;
              for (Object localObject = BitmapFactory.decodeStream(localInputStream2, new Rect(0, 0, i, j), null); ; localObject = localBitmap)
              {
                localBitmapDrawable = new BitmapDrawable(EditStyledText.this.getContext().getResources(), (Bitmap)localObject);
                localBitmapDrawable.setBounds(0, 0, i, j);
                localInputStream2.close();
                break;
                localBitmap = BitmapFactory.decodeStream(localInputStream2);
              }
            }
            catch (Exception localException)
            {
              Log.e("EditStyledText", "--- set html: Failed to loaded content " + localUri, localException);
              localBitmapDrawable = null;
            }
            catch (OutOfMemoryError localOutOfMemoryError)
            {
              Log.e("EditStyledText", "OutOfMemoryError");
              EditStyledText.this.setHint(5);
              localBitmapDrawable = null;
            }
          }
          else
          {
            localBitmapDrawable = null;
          }
          return localBitmapDrawable;
        }
      }
      , null);
      this.mEST.setText(localSpanned);
    }

    public final void setStyledTextHtmlConverter(EditStyledText.StyledTextHtmlConverter paramStyledTextHtmlConverter)
    {
      this.mHtml = paramStyledTextHtmlConverter;
    }
  }

  private static final class StyledTextDialog
  {
    private AlertDialog mAlertDialog;
    private CharSequence[] mAlignNames;
    private CharSequence mAlignTitle;
    private AlertDialog.Builder mBuilder;
    private CharSequence mColorDefaultMessage;
    private CharSequence[] mColorInts;
    private CharSequence[] mColorNames;
    private CharSequence mColorTitle;
    private EditStyledText mEST;
    private CharSequence[] mMarqueeNames;
    private CharSequence mMarqueeTitle;
    private CharSequence[] mSizeDisplayInts;
    private CharSequence[] mSizeNames;
    private CharSequence[] mSizeSendInts;
    private CharSequence mSizeTitle;

    public StyledTextDialog(EditStyledText paramEditStyledText)
    {
      this.mEST = paramEditStyledText;
    }

    private void buildAndShowColorDialogue(int paramInt, CharSequence paramCharSequence, int[] paramArrayOfInt)
    {
      int i = this.mEST.dipToPx(50);
      int j = this.mEST.dipToPx(2);
      int k = this.mEST.dipToPx(15);
      this.mBuilder.setTitle(paramCharSequence);
      this.mBuilder.setIcon(0);
      this.mBuilder.setPositiveButton(null, null);
      this.mBuilder.setNegativeButton(17039360, new DialogInterface.OnClickListener()
      {
        public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          EditStyledText.this.onStartEdit();
        }
      });
      this.mBuilder.setItems(null, null);
      LinearLayout localLinearLayout1 = new LinearLayout(this.mEST.getContext());
      localLinearLayout1.setOrientation(1);
      localLinearLayout1.setGravity(1);
      localLinearLayout1.setPadding(k, k, k, k);
      LinearLayout localLinearLayout2 = null;
      int m = 0;
      if (m < paramArrayOfInt.length)
      {
        if (m % 5 == 0)
        {
          localLinearLayout2 = new LinearLayout(this.mEST.getContext());
          localLinearLayout1.addView(localLinearLayout2);
        }
        Button localButton = new Button(this.mEST.getContext());
        localButton.setHeight(i);
        localButton.setWidth(i);
        localButton.setBackgroundDrawable(new EditStyledText.ColorPaletteDrawable(paramArrayOfInt[m], i, i, j));
        localButton.setDrawingCacheBackgroundColor(paramArrayOfInt[m]);
        if (paramInt == 0)
          localButton.setOnClickListener(new View.OnClickListener()
          {
            public final void onClick(View paramAnonymousView)
            {
              EditStyledText.this.setItemColor(paramAnonymousView.getDrawingCacheBackgroundColor());
              if (EditStyledText.StyledTextDialog.this.mAlertDialog != null)
              {
                EditStyledText.StyledTextDialog.this.mAlertDialog.setView(null);
                EditStyledText.StyledTextDialog.this.mAlertDialog.dismiss();
                EditStyledText.StyledTextDialog.access$1502(EditStyledText.StyledTextDialog.this, null);
              }
              while (true)
              {
                return;
                Log.e("EditStyledText", "--- buildAndShowColorDialogue: can't find alertDialog");
              }
            }
          });
        while (true)
        {
          localLinearLayout2.addView(localButton);
          m++;
          break;
          if (paramInt == 1)
            localButton.setOnClickListener(new View.OnClickListener()
            {
              public final void onClick(View paramAnonymousView)
              {
                EditStyledText.this.setBackgroundColor(paramAnonymousView.getDrawingCacheBackgroundColor());
                if (EditStyledText.StyledTextDialog.this.mAlertDialog != null)
                {
                  EditStyledText.StyledTextDialog.this.mAlertDialog.setView(null);
                  EditStyledText.StyledTextDialog.this.mAlertDialog.dismiss();
                  EditStyledText.StyledTextDialog.access$1502(EditStyledText.StyledTextDialog.this, null);
                }
                while (true)
                {
                  return;
                  Log.e("EditStyledText", "--- buildAndShowColorDialogue: can't find alertDialog");
                }
              }
            });
        }
      }
      if (paramInt == 1)
        this.mBuilder.setPositiveButton(this.mColorDefaultMessage, new DialogInterface.OnClickListener()
        {
          public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
          {
            EditStyledText.this.setBackgroundColor(16777215);
          }
        });
      while (true)
      {
        this.mBuilder.setView(localLinearLayout1);
        this.mBuilder.setCancelable(true);
        this.mBuilder.setOnCancelListener(new DialogInterface.OnCancelListener()
        {
          public final void onCancel(DialogInterface paramAnonymousDialogInterface)
          {
            EditStyledText.this.onStartEdit();
          }
        });
        this.mAlertDialog = this.mBuilder.show();
        return;
        if (paramInt == 0)
          this.mBuilder.setPositiveButton(this.mColorDefaultMessage, new DialogInterface.OnClickListener()
          {
            public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
            {
              EditStyledText.this.setItemColor(-16777216);
            }
          });
      }
    }

    private void buildDialogue(CharSequence paramCharSequence, CharSequence[] paramArrayOfCharSequence, DialogInterface.OnClickListener paramOnClickListener)
    {
      this.mBuilder.setTitle(paramCharSequence);
      this.mBuilder.setIcon(0);
      this.mBuilder.setPositiveButton(null, null);
      this.mBuilder.setNegativeButton(17039360, new DialogInterface.OnClickListener()
      {
        public final void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
        {
          EditStyledText.this.onStartEdit();
        }
      });
      this.mBuilder.setItems(paramArrayOfCharSequence, paramOnClickListener);
      this.mBuilder.setView(null);
      this.mBuilder.setCancelable(true);
      this.mBuilder.setOnCancelListener(new DialogInterface.OnCancelListener()
      {
        public final void onCancel(DialogInterface paramAnonymousDialogInterface)
        {
          Log.d("EditStyledText", "--- oncancel");
          EditStyledText.this.onStartEdit();
        }
      });
      this.mBuilder.show();
    }

    private boolean checkColorAlertParams()
    {
      boolean bool = false;
      Log.d("EditStyledText", "--- checkParams");
      if (this.mBuilder == null)
        Log.e("EditStyledText", "--- builder is null.");
      while (true)
      {
        return bool;
        if ((this.mColorTitle == null) || (this.mColorNames == null) || (this.mColorInts == null))
        {
          Log.e("EditStyledText", "--- color alert params are null.");
          bool = false;
        }
        else if (this.mColorNames.length != this.mColorInts.length)
        {
          Log.e("EditStyledText", "--- the length of color alert params are different.");
          bool = false;
        }
        else
        {
          bool = true;
        }
      }
    }

    public final void setAlignAlertParams(CharSequence paramCharSequence, CharSequence[] paramArrayOfCharSequence)
    {
      this.mAlignTitle = paramCharSequence;
      this.mAlignNames = paramArrayOfCharSequence;
    }

    public final void setBuilder(AlertDialog.Builder paramBuilder)
    {
      this.mBuilder = paramBuilder;
    }

    public final void setColorAlertParams(CharSequence paramCharSequence1, CharSequence[] paramArrayOfCharSequence1, CharSequence[] paramArrayOfCharSequence2, CharSequence paramCharSequence2)
    {
      this.mColorTitle = paramCharSequence1;
      this.mColorNames = paramArrayOfCharSequence1;
      this.mColorInts = paramArrayOfCharSequence2;
      this.mColorDefaultMessage = paramCharSequence2;
    }

    public final void setMarqueeAlertParams(CharSequence paramCharSequence, CharSequence[] paramArrayOfCharSequence)
    {
      this.mMarqueeTitle = paramCharSequence;
      this.mMarqueeNames = paramArrayOfCharSequence;
    }

    public final void setSizeAlertParams(CharSequence paramCharSequence, CharSequence[] paramArrayOfCharSequence1, CharSequence[] paramArrayOfCharSequence2, CharSequence[] paramArrayOfCharSequence3)
    {
      this.mSizeTitle = paramCharSequence;
      this.mSizeNames = paramArrayOfCharSequence1;
      this.mSizeDisplayInts = paramArrayOfCharSequence2;
      this.mSizeSendInts = paramArrayOfCharSequence3;
    }
  }

  public static abstract interface StyledTextHtmlConverter
  {
    public abstract Spanned fromHtml(String paramString, Html.ImageGetter paramImageGetter, Html.TagHandler paramTagHandler);
  }

  private final class StyledTextHtmlStandard
    implements EditStyledText.StyledTextHtmlConverter
  {
    private StyledTextHtmlStandard()
    {
    }

    public final Spanned fromHtml(String paramString, Html.ImageGetter paramImageGetter, Html.TagHandler paramTagHandler)
    {
      return Html.fromHtml(paramString, paramImageGetter, null);
    }
  }

  public static final class StyledTextInputConnection extends InputConnectionWrapper
  {
    EditStyledText mEST;

    public StyledTextInputConnection(InputConnection paramInputConnection, EditStyledText paramEditStyledText)
    {
      super(true);
      this.mEST = paramEditStyledText;
    }

    public final boolean commitText(CharSequence paramCharSequence, int paramInt)
    {
      Log.d("EditStyledText", "--- commitText:");
      this.mEST.mManager.unsetTextComposingMask();
      return super.commitText(paramCharSequence, paramInt);
    }

    public final boolean finishComposingText()
    {
      Log.d("EditStyledText", "--- finishcomposing:");
      if ((!this.mEST.isSoftKeyBlocked()) && (!this.mEST.isButtonsFocused()) && (!this.mEST.isEditting()))
        this.mEST.onEndEdit();
      return super.finishComposingText();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.android.ex.editstyledtext.EditStyledText
 * JD-Core Version:    0.6.2
 */