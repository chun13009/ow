package net.vtst.eclipse.easy.ui.properties.fields;

import net.vtst.eclipse.easy.ui.properties.editors.AbstractFieldEditor;
import net.vtst.eclipse.easy.ui.properties.editors.IEditorContainer;
import net.vtst.eclipse.easy.ui.properties.stores.IReadOnlyStore;
import net.vtst.eclipse.easy.ui.properties.stores.IStore;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.swt.widgets.Composite;

/**
 * Base class for fields.
 * @author Vincent Simonet
 *
 * @param <T>  The type of the field values.
 */
public abstract class AbstractField<T> implements IField<T> {
  
  private AbstractFieldEditor<T> editor;
  protected String name = "undefined";
  protected T defaultValue;
  
  /**
   * Create a new field with a given default value.
   * @param defaultValue
   */
  public AbstractField(T defaultValue) {
    this.defaultValue = defaultValue;
  }
  
  public void bind(String name) {
    this.name = name;
  }
  
  public String getName() {
    return this.name;
  }
  
  public abstract T get(IReadOnlyStore store) throws CoreException;
  public abstract void set(IStore store, T value) throws CoreException;
  
  public T getDefault() {
    return defaultValue;
  }
  
  @Override
  public boolean valueEqual(T value1, T value2) {
    if (value1 == null) return (value2 == null);
    else return value1.equals(value2);
  }
  
  /**
   * Create an editor for the current field.
   * @param container  The container for the newly created editor.
   * @return  The newly created editor.
   */
  abstract public AbstractFieldEditor<T> createEditor(IEditorContainer container, Composite parent);
  
  /**
   * Create an editor for the current field, into the container.
   * @param container
   */
  public void bindEditor(IEditorContainer container) {
    bindEditor(container, container.getComposite());
  }
  
  /**
   * @param container  The containing editor.
   * @param parent  The containing composite.
   */
  public void bindEditor(IEditorContainer container, Composite parent) {
    editor = createEditor(container, parent);
  }

  
  /**
   * @return  The last created editor for the current field.
   */
  public AbstractFieldEditor<T> editor() { return editor; }
}
