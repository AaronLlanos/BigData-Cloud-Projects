/**
 * Autogenerated by Avro
 * 
 * DO NOT EDIT DIRECTLY
 */
package com.refactorlabs.cs378;  
@SuppressWarnings("all")
@org.apache.avro.specific.AvroGenerated
public class Impression extends org.apache.avro.specific.SpecificRecordBase implements org.apache.avro.specific.SpecificRecord {
  public static final org.apache.avro.Schema SCHEMA$ = new org.apache.avro.Schema.Parser().parse("{\"type\":\"record\",\"name\":\"Impression\",\"namespace\":\"com.refactorlabs.cs378\",\"fields\":[{\"name\":\"impression_type\",\"type\":{\"type\":\"enum\",\"name\":\"ImpressionType\",\"symbols\":[\"ACTION\",\"VDP\",\"SRP\",\"THANK_YOU\"],\"default\":\"SRP\"},\"default\":\"SRP\"},{\"name\":\"action\",\"type\":{\"type\":\"enum\",\"name\":\"Action\",\"symbols\":[\"CLICK\",\"PAGE_VIEW\"],\"default\":\"PAGE_VIEW\"},\"default\":\"PAGE_VIEW\"},{\"name\":\"action_name\",\"type\":{\"type\":\"enum\",\"name\":\"ActionName\",\"symbols\":[\"VIEWED_CARFAX_REPORT\",\"VIEWED_CARFAX_REPORT_UNHOSTED\",\"MORE_PHOTOS_VIEWED\",\"DEALER_PAGE_VIEWED\",\"DEALER_WEBSITE_VIEWED\",\"UNKNOWN\",\"NONE\"],\"default\":\"NONE\"},\"default\":\"NONE\"},{\"name\":\"id\",\"type\":[\"null\",{\"type\":\"array\",\"items\":\"long\"}],\"default\":null},{\"name\":\"timestamp\",\"type\":\"long\",\"default\":0},{\"name\":\"ab\",\"type\":[\"string\",\"null\"],\"default\":\"null\"},{\"name\":\"vertical\",\"type\":{\"type\":\"enum\",\"name\":\"Vertical\",\"symbols\":[\"CARS\",\"OTHER\"],\"default\":\"CARS\"},\"default\":\"CARS\"},{\"name\":\"start_index\",\"type\":\"int\",\"default\":0},{\"name\":\"total\",\"type\":\"int\",\"default\":0},{\"name\":\"domain\",\"type\":[\"string\",\"null\"],\"default\":\"null\"},{\"name\":\"lat\",\"type\":\"double\",\"default\":0.0},{\"name\":\"lon\",\"type\":\"double\",\"default\":0.0},{\"name\":\"address\",\"type\":[\"string\",\"null\"],\"default\":\"null\"},{\"name\":\"city\",\"type\":[\"string\",\"null\"],\"default\":\"null\"},{\"name\":\"zip\",\"type\":[\"string\",\"null\"],\"default\":\"null\"},{\"name\":\"state\",\"type\":[\"string\",\"null\"],\"default\":\"null\"},{\"name\":\"phone_type\",\"type\":{\"type\":\"enum\",\"name\":\"PhoneType\",\"symbols\":[\"TRACKED\",\"NONE\"],\"default\":\"NONE\"},\"default\":\"NONE\"}]}");
  public static org.apache.avro.Schema getClassSchema() { return SCHEMA$; }
  @Deprecated public com.refactorlabs.cs378.ImpressionType impression_type;
  @Deprecated public com.refactorlabs.cs378.Action action;
  @Deprecated public com.refactorlabs.cs378.ActionName action_name;
  @Deprecated public java.util.List<java.lang.Long> id;
  @Deprecated public long timestamp;
  @Deprecated public java.lang.CharSequence ab;
  @Deprecated public com.refactorlabs.cs378.Vertical vertical;
  @Deprecated public int start_index;
  @Deprecated public int total;
  @Deprecated public java.lang.CharSequence domain;
  @Deprecated public double lat;
  @Deprecated public double lon;
  @Deprecated public java.lang.CharSequence address;
  @Deprecated public java.lang.CharSequence city;
  @Deprecated public java.lang.CharSequence zip;
  @Deprecated public java.lang.CharSequence state;
  @Deprecated public com.refactorlabs.cs378.PhoneType phone_type;

  /**
   * Default constructor.
   */
  public Impression() {}

