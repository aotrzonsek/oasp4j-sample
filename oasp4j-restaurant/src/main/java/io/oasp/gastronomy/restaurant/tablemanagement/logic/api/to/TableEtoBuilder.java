// CHECKSTYLE:OFF
/**
 * Source code generated by Fluent Builders Generator
 * Do not modify this file
 * See generator home page at: http://code.google.com/p/fluent-builders-generator-eclipse-plugin/
 */

package io.oasp.gastronomy.restaurant.tablemanagement.logic.api.to;

import io.oasp.gastronomy.restaurant.tablemanagement.common.api.datatype.TableState;

@SuppressWarnings("javadoc")
public class TableEtoBuilder extends TableEtoBuilderBase<TableEtoBuilder> {
  public static TableEtoBuilder tableEto() {

    return new TableEtoBuilder();
  }

  public TableEtoBuilder() {

    super(new TableEto());
  }

  public TableEto build() {

    return getInstance();
  }
}

class TableEtoBuilderBase<GeneratorT extends TableEtoBuilderBase<GeneratorT>> {
  private TableEto instance;

  protected TableEtoBuilderBase(TableEto aInstance) {

    this.instance = aInstance;
  }

  protected TableEto getInstance() {

    return this.instance;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withNumber(Long aValue) {

    this.instance.setNumber(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withWaiterId(Long aValue) {

    this.instance.setWaiterId(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withState(TableState aValue) {

    this.instance.setState(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withId(Long aValue) {

    this.instance.setId(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withModificationCounter(int aValue) {

    this.instance.setModificationCounter(aValue);

    return (GeneratorT) this;
  }

  @SuppressWarnings("unchecked")
  public GeneratorT withRevision(Number aValue) {

    this.instance.setRevision(aValue);

    return (GeneratorT) this;
  }
}
