/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.refactorlabs.cs378;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Impression extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Impression\",\"namespace\":\"com.refactorlabs.cs378\",\"fields\":[{\"name\":\"impression_type\",\"type\":{\"type\":\"enum\",\"name\":\"ImpressionType\",\"symbols\":[\"ACTION\",\"VDP\",\"SRP\"],\"default\":\"SRP\"},\"default\":\"SRP\"},{\"name\":\"action\",\"type\":{\"type\":\"enum\",\"name\":\"Action\",\"symbols\":[\"CLICK\",\"PAGE_VIEW\"],\"default\":\"PAGE_VIEW\"},\"default\":\"PAGE_VIEW\"},{\"name\":\"action_name\",\"type\":{\"type\":\"enum\",\"name\":\"ActionName\",\"symbols\":[\"VIEWED_CARFAX_REPORT\",\"MORE_PHOTOS_VIEWED\",\"VIEWED_CARFAX_REPORT_UNHOSTED\",\"DEALER_PAGE_VIEWED\",\"DEALER_WEBSITE_VIEWED\",\"NONE\"],\"default\":\"NONE\"},\"default\":\"NONE\"},{\"name\":\"phone_type\",\"type\":{\"type\":\"enum\",\"name\":\"PhoneType\",\"symbols\":[\"TRACKED\",\"NONE\"],\"default\":\"NONE\"},\"default\":\"NONE\"},{\"name\":\"vertical\",\"type\":{\"type\":\"enum\",\"name\":\"Vertical\",\"symbols\":[\"CARS\",\"NONE\"],\"default\":\"NONE\"},\"default\":\"NONE\"},{\"name\":\"id\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"long\"}],\"default\":null},{\"name\":\"city\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"ab\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"state\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"zip\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"address\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"domain\",\"type\":[\"null\",\"string\"],\"default\":null},{\"name\":\"start_index\",\"type\":\"long\",\"default\":0},{\"name\":\"total\",\"type\":\"long\",\"default\":0},{\"name\":\"timestamp\",\"type\":\"long\"},{\"name\":\"lon\",\"type\":\"double\",\"default\":0.0},{\"name\":\"lat\",\"type\":\"double\",\"default\":0.0}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public com.refactorlabs.cs378.ImpressionType impression_type;
  @Deprecated public com.refactorlabs.cs378.Action action;
  @Deprecated public com.refactorlabs.cs378.ActionName action_name;
  @Deprecated public com.refactorlabs.cs378.PhoneType phone_type;
  @Deprecated public com.refactorlabs.cs378.Vertical vertical;
  @Deprecated public java.util.List<java.lang.Long> id;
  @Deprecated public java.lang.CharSequence city;
  @Deprecated public java.lang.CharSequence ab;
  @Deprecated public java.lang.CharSequence state;
  @Deprecated public java.lang.CharSequence zip;
  @Deprecated public java.lang.CharSequence address;
  @Deprecated public java.lang.CharSequence domain;
  @Deprecated public long start_index;
  @Deprecated public long total;
  @Deprecated public long timestamp;
  @Deprecated public double lon;
  @Deprecated public double lat;

  /**
   * Default constructor.
   */
  public Impression() {}

  /**
   * All-args constructor.
   */
  public Impression(com.refactorlabs.cs378.ImpressionType impression_type, com.refactorlabs.cs378.Action action, com.refactorlabs.cs378.ActionName action_name, com.refactorlabs.cs378.PhoneType phone_type, com.refactorlabs.cs378.Vertical vertical, java.util.List<java.lang.Long> id, java.lang.CharSequence city, java.lang.CharSequence ab, java.lang.CharSequence state, java.lang.CharSequence zip, java.lang.CharSequence address, java.lang.CharSequence domain, java.lang.Long start_index, java.lang.Long total, java.lang.Long timestamp, java.lang.Double lon, java.lang.Double lat) {
    this.impression_type = impression_type;
    this.action = action;
    this.action_name = action_name;
    this.phone_type = phone_type;
    this.vertical = vertical;
    this.id = id;
    this.city = city;
    this.ab = ab;
    this.state = state;
    this.zip = zip;
    this.address = address;
    this.domain = domain;
    this.start_index = start_index;
    this.total = total;
    this.timestamp = timestamp;
    this.lon = lon;
    this.lat = lat;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return impression_type;
    case 1: return action;
    case 2: return action_name;
    case 3: return phone_type;
    case 4: return vertical;
    case 5: return id;
    case 6: return city;
    case 7: return ab;
    case 8: return state;
    case 9: return zip;
    case 10: return address;
    case 11: return domain;
    case 12: return start_index;
    case 13: return total;
    case 14: return timestamp;
    case 15: return lon;
    case 16: return lat;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }
  // Used by DatumReader.  Applications should not call. 
  @SuppressWarnings(value="unchecked")
  public void put(int field$, java.lang.Object value$) {
    switch (field$) {
    case 0: impression_type = (com.refactorlabs.cs378.ImpressionType)value$; break;
    case 1: action = (com.refactorlabs.cs378.Action)value$; break;
    case 2: action_name = (com.refactorlabs.cs378.ActionName)value$; break;
    case 3: phone_type = (com.refactorlabs.cs378.PhoneType)value$; break;
    case 4: vertical = (com.refactorlabs.cs378.Vertical)value$; break;
    case 5: id = (java.util.List<java.lang.Long>)value$; break;
    case 6: city = (java.lang.CharSequence)value$; break;
    case 7: ab = (java.lang.CharSequence)value$; break;
    case 8: state = (java.lang.CharSequence)value$; break;
    case 9: zip = (java.lang.CharSequence)value$; break;
    case 10: address = (java.lang.CharSequence)value$; break;
    case 11: domain = (java.lang.CharSequence)value$; break;
    case 12: start_index = (java.lang.Long)value$; break;
    case 13: total = (java.lang.Long)value$; break;
    case 14: timestamp = (java.lang.Long)value$; break;
    case 15: lon = (java.lang.Double)value$; break;
    case 16: lat = (java.lang.Double)value$; break;
    default: throw new org.apache.avro.AvroRuntimeException("Bad index");
    }
  }

  /**
   * Gets the value of the 'impression_type' field.
   */
  public com.refactorlabs.cs378.ImpressionType getImpressionType() {
    return impression_type;
  }

  /**
   * Sets the value of the 'impression_type' field.
   * @param value the value to set.
   */
  public void setImpressionType(com.refactorlabs.cs378.ImpressionType value) {
    this.impression_type = value;
  }

  /**
   * Gets the value of the 'action' field.
   */
  public com.refactorlabs.cs378.Action getAction() {
    return action;
  }

  /**
   * Sets the value of the 'action' field.
   * @param value the value to set.
   */
  public void setAction(com.refactorlabs.cs378.Action value) {
    this.action = value;
  }

  /**
   * Gets the value of the 'action_name' field.
   */
  public com.refactorlabs.cs378.ActionName getActionName() {
    return action_name;
  }

  /**
   * Sets the value of the 'action_name' field.
   * @param value the value to set.
   */
  public void setActionName(com.refactorlabs.cs378.ActionName value) {
    this.action_name = value;
  }

  /**
   * Gets the value of the 'phone_type' field.
   */
  public com.refactorlabs.cs378.PhoneType getPhoneType() {
    return phone_type;
  }

  /**
   * Sets the value of the 'phone_type' field.
   * @param value the value to set.
   */
  public void setPhoneType(com.refactorlabs.cs378.PhoneType value) {
    this.phone_type = value;
  }

  /**
   * Gets the value of the 'vertical' field.
   */
  public com.refactorlabs.cs378.Vertical getVertical() {
    return vertical;
  }

  /**
   * Sets the value of the 'vertical' field.
   * @param value the value to set.
   */
  public void setVertical(com.refactorlabs.cs378.Vertical value) {
    this.vertical = value;
  }

  /**
   * Gets the value of the 'id' field.
   */
  public java.util.List<java.lang.Long> getId() {
    return id;
  }

  /**
   * Sets the value of the 'id' field.
   * @param value the value to set.
   */
  public void setId(java.util.List<java.lang.Long> value) {
    this.id = value;
  }

  /**
   * Gets the value of the 'city' field.
   */
  public java.lang.CharSequence getCity() {
    return city;
  }

  /**
   * Sets the value of the 'city' field.
   * @param value the value to set.
   */
  public void setCity(java.lang.CharSequence value) {
    this.city = value;
  }

  /**
   * Gets the value of the 'ab' field.
   */
  public java.lang.CharSequence getAb() {
    return ab;
  }

  /**
   * Sets the value of the 'ab' field.
   * @param value the value to set.
   */
  public void setAb(java.lang.CharSequence value) {
    this.ab = value;
  }

  /**
   * Gets the value of the 'state' field.
   */
  public java.lang.CharSequence getState() {
    return state;
  }

  /**
   * Sets the value of the 'state' field.
   * @param value the value to set.
   */
  public void setState(java.lang.CharSequence value) {
    this.state = value;
  }

  /**
   * Gets the value of the 'zip' field.
   */
  public java.lang.CharSequence getZip() {
    return zip;
  }

  /**
   * Sets the value of the 'zip' field.
   * @param value the value to set.
   */
  public void setZip(java.lang.CharSequence value) {
    this.zip = value;
  }

  /**
   * Gets the value of the 'address' field.
   */
  public java.lang.CharSequence getAddress() {
    return address;
  }

  /**
   * Sets the value of the 'address' field.
   * @param value the value to set.
   */
  public void setAddress(java.lang.CharSequence value) {
    this.address = value;
  }

  /**
   * Gets the value of the 'domain' field.
   */
  public java.lang.CharSequence getDomain() {
    return domain;
  }

  /**
   * Sets the value of the 'domain' field.
   * @param value the value to set.
   */
  public void setDomain(java.lang.CharSequence value) {
    this.domain = value;
  }

  /**
   * Gets the value of the 'start_index' field.
   */
  public java.lang.Long getStartIndex() {
    return start_index;
  }

  /**
   * Sets the value of the 'start_index' field.
   * @param value the value to set.
   */
  public void setStartIndex(java.lang.Long value) {
    this.start_index = value;
  }

  /**
   * Gets the value of the 'total' field.
   */
  public java.lang.Long getTotal() {
    return total;
  }

  /**
   * Sets the value of the 'total' field.
   * @param value the value to set.
   */
  public void setTotal(java.lang.Long value) {
    this.total = value;
  }

  /**
   * Gets the value of the 'timestamp' field.
   */
  public java.lang.Long getTimestamp() {
    return timestamp;
  }

  /**
   * Sets the value of the 'timestamp' field.
   * @param value the value to set.
   */
  public void setTimestamp(java.lang.Long value) {
    this.timestamp = value;
  }

  /**
   * Gets the value of the 'lon' field.
   */
  public java.lang.Double getLon() {
    return lon;
  }

  /**
   * Sets the value of the 'lon' field.
   * @param value the value to set.
   */
  public void setLon(java.lang.Double value) {
    this.lon = value;
  }

  /**
   * Gets the value of the 'lat' field.
   */
  public java.lang.Double getLat() {
    return lat;
  }

  /**
   * Sets the value of the 'lat' field.
   * @param value the value to set.
   */
  public void setLat(java.lang.Double value) {
    this.lat = value;
  }

  /** Creates a new Impression RecordBuilder */
  public static com.refactorlabs.cs378.Impression.Builder newBuilder() {
    return new com.refactorlabs.cs378.Impression.Builder();
  }
  
  /** Creates a new Impression RecordBuilder by copying an existing Builder */
  public static com.refactorlabs.cs378.Impression.Builder newBuilder(com.refactorlabs.cs378.Impression.Builder other) {
    return new com.refactorlabs.cs378.Impression.Builder(other);
  }
  
  /** Creates a new Impression RecordBuilder by copying an existing Impression instance */
  public static com.refactorlabs.cs378.Impression.Builder newBuilder(com.refactorlabs.cs378.Impression other) {
    return new com.refactorlabs.cs378.Impression.Builder(other);
  }
  
  /**
   * RecordBuilder for Impression instances.
   */
  public static class Builder extends org.apache.avro.specific.SpecificRecordBuilderBase<Impression>
    implements org.apache.avro.data.RecordBuilder<Impression> {

    private com.refactorlabs.cs378.ImpressionType impression_type;
    private com.refactorlabs.cs378.Action action;
    private com.refactorlabs.cs378.ActionName action_name;
    private com.refactorlabs.cs378.PhoneType phone_type;
    private com.refactorlabs.cs378.Vertical vertical;
    private java.util.List<java.lang.Long> id;
    private java.lang.CharSequence city;
    private java.lang.CharSequence ab;
    private java.lang.CharSequence state;
    private java.lang.CharSequence zip;
    private java.lang.CharSequence address;
    private java.lang.CharSequence domain;
    private long start_index;
    private long total;
    private long timestamp;
    private double lon;
    private double lat;

    /** Creates a new Builder */
    private Builder() {
      super(com.refactorlabs.cs378.Impression.SCHEMA$);
    }
    
    /** Creates a Builder by copying an existing Builder */
    private Builder(com.refactorlabs.cs378.Impression.Builder other) {
      super(other);
    }
    
    /** Creates a Builder by copying an existing Impression instance */
    private Builder(com.refactorlabs.cs378.Impression other) {
            super(com.refactorlabs.cs378.Impression.SCHEMA$);
      if (isValidValue(fields()[0], other.impression_type)) {
        this.impression_type = data().deepCopy(fields()[0].schema(), other.impression_type);
        fieldSetFlags()[0] = true;
      }
      if (isValidValue(fields()[1], other.action)) {
        this.action = data().deepCopy(fields()[1].schema(), other.action);
        fieldSetFlags()[1] = true;
      }
      if (isValidValue(fields()[2], other.action_name)) {
        this.action_name = data().deepCopy(fields()[2].schema(), other.action_name);
        fieldSetFlags()[2] = true;
      }
      if (isValidValue(fields()[3], other.phone_type)) {
        this.phone_type = data().deepCopy(fields()[3].schema(), other.phone_type);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.vertical)) {
        this.vertical = data().deepCopy(fields()[4].schema(), other.vertical);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.id)) {
        this.id = data().deepCopy(fields()[5].schema(), other.id);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.city)) {
        this.city = data().deepCopy(fields()[6].schema(), other.city);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.ab)) {
        this.ab = data().deepCopy(fields()[7].schema(), other.ab);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.state)) {
        this.state = data().deepCopy(fields()[8].schema(), other.state);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.zip)) {
        this.zip = data().deepCopy(fields()[9].schema(), other.zip);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.address)) {
        this.address = data().deepCopy(fields()[10].schema(), other.address);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.domain)) {
        this.domain = data().deepCopy(fields()[11].schema(), other.domain);
        fieldSetFlags()[11] = true;
      }
      if (isValidValue(fields()[12], other.start_index)) {
        this.start_index = data().deepCopy(fields()[12].schema(), other.start_index);
        fieldSetFlags()[12] = true;
      }
      if (isValidValue(fields()[13], other.total)) {
        this.total = data().deepCopy(fields()[13].schema(), other.total);
        fieldSetFlags()[13] = true;
      }
      if (isValidValue(fields()[14], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[14].schema(), other.timestamp);
        fieldSetFlags()[14] = true;
      }
      if (isValidValue(fields()[15], other.lon)) {
        this.lon = data().deepCopy(fields()[15].schema(), other.lon);
        fieldSetFlags()[15] = true;
      }
      if (isValidValue(fields()[16], other.lat)) {
        this.lat = data().deepCopy(fields()[16].schema(), other.lat);
        fieldSetFlags()[16] = true;
      }
    }

    /** Gets the value of the 'impression_type' field */
    public com.refactorlabs.cs378.ImpressionType getImpressionType() {
      return impression_type;
    }
    
    /** Sets the value of the 'impression_type' field */
    public com.refactorlabs.cs378.Impression.Builder setImpressionType(com.refactorlabs.cs378.ImpressionType value) {
      validate(fields()[0], value);
      this.impression_type = value;
      fieldSetFlags()[0] = true;
      return this; 
    }
    
    /** Checks whether the 'impression_type' field has been set */
    public boolean hasImpressionType() {
      return fieldSetFlags()[0];
    }
    
    /** Clears the value of the 'impression_type' field */
    public com.refactorlabs.cs378.Impression.Builder clearImpressionType() {
      impression_type = null;
      fieldSetFlags()[0] = false;
      return this;
    }

    /** Gets the value of the 'action' field */
    public com.refactorlabs.cs378.Action getAction() {
      return action;
    }
    
    /** Sets the value of the 'action' field */
    public com.refactorlabs.cs378.Impression.Builder setAction(com.refactorlabs.cs378.Action value) {
      validate(fields()[1], value);
      this.action = value;
      fieldSetFlags()[1] = true;
      return this; 
    }
    
    /** Checks whether the 'action' field has been set */
    public boolean hasAction() {
      return fieldSetFlags()[1];
    }
    
    /** Clears the value of the 'action' field */
    public com.refactorlabs.cs378.Impression.Builder clearAction() {
      action = null;
      fieldSetFlags()[1] = false;
      return this;
    }

    /** Gets the value of the 'action_name' field */
    public com.refactorlabs.cs378.ActionName getActionName() {
      return action_name;
    }
    
    /** Sets the value of the 'action_name' field */
    public com.refactorlabs.cs378.Impression.Builder setActionName(com.refactorlabs.cs378.ActionName value) {
      validate(fields()[2], value);
      this.action_name = value;
      fieldSetFlags()[2] = true;
      return this; 
    }
    
    /** Checks whether the 'action_name' field has been set */
    public boolean hasActionName() {
      return fieldSetFlags()[2];
    }
    
    /** Clears the value of the 'action_name' field */
    public com.refactorlabs.cs378.Impression.Builder clearActionName() {
      action_name = null;
      fieldSetFlags()[2] = false;
      return this;
    }

    /** Gets the value of the 'phone_type' field */
    public com.refactorlabs.cs378.PhoneType getPhoneType() {
      return phone_type;
    }
    
    /** Sets the value of the 'phone_type' field */
    public com.refactorlabs.cs378.Impression.Builder setPhoneType(com.refactorlabs.cs378.PhoneType value) {
      validate(fields()[3], value);
      this.phone_type = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'phone_type' field has been set */
    public boolean hasPhoneType() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'phone_type' field */
    public com.refactorlabs.cs378.Impression.Builder clearPhoneType() {
      phone_type = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'vertical' field */
    public com.refactorlabs.cs378.Vertical getVertical() {
      return vertical;
    }
    
    /** Sets the value of the 'vertical' field */
    public com.refactorlabs.cs378.Impression.Builder setVertical(com.refactorlabs.cs378.Vertical value) {
      validate(fields()[4], value);
      this.vertical = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'vertical' field has been set */
    public boolean hasVertical() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'vertical' field */
    public com.refactorlabs.cs378.Impression.Builder clearVertical() {
      vertical = null;
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'id' field */
    public java.util.List<java.lang.Long> getId() {
      return id;
    }
    
    /** Sets the value of the 'id' field */
    public com.refactorlabs.cs378.Impression.Builder setId(java.util.List<java.lang.Long> value) {
      validate(fields()[5], value);
      this.id = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'id' field has been set */
    public boolean hasId() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'id' field */
    public com.refactorlabs.cs378.Impression.Builder clearId() {
      id = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /** Gets the value of the 'city' field */
    public java.lang.CharSequence getCity() {
      return city;
    }
    
    /** Sets the value of the 'city' field */
    public com.refactorlabs.cs378.Impression.Builder setCity(java.lang.CharSequence value) {
      validate(fields()[6], value);
      this.city = value;
      fieldSetFlags()[6] = true;
      return this; 
    }
    
    /** Checks whether the 'city' field has been set */
    public boolean hasCity() {
      return fieldSetFlags()[6];
    }
    
    /** Clears the value of the 'city' field */
    public com.refactorlabs.cs378.Impression.Builder clearCity() {
      city = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /** Gets the value of the 'ab' field */
    public java.lang.CharSequence getAb() {
      return ab;
    }
    
    /** Sets the value of the 'ab' field */
    public com.refactorlabs.cs378.Impression.Builder setAb(java.lang.CharSequence value) {
      validate(fields()[7], value);
      this.ab = value;
      fieldSetFlags()[7] = true;
      return this; 
    }
    
    /** Checks whether the 'ab' field has been set */
    public boolean hasAb() {
      return fieldSetFlags()[7];
    }
    
    /** Clears the value of the 'ab' field */
    public com.refactorlabs.cs378.Impression.Builder clearAb() {
      ab = null;
      fieldSetFlags()[7] = false;
      return this;
    }

    /** Gets the value of the 'state' field */
    public java.lang.CharSequence getState() {
      return state;
    }
    
    /** Sets the value of the 'state' field */
    public com.refactorlabs.cs378.Impression.Builder setState(java.lang.CharSequence value) {
      validate(fields()[8], value);
      this.state = value;
      fieldSetFlags()[8] = true;
      return this; 
    }
    
    /** Checks whether the 'state' field has been set */
    public boolean hasState() {
      return fieldSetFlags()[8];
    }
    
    /** Clears the value of the 'state' field */
    public com.refactorlabs.cs378.Impression.Builder clearState() {
      state = null;
      fieldSetFlags()[8] = false;
      return this;
    }

    /** Gets the value of the 'zip' field */
    public java.lang.CharSequence getZip() {
      return zip;
    }
    
    /** Sets the value of the 'zip' field */
    public com.refactorlabs.cs378.Impression.Builder setZip(java.lang.CharSequence value) {
      validate(fields()[9], value);
      this.zip = value;
      fieldSetFlags()[9] = true;
      return this; 
    }
    
    /** Checks whether the 'zip' field has been set */
    public boolean hasZip() {
      return fieldSetFlags()[9];
    }
    
    /** Clears the value of the 'zip' field */
    public com.refactorlabs.cs378.Impression.Builder clearZip() {
      zip = null;
      fieldSetFlags()[9] = false;
      return this;
    }

    /** Gets the value of the 'address' field */
    public java.lang.CharSequence getAddress() {
      return address;
    }
    
    /** Sets the value of the 'address' field */
    public com.refactorlabs.cs378.Impression.Builder setAddress(java.lang.CharSequence value) {
      validate(fields()[10], value);
      this.address = value;
      fieldSetFlags()[10] = true;
      return this; 
    }
    
    /** Checks whether the 'address' field has been set */
    public boolean hasAddress() {
      return fieldSetFlags()[10];
    }
    
    /** Clears the value of the 'address' field */
    public com.refactorlabs.cs378.Impression.Builder clearAddress() {
      address = null;
      fieldSetFlags()[10] = false;
      return this;
    }

    /** Gets the value of the 'domain' field */
    public java.lang.CharSequence getDomain() {
      return domain;
    }
    
    /** Sets the value of the 'domain' field */
    public com.refactorlabs.cs378.Impression.Builder setDomain(java.lang.CharSequence value) {
      validate(fields()[11], value);
      this.domain = value;
      fieldSetFlags()[11] = true;
      return this; 
    }
    
    /** Checks whether the 'domain' field has been set */
    public boolean hasDomain() {
      return fieldSetFlags()[11];
    }
    
    /** Clears the value of the 'domain' field */
    public com.refactorlabs.cs378.Impression.Builder clearDomain() {
      domain = null;
      fieldSetFlags()[11] = false;
      return this;
    }

    /** Gets the value of the 'start_index' field */
    public java.lang.Long getStartIndex() {
      return start_index;
    }
    
    /** Sets the value of the 'start_index' field */
    public com.refactorlabs.cs378.Impression.Builder setStartIndex(long value) {
      validate(fields()[12], value);
      this.start_index = value;
      fieldSetFlags()[12] = true;
      return this; 
    }
    
    /** Checks whether the 'start_index' field has been set */
    public boolean hasStartIndex() {
      return fieldSetFlags()[12];
    }
    
    /** Clears the value of the 'start_index' field */
    public com.refactorlabs.cs378.Impression.Builder clearStartIndex() {
      fieldSetFlags()[12] = false;
      return this;
    }

    /** Gets the value of the 'total' field */
    public java.lang.Long getTotal() {
      return total;
    }
    
    /** Sets the value of the 'total' field */
    public com.refactorlabs.cs378.Impression.Builder setTotal(long value) {
      validate(fields()[13], value);
      this.total = value;
      fieldSetFlags()[13] = true;
      return this; 
    }
    
    /** Checks whether the 'total' field has been set */
    public boolean hasTotal() {
      return fieldSetFlags()[13];
    }
    
    /** Clears the value of the 'total' field */
    public com.refactorlabs.cs378.Impression.Builder clearTotal() {
      fieldSetFlags()[13] = false;
      return this;
    }

    /** Gets the value of the 'timestamp' field */
    public java.lang.Long getTimestamp() {
      return timestamp;
    }
    
    /** Sets the value of the 'timestamp' field */
    public com.refactorlabs.cs378.Impression.Builder setTimestamp(long value) {
      validate(fields()[14], value);
      this.timestamp = value;
      fieldSetFlags()[14] = true;
      return this; 
    }
    
    /** Checks whether the 'timestamp' field has been set */
    public boolean hasTimestamp() {
      return fieldSetFlags()[14];
    }
    
    /** Clears the value of the 'timestamp' field */
    public com.refactorlabs.cs378.Impression.Builder clearTimestamp() {
      fieldSetFlags()[14] = false;
      return this;
    }

    /** Gets the value of the 'lon' field */
    public java.lang.Double getLon() {
      return lon;
    }
    
    /** Sets the value of the 'lon' field */
    public com.refactorlabs.cs378.Impression.Builder setLon(double value) {
      validate(fields()[15], value);
      this.lon = value;
      fieldSetFlags()[15] = true;
      return this; 
    }
    
    /** Checks whether the 'lon' field has been set */
    public boolean hasLon() {
      return fieldSetFlags()[15];
    }
    
    /** Clears the value of the 'lon' field */
    public com.refactorlabs.cs378.Impression.Builder clearLon() {
      fieldSetFlags()[15] = false;
      return this;
    }

    /** Gets the value of the 'lat' field */
    public java.lang.Double getLat() {
      return lat;
    }
    
    /** Sets the value of the 'lat' field */
    public com.refactorlabs.cs378.Impression.Builder setLat(double value) {
      validate(fields()[16], value);
      this.lat = value;
      fieldSetFlags()[16] = true;
      return this; 
    }
    
    /** Checks whether the 'lat' field has been set */
    public boolean hasLat() {
      return fieldSetFlags()[16];
    }
    
    /** Clears the value of the 'lat' field */
    public com.refactorlabs.cs378.Impression.Builder clearLat() {
      fieldSetFlags()[16] = false;
      return this;
    }

    @Override
    public Impression build() {
      try {
        Impression record = new Impression();
        record.impression_type = fieldSetFlags()[0] ? this.impression_type : (com.refactorlabs.cs378.ImpressionType) defaultValue(fields()[0]);
        record.action = fieldSetFlags()[1] ? this.action : (com.refactorlabs.cs378.Action) defaultValue(fields()[1]);
        record.action_name = fieldSetFlags()[2] ? this.action_name : (com.refactorlabs.cs378.ActionName) defaultValue(fields()[2]);
        record.phone_type = fieldSetFlags()[3] ? this.phone_type : (com.refactorlabs.cs378.PhoneType) defaultValue(fields()[3]);
        record.vertical = fieldSetFlags()[4] ? this.vertical : (com.refactorlabs.cs378.Vertical) defaultValue(fields()[4]);
        record.id = fieldSetFlags()[5] ? this.id : (java.util.List<java.lang.Long>) defaultValue(fields()[5]);
        record.city = fieldSetFlags()[6] ? this.city : (java.lang.CharSequence) defaultValue(fields()[6]);
        record.ab = fieldSetFlags()[7] ? this.ab : (java.lang.CharSequence) defaultValue(fields()[7]);
        record.state = fieldSetFlags()[8] ? this.state : (java.lang.CharSequence) defaultValue(fields()[8]);
        record.zip = fieldSetFlags()[9] ? this.zip : (java.lang.CharSequence) defaultValue(fields()[9]);
        record.address = fieldSetFlags()[10] ? this.address : (java.lang.CharSequence) defaultValue(fields()[10]);
        record.domain = fieldSetFlags()[11] ? this.domain : (java.lang.CharSequence) defaultValue(fields()[11]);
        record.start_index = fieldSetFlags()[12] ? this.start_index : (java.lang.Long) defaultValue(fields()[12]);
        record.total = fieldSetFlags()[13] ? this.total : (java.lang.Long) defaultValue(fields()[13]);
        record.timestamp = fieldSetFlags()[14] ? this.timestamp : (java.lang.Long) defaultValue(fields()[14]);
        record.lon = fieldSetFlags()[15] ? this.lon : (java.lang.Double) defaultValue(fields()[15]);
        record.lat = fieldSetFlags()[16] ? this.lat : (java.lang.Double) defaultValue(fields()[16]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