  /**
   * All-args constructor.
   */
  public Impression(com.refactorlabs.cs378.ImpressionType impression_type, com.refactorlabs.cs378.Action action, com.refactorlabs.cs378.ActionName action_name, java.util.List<java.lang.Long> id, java.lang.Long timestamp, java.lang.CharSequence ab, com.refactorlabs.cs378.Vertical vertical, java.lang.Integer start_index, java.lang.Integer total, java.lang.CharSequence domain, java.lang.Double lat, java.lang.Double lon, java.lang.CharSequence address, java.lang.CharSequence city, java.lang.CharSequence zip, java.lang.CharSequence state, com.refactorlabs.cs378.PhoneType phone_type) {
    this.impression_type = impression_type;
    this.action = action;
    this.action_name = action_name;
    this.id = id;
    this.timestamp = timestamp;
    this.ab = ab;
    this.vertical = vertical;
    this.start_index = start_index;
    this.total = total;
    this.domain = domain;
    this.lat = lat;
    this.lon = lon;
    this.address = address;
    this.city = city;
    this.zip = zip;
    this.state = state;
    this.phone_type = phone_type;
  }

  public org.apache.avro.Schema getSchema() { return SCHEMA$; }
  // Used by DatumWriter.  Applications should not call. 
  public java.lang.Object get(int field$) {
    switch (field$) {
    case 0: return impression_type;
    case 1: return action;
    case 2: return action_name;
    case 3: return id;
    case 4: return timestamp;
    case 5: return ab;
    case 6: return vertical;
    case 7: return start_index;
    case 8: return total;
    case 9: return domain;
    case 10: return lat;
    case 11: return lon;
    case 12: return address;
    case 13: return city;
    case 14: return zip;
    case 15: return state;
    case 16: return phone_type;
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
    case 3: id = (java.util.List<java.lang.Long>)value$; break;
    case 4: timestamp = (java.lang.Long)value$; break;
    case 5: ab = (java.lang.CharSequence)value$; break;
    case 6: vertical = (com.refactorlabs.cs378.Vertical)value$; break;
    case 7: start_index = (java.lang.Integer)value$; break;
    case 8: total = (java.lang.Integer)value$; break;
    case 9: domain = (java.lang.CharSequence)value$; break;
    case 10: lat = (java.lang.Double)value$; break;
    case 11: lon = (java.lang.Double)value$; break;
    case 12: address = (java.lang.CharSequence)value$; break;
    case 13: city = (java.lang.CharSequence)value$; break;
    case 14: zip = (java.lang.CharSequence)value$; break;
    case 15: state = (java.lang.CharSequence)value$; break;
    case 16: phone_type = (com.refactorlabs.cs378.PhoneType)value$; break;
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
   * Gets the value of the 'start_index' field.
   */
  public java.lang.Integer getStartIndex() {
    return start_index;
  }

  /**
   * Sets the value of the 'start_index' field.
   * @param value the value to set.
   */
  public void setStartIndex(java.lang.Integer value) {
    this.start_index = value;
  }

  /**
   * Gets the value of the 'total' field.
   */
  public java.lang.Integer getTotal() {
    return total;
  }

  /**
   * Sets the value of the 'total' field.
   * @param value the value to set.
   */
  public void setTotal(java.lang.Integer value) {
    this.total = value;
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
    private java.util.List<java.lang.Long> id;
    private long timestamp;
    private java.lang.CharSequence ab;
    private com.refactorlabs.cs378.Vertical vertical;
    private int start_index;
    private int total;
    private java.lang.CharSequence domain;
    private double lat;
    private double lon;
    private java.lang.CharSequence address;
    private java.lang.CharSequence city;
    private java.lang.CharSequence zip;
    private java.lang.CharSequence state;
    private com.refactorlabs.cs378.PhoneType phone_type;

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
      if (isValidValue(fields()[3], other.id)) {
        this.id = data().deepCopy(fields()[3].schema(), other.id);
        fieldSetFlags()[3] = true;
      }
      if (isValidValue(fields()[4], other.timestamp)) {
        this.timestamp = data().deepCopy(fields()[4].schema(), other.timestamp);
        fieldSetFlags()[4] = true;
      }
      if (isValidValue(fields()[5], other.ab)) {
        this.ab = data().deepCopy(fields()[5].schema(), other.ab);
        fieldSetFlags()[5] = true;
      }
      if (isValidValue(fields()[6], other.vertical)) {
        this.vertical = data().deepCopy(fields()[6].schema(), other.vertical);
        fieldSetFlags()[6] = true;
      }
      if (isValidValue(fields()[7], other.start_index)) {
        this.start_index = data().deepCopy(fields()[7].schema(), other.start_index);
        fieldSetFlags()[7] = true;
      }
      if (isValidValue(fields()[8], other.total)) {
        this.total = data().deepCopy(fields()[8].schema(), other.total);
        fieldSetFlags()[8] = true;
      }
      if (isValidValue(fields()[9], other.domain)) {
        this.domain = data().deepCopy(fields()[9].schema(), other.domain);
        fieldSetFlags()[9] = true;
      }
      if (isValidValue(fields()[10], other.lat)) {
        this.lat = data().deepCopy(fields()[10].schema(), other.lat);
        fieldSetFlags()[10] = true;
      }
      if (isValidValue(fields()[11], other.lon)) {
        this.lon = data().deepCopy(fields()[11].schema(), other.lon);
        fieldSetFlags()[11] = true;
      }
      if (isValidValue(fields()[12], other.address)) {
        this.address = data().deepCopy(fields()[12].schema(), other.address);
        fieldSetFlags()[12] = true;
      }
      if (isValidValue(fields()[13], other.city)) {
        this.city = data().deepCopy(fields()[13].schema(), other.city);
        fieldSetFlags()[13] = true;
      }
      if (isValidValue(fields()[14], other.zip)) {
        this.zip = data().deepCopy(fields()[14].schema(), other.zip);
        fieldSetFlags()[14] = true;
      }
      if (isValidValue(fields()[15], other.state)) {
        this.state = data().deepCopy(fields()[15].schema(), other.state);
        fieldSetFlags()[15] = true;
      }
      if (isValidValue(fields()[16], other.phone_type)) {
        this.phone_type = data().deepCopy(fields()[16].schema(), other.phone_type);
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

    /** Gets the value of the 'id' field */
    public java.util.List<java.lang.Long> getId() {
      return id;
    }
    
    /** Sets the value of the 'id' field */
    public com.refactorlabs.cs378.Impression.Builder setId(java.util.List<java.lang.Long> value) {
      validate(fields()[3], value);
      this.id = value;
      fieldSetFlags()[3] = true;
      return this; 
    }
    
    /** Checks whether the 'id' field has been set */
    public boolean hasId() {
      return fieldSetFlags()[3];
    }
    
    /** Clears the value of the 'id' field */
    public com.refactorlabs.cs378.Impression.Builder clearId() {
      id = null;
      fieldSetFlags()[3] = false;
      return this;
    }

    /** Gets the value of the 'timestamp' field */
    public java.lang.Long getTimestamp() {
      return timestamp;
    }
    
    /** Sets the value of the 'timestamp' field */
    public com.refactorlabs.cs378.Impression.Builder setTimestamp(long value) {
      validate(fields()[4], value);
      this.timestamp = value;
      fieldSetFlags()[4] = true;
      return this; 
    }
    
    /** Checks whether the 'timestamp' field has been set */
    public boolean hasTimestamp() {
      return fieldSetFlags()[4];
    }
    
    /** Clears the value of the 'timestamp' field */
    public com.refactorlabs.cs378.Impression.Builder clearTimestamp() {
      fieldSetFlags()[4] = false;
      return this;
    }

    /** Gets the value of the 'ab' field */
    public java.lang.CharSequence getAb() {
      return ab;
    }
    
    /** Sets the value of the 'ab' field */
    public com.refactorlabs.cs378.Impression.Builder setAb(java.lang.CharSequence value) {
      validate(fields()[5], value);
      this.ab = value;
      fieldSetFlags()[5] = true;
      return this; 
    }
    
    /** Checks whether the 'ab' field has been set */
    public boolean hasAb() {
      return fieldSetFlags()[5];
    }
    
    /** Clears the value of the 'ab' field */
    public com.refactorlabs.cs378.Impression.Builder clearAb() {
      ab = null;
      fieldSetFlags()[5] = false;
      return this;
    }

    /** Gets the value of the 'vertical' field */
    public com.refactorlabs.cs378.Vertical getVertical() {
      return vertical;
    }
    
    /** Sets the value of the 'vertical' field */
    public com.refactorlabs.cs378.Impression.Builder setVertical(com.refactorlabs.cs378.Vertical value) {
      validate(fields()[6], value);
      this.vertical = value;
      fieldSetFlags()[6] = true;
      return this; 
    }
    
    /** Checks whether the 'vertical' field has been set */
    public boolean hasVertical() {
      return fieldSetFlags()[6];
    }
    
    /** Clears the value of the 'vertical' field */
    public com.refactorlabs.cs378.Impression.Builder clearVertical() {
      vertical = null;
      fieldSetFlags()[6] = false;
      return this;
    }

    /** Gets the value of the 'start_index' field */
    public java.lang.Integer getStartIndex() {
      return start_index;
    }
    
    /** Sets the value of the 'start_index' field */
    public com.refactorlabs.cs378.Impression.Builder setStartIndex(int value) {
      validate(fields()[7], value);
      this.start_index = value;
      fieldSetFlags()[7] = true;
      return this; 
    }
    
    /** Checks whether the 'start_index' field has been set */
    public boolean hasStartIndex() {
      return fieldSetFlags()[7];
    }
    
    /** Clears the value of the 'start_index' field */
    public com.refactorlabs.cs378.Impression.Builder clearStartIndex() {
      fieldSetFlags()[7] = false;
      return this;
    }

    /** Gets the value of the 'total' field */
    public java.lang.Integer getTotal() {
      return total;
    }
    
    /** Sets the value of the 'total' field */
    public com.refactorlabs.cs378.Impression.Builder setTotal(int value) {
      validate(fields()[8], value);
      this.total = value;
      fieldSetFlags()[8] = true;
      return this; 
    }
    
    /** Checks whether the 'total' field has been set */
    public boolean hasTotal() {
      return fieldSetFlags()[8];
    }
    
    /** Clears the value of the 'total' field */
    public com.refactorlabs.cs378.Impression.Builder clearTotal() {
      fieldSetFlags()[8] = false;
      return this;
    }

    /** Gets the value of the 'domain' field */
    public java.lang.CharSequence getDomain() {
      return domain;
    }
    
    /** Sets the value of the 'domain' field */
    public com.refactorlabs.cs378.Impression.Builder setDomain(java.lang.CharSequence value) {
      validate(fields()[9], value);
      this.domain = value;
      fieldSetFlags()[9] = true;
      return this; 
    }
    
    /** Checks whether the 'domain' field has been set */
    public boolean hasDomain() {
      return fieldSetFlags()[9];
    }
    
    /** Clears the value of the 'domain' field */
    public com.refactorlabs.cs378.Impression.Builder clearDomain() {
      domain = null;
      fieldSetFlags()[9] = false;
      return this;
    }

    /** Gets the value of the 'lat' field */
    public java.lang.Double getLat() {
      return lat;
    }
    
    /** Sets the value of the 'lat' field */
    public com.refactorlabs.cs378.Impression.Builder setLat(double value) {
      validate(fields()[10], value);
      this.lat = value;
      fieldSetFlags()[10] = true;
      return this; 
    }
    
    /** Checks whether the 'lat' field has been set */
    public boolean hasLat() {
      return fieldSetFlags()[10];
    }
    
    /** Clears the value of the 'lat' field */
    public com.refactorlabs.cs378.Impression.Builder clearLat() {
      fieldSetFlags()[10] = false;
      return this;
    }

    /** Gets the value of the 'lon' field */
    public java.lang.Double getLon() {
      return lon;
    }
    
    /** Sets the value of the 'lon' field */
    public com.refactorlabs.cs378.Impression.Builder setLon(double value) {
      validate(fields()[11], value);
      this.lon = value;
      fieldSetFlags()[11] = true;
      return this; 
    }
    
    /** Checks whether the 'lon' field has been set */
    public boolean hasLon() {
      return fieldSetFlags()[11];
    }
    
    /** Clears the value of the 'lon' field */
    public com.refactorlabs.cs378.Impression.Builder clearLon() {
      fieldSetFlags()[11] = false;
      return this;
    }

    /** Gets the value of the 'address' field */
    public java.lang.CharSequence getAddress() {
      return address;
    }
    
    /** Sets the value of the 'address' field */
    public com.refactorlabs.cs378.Impression.Builder setAddress(java.lang.CharSequence value) {
      validate(fields()[12], value);
      this.address = value;
      fieldSetFlags()[12] = true;
      return this; 
    }
    
    /** Checks whether the 'address' field has been set */
    public boolean hasAddress() {
      return fieldSetFlags()[12];
    }
    
    /** Clears the value of the 'address' field */
    public com.refactorlabs.cs378.Impression.Builder clearAddress() {
      address = null;
      fieldSetFlags()[12] = false;
      return this;
    }

    /** Gets the value of the 'city' field */
    public java.lang.CharSequence getCity() {
      return city;
    }
    
    /** Sets the value of the 'city' field */
    public com.refactorlabs.cs378.Impression.Builder setCity(java.lang.CharSequence value) {
      validate(fields()[13], value);
      this.city = value;
      fieldSetFlags()[13] = true;
      return this; 
    }
    
    /** Checks whether the 'city' field has been set */
    public boolean hasCity() {
      return fieldSetFlags()[13];
    }
    
    /** Clears the value of the 'city' field */
    public com.refactorlabs.cs378.Impression.Builder clearCity() {
      city = null;
      fieldSetFlags()[13] = false;
      return this;
    }

    /** Gets the value of the 'zip' field */
    public java.lang.CharSequence getZip() {
      return zip;
    }
    
    /** Sets the value of the 'zip' field */
    public com.refactorlabs.cs378.Impression.Builder setZip(java.lang.CharSequence value) {
      validate(fields()[14], value);
      this.zip = value;
      fieldSetFlags()[14] = true;
      return this; 
    }
    
    /** Checks whether the 'zip' field has been set */
    public boolean hasZip() {
      return fieldSetFlags()[14];
    }
    
    /** Clears the value of the 'zip' field */
    public com.refactorlabs.cs378.Impression.Builder clearZip() {
      zip = null;
      fieldSetFlags()[14] = false;
      return this;
    }

    /** Gets the value of the 'state' field */
    public java.lang.CharSequence getState() {
      return state;
    }
    
    /** Sets the value of the 'state' field */
    public com.refactorlabs.cs378.Impression.Builder setState(java.lang.CharSequence value) {
      validate(fields()[15], value);
      this.state = value;
      fieldSetFlags()[15] = true;
      return this; 
    }
    
    /** Checks whether the 'state' field has been set */
    public boolean hasState() {
      return fieldSetFlags()[15];
    }
    
    /** Clears the value of the 'state' field */
    public com.refactorlabs.cs378.Impression.Builder clearState() {
      state = null;
      fieldSetFlags()[15] = false;
      return this;
    }

    /** Gets the value of the 'phone_type' field */
    public com.refactorlabs.cs378.PhoneType getPhoneType() {
      return phone_type;
    }
    
    /** Sets the value of the 'phone_type' field */
    public com.refactorlabs.cs378.Impression.Builder setPhoneType(com.refactorlabs.cs378.PhoneType value) {
      validate(fields()[16], value);
      this.phone_type = value;
      fieldSetFlags()[16] = true;
      return this; 
    }
    
    /** Checks whether the 'phone_type' field has been set */
    public boolean hasPhoneType() {
      return fieldSetFlags()[16];
    }
    
    /** Clears the value of the 'phone_type' field */
    public com.refactorlabs.cs378.Impression.Builder clearPhoneType() {
      phone_type = null;
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
        record.id = fieldSetFlags()[3] ? this.id : (java.util.List<java.lang.Long>) defaultValue(fields()[3]);
        record.timestamp = fieldSetFlags()[4] ? this.timestamp : (java.lang.Long) defaultValue(fields()[4]);
        record.ab = fieldSetFlags()[5] ? this.ab : (java.lang.CharSequence) defaultValue(fields()[5]);
        record.vertical = fieldSetFlags()[6] ? this.vertical : (com.refactorlabs.cs378.Vertical) defaultValue(fields()[6]);
        record.start_index = fieldSetFlags()[7] ? this.start_index : (java.lang.Integer) defaultValue(fields()[7]);
        record.total = fieldSetFlags()[8] ? this.total : (java.lang.Integer) defaultValue(fields()[8]);
        record.domain = fieldSetFlags()[9] ? this.domain : (java.lang.CharSequence) defaultValue(fields()[9]);
        record.lat = fieldSetFlags()[10] ? this.lat : (java.lang.Double) defaultValue(fields()[10]);
        record.lon = fieldSetFlags()[11] ? this.lon : (java.lang.Double) defaultValue(fields()[11]);
        record.address = fieldSetFlags()[12] ? this.address : (java.lang.CharSequence) defaultValue(fields()[12]);
        record.city = fieldSetFlags()[13] ? this.city : (java.lang.CharSequence) defaultValue(fields()[13]);
        record.zip = fieldSetFlags()[14] ? this.zip : (java.lang.CharSequence) defaultValue(fields()[14]);
        record.state = fieldSetFlags()[15] ? this.state : (java.lang.CharSequence) defaultValue(fields()[15]);
        record.phone_type = fieldSetFlags()[16] ? this.phone_type : (com.refactorlabs.cs378.PhoneType) defaultValue(fields()[16]);
        return record;
      } catch (Exception e) {
        throw new org.apache.avro.AvroRuntimeException(e);
      }
    }
  }
}
