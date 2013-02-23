package com.google.wireless.realtimechat.proto;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.GeneratedMessageLite.Builder;
import com.google.protobuf.LazyStringArrayList;
import com.google.protobuf.LazyStringList;
import com.google.protobuf.MessageLite;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.UninitializedMessageException;
import com.google.protobuf.UnmodifiableLazyStringList;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class Experiments
{
  public static final class Config extends GeneratedMessageLite
    implements Experiments.ConfigOrBuilder
  {
    private static final Config defaultInstance;
    private static final long serialVersionUID;
    private List<Experiments.ExperimentMapping> experimentMapping_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;

    static
    {
      Config localConfig = new Config();
      defaultInstance = localConfig;
      localConfig.experimentMapping_ = Collections.emptyList();
    }

    private Config()
    {
    }

    private Config(Builder paramBuilder)
    {
      super();
    }

    public static Config getDefaultInstance()
    {
      return defaultInstance;
    }

    public final Config getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final Experiments.ExperimentMapping getExperimentMapping(int paramInt)
    {
      return (Experiments.ExperimentMapping)this.experimentMapping_.get(paramInt);
    }

    public final int getExperimentMappingCount()
    {
      return this.experimentMapping_.size();
    }

    public final List<Experiments.ExperimentMapping> getExperimentMappingList()
    {
      return this.experimentMapping_;
    }

    public final Experiments.ExperimentMappingOrBuilder getExperimentMappingOrBuilder(int paramInt)
    {
      return (Experiments.ExperimentMappingOrBuilder)this.experimentMapping_.get(paramInt);
    }

    public final List<? extends Experiments.ExperimentMappingOrBuilder> getExperimentMappingOrBuilderList()
    {
      return this.experimentMapping_;
    }

    public final int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1);
      int j;
      for (int m = i; ; m = j)
      {
        return m;
        j = 0;
        for (int k = 0; k < this.experimentMapping_.size(); k++)
          j += CodedOutputStream.computeMessageSize(1, (MessageLite)this.experimentMapping_.get(k));
        this.memoizedSerializedSize = j;
      }
    }

    public final boolean isInitialized()
    {
      int i = 1;
      int j = this.memoizedIsInitialized;
      if (j != -1)
        if (j != i);
      while (true)
      {
        return i;
        i = 0;
        continue;
        this.memoizedIsInitialized = i;
      }
    }

    protected final Object writeReplace()
      throws ObjectStreamException
    {
      return super.writeReplace();
    }

    public final void writeTo(CodedOutputStream paramCodedOutputStream)
      throws IOException
    {
      getSerializedSize();
      for (int i = 0; i < this.experimentMapping_.size(); i++)
        paramCodedOutputStream.writeMessage(1, (MessageLite)this.experimentMapping_.get(i));
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Experiments.Config, Builder>
      implements Experiments.ConfigOrBuilder
    {
      private int bitField0_;
      private List<Experiments.ExperimentMapping> experimentMapping_ = Collections.emptyList();

      private Experiments.Config buildPartial()
      {
        Experiments.Config localConfig = new Experiments.Config(this, (byte)0);
        if ((0x1 & this.bitField0_) == 1)
        {
          this.experimentMapping_ = Collections.unmodifiableList(this.experimentMapping_);
          this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        }
        Experiments.Config.access$2202(localConfig, this.experimentMapping_);
        return localConfig;
      }

      private Builder clone()
      {
        return new Builder().mergeFrom(buildPartial());
      }

      private void ensureExperimentMappingIsMutable()
      {
        if ((0x1 & this.bitField0_) != 1)
        {
          this.experimentMapping_ = new ArrayList(this.experimentMapping_);
          this.bitField0_ = (0x1 | this.bitField0_);
        }
      }

      private Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        while (true)
        {
          int i = paramCodedInputStream.readTag();
          switch (i)
          {
          default:
            if (parseUnknownField(paramCodedInputStream, paramExtensionRegistryLite, i))
              continue;
          case 0:
            return this;
          case 10:
          }
          Experiments.ExperimentMapping.Builder localBuilder = Experiments.ExperimentMapping.newBuilder();
          paramCodedInputStream.readMessage(localBuilder, paramExtensionRegistryLite);
          addExperimentMapping(localBuilder.buildPartial());
        }
      }

      public final Builder addAllExperimentMapping(Iterable<? extends Experiments.ExperimentMapping> paramIterable)
      {
        ensureExperimentMappingIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.experimentMapping_);
        return this;
      }

      public final Builder addExperimentMapping(int paramInt, Experiments.ExperimentMapping.Builder paramBuilder)
      {
        ensureExperimentMappingIsMutable();
        this.experimentMapping_.add(paramInt, paramBuilder.build());
        return this;
      }

      public final Builder addExperimentMapping(int paramInt, Experiments.ExperimentMapping paramExperimentMapping)
      {
        if (paramExperimentMapping == null)
          throw new NullPointerException();
        ensureExperimentMappingIsMutable();
        this.experimentMapping_.add(paramInt, paramExperimentMapping);
        return this;
      }

      public final Builder addExperimentMapping(Experiments.ExperimentMapping.Builder paramBuilder)
      {
        ensureExperimentMappingIsMutable();
        this.experimentMapping_.add(paramBuilder.build());
        return this;
      }

      public final Builder addExperimentMapping(Experiments.ExperimentMapping paramExperimentMapping)
      {
        if (paramExperimentMapping == null)
          throw new NullPointerException();
        ensureExperimentMappingIsMutable();
        this.experimentMapping_.add(paramExperimentMapping);
        return this;
      }

      public final Builder clear()
      {
        super.clear();
        this.experimentMapping_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        return this;
      }

      public final Builder clearExperimentMapping()
      {
        this.experimentMapping_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        return this;
      }

      public final Experiments.Config getDefaultInstanceForType()
      {
        return Experiments.Config.getDefaultInstance();
      }

      public final Experiments.ExperimentMapping getExperimentMapping(int paramInt)
      {
        return (Experiments.ExperimentMapping)this.experimentMapping_.get(paramInt);
      }

      public final int getExperimentMappingCount()
      {
        return this.experimentMapping_.size();
      }

      public final List<Experiments.ExperimentMapping> getExperimentMappingList()
      {
        return Collections.unmodifiableList(this.experimentMapping_);
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(Experiments.Config paramConfig)
      {
        if (paramConfig == Experiments.Config.getDefaultInstance());
        while (true)
        {
          return this;
          if (!paramConfig.experimentMapping_.isEmpty())
            if (this.experimentMapping_.isEmpty())
            {
              this.experimentMapping_ = paramConfig.experimentMapping_;
              this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
            }
            else
            {
              ensureExperimentMappingIsMutable();
              this.experimentMapping_.addAll(paramConfig.experimentMapping_);
            }
        }
      }

      public final Builder setExperimentMapping(int paramInt, Experiments.ExperimentMapping.Builder paramBuilder)
      {
        ensureExperimentMappingIsMutable();
        this.experimentMapping_.set(paramInt, paramBuilder.build());
        return this;
      }

      public final Builder setExperimentMapping(int paramInt, Experiments.ExperimentMapping paramExperimentMapping)
      {
        if (paramExperimentMapping == null)
          throw new NullPointerException();
        ensureExperimentMappingIsMutable();
        this.experimentMapping_.set(paramInt, paramExperimentMapping);
        return this;
      }
    }
  }

  public static abstract interface ConfigOrBuilder extends MessageLiteOrBuilder
  {
    public abstract Experiments.ExperimentMapping getExperimentMapping(int paramInt);

    public abstract int getExperimentMappingCount();

    public abstract List<Experiments.ExperimentMapping> getExperimentMappingList();
  }

  public static final class ExperimentMapping extends GeneratedMessageLite
    implements Experiments.ExperimentMappingOrBuilder
  {
    private static final ExperimentMapping defaultInstance;
    private static final long serialVersionUID;
    private int bitField0_;
    private boolean disabledAllUser_;
    private LazyStringList disabledDomain_;
    private LazyStringList disabledEmail_;
    private LazyStringList disabledId_;
    private LazyStringList disabledRegexDomain_;
    private LazyStringList disabledRegexEmail_;
    private LazyStringList disabledRegexId_;
    private boolean enabledAllUser_;
    private LazyStringList enabledDomain_;
    private LazyStringList enabledEmail_;
    private LazyStringList enabledId_;
    private LazyStringList enabledRegexDomain_;
    private LazyStringList enabledRegexEmail_;
    private LazyStringList enabledRegexId_;
    private List<Integer> experiment_;
    private byte memoizedIsInitialized = -1;
    private int memoizedSerializedSize = -1;

    static
    {
      ExperimentMapping localExperimentMapping = new ExperimentMapping();
      defaultInstance = localExperimentMapping;
      localExperimentMapping.experiment_ = Collections.emptyList();
      localExperimentMapping.disabledId_ = LazyStringArrayList.EMPTY;
      localExperimentMapping.enabledId_ = LazyStringArrayList.EMPTY;
      localExperimentMapping.disabledRegexId_ = LazyStringArrayList.EMPTY;
      localExperimentMapping.enabledRegexId_ = LazyStringArrayList.EMPTY;
      localExperimentMapping.disabledEmail_ = LazyStringArrayList.EMPTY;
      localExperimentMapping.enabledEmail_ = LazyStringArrayList.EMPTY;
      localExperimentMapping.disabledRegexEmail_ = LazyStringArrayList.EMPTY;
      localExperimentMapping.enabledRegexEmail_ = LazyStringArrayList.EMPTY;
      localExperimentMapping.disabledDomain_ = LazyStringArrayList.EMPTY;
      localExperimentMapping.enabledDomain_ = LazyStringArrayList.EMPTY;
      localExperimentMapping.disabledRegexDomain_ = LazyStringArrayList.EMPTY;
      localExperimentMapping.enabledRegexDomain_ = LazyStringArrayList.EMPTY;
      localExperimentMapping.disabledAllUser_ = false;
      localExperimentMapping.enabledAllUser_ = false;
    }

    private ExperimentMapping()
    {
    }

    private ExperimentMapping(Builder paramBuilder)
    {
      super();
    }

    public static ExperimentMapping getDefaultInstance()
    {
      return defaultInstance;
    }

    public static Builder newBuilder()
    {
      return Builder.access$100();
    }

    public final ExperimentMapping getDefaultInstanceForType()
    {
      return defaultInstance;
    }

    public final boolean getDisabledAllUser()
    {
      return this.disabledAllUser_;
    }

    public final String getDisabledDomain(int paramInt)
    {
      return (String)this.disabledDomain_.get(paramInt);
    }

    public final int getDisabledDomainCount()
    {
      return this.disabledDomain_.size();
    }

    public final List<String> getDisabledDomainList()
    {
      return this.disabledDomain_;
    }

    public final String getDisabledEmail(int paramInt)
    {
      return (String)this.disabledEmail_.get(paramInt);
    }

    public final int getDisabledEmailCount()
    {
      return this.disabledEmail_.size();
    }

    public final List<String> getDisabledEmailList()
    {
      return this.disabledEmail_;
    }

    public final String getDisabledId(int paramInt)
    {
      return (String)this.disabledId_.get(paramInt);
    }

    public final int getDisabledIdCount()
    {
      return this.disabledId_.size();
    }

    public final List<String> getDisabledIdList()
    {
      return this.disabledId_;
    }

    public final String getDisabledRegexDomain(int paramInt)
    {
      return (String)this.disabledRegexDomain_.get(paramInt);
    }

    public final int getDisabledRegexDomainCount()
    {
      return this.disabledRegexDomain_.size();
    }

    public final List<String> getDisabledRegexDomainList()
    {
      return this.disabledRegexDomain_;
    }

    public final String getDisabledRegexEmail(int paramInt)
    {
      return (String)this.disabledRegexEmail_.get(paramInt);
    }

    public final int getDisabledRegexEmailCount()
    {
      return this.disabledRegexEmail_.size();
    }

    public final List<String> getDisabledRegexEmailList()
    {
      return this.disabledRegexEmail_;
    }

    public final String getDisabledRegexId(int paramInt)
    {
      return (String)this.disabledRegexId_.get(paramInt);
    }

    public final int getDisabledRegexIdCount()
    {
      return this.disabledRegexId_.size();
    }

    public final List<String> getDisabledRegexIdList()
    {
      return this.disabledRegexId_;
    }

    public final boolean getEnabledAllUser()
    {
      return this.enabledAllUser_;
    }

    public final String getEnabledDomain(int paramInt)
    {
      return (String)this.enabledDomain_.get(paramInt);
    }

    public final int getEnabledDomainCount()
    {
      return this.enabledDomain_.size();
    }

    public final List<String> getEnabledDomainList()
    {
      return this.enabledDomain_;
    }

    public final String getEnabledEmail(int paramInt)
    {
      return (String)this.enabledEmail_.get(paramInt);
    }

    public final int getEnabledEmailCount()
    {
      return this.enabledEmail_.size();
    }

    public final List<String> getEnabledEmailList()
    {
      return this.enabledEmail_;
    }

    public final String getEnabledId(int paramInt)
    {
      return (String)this.enabledId_.get(paramInt);
    }

    public final int getEnabledIdCount()
    {
      return this.enabledId_.size();
    }

    public final List<String> getEnabledIdList()
    {
      return this.enabledId_;
    }

    public final String getEnabledRegexDomain(int paramInt)
    {
      return (String)this.enabledRegexDomain_.get(paramInt);
    }

    public final int getEnabledRegexDomainCount()
    {
      return this.enabledRegexDomain_.size();
    }

    public final List<String> getEnabledRegexDomainList()
    {
      return this.enabledRegexDomain_;
    }

    public final String getEnabledRegexEmail(int paramInt)
    {
      return (String)this.enabledRegexEmail_.get(paramInt);
    }

    public final int getEnabledRegexEmailCount()
    {
      return this.enabledRegexEmail_.size();
    }

    public final List<String> getEnabledRegexEmailList()
    {
      return this.enabledRegexEmail_;
    }

    public final String getEnabledRegexId(int paramInt)
    {
      return (String)this.enabledRegexId_.get(paramInt);
    }

    public final int getEnabledRegexIdCount()
    {
      return this.enabledRegexId_.size();
    }

    public final List<String> getEnabledRegexIdList()
    {
      return this.enabledRegexId_;
    }

    public final int getExperiment(int paramInt)
    {
      return ((Integer)this.experiment_.get(paramInt)).intValue();
    }

    public final int getExperimentCount()
    {
      return this.experiment_.size();
    }

    public final List<Integer> getExperimentList()
    {
      return this.experiment_;
    }

    public final int getSerializedSize()
    {
      int i = this.memoizedSerializedSize;
      if (i != -1);
      int i35;
      for (int i36 = i; ; i36 = i35)
      {
        return i36;
        int j = 0;
        for (int k = 0; k < this.experiment_.size(); k++)
          j += CodedOutputStream.computeInt32SizeNoTag(((Integer)this.experiment_.get(k)).intValue());
        int m = j + 0 + 1 * getExperimentList().size();
        int n = 0;
        for (int i1 = 0; i1 < this.disabledId_.size(); i1++)
          n += CodedOutputStream.computeBytesSizeNoTag(this.disabledId_.getByteString(i1));
        int i2 = m + n + 1 * getDisabledIdList().size();
        int i3 = 0;
        for (int i4 = 0; i4 < this.enabledId_.size(); i4++)
          i3 += CodedOutputStream.computeBytesSizeNoTag(this.enabledId_.getByteString(i4));
        int i5 = i2 + i3 + 1 * getEnabledIdList().size();
        int i6 = 0;
        for (int i7 = 0; i7 < this.disabledRegexId_.size(); i7++)
          i6 += CodedOutputStream.computeBytesSizeNoTag(this.disabledRegexId_.getByteString(i7));
        int i8 = i5 + i6 + 1 * getDisabledRegexIdList().size();
        int i9 = 0;
        for (int i10 = 0; i10 < this.enabledRegexId_.size(); i10++)
          i9 += CodedOutputStream.computeBytesSizeNoTag(this.enabledRegexId_.getByteString(i10));
        int i11 = i8 + i9 + 1 * getEnabledRegexIdList().size();
        int i12 = 0;
        for (int i13 = 0; i13 < this.disabledEmail_.size(); i13++)
          i12 += CodedOutputStream.computeBytesSizeNoTag(this.disabledEmail_.getByteString(i13));
        int i14 = i11 + i12 + 1 * getDisabledEmailList().size();
        int i15 = 0;
        for (int i16 = 0; i16 < this.enabledEmail_.size(); i16++)
          i15 += CodedOutputStream.computeBytesSizeNoTag(this.enabledEmail_.getByteString(i16));
        int i17 = i14 + i15 + 1 * getEnabledEmailList().size();
        int i18 = 0;
        for (int i19 = 0; i19 < this.disabledRegexEmail_.size(); i19++)
          i18 += CodedOutputStream.computeBytesSizeNoTag(this.disabledRegexEmail_.getByteString(i19));
        int i20 = i17 + i18 + 1 * getDisabledRegexEmailList().size();
        int i21 = 0;
        for (int i22 = 0; i22 < this.enabledRegexEmail_.size(); i22++)
          i21 += CodedOutputStream.computeBytesSizeNoTag(this.enabledRegexEmail_.getByteString(i22));
        int i23 = i20 + i21 + 1 * getEnabledRegexEmailList().size();
        int i24 = 0;
        for (int i25 = 0; i25 < this.disabledDomain_.size(); i25++)
          i24 += CodedOutputStream.computeBytesSizeNoTag(this.disabledDomain_.getByteString(i25));
        int i26 = i23 + i24 + 1 * getDisabledDomainList().size();
        int i27 = 0;
        for (int i28 = 0; i28 < this.enabledDomain_.size(); i28++)
          i27 += CodedOutputStream.computeBytesSizeNoTag(this.enabledDomain_.getByteString(i28));
        int i29 = i26 + i27 + 1 * getEnabledDomainList().size();
        int i30 = 0;
        for (int i31 = 0; i31 < this.disabledRegexDomain_.size(); i31++)
          i30 += CodedOutputStream.computeBytesSizeNoTag(this.disabledRegexDomain_.getByteString(i31));
        int i32 = i29 + i30 + 1 * getDisabledRegexDomainList().size();
        int i33 = 0;
        for (int i34 = 0; i34 < this.enabledRegexDomain_.size(); i34++)
          i33 += CodedOutputStream.computeBytesSizeNoTag(this.enabledRegexDomain_.getByteString(i34));
        i35 = i32 + i33 + 1 * getEnabledRegexDomainList().size();
        if ((0x1 & this.bitField0_) == 1)
          i35 += CodedOutputStream.computeBoolSize(14, this.disabledAllUser_);
        if ((0x2 & this.bitField0_) == 2)
          i35 += CodedOutputStream.computeBoolSize(15, this.enabledAllUser_);
        this.memoizedSerializedSize = i35;
      }
    }

    public final boolean hasDisabledAllUser()
    {
      int i = 1;
      if ((0x1 & this.bitField0_) == i);
      while (true)
      {
        return i;
        int j = 0;
      }
    }

    public final boolean hasEnabledAllUser()
    {
      if ((0x2 & this.bitField0_) == 2);
      for (boolean bool = true; ; bool = false)
        return bool;
    }

    public final boolean isInitialized()
    {
      int i = 1;
      int j = this.memoizedIsInitialized;
      if (j != -1)
        if (j != i);
      while (true)
      {
        return i;
        i = 0;
        continue;
        this.memoizedIsInitialized = i;
      }
    }

    protected final Object writeReplace()
      throws ObjectStreamException
    {
      return super.writeReplace();
    }

    public final void writeTo(CodedOutputStream paramCodedOutputStream)
      throws IOException
    {
      getSerializedSize();
      for (int i = 0; i < this.experiment_.size(); i++)
        paramCodedOutputStream.writeInt32(1, ((Integer)this.experiment_.get(i)).intValue());
      for (int j = 0; j < this.disabledId_.size(); j++)
        paramCodedOutputStream.writeBytes(2, this.disabledId_.getByteString(j));
      for (int k = 0; k < this.enabledId_.size(); k++)
        paramCodedOutputStream.writeBytes(3, this.enabledId_.getByteString(k));
      for (int m = 0; m < this.disabledRegexId_.size(); m++)
        paramCodedOutputStream.writeBytes(4, this.disabledRegexId_.getByteString(m));
      for (int n = 0; n < this.enabledRegexId_.size(); n++)
        paramCodedOutputStream.writeBytes(5, this.enabledRegexId_.getByteString(n));
      for (int i1 = 0; i1 < this.disabledEmail_.size(); i1++)
        paramCodedOutputStream.writeBytes(6, this.disabledEmail_.getByteString(i1));
      for (int i2 = 0; i2 < this.enabledEmail_.size(); i2++)
        paramCodedOutputStream.writeBytes(7, this.enabledEmail_.getByteString(i2));
      for (int i3 = 0; i3 < this.disabledRegexEmail_.size(); i3++)
        paramCodedOutputStream.writeBytes(8, this.disabledRegexEmail_.getByteString(i3));
      for (int i4 = 0; i4 < this.enabledRegexEmail_.size(); i4++)
        paramCodedOutputStream.writeBytes(9, this.enabledRegexEmail_.getByteString(i4));
      for (int i5 = 0; i5 < this.disabledDomain_.size(); i5++)
        paramCodedOutputStream.writeBytes(10, this.disabledDomain_.getByteString(i5));
      for (int i6 = 0; i6 < this.enabledDomain_.size(); i6++)
        paramCodedOutputStream.writeBytes(11, this.enabledDomain_.getByteString(i6));
      for (int i7 = 0; i7 < this.disabledRegexDomain_.size(); i7++)
        paramCodedOutputStream.writeBytes(12, this.disabledRegexDomain_.getByteString(i7));
      for (int i8 = 0; i8 < this.enabledRegexDomain_.size(); i8++)
        paramCodedOutputStream.writeBytes(13, this.enabledRegexDomain_.getByteString(i8));
      if ((0x1 & this.bitField0_) == 1)
        paramCodedOutputStream.writeBool(14, this.disabledAllUser_);
      if ((0x2 & this.bitField0_) == 2)
        paramCodedOutputStream.writeBool(15, this.enabledAllUser_);
    }

    public static final class Builder extends GeneratedMessageLite.Builder<Experiments.ExperimentMapping, Builder>
      implements Experiments.ExperimentMappingOrBuilder
    {
      private int bitField0_;
      private boolean disabledAllUser_;
      private LazyStringList disabledDomain_ = LazyStringArrayList.EMPTY;
      private LazyStringList disabledEmail_ = LazyStringArrayList.EMPTY;
      private LazyStringList disabledId_ = LazyStringArrayList.EMPTY;
      private LazyStringList disabledRegexDomain_ = LazyStringArrayList.EMPTY;
      private LazyStringList disabledRegexEmail_ = LazyStringArrayList.EMPTY;
      private LazyStringList disabledRegexId_ = LazyStringArrayList.EMPTY;
      private boolean enabledAllUser_;
      private LazyStringList enabledDomain_ = LazyStringArrayList.EMPTY;
      private LazyStringList enabledEmail_ = LazyStringArrayList.EMPTY;
      private LazyStringList enabledId_ = LazyStringArrayList.EMPTY;
      private LazyStringList enabledRegexDomain_ = LazyStringArrayList.EMPTY;
      private LazyStringList enabledRegexEmail_ = LazyStringArrayList.EMPTY;
      private LazyStringList enabledRegexId_ = LazyStringArrayList.EMPTY;
      private List<Integer> experiment_ = Collections.emptyList();

      private Builder clone()
      {
        return new Builder().mergeFrom(buildPartial());
      }

      private void ensureDisabledDomainIsMutable()
      {
        if ((0x200 & this.bitField0_) != 512)
        {
          this.disabledDomain_ = new LazyStringArrayList(this.disabledDomain_);
          this.bitField0_ = (0x200 | this.bitField0_);
        }
      }

      private void ensureDisabledEmailIsMutable()
      {
        if ((0x20 & this.bitField0_) != 32)
        {
          this.disabledEmail_ = new LazyStringArrayList(this.disabledEmail_);
          this.bitField0_ = (0x20 | this.bitField0_);
        }
      }

      private void ensureDisabledIdIsMutable()
      {
        if ((0x2 & this.bitField0_) != 2)
        {
          this.disabledId_ = new LazyStringArrayList(this.disabledId_);
          this.bitField0_ = (0x2 | this.bitField0_);
        }
      }

      private void ensureDisabledRegexDomainIsMutable()
      {
        if ((0x800 & this.bitField0_) != 2048)
        {
          this.disabledRegexDomain_ = new LazyStringArrayList(this.disabledRegexDomain_);
          this.bitField0_ = (0x800 | this.bitField0_);
        }
      }

      private void ensureDisabledRegexEmailIsMutable()
      {
        if ((0x80 & this.bitField0_) != 128)
        {
          this.disabledRegexEmail_ = new LazyStringArrayList(this.disabledRegexEmail_);
          this.bitField0_ = (0x80 | this.bitField0_);
        }
      }

      private void ensureDisabledRegexIdIsMutable()
      {
        if ((0x8 & this.bitField0_) != 8)
        {
          this.disabledRegexId_ = new LazyStringArrayList(this.disabledRegexId_);
          this.bitField0_ = (0x8 | this.bitField0_);
        }
      }

      private void ensureEnabledDomainIsMutable()
      {
        if ((0x400 & this.bitField0_) != 1024)
        {
          this.enabledDomain_ = new LazyStringArrayList(this.enabledDomain_);
          this.bitField0_ = (0x400 | this.bitField0_);
        }
      }

      private void ensureEnabledEmailIsMutable()
      {
        if ((0x40 & this.bitField0_) != 64)
        {
          this.enabledEmail_ = new LazyStringArrayList(this.enabledEmail_);
          this.bitField0_ = (0x40 | this.bitField0_);
        }
      }

      private void ensureEnabledIdIsMutable()
      {
        if ((0x4 & this.bitField0_) != 4)
        {
          this.enabledId_ = new LazyStringArrayList(this.enabledId_);
          this.bitField0_ = (0x4 | this.bitField0_);
        }
      }

      private void ensureEnabledRegexDomainIsMutable()
      {
        if ((0x1000 & this.bitField0_) != 4096)
        {
          this.enabledRegexDomain_ = new LazyStringArrayList(this.enabledRegexDomain_);
          this.bitField0_ = (0x1000 | this.bitField0_);
        }
      }

      private void ensureEnabledRegexEmailIsMutable()
      {
        if ((0x100 & this.bitField0_) != 256)
        {
          this.enabledRegexEmail_ = new LazyStringArrayList(this.enabledRegexEmail_);
          this.bitField0_ = (0x100 | this.bitField0_);
        }
      }

      private void ensureEnabledRegexIdIsMutable()
      {
        if ((0x10 & this.bitField0_) != 16)
        {
          this.enabledRegexId_ = new LazyStringArrayList(this.enabledRegexId_);
          this.bitField0_ = (0x10 | this.bitField0_);
        }
      }

      private void ensureExperimentIsMutable()
      {
        if ((0x1 & this.bitField0_) != 1)
        {
          this.experiment_ = new ArrayList(this.experiment_);
          this.bitField0_ = (0x1 | this.bitField0_);
        }
      }

      private Builder mergeFrom(CodedInputStream paramCodedInputStream, ExtensionRegistryLite paramExtensionRegistryLite)
        throws IOException
      {
        while (true)
        {
          int i = paramCodedInputStream.readTag();
          switch (i)
          {
          default:
            if (parseUnknownField(paramCodedInputStream, paramExtensionRegistryLite, i))
              continue;
          case 0:
            return this;
          case 8:
            ensureExperimentIsMutable();
            this.experiment_.add(Integer.valueOf(paramCodedInputStream.readInt32()));
            break;
          case 10:
            int j = paramCodedInputStream.pushLimit(paramCodedInputStream.readRawVarint32());
            while (paramCodedInputStream.getBytesUntilLimit() > 0)
              addExperiment(paramCodedInputStream.readInt32());
            paramCodedInputStream.popLimit(j);
            break;
          case 18:
            ensureDisabledIdIsMutable();
            this.disabledId_.add(paramCodedInputStream.readBytes());
            break;
          case 26:
            ensureEnabledIdIsMutable();
            this.enabledId_.add(paramCodedInputStream.readBytes());
            break;
          case 34:
            ensureDisabledRegexIdIsMutable();
            this.disabledRegexId_.add(paramCodedInputStream.readBytes());
            break;
          case 42:
            ensureEnabledRegexIdIsMutable();
            this.enabledRegexId_.add(paramCodedInputStream.readBytes());
            break;
          case 50:
            ensureDisabledEmailIsMutable();
            this.disabledEmail_.add(paramCodedInputStream.readBytes());
            break;
          case 58:
            ensureEnabledEmailIsMutable();
            this.enabledEmail_.add(paramCodedInputStream.readBytes());
            break;
          case 66:
            ensureDisabledRegexEmailIsMutable();
            this.disabledRegexEmail_.add(paramCodedInputStream.readBytes());
            break;
          case 74:
            ensureEnabledRegexEmailIsMutable();
            this.enabledRegexEmail_.add(paramCodedInputStream.readBytes());
            break;
          case 82:
            ensureDisabledDomainIsMutable();
            this.disabledDomain_.add(paramCodedInputStream.readBytes());
            break;
          case 90:
            ensureEnabledDomainIsMutable();
            this.enabledDomain_.add(paramCodedInputStream.readBytes());
            break;
          case 98:
            ensureDisabledRegexDomainIsMutable();
            this.disabledRegexDomain_.add(paramCodedInputStream.readBytes());
            break;
          case 106:
            ensureEnabledRegexDomainIsMutable();
            this.enabledRegexDomain_.add(paramCodedInputStream.readBytes());
            break;
          case 112:
            this.bitField0_ = (0x2000 | this.bitField0_);
            this.disabledAllUser_ = paramCodedInputStream.readBool();
            break;
          case 120:
          }
          this.bitField0_ = (0x4000 | this.bitField0_);
          this.enabledAllUser_ = paramCodedInputStream.readBool();
        }
      }

      public final Builder addAllDisabledDomain(Iterable<String> paramIterable)
      {
        ensureDisabledDomainIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.disabledDomain_);
        return this;
      }

      public final Builder addAllDisabledEmail(Iterable<String> paramIterable)
      {
        ensureDisabledEmailIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.disabledEmail_);
        return this;
      }

      public final Builder addAllDisabledId(Iterable<String> paramIterable)
      {
        ensureDisabledIdIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.disabledId_);
        return this;
      }

      public final Builder addAllDisabledRegexDomain(Iterable<String> paramIterable)
      {
        ensureDisabledRegexDomainIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.disabledRegexDomain_);
        return this;
      }

      public final Builder addAllDisabledRegexEmail(Iterable<String> paramIterable)
      {
        ensureDisabledRegexEmailIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.disabledRegexEmail_);
        return this;
      }

      public final Builder addAllDisabledRegexId(Iterable<String> paramIterable)
      {
        ensureDisabledRegexIdIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.disabledRegexId_);
        return this;
      }

      public final Builder addAllEnabledDomain(Iterable<String> paramIterable)
      {
        ensureEnabledDomainIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.enabledDomain_);
        return this;
      }

      public final Builder addAllEnabledEmail(Iterable<String> paramIterable)
      {
        ensureEnabledEmailIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.enabledEmail_);
        return this;
      }

      public final Builder addAllEnabledId(Iterable<String> paramIterable)
      {
        ensureEnabledIdIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.enabledId_);
        return this;
      }

      public final Builder addAllEnabledRegexDomain(Iterable<String> paramIterable)
      {
        ensureEnabledRegexDomainIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.enabledRegexDomain_);
        return this;
      }

      public final Builder addAllEnabledRegexEmail(Iterable<String> paramIterable)
      {
        ensureEnabledRegexEmailIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.enabledRegexEmail_);
        return this;
      }

      public final Builder addAllEnabledRegexId(Iterable<String> paramIterable)
      {
        ensureEnabledRegexIdIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.enabledRegexId_);
        return this;
      }

      public final Builder addAllExperiment(Iterable<? extends Integer> paramIterable)
      {
        ensureExperimentIsMutable();
        GeneratedMessageLite.Builder.addAll(paramIterable, this.experiment_);
        return this;
      }

      public final Builder addDisabledDomain(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureDisabledDomainIsMutable();
        this.disabledDomain_.add(paramString);
        return this;
      }

      public final Builder addDisabledEmail(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureDisabledEmailIsMutable();
        this.disabledEmail_.add(paramString);
        return this;
      }

      public final Builder addDisabledId(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureDisabledIdIsMutable();
        this.disabledId_.add(paramString);
        return this;
      }

      public final Builder addDisabledRegexDomain(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureDisabledRegexDomainIsMutable();
        this.disabledRegexDomain_.add(paramString);
        return this;
      }

      public final Builder addDisabledRegexEmail(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureDisabledRegexEmailIsMutable();
        this.disabledRegexEmail_.add(paramString);
        return this;
      }

      public final Builder addDisabledRegexId(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureDisabledRegexIdIsMutable();
        this.disabledRegexId_.add(paramString);
        return this;
      }

      public final Builder addEnabledDomain(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureEnabledDomainIsMutable();
        this.enabledDomain_.add(paramString);
        return this;
      }

      public final Builder addEnabledEmail(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureEnabledEmailIsMutable();
        this.enabledEmail_.add(paramString);
        return this;
      }

      public final Builder addEnabledId(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureEnabledIdIsMutable();
        this.enabledId_.add(paramString);
        return this;
      }

      public final Builder addEnabledRegexDomain(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureEnabledRegexDomainIsMutable();
        this.enabledRegexDomain_.add(paramString);
        return this;
      }

      public final Builder addEnabledRegexEmail(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureEnabledRegexEmailIsMutable();
        this.enabledRegexEmail_.add(paramString);
        return this;
      }

      public final Builder addEnabledRegexId(String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureEnabledRegexIdIsMutable();
        this.enabledRegexId_.add(paramString);
        return this;
      }

      public final Builder addExperiment(int paramInt)
      {
        ensureExperimentIsMutable();
        this.experiment_.add(Integer.valueOf(paramInt));
        return this;
      }

      public final Experiments.ExperimentMapping build()
      {
        Experiments.ExperimentMapping localExperimentMapping = buildPartial();
        if (!localExperimentMapping.isInitialized())
          throw new UninitializedMessageException();
        return localExperimentMapping;
      }

      public final Experiments.ExperimentMapping buildPartial()
      {
        Experiments.ExperimentMapping localExperimentMapping = new Experiments.ExperimentMapping(this, (byte)0);
        int i = this.bitField0_;
        if ((0x1 & this.bitField0_) == 1)
        {
          this.experiment_ = Collections.unmodifiableList(this.experiment_);
          this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        }
        Experiments.ExperimentMapping.access$302(localExperimentMapping, this.experiment_);
        if ((0x2 & this.bitField0_) == 2)
        {
          this.disabledId_ = new UnmodifiableLazyStringList(this.disabledId_);
          this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        }
        Experiments.ExperimentMapping.access$402(localExperimentMapping, this.disabledId_);
        if ((0x4 & this.bitField0_) == 4)
        {
          this.enabledId_ = new UnmodifiableLazyStringList(this.enabledId_);
          this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        }
        Experiments.ExperimentMapping.access$502(localExperimentMapping, this.enabledId_);
        if ((0x8 & this.bitField0_) == 8)
        {
          this.disabledRegexId_ = new UnmodifiableLazyStringList(this.disabledRegexId_);
          this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        }
        Experiments.ExperimentMapping.access$602(localExperimentMapping, this.disabledRegexId_);
        if ((0x10 & this.bitField0_) == 16)
        {
          this.enabledRegexId_ = new UnmodifiableLazyStringList(this.enabledRegexId_);
          this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        }
        Experiments.ExperimentMapping.access$702(localExperimentMapping, this.enabledRegexId_);
        if ((0x20 & this.bitField0_) == 32)
        {
          this.disabledEmail_ = new UnmodifiableLazyStringList(this.disabledEmail_);
          this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        }
        Experiments.ExperimentMapping.access$802(localExperimentMapping, this.disabledEmail_);
        if ((0x40 & this.bitField0_) == 64)
        {
          this.enabledEmail_ = new UnmodifiableLazyStringList(this.enabledEmail_);
          this.bitField0_ = (0xFFFFFFBF & this.bitField0_);
        }
        Experiments.ExperimentMapping.access$902(localExperimentMapping, this.enabledEmail_);
        if ((0x80 & this.bitField0_) == 128)
        {
          this.disabledRegexEmail_ = new UnmodifiableLazyStringList(this.disabledRegexEmail_);
          this.bitField0_ = (0xFFFFFF7F & this.bitField0_);
        }
        Experiments.ExperimentMapping.access$1002(localExperimentMapping, this.disabledRegexEmail_);
        if ((0x100 & this.bitField0_) == 256)
        {
          this.enabledRegexEmail_ = new UnmodifiableLazyStringList(this.enabledRegexEmail_);
          this.bitField0_ = (0xFFFFFEFF & this.bitField0_);
        }
        Experiments.ExperimentMapping.access$1102(localExperimentMapping, this.enabledRegexEmail_);
        if ((0x200 & this.bitField0_) == 512)
        {
          this.disabledDomain_ = new UnmodifiableLazyStringList(this.disabledDomain_);
          this.bitField0_ = (0xFFFFFDFF & this.bitField0_);
        }
        Experiments.ExperimentMapping.access$1202(localExperimentMapping, this.disabledDomain_);
        if ((0x400 & this.bitField0_) == 1024)
        {
          this.enabledDomain_ = new UnmodifiableLazyStringList(this.enabledDomain_);
          this.bitField0_ = (0xFFFFFBFF & this.bitField0_);
        }
        Experiments.ExperimentMapping.access$1302(localExperimentMapping, this.enabledDomain_);
        if ((0x800 & this.bitField0_) == 2048)
        {
          this.disabledRegexDomain_ = new UnmodifiableLazyStringList(this.disabledRegexDomain_);
          this.bitField0_ = (0xFFFFF7FF & this.bitField0_);
        }
        Experiments.ExperimentMapping.access$1402(localExperimentMapping, this.disabledRegexDomain_);
        if ((0x1000 & this.bitField0_) == 4096)
        {
          this.enabledRegexDomain_ = new UnmodifiableLazyStringList(this.enabledRegexDomain_);
          this.bitField0_ = (0xFFFFEFFF & this.bitField0_);
        }
        Experiments.ExperimentMapping.access$1502(localExperimentMapping, this.enabledRegexDomain_);
        int j = i & 0x2000;
        int k = 0;
        if (j == 8192)
          k = 1;
        Experiments.ExperimentMapping.access$1602(localExperimentMapping, this.disabledAllUser_);
        if ((i & 0x4000) == 16384)
          k |= 2;
        Experiments.ExperimentMapping.access$1702(localExperimentMapping, this.enabledAllUser_);
        Experiments.ExperimentMapping.access$1802(localExperimentMapping, k);
        return localExperimentMapping;
      }

      public final Builder clear()
      {
        super.clear();
        this.experiment_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        this.disabledId_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        this.enabledId_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        this.disabledRegexId_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        this.enabledRegexId_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        this.disabledEmail_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        this.enabledEmail_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFFBF & this.bitField0_);
        this.disabledRegexEmail_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFF7F & this.bitField0_);
        this.enabledRegexEmail_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFEFF & this.bitField0_);
        this.disabledDomain_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFDFF & this.bitField0_);
        this.enabledDomain_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFBFF & this.bitField0_);
        this.disabledRegexDomain_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFF7FF & this.bitField0_);
        this.enabledRegexDomain_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFEFFF & this.bitField0_);
        this.disabledAllUser_ = false;
        this.bitField0_ = (0xFFFFDFFF & this.bitField0_);
        this.enabledAllUser_ = false;
        this.bitField0_ = (0xFFFFBFFF & this.bitField0_);
        return this;
      }

      public final Builder clearDisabledAllUser()
      {
        this.bitField0_ = (0xFFFFDFFF & this.bitField0_);
        this.disabledAllUser_ = false;
        return this;
      }

      public final Builder clearDisabledDomain()
      {
        this.disabledDomain_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFDFF & this.bitField0_);
        return this;
      }

      public final Builder clearDisabledEmail()
      {
        this.disabledEmail_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
        return this;
      }

      public final Builder clearDisabledId()
      {
        this.disabledId_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
        return this;
      }

      public final Builder clearDisabledRegexDomain()
      {
        this.disabledRegexDomain_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFF7FF & this.bitField0_);
        return this;
      }

      public final Builder clearDisabledRegexEmail()
      {
        this.disabledRegexEmail_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFF7F & this.bitField0_);
        return this;
      }

      public final Builder clearDisabledRegexId()
      {
        this.disabledRegexId_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
        return this;
      }

      public final Builder clearEnabledAllUser()
      {
        this.bitField0_ = (0xFFFFBFFF & this.bitField0_);
        this.enabledAllUser_ = false;
        return this;
      }

      public final Builder clearEnabledDomain()
      {
        this.enabledDomain_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFBFF & this.bitField0_);
        return this;
      }

      public final Builder clearEnabledEmail()
      {
        this.enabledEmail_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFFBF & this.bitField0_);
        return this;
      }

      public final Builder clearEnabledId()
      {
        this.enabledId_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
        return this;
      }

      public final Builder clearEnabledRegexDomain()
      {
        this.enabledRegexDomain_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFEFFF & this.bitField0_);
        return this;
      }

      public final Builder clearEnabledRegexEmail()
      {
        this.enabledRegexEmail_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFEFF & this.bitField0_);
        return this;
      }

      public final Builder clearEnabledRegexId()
      {
        this.enabledRegexId_ = LazyStringArrayList.EMPTY;
        this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
        return this;
      }

      public final Builder clearExperiment()
      {
        this.experiment_ = Collections.emptyList();
        this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
        return this;
      }

      public final Experiments.ExperimentMapping getDefaultInstanceForType()
      {
        return Experiments.ExperimentMapping.getDefaultInstance();
      }

      public final boolean getDisabledAllUser()
      {
        return this.disabledAllUser_;
      }

      public final String getDisabledDomain(int paramInt)
      {
        return (String)this.disabledDomain_.get(paramInt);
      }

      public final int getDisabledDomainCount()
      {
        return this.disabledDomain_.size();
      }

      public final List<String> getDisabledDomainList()
      {
        return Collections.unmodifiableList(this.disabledDomain_);
      }

      public final String getDisabledEmail(int paramInt)
      {
        return (String)this.disabledEmail_.get(paramInt);
      }

      public final int getDisabledEmailCount()
      {
        return this.disabledEmail_.size();
      }

      public final List<String> getDisabledEmailList()
      {
        return Collections.unmodifiableList(this.disabledEmail_);
      }

      public final String getDisabledId(int paramInt)
      {
        return (String)this.disabledId_.get(paramInt);
      }

      public final int getDisabledIdCount()
      {
        return this.disabledId_.size();
      }

      public final List<String> getDisabledIdList()
      {
        return Collections.unmodifiableList(this.disabledId_);
      }

      public final String getDisabledRegexDomain(int paramInt)
      {
        return (String)this.disabledRegexDomain_.get(paramInt);
      }

      public final int getDisabledRegexDomainCount()
      {
        return this.disabledRegexDomain_.size();
      }

      public final List<String> getDisabledRegexDomainList()
      {
        return Collections.unmodifiableList(this.disabledRegexDomain_);
      }

      public final String getDisabledRegexEmail(int paramInt)
      {
        return (String)this.disabledRegexEmail_.get(paramInt);
      }

      public final int getDisabledRegexEmailCount()
      {
        return this.disabledRegexEmail_.size();
      }

      public final List<String> getDisabledRegexEmailList()
      {
        return Collections.unmodifiableList(this.disabledRegexEmail_);
      }

      public final String getDisabledRegexId(int paramInt)
      {
        return (String)this.disabledRegexId_.get(paramInt);
      }

      public final int getDisabledRegexIdCount()
      {
        return this.disabledRegexId_.size();
      }

      public final List<String> getDisabledRegexIdList()
      {
        return Collections.unmodifiableList(this.disabledRegexId_);
      }

      public final boolean getEnabledAllUser()
      {
        return this.enabledAllUser_;
      }

      public final String getEnabledDomain(int paramInt)
      {
        return (String)this.enabledDomain_.get(paramInt);
      }

      public final int getEnabledDomainCount()
      {
        return this.enabledDomain_.size();
      }

      public final List<String> getEnabledDomainList()
      {
        return Collections.unmodifiableList(this.enabledDomain_);
      }

      public final String getEnabledEmail(int paramInt)
      {
        return (String)this.enabledEmail_.get(paramInt);
      }

      public final int getEnabledEmailCount()
      {
        return this.enabledEmail_.size();
      }

      public final List<String> getEnabledEmailList()
      {
        return Collections.unmodifiableList(this.enabledEmail_);
      }

      public final String getEnabledId(int paramInt)
      {
        return (String)this.enabledId_.get(paramInt);
      }

      public final int getEnabledIdCount()
      {
        return this.enabledId_.size();
      }

      public final List<String> getEnabledIdList()
      {
        return Collections.unmodifiableList(this.enabledId_);
      }

      public final String getEnabledRegexDomain(int paramInt)
      {
        return (String)this.enabledRegexDomain_.get(paramInt);
      }

      public final int getEnabledRegexDomainCount()
      {
        return this.enabledRegexDomain_.size();
      }

      public final List<String> getEnabledRegexDomainList()
      {
        return Collections.unmodifiableList(this.enabledRegexDomain_);
      }

      public final String getEnabledRegexEmail(int paramInt)
      {
        return (String)this.enabledRegexEmail_.get(paramInt);
      }

      public final int getEnabledRegexEmailCount()
      {
        return this.enabledRegexEmail_.size();
      }

      public final List<String> getEnabledRegexEmailList()
      {
        return Collections.unmodifiableList(this.enabledRegexEmail_);
      }

      public final String getEnabledRegexId(int paramInt)
      {
        return (String)this.enabledRegexId_.get(paramInt);
      }

      public final int getEnabledRegexIdCount()
      {
        return this.enabledRegexId_.size();
      }

      public final List<String> getEnabledRegexIdList()
      {
        return Collections.unmodifiableList(this.enabledRegexId_);
      }

      public final int getExperiment(int paramInt)
      {
        return ((Integer)this.experiment_.get(paramInt)).intValue();
      }

      public final int getExperimentCount()
      {
        return this.experiment_.size();
      }

      public final List<Integer> getExperimentList()
      {
        return Collections.unmodifiableList(this.experiment_);
      }

      public final boolean hasDisabledAllUser()
      {
        if ((0x2000 & this.bitField0_) == 8192);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean hasEnabledAllUser()
      {
        if ((0x4000 & this.bitField0_) == 16384);
        for (boolean bool = true; ; bool = false)
          return bool;
      }

      public final boolean isInitialized()
      {
        return true;
      }

      public final Builder mergeFrom(Experiments.ExperimentMapping paramExperimentMapping)
      {
        if (paramExperimentMapping == Experiments.ExperimentMapping.getDefaultInstance())
          return this;
        if (!paramExperimentMapping.experiment_.isEmpty())
        {
          if (this.experiment_.isEmpty())
          {
            this.experiment_ = paramExperimentMapping.experiment_;
            this.bitField0_ = (0xFFFFFFFE & this.bitField0_);
          }
        }
        else
        {
          label52: if (!paramExperimentMapping.disabledId_.isEmpty())
          {
            if (!this.disabledId_.isEmpty())
              break label630;
            this.disabledId_ = paramExperimentMapping.disabledId_;
            this.bitField0_ = (0xFFFFFFFD & this.bitField0_);
          }
          label95: if (!paramExperimentMapping.enabledId_.isEmpty())
          {
            if (!this.enabledId_.isEmpty())
              break label651;
            this.enabledId_ = paramExperimentMapping.enabledId_;
            this.bitField0_ = (0xFFFFFFFB & this.bitField0_);
          }
          label138: if (!paramExperimentMapping.disabledRegexId_.isEmpty())
          {
            if (!this.disabledRegexId_.isEmpty())
              break label672;
            this.disabledRegexId_ = paramExperimentMapping.disabledRegexId_;
            this.bitField0_ = (0xFFFFFFF7 & this.bitField0_);
          }
          label181: if (!paramExperimentMapping.enabledRegexId_.isEmpty())
          {
            if (!this.enabledRegexId_.isEmpty())
              break label693;
            this.enabledRegexId_ = paramExperimentMapping.enabledRegexId_;
            this.bitField0_ = (0xFFFFFFEF & this.bitField0_);
          }
          label224: if (!paramExperimentMapping.disabledEmail_.isEmpty())
          {
            if (!this.disabledEmail_.isEmpty())
              break label714;
            this.disabledEmail_ = paramExperimentMapping.disabledEmail_;
            this.bitField0_ = (0xFFFFFFDF & this.bitField0_);
          }
          label267: if (!paramExperimentMapping.enabledEmail_.isEmpty())
          {
            if (!this.enabledEmail_.isEmpty())
              break label735;
            this.enabledEmail_ = paramExperimentMapping.enabledEmail_;
            this.bitField0_ = (0xFFFFFFBF & this.bitField0_);
          }
          label310: if (!paramExperimentMapping.disabledRegexEmail_.isEmpty())
          {
            if (!this.disabledRegexEmail_.isEmpty())
              break label756;
            this.disabledRegexEmail_ = paramExperimentMapping.disabledRegexEmail_;
            this.bitField0_ = (0xFFFFFF7F & this.bitField0_);
          }
          label354: if (!paramExperimentMapping.enabledRegexEmail_.isEmpty())
          {
            if (!this.enabledRegexEmail_.isEmpty())
              break label777;
            this.enabledRegexEmail_ = paramExperimentMapping.enabledRegexEmail_;
            this.bitField0_ = (0xFFFFFEFF & this.bitField0_);
          }
          label398: if (!paramExperimentMapping.disabledDomain_.isEmpty())
          {
            if (!this.disabledDomain_.isEmpty())
              break label798;
            this.disabledDomain_ = paramExperimentMapping.disabledDomain_;
            this.bitField0_ = (0xFFFFFDFF & this.bitField0_);
          }
          label442: if (!paramExperimentMapping.enabledDomain_.isEmpty())
          {
            if (!this.enabledDomain_.isEmpty())
              break label819;
            this.enabledDomain_ = paramExperimentMapping.enabledDomain_;
            this.bitField0_ = (0xFFFFFBFF & this.bitField0_);
          }
          label486: if (!paramExperimentMapping.disabledRegexDomain_.isEmpty())
          {
            if (!this.disabledRegexDomain_.isEmpty())
              break label840;
            this.disabledRegexDomain_ = paramExperimentMapping.disabledRegexDomain_;
            this.bitField0_ = (0xFFFFF7FF & this.bitField0_);
          }
          label530: if (!paramExperimentMapping.enabledRegexDomain_.isEmpty())
          {
            if (!this.enabledRegexDomain_.isEmpty())
              break label861;
            this.enabledRegexDomain_ = paramExperimentMapping.enabledRegexDomain_;
            this.bitField0_ = (0xFFFFEFFF & this.bitField0_);
          }
        }
        while (true)
        {
          if (paramExperimentMapping.hasDisabledAllUser())
            setDisabledAllUser(paramExperimentMapping.getDisabledAllUser());
          if (!paramExperimentMapping.hasEnabledAllUser())
            break;
          setEnabledAllUser(paramExperimentMapping.getEnabledAllUser());
          break;
          ensureExperimentIsMutable();
          this.experiment_.addAll(paramExperimentMapping.experiment_);
          break label52;
          label630: ensureDisabledIdIsMutable();
          this.disabledId_.addAll(paramExperimentMapping.disabledId_);
          break label95;
          label651: ensureEnabledIdIsMutable();
          this.enabledId_.addAll(paramExperimentMapping.enabledId_);
          break label138;
          label672: ensureDisabledRegexIdIsMutable();
          this.disabledRegexId_.addAll(paramExperimentMapping.disabledRegexId_);
          break label181;
          label693: ensureEnabledRegexIdIsMutable();
          this.enabledRegexId_.addAll(paramExperimentMapping.enabledRegexId_);
          break label224;
          label714: ensureDisabledEmailIsMutable();
          this.disabledEmail_.addAll(paramExperimentMapping.disabledEmail_);
          break label267;
          label735: ensureEnabledEmailIsMutable();
          this.enabledEmail_.addAll(paramExperimentMapping.enabledEmail_);
          break label310;
          label756: ensureDisabledRegexEmailIsMutable();
          this.disabledRegexEmail_.addAll(paramExperimentMapping.disabledRegexEmail_);
          break label354;
          label777: ensureEnabledRegexEmailIsMutable();
          this.enabledRegexEmail_.addAll(paramExperimentMapping.enabledRegexEmail_);
          break label398;
          label798: ensureDisabledDomainIsMutable();
          this.disabledDomain_.addAll(paramExperimentMapping.disabledDomain_);
          break label442;
          label819: ensureEnabledDomainIsMutable();
          this.enabledDomain_.addAll(paramExperimentMapping.enabledDomain_);
          break label486;
          label840: ensureDisabledRegexDomainIsMutable();
          this.disabledRegexDomain_.addAll(paramExperimentMapping.disabledRegexDomain_);
          break label530;
          label861: ensureEnabledRegexDomainIsMutable();
          this.enabledRegexDomain_.addAll(paramExperimentMapping.enabledRegexDomain_);
        }
      }

      public final Builder setDisabledAllUser(boolean paramBoolean)
      {
        this.bitField0_ = (0x2000 | this.bitField0_);
        this.disabledAllUser_ = paramBoolean;
        return this;
      }

      public final Builder setDisabledDomain(int paramInt, String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureDisabledDomainIsMutable();
        this.disabledDomain_.set(paramInt, paramString);
        return this;
      }

      public final Builder setDisabledEmail(int paramInt, String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureDisabledEmailIsMutable();
        this.disabledEmail_.set(paramInt, paramString);
        return this;
      }

      public final Builder setDisabledId(int paramInt, String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureDisabledIdIsMutable();
        this.disabledId_.set(paramInt, paramString);
        return this;
      }

      public final Builder setDisabledRegexDomain(int paramInt, String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureDisabledRegexDomainIsMutable();
        this.disabledRegexDomain_.set(paramInt, paramString);
        return this;
      }

      public final Builder setDisabledRegexEmail(int paramInt, String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureDisabledRegexEmailIsMutable();
        this.disabledRegexEmail_.set(paramInt, paramString);
        return this;
      }

      public final Builder setDisabledRegexId(int paramInt, String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureDisabledRegexIdIsMutable();
        this.disabledRegexId_.set(paramInt, paramString);
        return this;
      }

      public final Builder setEnabledAllUser(boolean paramBoolean)
      {
        this.bitField0_ = (0x4000 | this.bitField0_);
        this.enabledAllUser_ = paramBoolean;
        return this;
      }

      public final Builder setEnabledDomain(int paramInt, String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureEnabledDomainIsMutable();
        this.enabledDomain_.set(paramInt, paramString);
        return this;
      }

      public final Builder setEnabledEmail(int paramInt, String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureEnabledEmailIsMutable();
        this.enabledEmail_.set(paramInt, paramString);
        return this;
      }

      public final Builder setEnabledId(int paramInt, String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureEnabledIdIsMutable();
        this.enabledId_.set(paramInt, paramString);
        return this;
      }

      public final Builder setEnabledRegexDomain(int paramInt, String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureEnabledRegexDomainIsMutable();
        this.enabledRegexDomain_.set(paramInt, paramString);
        return this;
      }

      public final Builder setEnabledRegexEmail(int paramInt, String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureEnabledRegexEmailIsMutable();
        this.enabledRegexEmail_.set(paramInt, paramString);
        return this;
      }

      public final Builder setEnabledRegexId(int paramInt, String paramString)
      {
        if (paramString == null)
          throw new NullPointerException();
        ensureEnabledRegexIdIsMutable();
        this.enabledRegexId_.set(paramInt, paramString);
        return this;
      }

      public final Builder setExperiment(int paramInt1, int paramInt2)
      {
        ensureExperimentIsMutable();
        this.experiment_.set(paramInt1, Integer.valueOf(paramInt2));
        return this;
      }
    }
  }

  public static abstract interface ExperimentMappingOrBuilder extends MessageLiteOrBuilder
  {
    public abstract boolean getDisabledAllUser();

    public abstract String getDisabledDomain(int paramInt);

    public abstract int getDisabledDomainCount();

    public abstract List<String> getDisabledDomainList();

    public abstract String getDisabledEmail(int paramInt);

    public abstract int getDisabledEmailCount();

    public abstract List<String> getDisabledEmailList();

    public abstract String getDisabledId(int paramInt);

    public abstract int getDisabledIdCount();

    public abstract List<String> getDisabledIdList();

    public abstract String getDisabledRegexDomain(int paramInt);

    public abstract int getDisabledRegexDomainCount();

    public abstract List<String> getDisabledRegexDomainList();

    public abstract String getDisabledRegexEmail(int paramInt);

    public abstract int getDisabledRegexEmailCount();

    public abstract List<String> getDisabledRegexEmailList();

    public abstract String getDisabledRegexId(int paramInt);

    public abstract int getDisabledRegexIdCount();

    public abstract List<String> getDisabledRegexIdList();

    public abstract boolean getEnabledAllUser();

    public abstract String getEnabledDomain(int paramInt);

    public abstract int getEnabledDomainCount();

    public abstract List<String> getEnabledDomainList();

    public abstract String getEnabledEmail(int paramInt);

    public abstract int getEnabledEmailCount();

    public abstract List<String> getEnabledEmailList();

    public abstract String getEnabledId(int paramInt);

    public abstract int getEnabledIdCount();

    public abstract List<String> getEnabledIdList();

    public abstract String getEnabledRegexDomain(int paramInt);

    public abstract int getEnabledRegexDomainCount();

    public abstract List<String> getEnabledRegexDomainList();

    public abstract String getEnabledRegexEmail(int paramInt);

    public abstract int getEnabledRegexEmailCount();

    public abstract List<String> getEnabledRegexEmailList();

    public abstract String getEnabledRegexId(int paramInt);

    public abstract int getEnabledRegexIdCount();

    public abstract List<String> getEnabledRegexIdList();

    public abstract int getExperiment(int paramInt);

    public abstract int getExperimentCount();

    public abstract List<Integer> getExperimentList();

    public abstract boolean hasDisabledAllUser();

    public abstract boolean hasEnabledAllUser();
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.wireless.realtimechat.proto.Experiments
 * JD-Core Version:    0.6.2
 */